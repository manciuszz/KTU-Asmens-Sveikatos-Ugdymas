package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.parameter.ICalParameters;
import biweekly.property.ListProperty;
import java.util.List;

public abstract class TextListPropertyScribe<T extends ListProperty<String>> extends ListPropertyScribe<T, String> {
    public TextListPropertyScribe(Class<T> clazz, String propertyName) {
        super(clazz, propertyName, ICalDataType.TEXT);
    }

    protected String writeValue(T t, String value) {
        return value;
    }

    protected String readValue(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return value;
    }
}
