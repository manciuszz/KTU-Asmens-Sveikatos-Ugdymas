package biweekly.io.scribe.property;

import biweekly.property.DateTimeStamp;
import java.util.Date;

public class DateTimeStampScribe extends DateTimePropertyScribe<DateTimeStamp> {
    public DateTimeStampScribe() {
        super(DateTimeStamp.class, "DTSTAMP");
    }

    protected DateTimeStamp newInstance(Date date) {
        return new DateTimeStamp(date);
    }
}
