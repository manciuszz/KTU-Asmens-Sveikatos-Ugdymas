package biweekly.io.scribe.property;

import biweekly.property.Classification;

public class ClassificationScribe extends TextPropertyScribe<Classification> {
    public ClassificationScribe() {
        super(Classification.class, "CLASS");
    }

    protected Classification newInstance(String value) {
        return new Classification(value);
    }
}
