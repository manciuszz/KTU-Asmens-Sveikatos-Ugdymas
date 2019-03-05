package biweekly.io.scribe.component;

import biweekly.component.VAlarm;

public class VAlarmScribe extends ICalComponentScribe<VAlarm> {
    public VAlarmScribe() {
        super(VAlarm.class, "VALARM");
    }

    protected VAlarm _newInstance() {
        return new VAlarm(null, null);
    }
}
