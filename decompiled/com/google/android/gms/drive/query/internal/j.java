package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class j implements Creator<NotFilter> {
    static void a(NotFilter notFilter, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1000, notFilter.xM);
        b.a(parcel, 1, notFilter.KZ, i, false);
        b.G(parcel, C);
    }

    public NotFilter aN(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        FilterHolder filterHolder = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    filterHolder = (FilterHolder) a.a(parcel, A, FilterHolder.CREATOR);
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
            return new NotFilter(i, filterHolder);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public NotFilter[] bJ(int i) {
        return new NotFilter[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aN(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bJ(x0);
    }
}
