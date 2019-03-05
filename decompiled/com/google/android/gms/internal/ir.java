package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.j;
import java.util.Arrays;
import java.util.Collections;

public class ir extends j<AppVisibleCustomProperties> {
    public ir(int i) {
        super("customFileProperties", Collections.emptyList(), Arrays.asList(new String[]{"customPropertiesExtra"}), i);
    }

    protected /* synthetic */ Object b(DataHolder dataHolder, int i, int i2) {
        return j(dataHolder, i, i2);
    }

    protected AppVisibleCustomProperties j(DataHolder dataHolder, int i, int i2) {
        return (AppVisibleCustomProperties) dataHolder.eU().getSparseParcelableArray("customPropertiesExtra").get(i, AppVisibleCustomProperties.JN);
    }
}
