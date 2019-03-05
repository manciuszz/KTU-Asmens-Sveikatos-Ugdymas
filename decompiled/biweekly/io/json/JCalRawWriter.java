package biweekly.io.json;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.parameter.ICalParameters;
import biweekly.util.StringUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JCalRawWriter implements Closeable, Flushable {
    private boolean componentEnded = false;
    private JsonGenerator generator;
    private boolean indent = false;
    private final LinkedList<Info> stack = new LinkedList();
    private final boolean wrapInArray;
    private final Writer writer;

    private static class Info {
        public boolean wroteEndPropertiesArray;
        public boolean wroteStartSubComponentsArray;

        private Info() {
            this.wroteEndPropertiesArray = false;
            this.wroteStartSubComponentsArray = false;
        }
    }

    public JCalRawWriter(Writer writer, boolean wrapInArray) {
        this.writer = writer;
        this.wrapInArray = wrapInArray;
    }

    public boolean isIndent() {
        return this.indent;
    }

    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    public void writeStartComponent(String componentName) throws IOException {
        if (this.generator == null) {
            init();
        }
        this.componentEnded = false;
        if (!this.stack.isEmpty()) {
            Info parent = (Info) this.stack.getLast();
            if (!parent.wroteEndPropertiesArray) {
                this.generator.writeEndArray();
                parent.wroteEndPropertiesArray = true;
            }
            if (!parent.wroteStartSubComponentsArray) {
                this.generator.writeStartArray();
                parent.wroteStartSubComponentsArray = true;
            }
        }
        this.generator.writeStartArray();
        indent(this.stack.size() * 2);
        this.generator.writeString(componentName);
        this.generator.writeStartArray();
        this.stack.add(new Info());
    }

    public void writeEndComponent() throws IOException {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("Call \"writeStartComponent\" first.");
        }
        Info cur = (Info) this.stack.removeLast();
        if (!cur.wroteEndPropertiesArray) {
            this.generator.writeEndArray();
        }
        if (!cur.wroteStartSubComponentsArray) {
            this.generator.writeStartArray();
        }
        this.generator.writeEndArray();
        this.generator.writeEndArray();
        this.componentEnded = true;
    }

    public void writeProperty(String propertyName, ICalDataType dataType, JCalValue value) throws IOException {
        writeProperty(propertyName, new ICalParameters(), dataType, value);
    }

    public void writeProperty(String propertyName, ICalParameters parameters, ICalDataType dataType, JCalValue value) throws IOException {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("Call \"writeStartComponent\" first.");
        } else if (this.componentEnded) {
            throw new IllegalStateException("Cannot write a property after calling \"writeEndComponent\".");
        } else {
            this.generator.writeStartArray();
            indent(this.stack.size() * 2);
            this.generator.writeString(propertyName);
            this.generator.writeStartObject();
            Iterator it = parameters.iterator();
            while (it.hasNext()) {
                Entry<String, List<String>> entry = (Entry) it.next();
                String name = ((String) entry.getKey()).toLowerCase();
                List<String> values = (List) entry.getValue();
                if (!values.isEmpty()) {
                    if (values.size() == 1) {
                        this.generator.writeStringField(name, (String) values.get(0));
                    } else {
                        this.generator.writeArrayFieldStart(name);
                        for (String paramValue : values) {
                            this.generator.writeString(paramValue);
                        }
                        this.generator.writeEndArray();
                    }
                }
            }
            this.generator.writeEndObject();
            this.generator.writeString(dataType == null ? EnvironmentCompat.MEDIA_UNKNOWN : dataType.getName().toLowerCase());
            for (JsonValue jsonValue : value.getValues()) {
                writeValue(jsonValue);
            }
            this.generator.writeEndArray();
        }
    }

    private void writeValue(JsonValue jsonValue) throws IOException {
        if (jsonValue.isNull()) {
            this.generator.writeNull();
            return;
        }
        Object val = jsonValue.getValue();
        if (val == null) {
            List<JsonValue> array = jsonValue.getArray();
            if (array != null) {
                this.generator.writeStartArray();
                for (JsonValue element : array) {
                    writeValue(element);
                }
                this.generator.writeEndArray();
                return;
            }
            Map<String, JsonValue> object = jsonValue.getObject();
            if (object != null) {
                this.generator.writeStartObject();
                for (Entry<String, JsonValue> entry : object.entrySet()) {
                    this.generator.writeFieldName((String) entry.getKey());
                    writeValue((JsonValue) entry.getValue());
                }
                this.generator.writeEndObject();
            }
        } else if (val instanceof Byte) {
            this.generator.writeNumber((short) ((Byte) val).byteValue());
        } else if (val instanceof Short) {
            this.generator.writeNumber(((Short) val).shortValue());
        } else if (val instanceof Integer) {
            this.generator.writeNumber(((Integer) val).intValue());
        } else if (val instanceof Long) {
            this.generator.writeNumber(((Long) val).longValue());
        } else if (val instanceof Float) {
            this.generator.writeNumber(((Float) val).floatValue());
        } else if (val instanceof Double) {
            this.generator.writeNumber(((Double) val).doubleValue());
        } else if (val instanceof Boolean) {
            this.generator.writeBoolean(((Boolean) val).booleanValue());
        } else {
            this.generator.writeString(val.toString());
        }
    }

    private void indent(int spaces) throws IOException {
        if (this.indent) {
            this.generator.writeRaw(StringUtils.NEWLINE);
            for (int i = 0; i < spaces; i++) {
                this.generator.writeRaw(' ');
            }
        }
    }

    public void flush() throws IOException {
        if (this.generator != null) {
            this.generator.flush();
        }
    }

    public void closeJsonStream() throws IOException {
        if (this.generator != null) {
            while (!this.stack.isEmpty()) {
                writeEndComponent();
            }
            if (this.wrapInArray) {
                indent(0);
                this.generator.writeEndArray();
            }
            this.generator.close();
        }
    }

    public void close() throws IOException {
        if (this.generator != null) {
            closeJsonStream();
            this.writer.close();
        }
    }

    private void init() throws IOException {
        JsonFactory factory = new JsonFactory();
        factory.configure(Feature.AUTO_CLOSE_TARGET, false);
        this.generator = factory.createGenerator(this.writer);
        if (this.wrapInArray) {
            this.generator.writeStartArray();
            indent(0);
        }
    }
}
