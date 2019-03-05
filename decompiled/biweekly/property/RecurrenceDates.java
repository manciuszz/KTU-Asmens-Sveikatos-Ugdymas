package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.component.VTimezone;
import biweekly.util.ICalDateFormat;
import biweekly.util.Period;
import java.util.Date;
import java.util.List;

public class RecurrenceDates extends ICalProperty {
    private List<Date> dates;
    private boolean hasTime;
    private List<Period> periods;

    public RecurrenceDates(List<Date> dates, boolean hasTime) {
        this.dates = dates;
        this.hasTime = hasTime;
    }

    public RecurrenceDates(List<Period> periods) {
        this.periods = periods;
    }

    public List<Date> getDates() {
        return this.dates;
    }

    public boolean hasTime() {
        return this.hasTime;
    }

    public List<Period> getPeriods() {
        return this.periods;
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

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        String tzid = getTimezoneId();
        if (tzid != null && tzid.contains("/") && ICalDateFormat.parseTimeZoneId(tzid) == null) {
            warnings.add(Warning.validate(27, tzid));
        }
    }
}
