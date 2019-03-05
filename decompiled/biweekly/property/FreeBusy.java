package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.parameter.FreeBusyType;
import biweekly.util.Duration;
import biweekly.util.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreeBusy extends ICalProperty {
    private final List<Period> values = new ArrayList();

    public void addValue(Date start, Date end) {
        this.values.add(new Period(start, end));
    }

    public void addValue(Date start, Duration duration) {
        this.values.add(new Period(start, duration));
    }

    public List<Period> getValues() {
        return this.values;
    }

    public FreeBusyType getType() {
        return this.parameters.getFreeBusyType();
    }

    public void setType(FreeBusyType fbType) {
        this.parameters.setFreeBusyType(fbType);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.values.isEmpty()) {
            warnings.add(Warning.validate(38, new Object[0]));
            return;
        }
        for (Period timePeriod : this.values) {
            if (timePeriod.getStartDate() == null) {
                warnings.add(Warning.validate(39, new Object[0]));
                break;
            }
        }
        for (Period timePeriod2 : this.values) {
            if (timePeriod2.getEndDate() == null && timePeriod2.getDuration() == null) {
                warnings.add(Warning.validate(40, new Object[0]));
                return;
            }
        }
    }
}
