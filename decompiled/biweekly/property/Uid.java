package biweekly.property;

import java.util.UUID;

public class Uid extends TextProperty {
    public Uid(String uid) {
        super(uid);
    }

    public static Uid random() {
        return new Uid(UUID.randomUUID().toString());
    }
}
