package biweekly.io.scribe.component;

import biweekly.component.VTimezone;

public class VTimezoneScribe extends ICalComponentScribe<VTimezone> {
    public VTimezoneScribe() {
        super(VTimezone.class, "VTIMEZONE");
    }

    protected VTimezone _newInstance() {
        return new VTimezone(null);
    }
}
