package biweekly.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class CaseClasses<T, V> {
    protected final Class<T> clazz;
    protected volatile Collection<T> preDefined = null;
    protected Collection<T> runtimeDefined = null;

    protected abstract T create(V v);

    protected abstract boolean matches(T t, V v);

    public CaseClasses(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T find(V value) {
        checkInit();
        for (T obj : this.preDefined) {
            if (matches(obj, value)) {
                return obj;
            }
        }
        return null;
    }

    public T get(V value) {
        T found = find(value);
        if (found != null) {
            return found;
        }
        synchronized (this.runtimeDefined) {
            for (T obj : this.runtimeDefined) {
                if (matches(obj, value)) {
                    return obj;
                }
            }
            T created = create(value);
            this.runtimeDefined.add(created);
            return created;
        }
    }

    public Collection<T> all() {
        checkInit();
        return this.preDefined;
    }

    private void checkInit() {
        if (this.preDefined == null) {
            synchronized (this) {
                if (this.preDefined == null) {
                    init();
                }
            }
        }
    }

    private void init() {
        Collection<T> preDefined = new ArrayList();
        for (Field field : this.clazz.getFields()) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && field.getDeclaringClass() == this.clazz && field.getType() == this.clazz) {
                try {
                    Object obj = field.get(null);
                    if (obj != null) {
                        preDefined.add(this.clazz.cast(obj));
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        this.runtimeDefined = new ArrayList(0);
        this.preDefined = Collections.unmodifiableCollection(preDefined);
    }
}
