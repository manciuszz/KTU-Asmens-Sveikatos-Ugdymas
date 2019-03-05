package biweekly.property;

import biweekly.util.DateTimeComponents;
import java.util.Date;

public class DateDue extends DateOrDateTimeProperty {
    public DateDue(Date dueDate) {
        this(dueDate, true);
    }

    public DateDue(Date dueDate, boolean hasTime) {
        super(dueDate, hasTime);
    }

    public DateDue(DateTimeComponents components) {
        super(components);
    }
}
