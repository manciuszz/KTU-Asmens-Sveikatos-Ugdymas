package com.google.android.gms.internal;

import android.content.Intent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.panorama.PanoramaApi.a;

public class kc extends kh implements a {
    private final int abj;

    public kc(Status status, Intent intent, int i) {
        super(status, intent);
        this.abj = i;
    }

    public /* bridge */ /* synthetic */ Status getStatus() {
        return super.getStatus();
    }

    public /* bridge */ /* synthetic */ Intent getViewerIntent() {
        return super.getViewerIntent();
    }
}
