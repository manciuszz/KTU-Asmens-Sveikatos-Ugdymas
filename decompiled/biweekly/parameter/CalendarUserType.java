package biweekly.parameter;

import java.util.Collection;

public class CalendarUserType extends EnumParameterValue {
    public static final CalendarUserType GROUP = new CalendarUserType("GROUP");
    public static final CalendarUserType INDIVIDUAL = new CalendarUserType("INDIVIDUAL");
    public static final CalendarUserType RESOURCE = new CalendarUserType("RESOURCE");
    public static final CalendarUserType ROOM = new CalendarUserType("ROOM");
    public static final CalendarUserType UNKNOWN = new CalendarUserType("UNKNOWN");
    private static final ICalParameterCaseClasses<CalendarUserType> enums = new ICalParameterCaseClasses(CalendarUserType.class);

    private CalendarUserType(String value) {
        super(value);
    }

    public static CalendarUserType find(String value) {
        return (CalendarUserType) enums.find(value);
    }

    public static CalendarUserType get(String value) {
        return (CalendarUserType) enums.get(value);
    }

    public static Collection<CalendarUserType> all() {
        return enums.all();
    }
}
