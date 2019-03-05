package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class m implements Creator<NotifyTransactionStatusRequest> {
    static void a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, notifyTransactionStatusRequest.xM);
        b.a(parcel, 2, notifyTransactionStatusRequest.aiQ, false);
        b.c(parcel, 3, notifyTransactionStatusRequest.status);
        b.a(parcel, 4, notifyTransactionStatusRequest.ajR, false);
        b.G(parcel, C);
    }

    public NotifyTransactionStatusRequest cb(Parcel parcel) {
        String str = null;
        int i = 0;
        int B = a.B(parcel);
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i2 = a.g(parcel, A);
                    break;
                case 2:
                    str2 = a.o(parcel, A);
                    break;
                case 3:
                    i = a.g(parcel, A);
                    break;
                case 4:
                    str = a.o(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new NotifyTransactionStatusRequest(i2, str2, i, str);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return cb(x0);
    }

    public NotifyTransactionStatusRequest[] dH(int i) {
        return new NotifyTransactionStatusRequest[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dH(x0);
    }
}
