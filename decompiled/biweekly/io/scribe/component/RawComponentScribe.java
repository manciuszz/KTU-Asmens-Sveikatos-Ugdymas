package biweekly.io.scribe.component;

import biweekly.component.RawComponent;

public class RawComponentScribe extends ICalComponentScribe<RawComponent> {
    public RawComponentScribe(String componentName) {
        super(RawComponent.class, componentName);
    }

    protected RawComponent _newInstance() {
        return new RawComponent(this.componentName);
    }
}
