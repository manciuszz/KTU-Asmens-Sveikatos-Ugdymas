package biweekly.property;

import java.util.Arrays;
import java.util.Collection;

public class Classification extends EnumProperty {
    private static final String CONFIDENTIAL = "CONFIDENTIAL";
    private static final String PRIVATE = "PRIVATE";
    private static final String PUBLIC = "PUBLIC";

    public Classification(String classification) {
        super(classification);
    }

    public static Classification public_() {
        return create(PUBLIC);
    }

    public boolean isPublic() {
        return is(PUBLIC);
    }

    public static Classification private_() {
        return create(PRIVATE);
    }

    public boolean isPrivate() {
        return is(PRIVATE);
    }

    public static Classification confidential() {
        return create(CONFIDENTIAL);
    }

    public boolean isConfidential() {
        return is(CONFIDENTIAL);
    }

    private static Classification create(String classification) {
        return new Classification(classification);
    }

    protected Collection<String> getStandardValues() {
        return Arrays.asList(new String[]{PUBLIC, PRIVATE, CONFIDENTIAL});
    }
}
