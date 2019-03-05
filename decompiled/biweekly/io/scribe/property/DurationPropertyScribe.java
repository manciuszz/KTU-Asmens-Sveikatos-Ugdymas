package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.DurationProperty;
import biweekly.util.Duration;
import java.util.List;

public class DurationPropertyScribe extends ICalPropertyScribe<DurationProperty> {
    public DurationPropertyScribe() {
        super(DurationProperty.class, "DURATION", ICalDataType.DURATION);
    }

    protected String _writeText(DurationProperty property) {
        Duration duration = (Duration) property.getValue();
        if (duration != null) {
            return duration.toString();
        }
        return "";
    }

    protected DurationProperty _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return parse(ICalPropertyScribe.unescape(value));
    }

    protected void _writeXml(DurationProperty property, XCalElement element) {
        String durationStr = null;
        Duration duration = (Duration) property.getValue();
        if (duration != null) {
            durationStr = duration.toString();
        }
        element.append(dataType(property), durationStr);
    }

    protected DurationProperty _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String value = element.first(this.defaultDataType);
        if (value != null) {
            return parse(value);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(DurationProperty property) {
        Duration value = (Duration) property.getValue();
        if (value != null) {
            return JCalValue.single(value.toString());
        }
        return JCalValue.single("");
    }

    protected DurationProperty _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return parse(value.asSingle());
    }

    private DurationProperty parse(String value) {
        if (value == null) {
            return new DurationProperty(null);
        }
        try {
            return new DurationProperty(Duration.parse(value));
        } catch (IllegalArgumentException e) {
            throw new CannotParseException(18, new Object[0]);
        }
    }
}
