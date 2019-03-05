package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class TextInsertedDetails implements SafeParcelable {
    public static final Creator<TextInsertedDetails> CREATOR = new f();
    final int LL;
    final int mIndex;
    final int xM;

    TextInsertedDetails(int versionCode, int index, int stringDataIndex) {
        this.xM = versionCode;
        this.mIndex = index;
        this.LL = stringDataIndex;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        f.a(this, dest, flags);
    }
}
