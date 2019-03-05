package biweekly.io.scribe.property;

import biweekly.property.DateEnd;
import java.util.Date;

public class DateEndScribe extends DateOrDateTimePropertyScribe<DateEnd> {
    public DateEndScribe() {
        super(DateEnd.class, "DTEND");
    }

    protected DateEnd newInstance(Date date, boolean hasTime) {
        return new DateEnd(date, hasTime);
    }
}
