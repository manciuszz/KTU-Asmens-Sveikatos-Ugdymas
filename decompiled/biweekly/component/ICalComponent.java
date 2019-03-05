package biweekly.component;

import biweekly.ICalDataType;
import biweekly.ValidationWarnings.WarningsGroup;
import biweekly.Warning;
import biweekly.property.ICalProperty;
import biweekly.property.RawProperty;
import biweekly.property.Status;
import biweekly.util.ListMultimap;
import java.util.ArrayList;
import java.util.List;

public abstract class ICalComponent {
    protected final ListMultimap<Class<? extends ICalComponent>, ICalComponent> components = new ListMultimap();
    protected final ListMultimap<Class<? extends ICalProperty>, ICalProperty> properties = new ListMultimap();

    public <T extends ICalProperty> T getProperty(Class<T> clazz) {
        return (ICalProperty) clazz.cast(this.properties.first(clazz));
    }

    public <T extends ICalProperty> List<T> getProperties(Class<T> clazz) {
        List<ICalProperty> props = this.properties.get(clazz);
        List<T> ret = new ArrayList(props.size());
        for (ICalProperty property : props) {
            ret.add(clazz.cast(property));
        }
        return ret;
    }

    public ListMultimap<Class<? extends ICalProperty>, ICalProperty> getProperties() {
        return this.properties;
    }

    public void addProperty(ICalProperty property) {
        this.properties.put(property.getClass(), property);
    }

    public void setProperty(ICalProperty property) {
        this.properties.replace(property.getClass(), (Object) property);
    }

    public <T extends ICalProperty> void setProperty(Class<T> clazz, T property) {
        this.properties.replace((Object) clazz, (Object) property);
    }

    public void removeProperties(Class<? extends ICalProperty> clazz) {
        this.properties.removeAll(clazz);
    }

    public RawProperty getExperimentalProperty(String name) {
        for (RawProperty raw : getProperties(RawProperty.class)) {
            if (raw.getName().equalsIgnoreCase(name)) {
                return raw;
            }
        }
        return null;
    }

    public List<RawProperty> getExperimentalProperties(String name) {
        List<RawProperty> props = new ArrayList();
        for (RawProperty raw : getProperties(RawProperty.class)) {
            if (raw.getName().equalsIgnoreCase(name)) {
                props.add(raw);
            }
        }
        return props;
    }

    public List<RawProperty> getExperimentalProperties() {
        return getProperties(RawProperty.class);
    }

    public RawProperty addExperimentalProperty(String name, String value) {
        return addExperimentalProperty(name, null, value);
    }

    public RawProperty addExperimentalProperty(String name, ICalDataType dataType, String value) {
        RawProperty raw = new RawProperty(name, dataType, value);
        addProperty(raw);
        return raw;
    }

    public RawProperty setExperimentalProperty(String name, String value) {
        return setExperimentalProperty(name, null, value);
    }

    public RawProperty setExperimentalProperty(String name, ICalDataType dataType, String value) {
        removeExperimentalProperty(name);
        return addExperimentalProperty(name, dataType, value);
    }

    public void removeExperimentalProperty(String name) {
        for (RawProperty xproperty : getExperimentalProperties(name)) {
            this.properties.remove(xproperty.getClass(), xproperty);
        }
    }

    public <T extends ICalComponent> T getComponent(Class<T> clazz) {
        return (ICalComponent) clazz.cast(this.components.first(clazz));
    }

    public <T extends ICalComponent> List<T> getComponents(Class<T> clazz) {
        List<ICalComponent> comp = this.components.get(clazz);
        List<T> ret = new ArrayList(comp.size());
        for (ICalComponent property : comp) {
            ret.add(clazz.cast(property));
        }
        return ret;
    }

    public ListMultimap<Class<? extends ICalComponent>, ICalComponent> getComponents() {
        return this.components;
    }

    public void addComponent(ICalComponent component) {
        this.components.put(component.getClass(), component);
    }

    public void setComponent(ICalComponent component) {
        this.components.replace(component.getClass(), (Object) component);
    }

    public <T extends ICalComponent> void setComponent(Class<T> clazz, T component) {
        this.components.replace((Object) clazz, (Object) component);
    }

    public RawComponent getExperimentalComponent(String name) {
        for (RawComponent raw : getComponents(RawComponent.class)) {
            if (raw.getName().equalsIgnoreCase(name)) {
                return raw;
            }
        }
        return null;
    }

    public List<RawComponent> getExperimentalComponents(String name) {
        List<RawComponent> props = new ArrayList();
        for (RawComponent raw : getComponents(RawComponent.class)) {
            if (raw.getName().equalsIgnoreCase(name)) {
                props.add(raw);
            }
        }
        return props;
    }

    public List<RawComponent> getExperimentalComponents() {
        return getComponents(RawComponent.class);
    }

    public RawComponent addExperimentalComponent(String name) {
        RawComponent raw = new RawComponent(name);
        addComponent(raw);
        return raw;
    }

    public RawComponent setExperimentalComponents(String name) {
        removeExperimentalComponents(name);
        return addExperimentalComponent(name);
    }

    public void removeExperimentalComponents(String name) {
        for (RawComponent xcomponent : getExperimentalComponents(name)) {
            this.components.remove(xcomponent.getClass(), xcomponent);
        }
    }

    public final List<WarningsGroup> validate(List<ICalComponent> hierarchy) {
        List<WarningsGroup> warnings = new ArrayList();
        List warningsBuf = new ArrayList(0);
        validate(hierarchy, warningsBuf);
        if (!warningsBuf.isEmpty()) {
            warnings.add(new WarningsGroup(this, (List) hierarchy, warningsBuf));
        }
        List hierarchy2 = new ArrayList(hierarchy);
        hierarchy2.add(this);
        for (ICalProperty property : this.properties.values()) {
            List propWarnings = property.validate(hierarchy2);
            if (!propWarnings.isEmpty()) {
                warnings.add(new WarningsGroup(property, hierarchy2, propWarnings));
            }
        }
        for (ICalComponent component : this.components.values()) {
            warnings.addAll(component.validate(hierarchy2));
        }
        return warnings;
    }

    protected void validate(List<ICalComponent> list, List<Warning> list2) {
    }

    protected void checkRequiredCardinality(List<Warning> warnings, Class<? extends ICalProperty>... classes) {
        for (Class<? extends ICalProperty> clazz : classes) {
            List<? extends ICalProperty> props = getProperties(clazz);
            if (props.isEmpty()) {
                warnings.add(Warning.validate(2, clazz.getSimpleName()));
            } else if (props.size() > 1) {
                warnings.add(Warning.validate(3, clazz.getSimpleName()));
            }
        }
    }

    protected void checkOptionalCardinality(List<Warning> warnings, Class<? extends ICalProperty>... classes) {
        for (Class<? extends ICalProperty> clazz : classes) {
            if (getProperties(clazz).size() > 1) {
                warnings.add(Warning.validate(3, arr$[i$].getSimpleName()));
            }
        }
    }

    protected void checkStatus(List<Warning> warnings, Status... allowed) {
        Status actual = (Status) getProperty(Status.class);
        if (actual != null) {
            List<String> allowedValues = new ArrayList(allowed.length);
            for (Status status : allowed) {
                allowedValues.add(((String) status.getValue()).toLowerCase());
            }
            if (!allowedValues.contains(((String) actual.getValue()).toLowerCase())) {
                warnings.add(Warning.validate(13, actual.getValue(), allowedValues));
            }
        }
    }
}
