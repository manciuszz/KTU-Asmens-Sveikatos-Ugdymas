package biweekly.io.scribe.property;

import biweekly.property.CalendarScale;

public class CalendarScaleScribe extends TextPropertyScribe<CalendarScale> {
    public CalendarScaleScribe() {
        super(CalendarScale.class, "CALSCALE");
    }

    protected CalendarScale newInstance(String value) {
        return new CalendarScale(value);
    }
}
