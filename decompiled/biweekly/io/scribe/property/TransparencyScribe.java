package biweekly.io.scribe.property;

import biweekly.property.Transparency;

public class TransparencyScribe extends TextPropertyScribe<Transparency> {
    public TransparencyScribe() {
        super(Transparency.class, "TRANSP");
    }

    protected Transparency newInstance(String value) {
        return new Transparency(value);
    }
}
