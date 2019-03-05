package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;
import com.google.android.gms.wearable.internal.h;
import cz.msebera.android.httpclient.cookie.ClientCookie;

public class DataEventBuffer extends g<DataEvent> implements Result {
    private final Status yz;

    public DataEventBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.yz = new Status(dataHolder.getStatusCode());
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return m(i, i2);
    }

    protected String eZ() {
        return ClientCookie.PATH_ATTR;
    }

    public Status getStatus() {
        return this.yz;
    }

    protected DataEvent m(int i, int i2) {
        return new h(this.DG, i, i2);
    }
}
