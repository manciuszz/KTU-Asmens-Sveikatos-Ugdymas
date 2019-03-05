package biweekly.io.scribe.property;

import biweekly.parameter.ICalParameters;
import biweekly.property.TimezoneId;

public class TimezoneIdScribe extends TextPropertyScribe<TimezoneId> {
    public TimezoneIdScribe() {
        super(TimezoneId.class, ICalParameters.TZID);
    }

    protected TimezoneId newInstance(String value) {
        return new TimezoneId(value);
    }
}
