package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.DateTimeProperty;
import java.util.Date;
import java.util.List;

public abstract class DateTimePropertyScribe<T extends DateTimeProperty> extends ICalPropertyScribe<T> {
    protected abstract T newInstance(Date date);

    public DateTimePropertyScribe(Class<T> clazz, String propertyName) {
        super(clazz, propertyName, ICalDataType.DATE_TIME);
    }

    protected String _writeText(T property) {
        Date value = (Date) property.getValue();
        if (value != null) {
            return ICalPropertyScribe.date(value).write();
        }
        return "";
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(ICalPropertyScribe.unescape(value), parameters, warnings);
    }

    protected void _writeXml(T property, XCalElement element) {
        String dateStr = null;
        Date value = (Date) property.getValue();
        if (value != null) {
            dateStr = ICalPropertyScribe.date(value).extended(true).write();
        }
        element.append(dataType(property), dateStr);
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        String value = element.first(this.defaultDataType);
        if (value != null) {
            return parse(value, parameters, warnings);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(T property) {
        Date value = (Date) property.getValue();
        if (value != null) {
            return JCalValue.single(ICalPropertyScribe.date(value).extended(true).write());
        }
        return JCalValue.single("");
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(value.asSingle(), parameters, warnings);
    }

    private T parse(String value, ICalParameters parameters, List<Warning> warnings) {
        try {
            return newInstance(ICalPropertyScribe.date(value).tzid(parameters.getTimezoneId(), warnings).parse());
        } catch (IllegalArgumentException e) {
            throw new CannotParseException(17, new Object[0]);
        }
    }
}
