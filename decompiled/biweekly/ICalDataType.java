package biweekly;

import biweekly.util.CaseClasses;
import java.util.Collection;

public class ICalDataType {
    public static final ICalDataType BINARY = new ICalDataType("BINARY");
    public static final ICalDataType BOOLEAN = new ICalDataType("BOOLEAN");
    public static final ICalDataType CAL_ADDRESS = new ICalDataType("CAL-ADDRESS");
    public static final ICalDataType DATE = new ICalDataType("DATE");
    public static final ICalDataType DATE_TIME = new ICalDataType("DATE-TIME");
    public static final ICalDataType DURATION = new ICalDataType("DURATION");
    public static final ICalDataType FLOAT = new ICalDataType("FLOAT");
    public static final ICalDataType INTEGER = new ICalDataType("INTEGER");
    public static final ICalDataType PERIOD = new ICalDataType("PERIOD");
    public static final ICalDataType RECUR = new ICalDataType("RECUR");
    public static final ICalDataType TEXT = new ICalDataType("TEXT");
    public static final ICalDataType TIME = new ICalDataType("TIME");
    public static final ICalDataType URI = new ICalDataType("URI");
    public static final ICalDataType UTC_OFFSET = new ICalDataType("UTC-OFFSET");
    private static final CaseClasses<ICalDataType, String> enums = new CaseClasses<ICalDataType, String>(ICalDataType.class) {
        protected ICalDataType create(String value) {
            return new ICalDataType(value);
        }

        protected boolean matches(ICalDataType dataType, String value) {
            return dataType.name.equalsIgnoreCase(value);
        }
    };
    private final String name;

    private ICalDataType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public static ICalDataType find(String value) {
        return (ICalDataType) enums.find(value);
    }

    public static ICalDataType get(String value) {
        return (ICalDataType) enums.get(value);
    }

    public static Collection<ICalDataType> all() {
        return enums.all();
    }
}
