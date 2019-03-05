package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.a;
import com.google.android.gms.internal.d;
import java.util.Map;

class z extends aj {
    private static final String ID = a.DEVICE_ID.toString();
    private final Context mContext;

    public z(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    protected String O(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    public boolean lh() {
        return true;
    }

    public d.a w(Map<String, d.a> map) {
        String O = O(this.mContext);
        return O == null ? dh.nd() : dh.r(O);
    }
}
