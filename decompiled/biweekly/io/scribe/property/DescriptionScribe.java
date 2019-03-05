package biweekly.io.scribe.property;

import biweekly.property.Description;

public class DescriptionScribe extends TextPropertyScribe<Description> {
    public DescriptionScribe() {
        super(Description.class, "DESCRIPTION");
    }

    protected Description newInstance(String value) {
        return new Description(value);
    }
}
