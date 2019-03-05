package biweekly.io.scribe.property;

import biweekly.property.Summary;

public class SummaryScribe extends TextPropertyScribe<Summary> {
    public SummaryScribe() {
        super(Summary.class, "SUMMARY");
    }

    protected Summary newInstance(String value) {
        return new Summary(value);
    }
}
