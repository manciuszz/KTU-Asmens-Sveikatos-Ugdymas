package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.property.Organizer;

public class OrganizerScribe extends TextPropertyScribe<Organizer> {
    public OrganizerScribe() {
        super(Organizer.class, "ORGANIZER", ICalDataType.CAL_ADDRESS);
    }

    protected Organizer newInstance(String value) {
        return new Organizer(value);
    }
}
