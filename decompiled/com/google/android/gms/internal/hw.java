package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class hw implements Creator<hv> {
    static void a(hv hvVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, hvVar.getVersionCode());
        b.b(parcel, 2, hvVar.fD(), false);
        b.G(parcel, C);
    }

    public hv F(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    arrayList = a.c(parcel, A, hv.a.CREATOR);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new hv(i, arrayList);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public hv[] au(int i) {
        return new hv[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return F(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return au(x0);
    }
}
