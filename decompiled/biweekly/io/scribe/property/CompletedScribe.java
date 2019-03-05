package biweekly.io.scribe.property;

import biweekly.property.Completed;
import java.util.Date;

public class CompletedScribe extends DateTimePropertyScribe<Completed> {
    public CompletedScribe() {
        super(Completed.class, "COMPLETED");
    }

    protected Completed newInstance(Date date) {
        return new Completed(date);
    }
}
