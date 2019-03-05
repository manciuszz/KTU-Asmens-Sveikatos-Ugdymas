package biweekly.property;

import java.util.Arrays;
import java.util.Collection;

public class Status extends EnumProperty {
    private static final String CANCELLED = "CANCELLED";
    private static final String COMPLETED = "COMPLETED";
    private static final String CONFIRMED = "CONFIRMED";
    private static final String DRAFT = "DRAFT";
    private static final String FINAL = "FINAL";
    private static final String IN_PROGRESS = "IN-PROGRESS";
    private static final String NEEDS_ACTION = "NEEDS-ACTION";
    private static final String TENTATIVE = "TENTATIVE";

    public Status(String status) {
        super(status);
    }

    public static Status tentative() {
        return create(TENTATIVE);
    }

    public boolean isTentative() {
        return is(TENTATIVE);
    }

    public static Status confirmed() {
        return create(CONFIRMED);
    }

    public boolean isConfirmed() {
        return is(CONFIRMED);
    }

    public static Status cancelled() {
        return create(CANCELLED);
    }

    public boolean isCancelled() {
        return is(CANCELLED);
    }

    public static Status needsAction() {
        return create(NEEDS_ACTION);
    }

    public boolean isNeedsAction() {
        return is(NEEDS_ACTION);
    }

    public static Status completed() {
        return create(COMPLETED);
    }

    public boolean isCompleted() {
        return is(COMPLETED);
    }

    public static Status inProgress() {
        return create(IN_PROGRESS);
    }

    public boolean isInProgress() {
        return is(IN_PROGRESS);
    }

    public static Status draft() {
        return create(DRAFT);
    }

    public boolean isDraft() {
        return is(DRAFT);
    }

    public static Status final_() {
        return create(FINAL);
    }

    public boolean isFinal() {
        return is(FINAL);
    }

    private static Status create(String status) {
        return new Status(status);
    }

    protected Collection<String> getStandardValues() {
        return Arrays.asList(new String[]{TENTATIVE, CONFIRMED, CANCELLED, NEEDS_ACTION, COMPLETED, IN_PROGRESS, DRAFT, FINAL});
    }
}
