package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.property.Attendee;

public class AttendeeScribe extends TextPropertyScribe<Attendee> {
    public AttendeeScribe() {
        super(Attendee.class, "ATTENDEE", ICalDataType.CAL_ADDRESS);
    }

    protected Attendee newInstance(String value) {
        return new Attendee(value);
    }
}
