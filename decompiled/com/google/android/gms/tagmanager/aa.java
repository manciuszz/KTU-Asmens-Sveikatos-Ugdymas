package com.google.android.gms.tagmanager;

import android.os.Build;
import android.support.v4.os.EnvironmentCompat;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import java.util.Map;

class aa extends aj {
    private static final String ID = a.DEVICE_NAME.toString();

    public aa() {
        super(ID, new String[0]);
    }

    public boolean lh() {
        return true;
    }

    public d.a w(Map<String, d.a> map) {
        String str = Build.MANUFACTURER;
        Object obj = Build.MODEL;
        if (!(obj.startsWith(str) || str.equals(EnvironmentCompat.MEDIA_UNKNOWN))) {
            obj = str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + obj;
        }
        return dh.r(obj);
    }
}
