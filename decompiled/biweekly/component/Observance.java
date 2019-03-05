package biweekly.component;

import biweekly.Warning;
import biweekly.property.Comment;
import biweekly.property.DateStart;
import biweekly.property.ExceptionDates;
import biweekly.property.RecurrenceDates;
import biweekly.property.RecurrenceRule;
import biweekly.property.TimezoneName;
import biweekly.property.TimezoneOffsetFrom;
import biweekly.property.TimezoneOffsetTo;
import biweekly.util.DateTimeComponents;
import biweekly.util.Recurrence;
import java.util.List;

public class Observance extends ICalComponent {
    public DateStart getDateStart() {
        return (DateStart) getProperty(DateStart.class);
    }

    public void setDateStart(DateStart dateStart) {
        if (dateStart != null) {
            dateStart.setLocalTime(true);
        }
        setProperty(DateStart.class, dateStart);
    }

    public DateStart setDateStart(DateTimeComponents components) {
        DateStart prop = components == null ? null : new DateStart(components);
        setDateStart(prop);
        return prop;
    }

    public TimezoneOffsetTo getTimezoneOffsetTo() {
        return (TimezoneOffsetTo) getProperty(TimezoneOffsetTo.class);
    }

    public void setTimezoneOffsetTo(TimezoneOffsetTo timezoneOffsetTo) {
        setProperty(TimezoneOffsetTo.class, timezoneOffsetTo);
    }

    public TimezoneOffsetTo setTimezoneOffsetTo(Integer hour, Integer minute) {
        TimezoneOffsetTo prop = new TimezoneOffsetTo(hour.intValue(), minute.intValue());
        setTimezoneOffsetTo(prop);
        return prop;
    }

    public TimezoneOffsetFrom getTimezoneOffsetFrom() {
        return (TimezoneOffsetFrom) getProperty(TimezoneOffsetFrom.class);
    }

    public void setTimezoneOffsetFrom(TimezoneOffsetFrom timezoneOffsetFrom) {
        setProperty(TimezoneOffsetFrom.class, timezoneOffsetFrom);
    }

    public TimezoneOffsetFrom setTimezoneOffsetFrom(Integer hour, Integer minute) {
        TimezoneOffsetFrom prop = new TimezoneOffsetFrom(hour, minute);
        setTimezoneOffsetFrom(prop);
        return prop;
    }

    public RecurrenceRule getRecurrenceRule() {
        return (RecurrenceRule) getProperty(RecurrenceRule.class);
    }

    public RecurrenceRule setRecurrenceRule(Recurrence recur) {
        RecurrenceRule prop = recur == null ? null : new RecurrenceRule(recur);
        setRecurrenceRule(prop);
        return prop;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        setProperty(RecurrenceRule.class, recurrenceRule);
    }

    public List<Comment> getComments() {
        return getProperties(Comment.class);
    }

    public void addComment(Comment comment) {
        addProperty(comment);
    }

    public Comment addComment(String comment) {
        Comment prop = new Comment(comment);
        addComment(prop);
        return prop;
    }

    public List<RecurrenceDates> getRecurrenceDates() {
        return getProperties(RecurrenceDates.class);
    }

    public void addRecurrenceDates(RecurrenceDates recurrenceDates) {
        addProperty(recurrenceDates);
    }

    public List<TimezoneName> getTimezoneNames() {
        return getProperties(TimezoneName.class);
    }

    public void addTimezoneName(TimezoneName timezoneName) {
        addProperty(timezoneName);
    }

    public TimezoneName addTimezoneName(String timezoneName) {
        TimezoneName prop = new TimezoneName(timezoneName);
        addTimezoneName(prop);
        return prop;
    }

    public List<ExceptionDates> getExceptionDates() {
        return getProperties(ExceptionDates.class);
    }

    public void addExceptionDates(ExceptionDates exceptionDates) {
        addProperty(exceptionDates);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        checkRequiredCardinality(warnings, DateStart.class, TimezoneOffsetTo.class, TimezoneOffsetFrom.class);
        DateStart dateStart = getDateStart();
        RecurrenceRule rrule = getRecurrenceRule();
        if (!(dateStart == null || rrule == null)) {
            Recurrence recur = (Recurrence) rrule.getValue();
            if (!(dateStart.getValue() == null || recur == null || dateStart.hasTime() || (recur.getByHour().isEmpty() && recur.getByMinute().isEmpty() && recur.getBySecond().isEmpty()))) {
                warnings.add(Warning.validate(5, new Object[0]));
            }
        }
        if (getProperties(RecurrenceRule.class).size() > 1) {
            warnings.add(Warning.validate(6, new Object[0]));
        }
    }
}
