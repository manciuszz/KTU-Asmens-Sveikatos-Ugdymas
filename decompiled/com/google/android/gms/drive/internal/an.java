package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class an implements Creator<OnLoadRealtimeResponse> {
    static void a(OnLoadRealtimeResponse onLoadRealtimeResponse, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, onLoadRealtimeResponse.xM);
        b.a(parcel, 2, onLoadRealtimeResponse.JC);
        b.G(parcel, C);
    }

    public OnLoadRealtimeResponse ao(Parcel parcel) {
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
            return new OnLoadRealtimeResponse(i, z);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public OnLoadRealtimeResponse[] bk(int i) {
        return new OnLoadRealtimeResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ao(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bk(x0);
    }
}
