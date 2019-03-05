package biweekly.io.scribe.property;

import biweekly.property.DateStart;
import java.util.Date;

public class DateStartScribe extends DateOrDateTimePropertyScribe<DateStart> {
    public DateStartScribe() {
        super(DateStart.class, "DTSTART");
    }

    protected DateStart newInstance(Date date, boolean hasTime) {
        return new DateStart(date, hasTime);
    }
}
