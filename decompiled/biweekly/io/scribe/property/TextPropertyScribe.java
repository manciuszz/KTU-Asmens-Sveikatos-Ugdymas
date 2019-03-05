package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.TextProperty;
import java.util.List;

public abstract class TextPropertyScribe<T extends TextProperty> extends ICalPropertyScribe<T> {
    protected abstract T newInstance(String str);

    public TextPropertyScribe(Class<T> clazz, String propertyName) {
        this(clazz, propertyName, ICalDataType.TEXT);
    }

    public TextPropertyScribe(Class<T> clazz, String propertyName, ICalDataType dataType) {
        super(clazz, propertyName, dataType);
    }

    protected String _writeText(T property) {
        String value = (String) property.getValue();
        if (value != null) {
            return ICalPropertyScribe.escape(value);
        }
        return "";
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return newInstance(ICalPropertyScribe.unescape(value));
    }

    protected void _writeXml(T property, XCalElement element) {
        element.append(dataType(property), (String) property.getValue());
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String value = element.first(this.defaultDataType);
        if (value != null) {
            return newInstance(value);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(T property) {
        return JCalValue.single(property.getValue());
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return newInstance(value.asSingle());
    }
}
