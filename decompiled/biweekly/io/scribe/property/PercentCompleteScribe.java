package biweekly.io.scribe.property;

import biweekly.property.PercentComplete;

public class PercentCompleteScribe extends IntegerPropertyScribe<PercentComplete> {
    public PercentCompleteScribe() {
        super(PercentComplete.class, "PERCENT-COMPLETE");
    }

    protected PercentComplete newInstance(Integer value) {
        return new PercentComplete(value);
    }
}
