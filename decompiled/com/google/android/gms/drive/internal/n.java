package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;

public class n implements Creator<DeleteResourceRequest> {
    static void a(DeleteResourceRequest deleteResourceRequest, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, deleteResourceRequest.xM);
        b.a(parcel, 2, deleteResourceRequest.Iu, i, false);
        b.G(parcel, C);
    }

    public DeleteResourceRequest[] aX(int i) {
        return new DeleteResourceRequest[i];
    }

    public DeleteResourceRequest ab(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        DriveId driveId = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    driveId = (DriveId) a.a(parcel, A, DriveId.CREATOR);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new DeleteResourceRequest(i, driveId);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ab(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aX(x0);
    }
}
