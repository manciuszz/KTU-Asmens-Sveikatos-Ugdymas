package biweekly.io.scribe.property;

import biweekly.property.Location;

public class LocationScribe extends TextPropertyScribe<Location> {
    public LocationScribe() {
        super(Location.class, "LOCATION");
    }

    protected Location newInstance(String value) {
        return new Location(value);
    }
}
