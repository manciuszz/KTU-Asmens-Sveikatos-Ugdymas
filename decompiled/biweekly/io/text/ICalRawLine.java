package biweekly.io.text;

import biweekly.parameter.ICalParameters;
import java.util.Arrays;

public class ICalRawLine {
    private final String name;
    private final ICalParameters parameters;
    private final String value;

    public static class Builder {
        private String name;
        private ICalParameters parameters = new ICalParameters();
        private String value;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder param(String name, String... values) {
            this.parameters.putAll(name, Arrays.asList(values));
            return this;
        }

        public ICalRawLine build() {
            if (this.name != null) {
                return new ICalRawLine(this.name, this.parameters, this.value);
            }
            throw new IllegalArgumentException("Property name required.");
        }
    }

    public ICalRawLine(String name, ICalParameters parameters, String value) {
        this.name = name;
        this.value = value;
        this.parameters = parameters;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public ICalParameters getParameters() {
        return this.parameters;
    }

    public String toString() {
        return this.name + this.parameters + ":" + this.value;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((this.name == null ? 0 : this.name.hashCode()) + 31) * 31) + (this.parameters == null ? 0 : this.parameters.hashCode())) * 31;
        if (this.value != null) {
            i = this.value.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ICalRawLine other = (ICalRawLine) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.parameters == null) {
            if (other.parameters != null) {
                return false;
            }
        } else if (!this.parameters.equals(other.parameters)) {
            return false;
        }
        if (this.value == null) {
            if (other.value != null) {
                return false;
            }
            return true;
        } else if (this.value.equals(other.value)) {
            return true;
        } else {
            return false;
        }
    }
}
