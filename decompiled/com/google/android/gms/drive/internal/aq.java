package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class aq implements Creator<OnSyncMoreResponse> {
    static void a(OnSyncMoreResponse onSyncMoreResponse, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, onSyncMoreResponse.xM);
        b.a(parcel, 2, onSyncMoreResponse.IP);
        b.G(parcel, C);
    }

    public OnSyncMoreResponse ar(Parcel parcel) {
        boolean z = false;
        int B = a.B(parcel);
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    z = a.c(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new OnSyncMoreResponse(i, z);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public OnSyncMoreResponse[] bn(int i) {
        return new OnSyncMoreResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ar(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bn(x0);
    }
}
