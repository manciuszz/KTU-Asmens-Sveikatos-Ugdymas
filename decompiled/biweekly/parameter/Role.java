package biweekly.parameter;

import java.util.Collection;

public class Role extends EnumParameterValue {
    public static final Role CHAIR = new Role("CHAIR");
    public static final Role NON_PARTICIPANT = new Role("NON-PARTICIPANT");
    public static final Role OPT_PARTICIPANT = new Role("OPT-PARTICIPANT");
    public static final Role REQ_PARTICIPANT = new Role("REQ-PARTICIPANT");
    private static final ICalParameterCaseClasses<Role> enums = new ICalParameterCaseClasses(Role.class);

    private Role(String value) {
        super(value);
    }

    public static Role find(String value) {
        return (Role) enums.find(value);
    }

    public static Role get(String value) {
        return (Role) enums.get(value);
    }

    public static Collection<Role> all() {
        return enums.all();
    }
}
