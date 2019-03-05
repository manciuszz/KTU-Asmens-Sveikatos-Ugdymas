package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;

public class b implements Creator<WalletFragmentOptions> {
    static void a(WalletFragmentOptions walletFragmentOptions, Parcel parcel, int i) {
        int C = com.google.android.gms.common.internal.safeparcel.b.C(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, walletFragmentOptions.xM);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 2, walletFragmentOptions.getEnvironment());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, walletFragmentOptions.getTheme());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, walletFragmentOptions.getFragmentStyle(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 5, walletFragmentOptions.getMode());
        com.google.android.gms.common.internal.safeparcel.b.G(parcel, C);
    }

    public WalletFragmentOptions ch(Parcel parcel) {
        int i = 1;
        int i2 = 0;
        int B = a.B(parcel);
        WalletFragmentStyle walletFragmentStyle = null;
        int i3 = 1;
        int i4 = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i4 = a.g(parcel, A);
                    break;
                case 2:
                    i3 = a.g(parcel, A);
                    break;
                case 3:
                    i2 = a.g(parcel, A);
                    break;
                case 4:
                    walletFragmentStyle = (WalletFragmentStyle) a.a(parcel, A, WalletFragmentStyle.CREATOR);
                    break;
                case 5:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new WalletFragmentOptions(i4, i3, i2, walletFragmentStyle, i);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return ch(x0);
    }

    public WalletFragmentOptions[] dO(int i) {
        return new WalletFragmentOptions[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dO(x0);
    }
}
