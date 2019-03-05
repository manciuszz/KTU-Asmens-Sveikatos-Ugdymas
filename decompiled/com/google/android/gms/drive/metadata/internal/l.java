package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.a;

public class l extends a<String> {
    public l(String str, int i) {
        super(str, i);
    }

    protected void a(Bundle bundle, String str) {
        bundle.putString(getName(), str);
    }

    protected /* synthetic */ Object b(DataHolder dataHolder, int i, int i2) {
        return h(dataHolder, i, i2);
    }

    protected /* synthetic */ Object f(Bundle bundle) {
        return m(bundle);
    }

    protected String h(DataHolder dataHolder, int i, int i2) {
        return dataHolder.c(getName(), i, i2);
    }

    protected String m(Bundle bundle) {
        return bundle.getString(getName());
    }
}
