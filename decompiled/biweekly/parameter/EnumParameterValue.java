package biweekly.parameter;

public class EnumParameterValue {
    protected final String value;

    protected EnumParameterValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}
