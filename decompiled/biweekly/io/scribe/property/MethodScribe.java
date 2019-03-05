package biweekly.io.scribe.property;

import biweekly.property.Method;

public class MethodScribe extends TextPropertyScribe<Method> {
    public MethodScribe() {
        super(Method.class, "METHOD");
    }

    protected Method newInstance(String value) {
        return new Method(value);
    }
}
