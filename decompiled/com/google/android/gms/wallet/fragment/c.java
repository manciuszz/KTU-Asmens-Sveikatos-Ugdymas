package com.google.android.gms.wallet.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Creator<WalletFragmentStyle> {
    static void a(WalletFragmentStyle walletFragmentStyle, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, walletFragmentStyle.xM);
        b.a(parcel, 2, walletFragmentStyle.akE, false);
        b.c(parcel, 3, walletFragmentStyle.akF);
        b.G(parcel, C);
    }

    public WalletFragmentStyle ci(Parcel parcel) {
        int i = 0;
        int B = a.B(parcel);
        Bundle bundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i2 = a.g(parcel, A);
                    break;
                case 2:
                    bundle = a.q(parcel, A);
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
            return new WalletFragmentStyle(i2, bundle, i);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ci(x0);
    }

    public WalletFragmentStyle[] dP(int i) {
        return new WalletFragmentStyle[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dP(x0);
    }
}
