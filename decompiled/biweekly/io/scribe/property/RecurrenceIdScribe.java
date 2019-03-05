package biweekly.io.scribe.property;

import biweekly.property.RecurrenceId;
import java.util.Date;

public class RecurrenceIdScribe extends DateOrDateTimePropertyScribe<RecurrenceId> {
    public RecurrenceIdScribe() {
        super(RecurrenceId.class, "RECURRENCE-ID");
    }

    protected RecurrenceId newInstance(Date date, boolean hasTime) {
        return new RecurrenceId(date, hasTime);
    }
}
