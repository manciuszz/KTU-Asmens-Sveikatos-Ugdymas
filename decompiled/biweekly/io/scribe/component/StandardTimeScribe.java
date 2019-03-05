package biweekly.io.scribe.component;

import biweekly.component.StandardTime;

public class StandardTimeScribe extends ICalComponentScribe<StandardTime> {
    public StandardTimeScribe() {
        super(StandardTime.class, "STANDARD");
    }

    protected StandardTime _newInstance() {
        return new StandardTime();
    }
}
