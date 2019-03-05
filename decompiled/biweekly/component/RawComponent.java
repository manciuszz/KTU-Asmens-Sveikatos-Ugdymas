package biweekly.component;

public class RawComponent extends ICalComponent {
    private final String name;

    public RawComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
