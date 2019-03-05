package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.ArrayList;
import java.util.List;

public class ListProperty<T> extends ICalProperty {
    private final List<T> values;

    public ListProperty() {
        this.values = new ArrayList();
    }

    public ListProperty(T... values) {
        this();
        for (T value : values) {
            this.values.add(value);
        }
    }

    public ListProperty(List<T> values) {
        this.values = values;
    }

    public void addValue(T value) {
        this.values.add(value);
    }

    public List<T> getValues() {
        return this.values;
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.values.isEmpty()) {
            warnings.add(Warning.validate(26, new Object[0]));
        }
    }
}
