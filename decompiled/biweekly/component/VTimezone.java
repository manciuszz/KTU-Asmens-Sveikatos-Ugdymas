package biweekly.component;

import biweekly.Warning;
import biweekly.property.LastModified;
import biweekly.property.TimezoneId;
import biweekly.property.TimezoneUrl;
import java.util.Date;
import java.util.List;

public class VTimezone extends ICalComponent {
    public VTimezone(String identifier) {
        setTimezoneId(identifier);
    }

    public TimezoneId getTimezoneId() {
        return (TimezoneId) getProperty(TimezoneId.class);
    }

    public void setTimezoneId(TimezoneId timezoneId) {
        setProperty(TimezoneId.class, timezoneId);
    }

    public TimezoneId setTimezoneId(String timezoneId) {
        TimezoneId prop = timezoneId == null ? null : new TimezoneId(timezoneId);
        setTimezoneId(prop);
        return prop;
    }

    public LastModified getLastModified() {
        return (LastModified) getProperty(LastModified.class);
    }

    public void setLastModified(LastModified lastModified) {
        setProperty(LastModified.class, lastModified);
    }

    public LastModified setLastModified(Date lastModified) {
        LastModified prop = lastModified == null ? null : new LastModified(lastModified);
        setLastModified(prop);
        return prop;
    }

    public TimezoneUrl getTimezoneUrl() {
        return (TimezoneUrl) getProperty(TimezoneUrl.class);
    }

    public void setTimezoneUrl(TimezoneUrl url) {
        setProperty(TimezoneUrl.class, url);
    }

    public TimezoneUrl setTimezoneUrl(String url) {
        TimezoneUrl prop = url == null ? null : new TimezoneUrl(url);
        setTimezoneUrl(prop);
        return prop;
    }

    public List<StandardTime> getStandardTimes() {
        return getComponents(StandardTime.class);
    }

    public void addStandardTime(StandardTime standardTime) {
        addComponent(standardTime);
    }

    public List<DaylightSavingsTime> getDaylightSavingsTime() {
        return getComponents(DaylightSavingsTime.class);
    }

    public void addDaylightSavingsTime(DaylightSavingsTime daylightSavingsTime) {
        addComponent(daylightSavingsTime);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        checkRequiredCardinality(warnings, TimezoneId.class);
        checkOptionalCardinality(warnings, LastModified.class, TimezoneUrl.class);
        if (getStandardTimes().isEmpty() && getDaylightSavingsTime().isEmpty()) {
            warnings.add(Warning.validate(21, new Object[0]));
        }
    }
}
