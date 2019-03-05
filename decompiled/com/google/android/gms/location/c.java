package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Creator<b> {
    static void a(b bVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, bVar.Vq);
        b.c(parcel, 1000, bVar.getVersionCode());
        b.c(parcel, 2, bVar.Vr);
        b.a(parcel, 3, bVar.Vs);
        b.G(parcel, C);
    }

    public b bs(Parcel parcel) {
        int i = 1;
        int B = a.B(parcel);
        int i2 = 0;
        long j = 0;
        int i3 = 1;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i3 = a.g(parcel, A);
                    break;
                case 2:
                    i = a.g(parcel, A);
                    break;
                case 3:
                    j = a.i(parcel, A);
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
            return new b(i2, i3, i, j);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public b[] cL(int i) {
        return new b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bs(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cL(x0);
    }
}
