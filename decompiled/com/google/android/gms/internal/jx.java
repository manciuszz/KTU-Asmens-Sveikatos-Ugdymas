package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class jx implements Creator<jw> {
    static void a(jw jwVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.a(parcel, 1, jwVar.qX, false);
        b.c(parcel, 1000, jwVar.xM);
        b.c(parcel, 2, jwVar.YS);
        b.G(parcel, C);
    }

    public jw bA(Parcel parcel) {
        int i = 0;
        int B = a.B(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    str = a.o(parcel, A);
                    break;
                case 2:
                    i = a.g(parcel, A);
                    break;
                case 1000:
                    i2 = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new jw(i2, str, i);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public jw[] cV(int i) {
        return new jw[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bA(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cV(x0);
    }
}
