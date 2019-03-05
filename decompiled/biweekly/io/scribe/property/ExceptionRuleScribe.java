package biweekly.io.scribe.property;

import biweekly.property.ExceptionRule;
import biweekly.util.Recurrence;

public class ExceptionRuleScribe extends RecurrencePropertyScribe<ExceptionRule> {
    public ExceptionRuleScribe() {
        super(ExceptionRule.class, "EXRULE");
    }

    protected ExceptionRule newInstance(Recurrence recur) {
        return new ExceptionRule(recur);
    }
}
