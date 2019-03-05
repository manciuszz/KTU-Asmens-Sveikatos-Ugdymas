package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.hm;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class WalletFragmentInitParams implements SafeParcelable {
    public static final Creator<WalletFragmentInitParams> CREATOR = new a();
    private int akA;
    private MaskedWalletRequest akm;
    private MaskedWallet akn;
    final int xM;
    private String yQ;

    public final class Builder {
        final /* synthetic */ WalletFragmentInitParams akB;

        private Builder(WalletFragmentInitParams walletFragmentInitParams) {
            this.akB = walletFragmentInitParams;
        }

        public WalletFragmentInitParams build() {
            boolean z = true;
            boolean z2 = (this.akB.akn != null && this.akB.akm == null) || (this.akB.akn == null && this.akB.akm != null);
            hm.a(z2, "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            if (this.akB.akA < 0) {
                z = false;
            }
            hm.a(z, "masked wallet request code is required and must be non-negative");
            return this.akB;
        }

        public Builder setAccountName(String accountName) {
            this.akB.yQ = accountName;
            return this;
        }

        public Builder setMaskedWallet(MaskedWallet maskedWallet) {
            this.akB.akn = maskedWallet;
            return this;
        }

        public Builder setMaskedWalletRequest(MaskedWalletRequest request) {
            this.akB.akm = request;
            return this;
        }

        public Builder setMaskedWalletRequestCode(int requestCode) {
            this.akB.akA = requestCode;
            return this;
        }
    }

    private WalletFragmentInitParams() {
        this.xM = 1;
        this.akA = -1;
    }

    WalletFragmentInitParams(int versionCode, String accountName, MaskedWalletRequest maskedWalletRequest, int maskedWalletRequestCode, MaskedWallet maskedWallet) {
        this.xM = versionCode;
        this.yQ = accountName;
        this.akm = maskedWalletRequest;
        this.akA = maskedWalletRequestCode;
        this.akn = maskedWallet;
    }

    public static Builder newBuilder() {
        WalletFragmentInitParams walletFragmentInitParams = new WalletFragmentInitParams();
        walletFragmentInitParams.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getAccountName() {
        return this.yQ;
    }

    public MaskedWallet getMaskedWallet() {
        return this.akn;
    }

    public MaskedWalletRequest getMaskedWalletRequest() {
        return this.akm;
    }

    public int getMaskedWalletRequestCode() {
        return this.akA;
    }

    public void writeToParcel(Parcel dest, int flags) {
        a.a(this, dest, flags);
    }
}
