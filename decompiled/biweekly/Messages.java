package biweekly;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum Messages {
    INSTANCE;
    
    private final ResourceBundle messages;

    public String getValidationWarning(int code, Object... args) {
        return getMessage("validate." + code, args);
    }

    public String getParseMessage(int code, Object... args) {
        return getMessage("parse." + code, args);
    }

    public String getMessage(String key, Object... args) {
        try {
            return MessageFormat.format(this.messages.getString(key), args);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
