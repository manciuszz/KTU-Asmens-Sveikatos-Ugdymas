package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class f implements Creator<TextInsertedDetails> {
    static void a(TextInsertedDetails textInsertedDetails, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, textInsertedDetails.xM);
        b.c(parcel, 2, textInsertedDetails.mIndex);
        b.c(parcel, 3, textInsertedDetails.LL);
        b.G(parcel, C);
    }

    public TextInsertedDetails aY(Parcel parcel) {
        int i = 0;
        int B = a.B(parcel);
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i3 = a.g(parcel, A);
                    break;
                case 2:
                    i2 = a.g(parcel, A);
                    break;
                case 3:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new TextInsertedDetails(i3, i2, i);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public TextInsertedDetails[] bV(int i) {
        return new TextInsertedDetails[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aY(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bV(x0);
    }
}
