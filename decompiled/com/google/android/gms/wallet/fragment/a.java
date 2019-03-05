package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public class a implements Creator<WalletFragmentInitParams> {
    static void a(WalletFragmentInitParams walletFragmentInitParams, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, walletFragmentInitParams.xM);
        b.a(parcel, 2, walletFragmentInitParams.getAccountName(), false);
        b.a(parcel, 3, walletFragmentInitParams.getMaskedWalletRequest(), i, false);
        b.c(parcel, 4, walletFragmentInitParams.getMaskedWalletRequestCode());
        b.a(parcel, 5, walletFragmentInitParams.getMaskedWallet(), i, false);
        b.G(parcel, C);
    }

    public WalletFragmentInitParams cg(Parcel parcel) {
        MaskedWallet maskedWallet = null;
        int B = com.google.android.gms.common.internal.safeparcel.a.B(parcel);
        int i = 0;
        int i2 = -1;
        MaskedWalletRequest maskedWalletRequest = null;
        String str = null;
        while (parcel.dataPosition() < B) {
            int A = com.google.android.gms.common.internal.safeparcel.a.A(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.ar(A)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, A);
                    break;
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.a.o(parcel, A);
                    break;
                case 3:
                    maskedWalletRequest = (MaskedWalletRequest) com.google.android.gms.common.internal.safeparcel.a.a(parcel, A, MaskedWalletRequest.CREATOR);
                    break;
                case 4:
                    i2 = com.google.android.gms.common.internal.safeparcel.a.g(parcel, A);
                    break;
                case 5:
                    maskedWallet = (MaskedWallet) com.google.android.gms.common.internal.safeparcel.a.a(parcel, A, MaskedWallet.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new WalletFragmentInitParams(i, str, maskedWalletRequest, i2, maskedWallet);
        }
        throw new com.google.android.gms.common.internal.safeparcel.a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return cg(x0);
    }

    public WalletFragmentInitParams[] dN(int i) {
        return new WalletFragmentInitParams[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dN(x0);
    }
}
