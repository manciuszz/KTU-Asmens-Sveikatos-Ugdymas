package biweekly.io.scribe.property;

import biweekly.property.DateDue;
import java.util.Date;

public class DateDueScribe extends DateOrDateTimePropertyScribe<DateDue> {
    public DateDueScribe() {
        super(DateDue.class, "DUE");
    }

    protected DateDue newInstance(Date date, boolean hasTime) {
        return new DateDue(date, hasTime);
    }
}
