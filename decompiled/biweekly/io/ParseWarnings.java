package biweekly.io;

import biweekly.Messages;
import biweekly.Warning;
import java.util.ArrayList;
import java.util.List;

public class ParseWarnings {
    private final List<String> warnings = new ArrayList();

    public void add(Integer line, String propertyName, int code, Object... args) {
        add(line, propertyName, Warning.parse(code, args));
    }

    public void add(Integer line, String propertyName, Warning message) {
        if (line == null && propertyName == null) {
            this.warnings.add(message.toString());
            return;
        }
        String key;
        if (line == null && propertyName != null) {
            key = "parse.prop";
        } else if (line == null || propertyName != null) {
            key = "parse.lineWithProp";
        } else {
            key = "parse.line";
        }
        this.warnings.add(Messages.INSTANCE.getMessage(key, line, propertyName, message));
    }

    public List<String> copy() {
        return new ArrayList(this.warnings);
    }

    public void clear() {
        this.warnings.clear();
    }
}
