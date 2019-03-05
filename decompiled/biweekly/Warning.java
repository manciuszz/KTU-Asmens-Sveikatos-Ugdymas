package biweekly;

public class Warning {
    private final Integer code;
    private final String message;

    public static Warning parse(int code, Object... args) {
        return new Warning(Messages.INSTANCE.getParseMessage(code, args), Integer.valueOf(code));
    }

    public static Warning validate(int code, Object... args) {
        return new Warning(Messages.INSTANCE.getValidationWarning(code, args), Integer.valueOf(code));
    }

    public Warning(String message) {
        this(message, null);
    }

    public Warning(String message, Integer code) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        if (this.code == null) {
            return this.message;
        }
        return "(" + this.code + ") " + this.message;
    }
}
