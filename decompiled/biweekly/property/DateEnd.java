package biweekly.property;

import biweekly.util.DateTimeComponents;
import java.util.Date;

public class DateEnd extends DateOrDateTimeProperty {
    public DateEnd(Date endDate) {
        this(endDate, true);
    }

    public DateEnd(Date endDate, boolean hasTime) {
        super(endDate, hasTime);
    }

    public DateEnd(DateTimeComponents components) {
        super(components);
    }
}
