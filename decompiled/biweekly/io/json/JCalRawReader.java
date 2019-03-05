package biweekly.io.json;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.io.scribe.ScribeIndex;
import biweekly.parameter.ICalParameters;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JCalRawReader implements Closeable {
    private static final String vcalendarComponentName = ScribeIndex.getICalendarScribe().getComponentName().toLowerCase();
    private boolean eof = false;
    private JCalDataStreamListener listener;
    private JsonParser parser;
    private final Reader reader;

    public interface JCalDataStreamListener {
        void readComponent(List<String> list, String str);

        void readProperty(List<String> list, String str, ICalParameters iCalParameters, ICalDataType iCalDataType, JCalValue jCalValue);
    }

    public JCalRawReader(Reader reader) {
        this.reader = reader;
    }

    public int getLineNum() {
        return this.parser == null ? 0 : this.parser.getCurrentLocation().getLineNr();
    }

    public void readNext(JCalDataStreamListener listener) throws IOException {
        if (this.parser == null) {
            this.parser = new JsonFactory().createParser(this.reader);
        }
        if (!this.parser.isClosed()) {
            JsonToken cur;
            this.listener = listener;
            JsonToken prev = null;
            while (true) {
                cur = this.parser.nextToken();
                if (cur != null && (prev != JsonToken.START_ARRAY || cur != JsonToken.VALUE_STRING || !vcalendarComponentName.equals(this.parser.getValueAsString()))) {
                    prev = cur;
                } else if (cur != null) {
                    this.eof = true;
                } else {
                    parseComponent(new ArrayList());
                }
            }
            if (cur != null) {
                parseComponent(new ArrayList());
            } else {
                this.eof = true;
            }
        }
    }

    private void parseComponent(List<String> components) throws IOException {
        checkCurrent(JsonToken.VALUE_STRING);
        String componentName = this.parser.getValueAsString();
        this.listener.readComponent(components, componentName);
        components.add(componentName);
        checkNext(JsonToken.START_ARRAY);
        while (this.parser.nextToken() != JsonToken.END_ARRAY) {
            checkCurrent(JsonToken.START_ARRAY);
            this.parser.nextToken();
            parseProperty(components);
        }
        checkNext(JsonToken.START_ARRAY);
        while (this.parser.nextToken() != JsonToken.END_ARRAY) {
            checkCurrent(JsonToken.START_ARRAY);
            this.parser.nextToken();
            parseComponent(new ArrayList(components));
        }
        checkNext(JsonToken.END_ARRAY);
    }

    private void parseProperty(List<String> components) throws IOException {
        checkCurrent(JsonToken.VALUE_STRING);
        String propertyName = this.parser.getValueAsString().toLowerCase();
        ICalParameters parameters = parseParameters();
        checkNext(JsonToken.VALUE_STRING);
        String dataTypeStr = this.parser.getText();
        this.listener.readProperty(components, propertyName, parameters, EnvironmentCompat.MEDIA_UNKNOWN.equals(dataTypeStr) ? null : ICalDataType.get(dataTypeStr), new JCalValue(parseValues()));
    }

    private ICalParameters parseParameters() throws IOException {
        checkNext(JsonToken.START_OBJECT);
        ICalParameters parameters = new ICalParameters();
        while (this.parser.nextToken() != JsonToken.END_OBJECT) {
            String parameterName = this.parser.getText();
            if (this.parser.nextToken() == JsonToken.START_ARRAY) {
                while (this.parser.nextToken() != JsonToken.END_ARRAY) {
                    parameters.put(parameterName, this.parser.getText());
                }
            } else {
                parameters.put(parameterName, this.parser.getValueAsString());
            }
        }
        return parameters;
    }

    private List<JsonValue> parseValues() throws IOException {
        List<JsonValue> values = new ArrayList();
        while (this.parser.nextToken() != JsonToken.END_ARRAY) {
            values.add(parseValue());
        }
        return values;
    }

    private Object parseValueElement() throws IOException {
        switch (this.parser.getCurrentToken()) {
            case VALUE_FALSE:
            case VALUE_TRUE:
                return Boolean.valueOf(this.parser.getBooleanValue());
            case VALUE_NUMBER_FLOAT:
                return Double.valueOf(this.parser.getDoubleValue());
            case VALUE_NUMBER_INT:
                return Long.valueOf(this.parser.getLongValue());
            case VALUE_NULL:
                return null;
            default:
                return this.parser.getText();
        }
    }

    private List<JsonValue> parseValueArray() throws IOException {
        List<JsonValue> array = new ArrayList();
        while (this.parser.nextToken() != JsonToken.END_ARRAY) {
            array.add(parseValue());
        }
        return array;
    }

    private Map<String, JsonValue> parseValueObject() throws IOException {
        Map<String, JsonValue> object = new HashMap();
        this.parser.nextToken();
        while (this.parser.getCurrentToken() != JsonToken.END_OBJECT) {
            checkCurrent(JsonToken.FIELD_NAME);
            String key = this.parser.getText();
            this.parser.nextToken();
            object.put(key, parseValue());
            this.parser.nextToken();
        }
        return object;
    }

    private JsonValue parseValue() throws IOException {
        switch (this.parser.getCurrentToken()) {
            case START_ARRAY:
                return new JsonValue(parseValueArray());
            case START_OBJECT:
                return new JsonValue(parseValueObject());
            default:
                return new JsonValue(parseValueElement());
        }
    }

    private void checkNext(JsonToken expected) throws IOException {
        check(expected, this.parser.nextToken());
    }

    private void checkCurrent(JsonToken expected) throws JCalParseException {
        check(expected, this.parser.getCurrentToken());
    }

    private void check(JsonToken expected, JsonToken actual) throws JCalParseException {
        if (actual != expected) {
            throw new JCalParseException(expected, actual);
        }
    }

    public boolean eof() {
        return this.eof;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
