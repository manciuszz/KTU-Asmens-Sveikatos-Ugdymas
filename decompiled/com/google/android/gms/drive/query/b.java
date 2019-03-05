package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.drive.query.internal.FieldWithSortOrder;
import java.util.List;

public class b implements Creator<SortOrder> {
    static void a(SortOrder sortOrder, Parcel parcel, int i) {
        int C = com.google.android.gms.common.internal.safeparcel.b.C(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1000, sortOrder.xM);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 1, sortOrder.KK, false);
        com.google.android.gms.common.internal.safeparcel.b.G(parcel, C);
    }

    public SortOrder aE(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        List list = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    list = a.c(parcel, A, FieldWithSortOrder.CREATOR);
                    break;
                case 1000:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new SortOrder(i, list);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public SortOrder[] bA(int i) {
        return new SortOrder[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aE(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bA(x0);
    }
}
