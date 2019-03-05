package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.ListProperty;
import java.util.List;

public abstract class ListPropertyScribe<T extends ListProperty<V>, V> extends ICalPropertyScribe<T> {
    protected abstract T newInstance(ICalDataType iCalDataType, ICalParameters iCalParameters);

    protected abstract V readValue(String str, ICalDataType iCalDataType, ICalParameters iCalParameters, List<Warning> list);

    protected abstract String writeValue(T t, V v);

    public ListPropertyScribe(Class<T> clazz, String propertyName) {
        this(clazz, propertyName, ICalDataType.TEXT);
    }

    public ListPropertyScribe(Class<T> clazz, String propertyName, ICalDataType dataType) {
        super(clazz, propertyName, dataType);
    }

    protected String _writeText(final T property) {
        return ICalPropertyScribe.list(property.getValues(), new ListCallback<V>() {
            public String asString(V value) {
                return ListPropertyScribe.this.writeValue(property, value);
            }
        });
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(ICalPropertyScribe.list(value), dataType, parameters, warnings);
    }

    protected void _writeXml(T property, XCalElement element) {
        for (V value : property.getValues()) {
            element.append(dataType(property), writeValue(property, value));
        }
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        List<String> values = element.all(this.defaultDataType);
        if (!values.isEmpty()) {
            return parse(values, this.defaultDataType, parameters, warnings);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(T property) {
        if (property.getValues().isEmpty()) {
            return JCalValue.single("");
        }
        return JCalValue.multi(property.getValues());
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(value.asMulti(), dataType, parameters, warnings);
    }

    private T parse(List<String> valueStrs, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        T property = newInstance(dataType, parameters);
        for (String valueStr : valueStrs) {
            property.addValue(readValue(valueStr, dataType, parameters, warnings));
        }
        return property;
    }
}
