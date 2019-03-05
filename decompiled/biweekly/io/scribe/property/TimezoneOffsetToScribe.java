package biweekly.io.scribe.property;

import biweekly.property.TimezoneOffsetTo;
import biweekly.util.UtcOffset;

public class TimezoneOffsetToScribe extends UtcOffsetPropertyScribe<TimezoneOffsetTo> {
    public TimezoneOffsetToScribe() {
        super(TimezoneOffsetTo.class, "TZOFFSETTO");
    }

    protected TimezoneOffsetTo newInstance(UtcOffset offset) {
        return new TimezoneOffsetTo(offset);
    }
}
