package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.parameter.Related;
import biweekly.util.Duration;
import java.util.Date;
import java.util.List;

public class Trigger extends ICalProperty {
    private Date date;
    private Duration duration;

    public Trigger(Duration duration, Related related) {
        setDuration(duration, related);
    }

    public Trigger(Date date) {
        setDate(date);
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration, Related related) {
        this.date = null;
        this.duration = duration;
        setRelated(related);
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.duration = null;
        setRelated(null);
    }

    public Related getRelated() {
        return this.parameters.getRelated();
    }

    public void setRelated(Related related) {
        this.parameters.setRelated(related);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.duration == null && this.date == null) {
            warnings.add(Warning.validate(33, new Object[0]));
        }
        Related related = getRelated();
        if (this.duration != null && related == null) {
            warnings.add(Warning.validate(10, new Object[0]));
        }
    }
}
