package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.Collection;
import java.util.List;

public abstract class EnumProperty extends TextProperty {
    protected abstract Collection<String> getStandardValues();

    public EnumProperty(String value) {
        super(value);
    }

    protected boolean is(String value) {
        return value.equalsIgnoreCase((String) this.value);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        if (this.value != null) {
            for (String standardValue : getStandardValues()) {
                if (((String) this.value).equalsIgnoreCase(standardValue)) {
                    return;
                }
            }
            warnings.add(Warning.validate(28, this.value, standardValues));
        }
    }
}
