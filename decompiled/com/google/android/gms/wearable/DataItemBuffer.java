package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;
import com.google.android.gms.wearable.internal.o;
import cz.msebera.android.httpclient.cookie.ClientCookie;

public class DataItemBuffer extends g<DataItem> implements Result {
    private final Status yz;

    public DataItemBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.yz = new Status(dataHolder.getStatusCode());
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return n(i, i2);
    }

    protected String eZ() {
        return ClientCookie.PATH_ATTR;
    }

    public Status getStatus() {
        return this.yz;
    }

    protected DataItem n(int i, int i2) {
        return new o(this.DG, i, i2);
    }
}
