package biweekly.property;

import java.util.Arrays;
import java.util.Collection;

public class CalendarScale extends EnumProperty {
    private static final String GREGORIAN = "GREGORIAN";

    public CalendarScale(String value) {
        super(value);
    }

    public static CalendarScale gregorian() {
        return new CalendarScale(GREGORIAN);
    }

    public boolean isGregorian() {
        return is(GREGORIAN);
    }

    protected Collection<String> getStandardValues() {
        return Arrays.asList(new String[]{GREGORIAN});
    }
}
