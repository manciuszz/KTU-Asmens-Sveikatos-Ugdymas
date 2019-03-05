package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class kb implements Creator<ka> {
    static void a(ka kaVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.a(parcel, 1, kaVar.YV, false);
        b.c(parcel, 1000, kaVar.versionCode);
        b.a(parcel, 2, kaVar.YW, false);
        b.G(parcel, C);
    }

    public ka bB(Parcel parcel) {
        String str = null;
        int B = a.B(parcel);
        int i = 0;
        String str2 = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    str2 = a.o(parcel, A);
                    break;
                case 2:
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
            return new ka(i, str2, str);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public ka[] cW(int i) {
        return new ka[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bB(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cW(x0);
    }
}
