package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.List;

public class Geo extends ICalProperty {
    private Double latitude;
    private Double longitude;

    public Geo(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static double toDecimal(int degrees, int minutes, int seconds) {
        return (((double) degrees) + (((double) minutes) / 60.0d)) + (((double) seconds) / 3600.0d);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.latitude == null) {
            warnings.add(Warning.validate(41, new Object[0]));
        }
        if (this.longitude == null) {
            warnings.add(Warning.validate(42, new Object[0]));
        }
    }
}
