package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.property.Url;

public class UrlScribe extends TextPropertyScribe<Url> {
    public UrlScribe() {
        super(Url.class, "URL", ICalDataType.URI);
    }

    protected Url newInstance(String value) {
        return new Url(value);
    }
}
