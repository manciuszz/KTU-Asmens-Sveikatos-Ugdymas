package biweekly.property;

public class Priority extends IntegerProperty {
    public Priority(Integer priority) {
        super(priority);
    }

    public boolean isHigh() {
        return this.value != null && ((Integer) this.value).intValue() >= 1 && ((Integer) this.value).intValue() <= 4;
    }

    public boolean isMedium() {
        return this.value != null && ((Integer) this.value).intValue() == 5;
    }

    public boolean isLow() {
        return this.value != null && ((Integer) this.value).intValue() >= 6 && ((Integer) this.value).intValue() <= 9;
    }

    public boolean isUndefined() {
        return this.value != null && ((Integer) this.value).intValue() == 0;
    }

    public String toCuaPriority() {
        if (this.value == null || ((Integer) this.value).intValue() < 1 || ((Integer) this.value).intValue() > 9) {
            return null;
        }
        return ((char) (((((Integer) this.value).intValue() - 1) / 3) + 65)) + "" + (((((Integer) this.value).intValue() - 1) % 3) + 1);
    }
}
