package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class ap implements Creator<ao> {
    static void a(ao aoVar, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, aoVar.xM);
        b.a(parcel, 2, aoVar.no(), false);
        b.G(parcel, C);
    }

    public ao cI(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    iBinder = a.p(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new ao(i, iBinder);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return cI(x0);
    }

    public ao[] er(int i) {
        return new ao[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return er(x0);
    }
}
