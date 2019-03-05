package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class FullWalletRequest implements SafeParcelable {
    public static final Creator<FullWalletRequest> CREATOR = new g();
    String aiQ;
    String aiR;
    Cart aja;
    private final int xM;

    public final class Builder {
        final /* synthetic */ FullWalletRequest ajb;

        private Builder(FullWalletRequest fullWalletRequest) {
            this.ajb = fullWalletRequest;
        }

        public FullWalletRequest build() {
            return this.ajb;
        }

        public Builder setCart(Cart cart) {
            this.ajb.aja = cart;
            return this;
        }

        public Builder setGoogleTransactionId(String googleTransactionId) {
            this.ajb.aiQ = googleTransactionId;
            return this;
        }

        public Builder setMerchantTransactionId(String merchantTransactionId) {
            this.ajb.aiR = merchantTransactionId;
            return this;
        }
    }

    FullWalletRequest() {
        this.xM = 1;
    }

    FullWalletRequest(int versionCode, String googleTransactionId, String merchantTransactionId, Cart cart) {
        this.xM = versionCode;
        this.aiQ = googleTransactionId;
        this.aiR = merchantTransactionId;
        this.aja = cart;
    }

    public static Builder newBuilder() {
        FullWalletRequest fullWalletRequest = new FullWalletRequest();
        fullWalletRequest.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public Cart getCart() {
        return this.aja;
    }

    public String getGoogleTransactionId() {
        return this.aiQ;
    }

    public String getMerchantTransactionId() {
        return this.aiR;
    }

    public int getVersionCode() {
        return this.xM;
    }

    public void writeToParcel(Parcel dest, int flags) {
        g.a(this, dest, flags);
    }
}
