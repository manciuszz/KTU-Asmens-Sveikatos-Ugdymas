package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

public class b implements Creator<Cart> {
    static void a(Cart cart, Parcel parcel, int i) {
        int C = com.google.android.gms.common.internal.safeparcel.b.C(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, cart.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, cart.aiK, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, cart.aiL, false);
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 4, cart.aiM, false);
        com.google.android.gms.common.internal.safeparcel.b.G(parcel, C);
    }

    public Cart bR(Parcel parcel) {
        String str = null;
        int B = a.B(parcel);
        int i = 0;
        ArrayList arrayList = new ArrayList();
        String str2 = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    str2 = a.o(parcel, A);
                    break;
                case 3:
                    str = a.o(parcel, A);
                    break;
                case 4:
                    arrayList = a.c(parcel, A, LineItem.CREATOR);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new Cart(i, str2, str, arrayList);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bR(x0);
    }

    public Cart[] dx(int i) {
        return new Cart[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dx(x0);
    }
}
