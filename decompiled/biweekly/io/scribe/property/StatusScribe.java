package biweekly.io.scribe.property;

import biweekly.property.Status;

public class StatusScribe extends TextPropertyScribe<Status> {
    public StatusScribe() {
        super(Status.class, "STATUS");
    }

    protected Status newInstance(String value) {
        return new Status(value);
    }
}
