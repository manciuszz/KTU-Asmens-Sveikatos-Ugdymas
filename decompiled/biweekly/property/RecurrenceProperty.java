package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.util.Recurrence;
import java.util.List;

public class RecurrenceProperty extends ValuedProperty<Recurrence> {
    public RecurrenceProperty(Recurrence recur) {
        super(recur);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        if (this.value != null) {
            if (((Recurrence) this.value).getFrequency() == null) {
                warnings.add(Warning.validate(30, new Object[0]));
            }
            if (((Recurrence) this.value).getUntil() != null && ((Recurrence) this.value).getCount() != null) {
                warnings.add(Warning.validate(31, new Object[0]));
            }
        }
    }
}
