package biweekly;

import biweekly.component.ICalComponent;
import biweekly.property.ICalProperty;
import biweekly.util.StringUtils;
import biweekly.util.StringUtils.JoinCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValidationWarnings implements Iterable<WarningsGroup> {
    private final List<WarningsGroup> warnings;

    public static class WarningsGroup {
        private final ICalComponent component;
        private final List<ICalComponent> componentHierarchy;
        private final ICalProperty property;
        private final List<Warning> warnings;

        public WarningsGroup(ICalProperty property, List<ICalComponent> componentHierarchy, List<Warning> warning) {
            this(null, property, componentHierarchy, warning);
        }

        public WarningsGroup(ICalComponent component, List<ICalComponent> componentHierarchy, List<Warning> warning) {
            this(component, null, componentHierarchy, warning);
        }

        private WarningsGroup(ICalComponent component, ICalProperty property, List<ICalComponent> componentHierarchy, List<Warning> warning) {
            this.component = component;
            this.property = property;
            this.componentHierarchy = componentHierarchy;
            this.warnings = warning;
        }

        public ICalProperty getProperty() {
            return this.property;
        }

        public ICalComponent getComponent() {
            return this.component;
        }

        public List<ICalComponent> getComponentHierarchy() {
            return this.componentHierarchy;
        }

        public List<Warning> getWarnings() {
            return this.warnings;
        }

        public String toString() {
            final String prefix = "[" + buildPath() + "]: ";
            return StringUtils.join(this.warnings, StringUtils.NEWLINE, new JoinCallback<Warning>() {
                public void handle(StringBuilder sb, Warning warning) {
                    sb.append(prefix).append(warning);
                }
            });
        }

        private String buildPath() {
            StringBuilder sb = new StringBuilder();
            if (!this.componentHierarchy.isEmpty()) {
                String delimitor = " > ";
                StringUtils.join(this.componentHierarchy, delimitor, sb, new JoinCallback<ICalComponent>() {
                    public void handle(StringBuilder sb, ICalComponent component) {
                        sb.append(component.getClass().getSimpleName());
                    }
                });
                sb.append(delimitor);
            }
            sb.append((this.property == null ? this.component : this.property).getClass().getSimpleName());
            return sb.toString();
        }
    }

    public ValidationWarnings(List<WarningsGroup> warnings) {
        this.warnings = warnings;
    }

    public List<WarningsGroup> getByProperty(Class<? extends ICalProperty> propertyClass) {
        List<WarningsGroup> warnings = new ArrayList();
        for (WarningsGroup group : this.warnings) {
            ICalProperty property = group.getProperty();
            if (property != null && propertyClass == property.getClass()) {
                warnings.add(group);
            }
        }
        return warnings;
    }

    public List<WarningsGroup> getByComponent(Class<? extends ICalComponent> componentClass) {
        List<WarningsGroup> warnings = new ArrayList();
        for (WarningsGroup group : this.warnings) {
            ICalComponent component = group.getComponent();
            if (component != null && componentClass == component.getClass()) {
                warnings.add(group);
            }
        }
        return warnings;
    }

    public List<WarningsGroup> getWarnings() {
        return this.warnings;
    }

    public boolean isEmpty() {
        return this.warnings.isEmpty();
    }

    public String toString() {
        return StringUtils.join(this.warnings, StringUtils.NEWLINE);
    }

    public Iterator<WarningsGroup> iterator() {
        return this.warnings.iterator();
    }
}
