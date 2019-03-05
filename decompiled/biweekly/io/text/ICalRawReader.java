package biweekly.io.text;

import biweekly.parameter.ICalParameters;
import biweekly.util.StringUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class ICalRawReader implements Closeable {
    private boolean caretDecodingEnabled = true;
    private final FoldedLineReader reader;

    public ICalRawReader(Reader reader) {
        this.reader = new FoldedLineReader(reader);
    }

    public int getLineNum() {
        return this.reader.getLineNum();
    }

    public ICalRawLine readLine() throws IOException {
        String line = this.reader.readLine();
        if (line == null) {
            return null;
        }
        String propertyName = null;
        ICalParameters parameters = new ICalParameters();
        String value = null;
        char escapeChar = '\u0000';
        boolean inQuotes = false;
        StringBuilder buffer = new StringBuilder();
        String curParamName = null;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (escapeChar != '\u0000') {
                if (escapeChar == '\\') {
                    if (ch == '\\') {
                        buffer.append(ch);
                    } else if (ch == 'n' || ch == 'N') {
                        buffer.append(StringUtils.NEWLINE);
                    } else if (ch == '\"') {
                        buffer.append(ch);
                    } else {
                        buffer.append(escapeChar).append(ch);
                    }
                } else if (escapeChar == '^') {
                    if (ch == '^') {
                        buffer.append(ch);
                    } else if (ch == 'n') {
                        buffer.append(StringUtils.NEWLINE);
                    } else if (ch == '\'') {
                        buffer.append('\"');
                    } else {
                        buffer.append(escapeChar).append(ch);
                    }
                }
                escapeChar = '\u0000';
            } else if (ch == '\\' || (ch == '^' && this.caretDecodingEnabled)) {
                escapeChar = ch;
            } else if ((ch == ';' || ch == ':') && !inQuotes) {
                if (propertyName == null) {
                    propertyName = buffer.toString();
                } else if (curParamName == null) {
                    parameters.put(buffer.toString(), null);
                } else {
                    parameters.put(curParamName, buffer.toString());
                    curParamName = null;
                }
                buffer.setLength(0);
                if (ch == ':') {
                    if (i < line.length() - 1) {
                        value = line.substring(i + 1);
                    } else {
                        value = "";
                    }
                    if (propertyName == null && value != null) {
                        return new ICalRawLine(propertyName, parameters, value);
                    }
                    throw new ICalParseException(line);
                }
            } else if (ch == ',' && !inQuotes) {
                parameters.put(curParamName, buffer.toString());
                buffer.setLength(0);
            } else if (ch == '=' && curParamName == null) {
                curParamName = buffer.toString();
                buffer.setLength(0);
            } else if (ch == '\"') {
                inQuotes = !inQuotes;
            } else {
                buffer.append(ch);
            }
        }
        if (propertyName == null) {
        }
        throw new ICalParseException(line);
    }

    public boolean isCaretDecodingEnabled() {
        return this.caretDecodingEnabled;
    }

    public void setCaretDecodingEnabled(boolean enable) {
        this.caretDecodingEnabled = enable;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
