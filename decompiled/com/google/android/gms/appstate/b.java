package com.google.android.gms.appstate;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.d;

public final class b extends d implements AppState {
    b(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public AppState dS() {
        return new a(this);
    }

    public boolean equals(Object obj) {
        return a.a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return dS();
    }

    public byte[] getConflictData() {
        return getByteArray("conflict_data");
    }

    public String getConflictVersion() {
        return getString("conflict_version");
    }

    public int getKey() {
        return getInteger("key");
    }

    public byte[] getLocalData() {
        return getByteArray("local_data");
    }

    public String getLocalVersion() {
        return getString("local_version");
    }

    public boolean hasConflict() {
        return !ax("conflict_version");
    }

    public int hashCode() {
        return a.a(this);
    }

    public String toString() {
        return a.b(this);
    }
}
