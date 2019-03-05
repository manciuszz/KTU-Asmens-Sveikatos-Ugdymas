package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class al implements Creator<OnListParentsResponse> {
    static void a(OnListParentsResponse onListParentsResponse, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, onListParentsResponse.xM);
        b.a(parcel, 2, onListParentsResponse.JB, i, false);
        b.G(parcel, C);
    }

    public OnListParentsResponse am(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        DataHolder dataHolder = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    dataHolder = (DataHolder) a.a(parcel, A, DataHolder.CREATOR);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new OnListParentsResponse(i, dataHolder);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public OnListParentsResponse[] bi(int i) {
        return new OnListParentsResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return am(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bi(x0);
    }
}
