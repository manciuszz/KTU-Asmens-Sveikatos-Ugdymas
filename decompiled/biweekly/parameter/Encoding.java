package biweekly.parameter;

import java.util.Collection;

public class Encoding extends EnumParameterValue {
    public static final Encoding BASE64 = new Encoding("BASE64");
    public static final Encoding _8BIT = new Encoding("8BIT");
    private static final ICalParameterCaseClasses<Encoding> enums = new ICalParameterCaseClasses(Encoding.class);

    private Encoding(String value) {
        super(value);
    }

    public static Encoding find(String value) {
        return (Encoding) enums.find(value);
    }

    public static Encoding get(String value) {
        return (Encoding) enums.get(value);
    }

    public static Collection<Encoding> all() {
        return enums.all();
    }
}
