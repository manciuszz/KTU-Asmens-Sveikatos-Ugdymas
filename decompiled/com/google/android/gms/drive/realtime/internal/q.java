package com.google.android.gms.drive.realtime.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class q implements Creator<ParcelableIndexReference> {
    static void a(ParcelableIndexReference parcelableIndexReference, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, parcelableIndexReference.xM);
        b.a(parcel, 2, parcelableIndexReference.Lq, false);
        b.c(parcel, 3, parcelableIndexReference.mIndex);
        b.a(parcel, 4, parcelableIndexReference.Lr);
        b.G(parcel, C);
    }

    public ParcelableIndexReference aS(Parcel parcel) {
        boolean z = false;
        int B = a.B(parcel);
        String str = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i2 = a.g(parcel, A);
                    break;
                case 2:
                    str = a.o(parcel, A);
                    break;
                case 3:
                    i = a.g(parcel, A);
                    break;
                case 4:
                    z = a.c(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new ParcelableIndexReference(i2, str, i, z);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public ParcelableIndexReference[] bP(int i) {
        return new ParcelableIndexReference[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aS(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bP(x0);
    }
}
