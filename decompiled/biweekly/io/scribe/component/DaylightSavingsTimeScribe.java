package biweekly.io.scribe.component;

import biweekly.component.DaylightSavingsTime;

public class DaylightSavingsTimeScribe extends ICalComponentScribe<DaylightSavingsTime> {
    public DaylightSavingsTimeScribe() {
        super(DaylightSavingsTime.class, "DAYLIGHT");
    }

    protected DaylightSavingsTime _newInstance() {
        return new DaylightSavingsTime();
    }
}
