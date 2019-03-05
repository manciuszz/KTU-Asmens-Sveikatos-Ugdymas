package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Creator<FieldWithSortOrder> {
    static void a(FieldWithSortOrder fieldWithSortOrder, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1000, fieldWithSortOrder.xM);
        b.a(parcel, 1, fieldWithSortOrder.JH, false);
        b.a(parcel, 2, fieldWithSortOrder.KO);
        b.G(parcel, C);
    }

    public FieldWithSortOrder aH(Parcel parcel) {
        boolean z = false;
        int B = a.B(parcel);
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    str = a.o(parcel, A);
                    break;
                case 2:
                    z = a.c(parcel, A);
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
            return new FieldWithSortOrder(i, str, z);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public FieldWithSortOrder[] bD(int i) {
        return new FieldWithSortOrder[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aH(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bD(x0);
    }
}
