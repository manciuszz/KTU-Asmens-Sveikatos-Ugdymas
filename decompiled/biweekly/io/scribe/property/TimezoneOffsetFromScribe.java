package biweekly.io.scribe.property;

import biweekly.property.TimezoneOffsetFrom;
import biweekly.util.UtcOffset;

public class TimezoneOffsetFromScribe extends UtcOffsetPropertyScribe<TimezoneOffsetFrom> {
    public TimezoneOffsetFromScribe() {
        super(TimezoneOffsetFrom.class, "TZOFFSETFROM");
    }

    protected TimezoneOffsetFrom newInstance(UtcOffset offset) {
        return new TimezoneOffsetFrom(offset);
    }
}
