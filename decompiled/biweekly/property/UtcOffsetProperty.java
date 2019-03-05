package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.util.UtcOffset;
import java.util.List;

public class UtcOffsetProperty extends ValuedProperty<UtcOffset> {
    public UtcOffsetProperty(int hourOffset, int minuteOffset) {
        this(new UtcOffset(hourOffset, minuteOffset));
    }

    public UtcOffsetProperty(UtcOffset offset) {
        super(offset);
    }

    public Integer getHourOffset() {
        return this.value == null ? null : Integer.valueOf(((UtcOffset) this.value).getHour());
    }

    public Integer getMinuteOffset() {
        return this.value == null ? null : Integer.valueOf(((UtcOffset) this.value).getMinute());
    }

    public void setValue(int hourOffset, int minuteOffset) {
        setValue(new UtcOffset(hourOffset, minuteOffset));
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        super.validate(components, warnings);
        if (this.value != null) {
            if (((UtcOffset) this.value).getMinute() < 0 || ((UtcOffset) this.value).getMinute() > 59) {
                warnings.add(Warning.validate(34, new Object[0]));
            }
        }
    }
}
