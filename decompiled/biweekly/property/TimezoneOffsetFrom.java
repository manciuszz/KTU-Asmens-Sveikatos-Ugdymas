package biweekly.property;

import biweekly.util.UtcOffset;

public class TimezoneOffsetFrom extends UtcOffsetProperty {
    public TimezoneOffsetFrom(Integer hourOffset, Integer minuteOffset) {
        super(hourOffset.intValue(), minuteOffset.intValue());
    }

    public TimezoneOffsetFrom(UtcOffset offset) {
        super(offset);
    }
}
