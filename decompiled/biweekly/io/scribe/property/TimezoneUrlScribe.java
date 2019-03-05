package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.property.TimezoneUrl;

public class TimezoneUrlScribe extends TextPropertyScribe<TimezoneUrl> {
    public TimezoneUrlScribe() {
        super(TimezoneUrl.class, "TZURL", ICalDataType.URI);
    }

    protected TimezoneUrl newInstance(String value) {
        return new TimezoneUrl(value);
    }
}
