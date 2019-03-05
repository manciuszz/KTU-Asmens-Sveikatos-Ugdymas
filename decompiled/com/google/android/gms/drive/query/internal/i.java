package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class i implements Creator<MatchAllFilter> {
    static void a(MatchAllFilter matchAllFilter, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1000, matchAllFilter.xM);
        b.G(parcel, C);
    }

    public MatchAllFilter aM(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1000:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new MatchAllFilter(i);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public MatchAllFilter[] bI(int i) {
        return new MatchAllFilter[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aM(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bI(x0);
    }
}
