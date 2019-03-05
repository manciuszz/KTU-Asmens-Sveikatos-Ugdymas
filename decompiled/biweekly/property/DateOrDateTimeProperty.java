package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.component.VTimezone;
import biweekly.util.DateTimeComponents;
import biweekly.util.ICalDateFormat;
import java.util.Date;
import java.util.List;

public class DateOrDateTimeProperty extends ICalProperty {
    protected boolean hasTime;
    protected boolean localTime;
    protected DateTimeComponents rawComponents;
    protected Date value;

    public DateOrDateTimeProperty(DateTimeComponents rawComponents) {
        setRawComponents(rawComponents);
    }

    public DateOrDateTimeProperty(Date value, boolean hasTime) {
        setValue(value, hasTime);
    }

    public Date getValue() {
        return this.value;
    }

    public void setValue(Date value, boolean hasTime) {
        this.value = value;
        this.hasTime = hasTime;
    }

    public DateTimeComponents getRawComponents() {
        return this.rawComponents;
    }

    public void setRawComponents(DateTimeComponents rawComponents) {
        this.rawComponents = rawComponents;
    }

    public boolean hasTime() {
        return this.hasTime;
    }

    public boolean isLocalTime() {
        return this.localTime;
    }

    public void setLocalTime(boolean localTime) {
        this.localTime = localTime;
        if (localTime) {
            setTimezoneId(null);
        }
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
        if (this.value == null && this.rawComponents == null) {
            warnings.add(Warning.validate(26, new Object[0]));
        }
        String tzid = getTimezoneId();
        if (tzid != null && tzid.contains("/") && ICalDateFormat.parseTimeZoneId(tzid) == null) {
            warnings.add(Warning.validate(27, tzid));
        }
    }
}
