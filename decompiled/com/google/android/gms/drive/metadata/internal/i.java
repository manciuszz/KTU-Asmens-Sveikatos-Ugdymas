package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.b;
import java.util.ArrayList;
import java.util.Collection;

public class i<T extends Parcelable> extends b<T> {
    public i(String str, int i) {
        super(str, i);
    }

    protected void a(Bundle bundle, Collection<T> collection) {
        bundle.putParcelableArrayList(getName(), new ArrayList(collection));
    }

    protected /* synthetic */ Object f(Bundle bundle) {
        return k(bundle);
    }

    protected Collection<T> k(Bundle bundle) {
        return bundle.getParcelableArrayList(getName());
    }
}
