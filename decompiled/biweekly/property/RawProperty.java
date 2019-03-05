package biweekly.property;

import biweekly.ICalDataType;

public class RawProperty extends ICalProperty {
    private ICalDataType dataType;
    private String name;
    private String value;

    public RawProperty(String name, String value) {
        this(name, null, value);
    }

    public RawProperty(String name, ICalDataType dataType, String value) {
        this.name = name;
        this.dataType = dataType;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public ICalDataType getDataType() {
        return this.dataType;
    }

    public String getName() {
        return this.name;
    }
}
