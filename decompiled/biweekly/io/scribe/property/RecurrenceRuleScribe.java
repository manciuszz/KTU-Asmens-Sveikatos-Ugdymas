package biweekly.io.scribe.property;

import biweekly.property.RecurrenceRule;
import biweekly.util.Recurrence;

public class RecurrenceRuleScribe extends RecurrencePropertyScribe<RecurrenceRule> {
    public RecurrenceRuleScribe() {
        super(RecurrenceRule.class, "RRULE");
    }

    protected RecurrenceRule newInstance(Recurrence recur) {
        return new RecurrenceRule(recur);
    }
}
