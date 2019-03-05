package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.List;

public class Version extends ICalProperty {
    private static final String DEFAULT = "2.0";
    private String maxVersion;
    private String minVersion;

    public Version(String version) {
        this(null, version);
    }

    public Version(String minVersion, String maxVersion) {
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    public static Version v2_0() {
        return new Version(DEFAULT);
    }

    public boolean isV2_0() {
        return DEFAULT.equalsIgnoreCase(this.maxVersion);
    }

    public String getMinVersion() {
        return this.minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getMaxVersion() {
        return this.maxVersion;
    }

    public void setMaxVersion(String maxVersion) {
        this.maxVersion = maxVersion;
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.maxVersion == null) {
            warnings.add(Warning.validate(35, new Object[0]));
        }
    }
}
