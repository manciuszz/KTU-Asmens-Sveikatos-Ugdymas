package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class lh implements Creator<lg> {
    static void a(lg lgVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, lgVar.getVersionCode());
        b.a(parcel, 2, lgVar.akd, false);
        b.G(parcel, C);
    }

    public lg ce(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        int[] iArr = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    iArr = a.u(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new lg(i, iArr);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ce(x0);
    }

    public lg[] dK(int i) {
        return new lg[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dK(x0);
    }
}
