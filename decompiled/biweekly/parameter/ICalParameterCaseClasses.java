package biweekly.parameter;

import biweekly.util.CaseClasses;
import java.lang.reflect.Constructor;

public class ICalParameterCaseClasses<T extends EnumParameterValue> extends CaseClasses<T, String> {
    public ICalParameterCaseClasses(Class<T> clazz) {
        super(clazz);
    }

    protected T create(String value) {
        try {
            Constructor<T> constructor = this.clazz.getDeclaredConstructor(new Class[]{String.class});
            constructor.setAccessible(true);
            return (EnumParameterValue) constructor.newInstance(new Object[]{value});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean matches(T object, String value) {
        return object.value.equalsIgnoreCase(value);
    }
}
