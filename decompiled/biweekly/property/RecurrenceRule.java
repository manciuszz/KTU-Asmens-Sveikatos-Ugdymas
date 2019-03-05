package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.util.Recurrence;
import java.util.List;

public class RecurrenceRule extends RecurrenceProperty {
    public RecurrenceRule(Recurrence recur) {
        super(recur);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        if (this.value != null && !((Recurrence) this.value).getXRules().isEmpty()) {
            warnings.add(Warning.validate(32, new Object[0]));
        }
    }
}
