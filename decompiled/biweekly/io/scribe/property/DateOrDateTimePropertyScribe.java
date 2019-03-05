package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.DateOrDateTimeProperty;
import biweekly.util.DateTimeComponents;
import biweekly.util.ICalDateFormat;
import java.util.Date;
import java.util.List;

public abstract class DateOrDateTimePropertyScribe<T extends DateOrDateTimeProperty> extends ICalPropertyScribe<T> {
    protected abstract T newInstance(Date date, boolean z);

    public DateOrDateTimePropertyScribe(Class<T> clazz, String propertyName) {
        super(clazz, propertyName, ICalDataType.DATE_TIME);
    }

    protected ICalDataType _dataType(T property) {
        return (property.getRawComponents() != null || property.getValue() == null || property.hasTime()) ? ICalDataType.DATE_TIME : ICalDataType.DATE;
    }

    protected String _writeText(T property) {
        DateTimeComponents components = property.getRawComponents();
        if (components != null) {
            return components.toString(false);
        }
        Date value = property.getValue();
        if (value != null) {
            return ICalPropertyScribe.date(value).time(property.hasTime()).tz(property.isLocalTime(), property.getTimezoneId()).write();
        }
        return "";
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(ICalPropertyScribe.unescape(value), parameters, warnings);
    }

    protected void _writeXml(T property, XCalElement element) {
        String dateStr = null;
        Date value = property.getValue();
        DateTimeComponents components = property.getRawComponents();
        if (components != null) {
            dateStr = components.toString(true);
        } else if (value != null) {
            dateStr = ICalPropertyScribe.date(value).time(property.hasTime()).tz(property.isLocalTime(), property.getTimezoneId()).extended(true).write();
        }
        element.append(dataType(property), dateStr);
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        String value = element.first(ICalDataType.DATE_TIME);
        if (value == null) {
            value = element.first(ICalDataType.DATE);
        }
        if (value != null) {
            return parse(value, parameters, warnings);
        }
        throw ICalPropertyScribe.missingXmlElements(ICalDataType.DATE_TIME, ICalDataType.DATE);
    }

    protected JCalValue _writeJson(T property) {
        DateTimeComponents components = property.getRawComponents();
        if (components != null) {
            return JCalValue.single(components.toString(true));
        }
        Date value = property.getValue();
        if (value != null) {
            return JCalValue.single(ICalPropertyScribe.date(value).time(property.hasTime()).tz(property.isLocalTime(), property.getTimezoneId()).extended(true).write());
        }
        return JCalValue.single("");
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(value.asSingle(), parameters, warnings);
    }

    private T parse(String value, ICalParameters parameters, List<Warning> warnings) {
        boolean localTz = true;
        if (value == null) {
            return newInstance(null, true);
        }
        try {
            DateTimeComponents components;
            Date date = ICalPropertyScribe.date(value).tzid(parameters.getTimezoneId(), warnings).parse();
            try {
                components = DateTimeComponents.parse(value);
            } catch (IllegalArgumentException e) {
                warnings.add(Warning.parse(6, value));
                components = null;
            }
            boolean hasTime = ICalDateFormat.dateHasTime(value);
            if (ICalDateFormat.dateHasTimezone(value) || parameters.getTimezoneId() != null) {
                localTz = false;
            }
            T prop = newInstance(date, hasTime);
            prop.setRawComponents(components);
            prop.setLocalTime(localTz);
            return prop;
        } catch (IllegalArgumentException e2) {
            throw new CannotParseException(17, new Object[0]);
        }
    }
}
