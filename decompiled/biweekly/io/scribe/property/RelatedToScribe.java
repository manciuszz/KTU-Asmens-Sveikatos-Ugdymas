package biweekly.io.scribe.property;

import biweekly.property.RelatedTo;

public class RelatedToScribe extends TextPropertyScribe<RelatedTo> {
    public RelatedToScribe() {
        super(RelatedTo.class, "RELATED-TO");
    }

    protected RelatedTo newInstance(String value) {
        return new RelatedTo(value);
    }
}
