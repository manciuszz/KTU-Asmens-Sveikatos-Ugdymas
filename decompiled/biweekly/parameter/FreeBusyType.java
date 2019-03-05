package biweekly.parameter;

import java.util.Collection;

public class FreeBusyType extends EnumParameterValue {
    public static final FreeBusyType BUSY = new FreeBusyType("BUSY");
    public static final FreeBusyType BUSY_TENTATIVE = new FreeBusyType("BUSY-TENTATIVE");
    public static final FreeBusyType BUSY_UNAVAILABLE = new FreeBusyType("BUSY-UNAVAILABLE");
    public static final FreeBusyType FREE = new FreeBusyType("FREE");
    private static final ICalParameterCaseClasses<FreeBusyType> enums = new ICalParameterCaseClasses(FreeBusyType.class);

    private FreeBusyType(String value) {
        super(value);
    }

    public static FreeBusyType find(String value) {
        return (FreeBusyType) enums.find(value);
    }

    public static FreeBusyType get(String value) {
        return (FreeBusyType) enums.get(value);
    }

    public static Collection<FreeBusyType> all() {
        return enums.all();
    }
}
