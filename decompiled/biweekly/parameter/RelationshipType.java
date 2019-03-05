package biweekly.parameter;

import java.util.Collection;

public class RelationshipType extends EnumParameterValue {
    public static final RelationshipType CHILD = new RelationshipType("CHILD");
    public static final RelationshipType PARENT = new RelationshipType("PARENT");
    public static final RelationshipType SIBLING = new RelationshipType("SIBLING");
    private static final ICalParameterCaseClasses<RelationshipType> enums = new ICalParameterCaseClasses(RelationshipType.class);

    private RelationshipType(String value) {
        super(value);
    }

    public static RelationshipType find(String value) {
        return (RelationshipType) enums.find(value);
    }

    public static RelationshipType get(String value) {
        return (RelationshipType) enums.get(value);
    }

    public static Collection<RelationshipType> all() {
        return enums.all();
    }
}
