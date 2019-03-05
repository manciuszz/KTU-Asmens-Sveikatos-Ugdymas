package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class k implements Creator<CreateFolderRequest> {
    static void a(CreateFolderRequest createFolderRequest, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, createFolderRequest.xM);
        b.a(parcel, 2, createFolderRequest.IG, i, false);
        b.a(parcel, 3, createFolderRequest.IE, i, false);
        b.G(parcel, C);
    }

    public CreateFolderRequest Z(Parcel parcel) {
        MetadataBundle metadataBundle = null;
        int B = a.B(parcel);
        int i = 0;
        DriveId driveId = null;
        while (parcel.dataPosition() < B) {
            DriveId driveId2;
            int g;
            MetadataBundle metadataBundle2;
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    MetadataBundle metadataBundle3 = metadataBundle;
                    driveId2 = driveId;
                    g = a.g(parcel, A);
                    metadataBundle2 = metadataBundle3;
                    break;
                case 2:
                    g = i;
                    DriveId driveId3 = (DriveId) a.a(parcel, A, DriveId.CREATOR);
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId3;
                    break;
                case 3:
                    metadataBundle2 = (MetadataBundle) a.a(parcel, A, MetadataBundle.CREATOR);
                    driveId2 = driveId;
                    g = i;
                    break;
                default:
                    a.b(parcel, A);
                    metadataBundle2 = metadataBundle;
                    driveId2 = driveId;
                    g = i;
                    break;
            }
            i = g;
            driveId = driveId2;
            metadataBundle = metadataBundle2;
        }
        if (parcel.dataPosition() == B) {
            return new CreateFolderRequest(i, driveId, metadataBundle);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public CreateFolderRequest[] aV(int i) {
        return new CreateFolderRequest[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return Z(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aV(x0);
    }
}
