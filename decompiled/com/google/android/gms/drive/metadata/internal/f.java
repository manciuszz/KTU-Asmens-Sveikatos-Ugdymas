package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.a;

public class f extends a<Integer> {
    public f(String str, int i) {
        super(str, i);
    }

    protected void a(Bundle bundle, Integer num) {
        bundle.putInt(getName(), num.intValue());
    }

    protected /* synthetic */ Object b(DataHolder dataHolder, int i, int i2) {
        return f(dataHolder, i, i2);
    }

    protected Integer f(DataHolder dataHolder, int i, int i2) {
        return Integer.valueOf(dataHolder.b(getName(), i, i2));
    }

    protected /* synthetic */ Object f(Bundle bundle) {
        return i(bundle);
    }

    protected Integer i(Bundle bundle) {
        return Integer.valueOf(bundle.getInt(getName()));
    }
}
