package biweekly.io.scribe.property;

import biweekly.property.TimezoneName;

public class TimezoneNameScribe extends TextPropertyScribe<TimezoneName> {
    public TimezoneNameScribe() {
        super(TimezoneName.class, "TZNAME");
    }

    protected TimezoneName newInstance(String value) {
        return new TimezoneName(value);
    }
}
