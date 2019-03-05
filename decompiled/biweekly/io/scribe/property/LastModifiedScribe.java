package biweekly.io.scribe.property;

import biweekly.property.LastModified;
import java.util.Date;

public class LastModifiedScribe extends DateTimePropertyScribe<LastModified> {
    public LastModifiedScribe() {
        super(LastModified.class, "LAST-MODIFIED");
    }

    protected LastModified newInstance(Date date) {
        return new LastModified(date);
    }
}
