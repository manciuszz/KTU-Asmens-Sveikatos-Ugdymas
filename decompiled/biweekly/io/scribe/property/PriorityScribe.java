package biweekly.io.scribe.property;

import biweekly.property.Priority;

public class PriorityScribe extends IntegerPropertyScribe<Priority> {
    public PriorityScribe() {
        super(Priority.class, "PRIORITY");
    }

    protected Priority newInstance(Integer value) {
        return new Priority(value);
    }
}
