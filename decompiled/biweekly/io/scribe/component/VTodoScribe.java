package biweekly.io.scribe.component;

import biweekly.component.VTodo;

public class VTodoScribe extends ICalComponentScribe<VTodo> {
    public VTodoScribe() {
        super(VTodo.class, "VTODO");
    }

    protected VTodo _newInstance() {
        return new VTodo();
    }
}
