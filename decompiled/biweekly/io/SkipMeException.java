package biweekly.io;

import biweekly.Warning;

public class SkipMeException extends RuntimeException {
    private final Warning warning;

    public SkipMeException(int code, Object... args) {
        this(Warning.parse(code, args));
    }

    public SkipMeException(String reason) {
        this(new Warning(reason));
    }

    private SkipMeException(Warning warning) {
        super(warning.toString());
        this.warning = warning;
    }

    public Warning getWarning() {
        return this.warning;
    }
}
