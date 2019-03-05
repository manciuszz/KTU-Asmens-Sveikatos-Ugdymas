package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.List;

public class PercentComplete extends IntegerProperty {
    public PercentComplete(Integer percent) {
        super(percent);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        if (this.value == null) {
            return;
        }
        if (((Integer) this.value).intValue() < 0 || ((Integer) this.value).intValue() > 100) {
            warnings.add(Warning.validate(29, this.value));
        }
    }
}
