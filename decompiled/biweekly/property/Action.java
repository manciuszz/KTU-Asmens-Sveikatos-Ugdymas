package biweekly.property;

import java.util.Arrays;
import java.util.Collection;

public class Action extends EnumProperty {
    private static final String AUDIO = "AUDIO";
    private static final String DISPLAY = "DISPLAY";
    private static final String EMAIL = "EMAIL";

    public Action(String value) {
        super(value);
    }

    public static Action audio() {
        return create(AUDIO);
    }

    public boolean isAudio() {
        return is(AUDIO);
    }

    public static Action display() {
        return create(DISPLAY);
    }

    public boolean isDisplay() {
        return is(DISPLAY);
    }

    public static Action email() {
        return create(EMAIL);
    }

    public boolean isEmail() {
        return is(EMAIL);
    }

    private static Action create(String value) {
        return new Action(value);
    }

    protected Collection<String> getStandardValues() {
        return Arrays.asList(new String[]{AUDIO, DISPLAY, EMAIL});
    }
}
