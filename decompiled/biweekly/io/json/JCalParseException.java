package biweekly.io.json;

import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

public class JCalParseException extends IOException {
    private final JsonToken actual;
    private final JsonToken expected;

    public JCalParseException(JsonToken expected, JsonToken actual) {
        super("Expected " + expected + " but was " + actual + ".");
        this.expected = expected;
        this.actual = actual;
    }

    public JsonToken getExpectedToken() {
        return this.expected;
    }

    public JsonToken getActualToken() {
        return this.actual;
    }
}
