package biweekly.io.scribe.property;

import biweekly.property.Repeat;

public class RepeatScribe extends IntegerPropertyScribe<Repeat> {
    public RepeatScribe() {
        super(Repeat.class, "REPEAT");
    }

    protected Repeat newInstance(Integer value) {
        return new Repeat(value);
    }
}
