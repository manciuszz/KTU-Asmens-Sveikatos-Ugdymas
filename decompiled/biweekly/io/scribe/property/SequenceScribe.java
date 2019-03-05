package biweekly.io.scribe.property;

import biweekly.property.Sequence;

public class SequenceScribe extends IntegerPropertyScribe<Sequence> {
    public SequenceScribe() {
        super(Sequence.class, "SEQUENCE");
    }

    protected Sequence newInstance(Integer value) {
        return new Sequence(value);
    }
}
