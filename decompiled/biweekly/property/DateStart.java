package biweekly.property;

import biweekly.util.DateTimeComponents;
import java.util.Date;

public class DateStart extends DateOrDateTimeProperty {
    public DateStart(Date startDate) {
        this(startDate, true);
    }

    public DateStart(Date startDate, boolean hasTime) {
        super(startDate, hasTime);
    }

    public DateStart(DateTimeComponents components) {
        super(components);
    }
}
