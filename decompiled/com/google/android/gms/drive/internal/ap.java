package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.StorageStats;

public class ap implements Creator<OnStorageStatsResponse> {
    static void a(OnStorageStatsResponse onStorageStatsResponse, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, onStorageStatsResponse.xM);
        b.a(parcel, 2, onStorageStatsResponse.JD, i, false);
        b.G(parcel, C);
    }

    public OnStorageStatsResponse aq(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        StorageStats storageStats = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    storageStats = (StorageStats) a.a(parcel, A, StorageStats.CREATOR);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new OnStorageStatsResponse(i, storageStats);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public OnStorageStatsResponse[] bm(int i) {
        return new OnStorageStatsResponse[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aq(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bm(x0);
    }
}
