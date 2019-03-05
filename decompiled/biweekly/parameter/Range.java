package biweekly.parameter;

import java.util.Collection;

public class Range extends EnumParameterValue {
    public static final Range THIS_AND_FUTURE = new Range("THISANDFUTURE");
    public static final Range THIS_AND_PRIOR = new Range("THISANDPRIOR");
    private static final ICalParameterCaseClasses<Range> enums = new ICalParameterCaseClasses(Range.class);

    private Range(String value) {
        super(value);
    }

    public static Range find(String value) {
        return (Range) enums.find(value);
    }

    public static Range get(String value) {
        return (Range) enums.get(value);
    }

    public static Collection<Range> all() {
        return enums.all();
    }
}
