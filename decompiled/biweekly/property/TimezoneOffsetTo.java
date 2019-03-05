package biweekly.property;

import biweekly.util.UtcOffset;

public class TimezoneOffsetTo extends UtcOffsetProperty {
    public TimezoneOffsetTo(int hourOffset, int minuteOffset) {
        super(hourOffset, minuteOffset);
    }

    public TimezoneOffsetTo(UtcOffset offset) {
        super(offset);
    }
}
