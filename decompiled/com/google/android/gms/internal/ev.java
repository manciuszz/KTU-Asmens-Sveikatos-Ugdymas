package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ev implements SafeParcelable {
    public static final ew CREATOR = new ew();
    public String sw;
    public int sx;
    public int sy;
    public boolean sz;
    public final int versionCode;

    public ev(int i, int i2, boolean z) {
        this(1, "afma-sdk-a-v" + i + "." + i2 + "." + (z ? "0" : "1"), i, i2, z);
    }

    ev(int i, String str, int i2, int i3, boolean z) {
        this.versionCode = i;
        this.sw = str;
        this.sx = i2;
        this.sy = i3;
        this.sz = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        ew.a(this, out, flags);
    }
}
