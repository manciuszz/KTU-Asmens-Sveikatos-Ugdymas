package biweekly.io.json;

import java.util.List;
import java.util.Map;

public class JsonValue {
    private final List<JsonValue> array;
    private final boolean isNull;
    private final Map<String, JsonValue> object;
    private final Object value;

    public JsonValue(Object value) {
        this.value = value;
        this.array = null;
        this.object = null;
        this.isNull = value == null;
    }

    public JsonValue(List<JsonValue> array) {
        this.array = array;
        this.value = null;
        this.object = null;
        this.isNull = array == null;
    }

    public JsonValue(Map<String, JsonValue> object) {
        this.object = object;
        this.value = null;
        this.array = null;
        this.isNull = object == null;
    }

    public Object getValue() {
        return this.value;
    }

    public List<JsonValue> getArray() {
        return this.array;
    }

    public Map<String, JsonValue> getObject() {
        return this.object;
    }

    public boolean isNull() {
        return this.isNull;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((this.array == null ? 0 : this.array.hashCode()) + 31) * 31) + (this.isNull ? 1231 : 1237)) * 31) + (this.object == null ? 0 : this.object.hashCode())) * 31;
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
        JsonValue other = (JsonValue) obj;
        if (this.array == null) {
            if (other.array != null) {
                return false;
            }
        } else if (!this.array.equals(other.array)) {
            return false;
        }
        if (this.isNull != other.isNull) {
            return false;
        }
        if (this.object == null) {
            if (other.object != null) {
                return false;
            }
        } else if (!this.object.equals(other.object)) {
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

    public String toString() {
        if (this.isNull) {
            return "NULL";
        }
        if (this.value != null) {
            return "VALUE = " + this.value;
        }
        if (this.array != null) {
            return "ARRAY = " + this.array;
        }
        if (this.object != null) {
            return "OBJECT = " + this.object;
        }
        return "";
    }
}
