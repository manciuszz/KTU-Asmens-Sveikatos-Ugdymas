package biweekly.io.scribe.component;

import biweekly.component.ICalComponent;
import biweekly.property.ICalProperty;
import java.util.Collection;
import java.util.List;

public abstract class ICalComponentScribe<T extends ICalComponent> {
    protected final Class<T> clazz;
    protected final String componentName;

    protected abstract T _newInstance();

    public ICalComponentScribe(Class<T> clazz, String componentName) {
        this.clazz = clazz;
        this.componentName = componentName;
    }

    public Class<T> getComponentClass() {
        return this.clazz;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public T emptyInstance() {
        T component = _newInstance();
        component.getProperties().clear();
        component.getComponents().clear();
        return component;
    }

    public Collection<ICalComponent> getComponents(T component) {
        return component.getComponents().values();
    }

    public List<ICalProperty> getProperties(T component) {
        return component.getProperties().values();
    }
}
