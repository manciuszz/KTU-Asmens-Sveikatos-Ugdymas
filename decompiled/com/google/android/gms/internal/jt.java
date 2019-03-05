package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class jt implements Creator<js> {
    static void a(js jsVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.a(parcel, 1, jsVar.qX, false);
        b.c(parcel, 1000, jsVar.xM);
        b.G(parcel, C);
    }

    public js by(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    str = a.o(parcel, A);
                    break;
                case 1000:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new js(i, str);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public js[] cT(int i) {
        return new js[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return by(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cT(x0);
    }
}
