package biweekly.io.text;

import java.io.IOException;

public class ICalParseException extends IOException {
    private final String line;

    public ICalParseException(String line) {
        super("Problem parsing iCal line: " + line);
        this.line = line;
    }

    public String getLine() {
        return this.line;
    }
}
