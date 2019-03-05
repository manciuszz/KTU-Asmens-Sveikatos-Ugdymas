package biweekly.property;

import java.util.Arrays;
import java.util.Collection;

public class Transparency extends EnumProperty {
    private static final String OPAQUE = "OPAQUE";
    private static final String TRANSPARENT = "TRANSPARENT";

    public Transparency(String value) {
        super(value);
    }

    public static Transparency opaque() {
        return create(OPAQUE);
    }

    public boolean isOpaque() {
        return is(OPAQUE);
    }

    public static Transparency transparent() {
        return create(TRANSPARENT);
    }

    public boolean isTransparent() {
        return is(TRANSPARENT);
    }

    private static Transparency create(String value) {
        return new Transparency(value);
    }

    protected Collection<String> getStandardValues() {
        return Arrays.asList(new String[]{OPAQUE, TRANSPARENT});
    }
}
