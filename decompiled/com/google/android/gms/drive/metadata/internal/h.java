package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class h implements Creator<MetadataBundle> {
    static void a(MetadataBundle metadataBundle, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, metadataBundle.xM);
        b.a(parcel, 2, metadataBundle.JS, false);
        b.G(parcel, C);
    }

    public MetadataBundle aC(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        Bundle bundle = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    bundle = a.q(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new MetadataBundle(i, bundle);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public MetadataBundle[] by(int i) {
        return new MetadataBundle[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aC(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return by(x0);
    }
}
