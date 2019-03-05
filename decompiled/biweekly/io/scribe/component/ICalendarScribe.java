package biweekly.io.scribe.component;

import biweekly.ICalendar;

public class ICalendarScribe extends ICalComponentScribe<ICalendar> {
    public ICalendarScribe() {
        super(ICalendar.class, "VCALENDAR");
    }

    protected ICalendar _newInstance() {
        return new ICalendar();
    }
}
