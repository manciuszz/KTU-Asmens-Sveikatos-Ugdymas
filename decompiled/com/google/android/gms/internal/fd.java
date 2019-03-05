package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class fd implements Parcelable {
    @Deprecated
    public static final Creator<fd> CREATOR = new Creator<fd>() {
        @Deprecated
        public fd[] C(int i) {
            return new fd[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return k(x0);
        }

        @Deprecated
        public fd k(Parcel parcel) {
            return new fd(parcel);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return C(x0);
        }
    };
    private String mValue;
    private String xG;
    private String xH;

    @Deprecated
    fd(Parcel parcel) {
        readFromParcel(parcel);
    }

    public fd(String str, String str2, String str3) {
        this.xG = str;
        this.xH = str2;
        this.mValue = str3;
    }

    @Deprecated
    private void readFromParcel(Parcel in) {
        this.xG = in.readString();
        this.xH = in.readString();
        this.mValue = in.readString();
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.xG;
    }

    public String getValue() {
        return this.mValue;
    }

    @Deprecated
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.xG);
        out.writeString(this.xH);
        out.writeString(this.mValue);
    }
}
