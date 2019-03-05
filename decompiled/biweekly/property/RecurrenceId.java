package biweekly.property;

import biweekly.parameter.Range;
import java.util.Date;

public class RecurrenceId extends DateOrDateTimeProperty {
    public RecurrenceId(Date originalStartDate) {
        this(originalStartDate, true);
    }

    public RecurrenceId(Date originalStartDate, boolean hasTime) {
        super(originalStartDate, hasTime);
    }

    public Range getRange() {
        return this.parameters.getRange();
    }

    public void setRange(Range range) {
        this.parameters.setRange(range);
    }
}
