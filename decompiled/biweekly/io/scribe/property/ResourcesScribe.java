package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.parameter.ICalParameters;
import biweekly.property.Resources;

public class ResourcesScribe extends TextListPropertyScribe<Resources> {
    public ResourcesScribe() {
        super(Resources.class, "RESOURCES");
    }

    public Resources newInstance(ICalDataType dataType, ICalParameters parameters) {
        return new Resources();
    }
}
