package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.UtcOffsetProperty;
import biweekly.util.UtcOffset;
import java.util.List;

public abstract class UtcOffsetPropertyScribe<T extends UtcOffsetProperty> extends ICalPropertyScribe<T> {
    protected abstract T newInstance(UtcOffset utcOffset);

    public UtcOffsetPropertyScribe(Class<T> clazz, String propertyName) {
        super(clazz, propertyName, ICalDataType.UTC_OFFSET);
    }

    protected String _writeText(T property) {
        UtcOffset offset = (UtcOffset) property.getValue();
        if (offset != null) {
            return offset.toString(false);
        }
        return "";
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return parse(ICalPropertyScribe.unescape(value));
    }

    protected void _writeXml(T property, XCalElement element) {
        String offsetStr = null;
        UtcOffset offset = (UtcOffset) property.getValue();
        if (offset != null) {
            offsetStr = offset.toString(true);
        }
        element.append(dataType(property), offsetStr);
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String value = element.first(this.defaultDataType);
        if (value != null) {
            return parse(value);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(T property) {
        UtcOffset offset = (UtcOffset) property.getValue();
        if (offset != null) {
            return JCalValue.single(offset.toString(true));
        }
        return JCalValue.single("");
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return parse(value.asSingle());
    }

    private T parse(String value) {
        if (value == null) {
            return newInstance(null);
        }
        try {
            return newInstance(UtcOffset.parse(value));
        } catch (IllegalArgumentException e) {
            throw new CannotParseException(28, new Object[0]);
        }
    }
}
