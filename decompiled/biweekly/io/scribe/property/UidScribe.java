package biweekly.io.scribe.property;

import biweekly.property.Uid;

public class UidScribe extends TextPropertyScribe<Uid> {
    public UidScribe() {
        super(Uid.class, "UID");
    }

    protected Uid newInstance(String value) {
        return new Uid(value);
    }
}
