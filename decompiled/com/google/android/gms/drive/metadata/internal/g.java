package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.a;

public class g extends a<Long> {
    public g(String str, int i) {
        super(str, i);
    }

    protected void a(Bundle bundle, Long l) {
        bundle.putLong(getName(), l.longValue());
    }

    protected /* synthetic */ Object b(DataHolder dataHolder, int i, int i2) {
        return g(dataHolder, i, i2);
    }

    protected /* synthetic */ Object f(Bundle bundle) {
        return j(bundle);
    }

    protected Long g(DataHolder dataHolder, int i, int i2) {
        return Long.valueOf(dataHolder.a(getName(), i, i2));
    }

    protected Long j(Bundle bundle) {
        return Long.valueOf(bundle.getLong(getName()));
    }
}
