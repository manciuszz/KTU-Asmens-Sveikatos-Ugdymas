package biweekly.io.text;

import android.support.v4.media.TransportMediator;
import biweekly.parameter.ICalParameters;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class ICalRawWriter implements Closeable, Flushable {
    private static final BitSet invalidParamValueChars = new BitSet(128);
    private static final Pattern newlineRegex = Pattern.compile("\\r\\n|\\r|\\n");
    private static final Pattern propertyNameRegex = Pattern.compile("(?i)[-a-z0-9]+");
    private static final Pattern quoteMeRegex = Pattern.compile(".*?[,:;].*");
    private boolean caretEncodingEnabled;
    private final FoldingScheme foldingScheme;
    private final String newline;
    private ParameterValueChangedListener parameterValueChangedListener;
    private final Writer writer;

    public interface ParameterValueChangedListener {
        void onParameterValueChanged(String str, String str2, String str3, String str4);
    }

    static {
        invalidParamValueChars.set(0, 31);
        invalidParamValueChars.set(TransportMediator.KEYCODE_MEDIA_PAUSE);
        invalidParamValueChars.set(9, false);
        invalidParamValueChars.set(10, false);
        invalidParamValueChars.set(13, false);
    }

    public ICalRawWriter(Writer writer) {
        this(writer, FoldingScheme.DEFAULT);
    }

    public ICalRawWriter(Writer writer, FoldingScheme foldingScheme) {
        this(writer, foldingScheme, "\r\n");
    }

    public ICalRawWriter(Writer writer, FoldingScheme foldingScheme, String newline) {
        this.caretEncodingEnabled = false;
        if (foldingScheme == null) {
            this.writer = writer;
        } else {
            this.writer = new FoldedLineWriter(writer, foldingScheme.getLineLength(), foldingScheme.getIndent(), newline);
        }
        this.foldingScheme = foldingScheme;
        this.newline = newline;
    }

    public boolean isCaretEncodingEnabled() {
        return this.caretEncodingEnabled;
    }

    public void setCaretEncodingEnabled(boolean enable) {
        this.caretEncodingEnabled = enable;
    }

    public String getNewline() {
        return this.newline;
    }

    public ParameterValueChangedListener getParameterValueChangedListener() {
        return this.parameterValueChangedListener;
    }

    public void setParameterValueChangedListener(ParameterValueChangedListener parameterValueChangedListener) {
        this.parameterValueChangedListener = parameterValueChangedListener;
    }

    public FoldingScheme getFoldingScheme() {
        return this.foldingScheme;
    }

    public void writeBeginComponent(String componentName) throws IOException {
        writeProperty("BEGIN", componentName);
    }

    public void writeEndComponent(String componentName) throws IOException {
        writeProperty("END", componentName);
    }

    public void writeProperty(String propertyName, String value) throws IOException {
        writeProperty(propertyName, new ICalParameters(), value);
    }

    public void writeProperty(String propertyName, ICalParameters parameters, String value) throws IOException {
        if (propertyNameRegex.matcher(propertyName).matches()) {
            this.writer.append(propertyName);
            Iterator it = parameters.iterator();
            while (it.hasNext()) {
                Entry<String, List<String>> subType = (Entry) it.next();
                String parameterName = (String) subType.getKey();
                List<String> parameterValues = (List) subType.getValue();
                if (!parameterValues.isEmpty()) {
                    boolean first = true;
                    this.writer.append(';').append(parameterName).append('=');
                    for (String parameterValue : parameterValues) {
                        if (!first) {
                            this.writer.append(',');
                        }
                        String parameterValue2 = sanitizeParameterValue(parameterValue2, parameterName, propertyName);
                        if (quoteMeRegex.matcher(parameterValue2).matches()) {
                            this.writer.append('\"');
                            this.writer.append(parameterValue2);
                            this.writer.append('\"');
                        } else {
                            this.writer.append(parameterValue2);
                        }
                        first = false;
                    }
                }
            }
            this.writer.append(':');
            if (value == null) {
                value = "";
            } else {
                value = escapeNewlines(value);
            }
            this.writer.append(value);
            this.writer.append(this.newline);
            return;
        }
        throw new IllegalArgumentException("Property name invalid.  Property names can only contain letters, numbers, and hyphens.");
    }

    private String sanitizeParameterValue(String parameterValue, String parameterName, String propertyName) {
        boolean valueChanged;
        String modifiedValue = removeInvalidParameterValueChars(parameterValue);
        if (this.caretEncodingEnabled) {
            valueChanged = modifiedValue != parameterValue;
            modifiedValue = applyCaretEncoding(modifiedValue);
        } else {
            modifiedValue = newlineRegex.matcher(modifiedValue.replace('\"', '\'')).replaceAll(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            valueChanged = modifiedValue != parameterValue;
        }
        if (valueChanged && this.parameterValueChangedListener != null) {
            this.parameterValueChangedListener.onParameterValueChanged(propertyName, parameterName, parameterValue, modifiedValue);
        }
        return modifiedValue;
    }

    private String removeInvalidParameterValueChars(String value) {
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (!invalidParamValueChars.get(ch)) {
                sb.append(ch);
            }
        }
        return sb.length() == value.length() ? value : sb.toString();
    }

    private String applyCaretEncoding(String value) {
        return newlineRegex.matcher(value.replace("^", "^^")).replaceAll("^n").replace("\"", "^'");
    }

    private String escapeNewlines(String text) {
        return newlineRegex.matcher(text).replaceAll("\\\\n");
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }
}
