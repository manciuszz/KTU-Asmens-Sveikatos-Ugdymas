package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class g implements Creator<CreateContentsRequest> {
    static void a(CreateContentsRequest createContentsRequest, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, createContentsRequest.xM);
        b.G(parcel, C);
    }

    public CreateContentsRequest W(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new CreateContentsRequest(i);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public CreateContentsRequest[] aR(int i) {
        return new CreateContentsRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return W(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aR(x0);
    }
}
