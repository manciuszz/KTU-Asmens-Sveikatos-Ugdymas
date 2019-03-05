package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.IntegerProperty;
import java.util.List;

public abstract class IntegerPropertyScribe<T extends IntegerProperty> extends ICalPropertyScribe<T> {
    protected abstract T newInstance(Integer num);

    public IntegerPropertyScribe(Class<T> clazz, String propertyName) {
        super(clazz, propertyName, ICalDataType.INTEGER);
    }

    protected String _writeText(T property) {
        Integer value = (Integer) property.getValue();
        if (value != null) {
            return value.toString();
        }
        return "";
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return parse(ICalPropertyScribe.unescape(value));
    }

    protected void _writeXml(T property, XCalElement element) {
        String valueStr = null;
        Integer value = (Integer) property.getValue();
        if (value != null) {
            valueStr = value.toString();
        }
        element.append(dataType(property), valueStr);
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String value = element.first(this.defaultDataType);
        if (value != null) {
            return parse(value);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(T property) {
        return JCalValue.single(property.getValue());
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return parse(value.asSingle());
    }

    private T parse(String value) {
        if (value == null || value.length() == 0) {
            return newInstance(null);
        }
        try {
            return newInstance(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            throw new CannotParseException(24, new Object[0]);
        }
    }
}
