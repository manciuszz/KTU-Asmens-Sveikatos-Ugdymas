package biweekly.io.scribe.property;

import biweekly.property.Action;

public class ActionScribe extends TextPropertyScribe<Action> {
    public ActionScribe() {
        super(Action.class, "ACTION");
    }

    protected Action newInstance(String value) {
        return new Action(value);
    }
}
