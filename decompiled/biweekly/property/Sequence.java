package biweekly.property;

public class Sequence extends IntegerProperty {
    public Sequence(Integer sequence) {
        super(sequence);
    }

    public void increment() {
        if (this.value == null) {
            this.value = Integer.valueOf(1);
            return;
        }
        Integer num = (Integer) this.value;
        this.value = Integer.valueOf(((Integer) this.value).intValue() + 1);
    }
}
