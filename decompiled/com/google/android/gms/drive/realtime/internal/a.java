package com.google.android.gms.drive.realtime.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.b;

public class a implements Creator<BeginCompoundOperationRequest> {
    static void a(BeginCompoundOperationRequest beginCompoundOperationRequest, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, beginCompoundOperationRequest.xM);
        b.a(parcel, 2, beginCompoundOperationRequest.Lj);
        b.a(parcel, 3, beginCompoundOperationRequest.mName, false);
        b.G(parcel, C);
    }

    public BeginCompoundOperationRequest aP(Parcel parcel) {
        boolean z = false;
        int B = com.google.android.gms.common.internal.safeparcel.a.B(parcel);
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = com.google.android.gms.common.internal.safeparcel.a.A(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.ar(A)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, A);
                    break;
                case 2:
                    z = com.google.android.gms.common.internal.safeparcel.a.c(parcel, A);
                    break;
                case 3:
                    str = com.google.android.gms.common.internal.safeparcel.a.o(parcel, A);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new BeginCompoundOperationRequest(i, z, str);
        }
        throw new com.google.android.gms.common.internal.safeparcel.a.a("Overread allowed size end=" + B, parcel);
    }

    public BeginCompoundOperationRequest[] bL(int i) {
        return new BeginCompoundOperationRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aP(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bL(x0);
    }
}
