package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class f implements Creator<HasFilter> {
    static void a(HasFilter hasFilter, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1000, hasFilter.xM);
        b.a(parcel, 1, hasFilter.KM, i, false);
        b.G(parcel, C);
    }

    public HasFilter aJ(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        MetadataBundle metadataBundle = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    metadataBundle = (MetadataBundle) a.a(parcel, A, MetadataBundle.CREATOR);
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
            return new HasFilter(i, metadataBundle);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public HasFilter[] bF(int i) {
        return new HasFilter[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aJ(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bF(x0);
    }
}
