package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.j;
import java.util.Arrays;

public class it extends j<DriveId> {
    public static final it KB = new it();

    private it() {
        super("driveId", Arrays.asList(new String[]{"sqlId", "resourceId"}), Arrays.asList(new String[]{"dbInstanceId"}), 4100000);
    }

    protected /* synthetic */ Object b(DataHolder dataHolder, int i, int i2) {
        return k(dataHolder, i, i2);
    }

    protected DriveId k(DataHolder dataHolder, int i, int i2) {
        long j = dataHolder.eU().getLong("dbInstanceId");
        String c = dataHolder.c("resourceId", i, i2);
        if (c != null && c.startsWith("generated-android-")) {
            c = null;
        }
        return new DriveId(c, Long.valueOf(dataHolder.a("sqlId", i, i2)).longValue(), j);
    }
}
