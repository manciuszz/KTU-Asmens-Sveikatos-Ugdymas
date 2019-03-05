package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.ks.g;
import java.util.HashSet;
import java.util.Set;

public class lb implements Creator<g> {
    static void a(g gVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        Set kk = gVar.kk();
        if (kk.contains(Integer.valueOf(1))) {
            b.c(parcel, 1, gVar.getVersionCode());
        }
        if (kk.contains(Integer.valueOf(2))) {
            b.a(parcel, 2, gVar.isPrimary());
        }
        if (kk.contains(Integer.valueOf(3))) {
            b.a(parcel, 3, gVar.getValue(), true);
        }
        b.G(parcel, C);
    }

    public g bO(Parcel parcel) {
        boolean z = false;
        int B = a.B(parcel);
        Set hashSet = new HashSet();
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case 2:
                    z = a.c(parcel, A);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case 3:
                    str = a.o(parcel, A);
                    hashSet.add(Integer.valueOf(3));
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new g(hashSet, i, z, str);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bO(x0);
    }

    public g[] dl(int i) {
        return new g[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dl(x0);
    }
}
