package biweekly.parameter;

import java.util.Collection;

public class Related extends EnumParameterValue {
    public static final Related END = new Related("END");
    public static final Related START = new Related("START");
    private static final ICalParameterCaseClasses<Related> enums = new ICalParameterCaseClasses(Related.class);

    private Related(String value) {
        super(value);
    }

    public static Related find(String value) {
        return (Related) enums.find(value);
    }

    public static Related get(String value) {
        return (Related) enums.get(value);
    }

    public static Collection<Related> all() {
        return enums.all();
    }
}
