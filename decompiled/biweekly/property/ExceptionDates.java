package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.component.VTimezone;
import biweekly.util.ICalDateFormat;
import java.util.Date;
import java.util.List;

public class ExceptionDates extends ListProperty<Date> {
    private boolean hasTime = true;

    public ExceptionDates(boolean hasTime) {
        setHasTime(hasTime);
    }

    public boolean hasTime() {
        return this.hasTime;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }

    public String getTimezoneId() {
        return super.getTimezoneId();
    }

    public void setTimezoneId(String timezoneId) {
        super.setTimezoneId(timezoneId);
    }

    public void setTimezone(VTimezone timezone) {
        super.setTimezone(timezone);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        String tzid = getTimezoneId();
        if (tzid != null && tzid.contains("/") && ICalDateFormat.parseTimeZoneId(tzid) == null) {
            warnings.add(Warning.validate(27, tzid));
        }
    }
}
