package biweekly.io;

import biweekly.Warning;

public class CannotParseException extends RuntimeException {
    private final Warning warning;

    public CannotParseException(int code, Object... args) {
        this(Warning.parse(code, args));
    }

    public CannotParseException(String reason) {
        this(new Warning(reason));
    }

    private CannotParseException(Warning warning) {
        super(warning.toString());
        this.warning = warning;
    }

    public Warning getWarning() {
        return this.warning;
    }
}
