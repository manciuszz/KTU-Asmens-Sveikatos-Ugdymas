package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.List;

public class ValuedProperty<T> extends ICalProperty {
    protected T value;

    public ValuedProperty(T value) {
        setValue(value);
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.value == null) {
            warnings.add(Warning.validate(26, new Object[0]));
        }
    }
}
