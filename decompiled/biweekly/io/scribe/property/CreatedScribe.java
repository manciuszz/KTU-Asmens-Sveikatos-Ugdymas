package biweekly.io.scribe.property;

import biweekly.property.Created;
import java.util.Date;

public class CreatedScribe extends DateTimePropertyScribe<Created> {
    public CreatedScribe() {
        super(Created.class, "CREATED");
    }

    protected Created newInstance(Date date) {
        return new Created(date);
    }
}
