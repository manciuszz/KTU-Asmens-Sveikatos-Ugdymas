package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.util.Recurrence;
import java.util.List;

public class ExceptionRule extends RecurrenceProperty {
    public ExceptionRule(Recurrence recur) {
        super(recur);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        warnings.add(Warning.validate(37, new Object[0]));
    }
}
