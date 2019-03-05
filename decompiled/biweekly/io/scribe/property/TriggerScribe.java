package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.Trigger;
import biweekly.util.Duration;
import java.util.Date;
import java.util.List;

public class TriggerScribe extends ICalPropertyScribe<Trigger> {
    public TriggerScribe() {
        super(Trigger.class, "TRIGGER", ICalDataType.DURATION);
    }

    protected ICalDataType _dataType(Trigger property) {
        return property.getDate() == null ? ICalDataType.DURATION : ICalDataType.DATE_TIME;
    }

    protected String _writeText(Trigger property) {
        Duration duration = property.getDuration();
        if (duration != null) {
            return duration.toString();
        }
        Date date = property.getDate();
        if (date != null) {
            return ICalPropertyScribe.date(date).write();
        }
        return "";
    }

    protected Trigger _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        value = ICalPropertyScribe.unescape(value);
        try {
            return new Trigger(ICalPropertyScribe.date(value).tzid(parameters.getTimezoneId(), warnings).parse());
        } catch (IllegalArgumentException e) {
            try {
                return new Trigger(Duration.parse(value), parameters.getRelated());
            } catch (IllegalArgumentException e2) {
                throw new CannotParseException(25, new Object[0]);
            }
        }
    }

    protected void _writeXml(Trigger property, XCalElement element) {
        Duration duration = property.getDuration();
        if (duration != null) {
            element.append(ICalDataType.DURATION, duration.toString());
            return;
        }
        Date date = property.getDate();
        if (date != null) {
            element.append(ICalDataType.DATE_TIME, ICalPropertyScribe.date(date).extended(true).write());
        } else {
            element.append(this.defaultDataType, "");
        }
    }

    protected Trigger _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        String value = element.first(ICalDataType.DURATION);
        if (value != null) {
            try {
                return new Trigger(Duration.parse(value), parameters.getRelated());
            } catch (IllegalArgumentException e) {
                throw new CannotParseException(26, value);
            }
        }
        value = element.first(ICalDataType.DATE_TIME);
        if (value != null) {
            try {
                return new Trigger(ICalPropertyScribe.date(value).tzid(parameters.getTimezoneId(), warnings).parse());
            } catch (IllegalArgumentException e2) {
                throw new CannotParseException(27, value);
            }
        }
        throw ICalPropertyScribe.missingXmlElements(ICalDataType.DURATION, ICalDataType.DATE_TIME);
    }

    protected JCalValue _writeJson(Trigger property) {
        Duration duration = property.getDuration();
        if (duration != null) {
            return JCalValue.single(duration.toString());
        }
        Date date = property.getDate();
        if (date != null) {
            return JCalValue.single(ICalPropertyScribe.date(date).extended(true).write());
        }
        return JCalValue.single("");
    }

    protected Trigger _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        String valueStr = value.asSingle();
        try {
            return new Trigger(ICalPropertyScribe.date(valueStr).tzid(parameters.getTimezoneId(), warnings).parse());
        } catch (IllegalArgumentException e) {
            try {
                return new Trigger(Duration.parse(valueStr), parameters.getRelated());
            } catch (IllegalArgumentException e2) {
                throw new CannotParseException(25, new Object[0]);
            }
        }
    }
}
