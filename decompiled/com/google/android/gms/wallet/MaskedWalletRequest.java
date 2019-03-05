package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;

public final class MaskedWalletRequest implements SafeParcelable {
    public static final Creator<MaskedWalletRequest> CREATOR = new l();
    String aiL;
    String aiR;
    boolean ajF;
    boolean ajG;
    boolean ajH;
    String ajI;
    String ajJ;
    boolean ajK;
    boolean ajL;
    CountrySpecification[] ajM;
    boolean ajN;
    boolean ajO;
    ArrayList<CountrySpecification> ajP;
    Cart aja;
    private final int xM;

    public final class Builder {
        final /* synthetic */ MaskedWalletRequest ajQ;

        private Builder(MaskedWalletRequest maskedWalletRequest) {
            this.ajQ = maskedWalletRequest;
        }

        public Builder addAllowedCountrySpecificationForShipping(CountrySpecification countrySpecification) {
            if (this.ajQ.ajP == null) {
                this.ajQ.ajP = new ArrayList();
            }
            this.ajQ.ajP.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecificationsForShipping(Collection<CountrySpecification> countrySpecifications) {
            if (countrySpecifications != null) {
                if (this.ajQ.ajP == null) {
                    this.ajQ.ajP = new ArrayList();
                }
                this.ajQ.ajP.addAll(countrySpecifications);
            }
            return this;
        }

        public MaskedWalletRequest build() {
            return this.ajQ;
        }

        public Builder setAllowDebitCard(boolean allowDebitCard) {
            this.ajQ.ajO = allowDebitCard;
            return this;
        }

        public Builder setAllowPrepaidCard(boolean allowPrepaidCard) {
            this.ajQ.ajN = allowPrepaidCard;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.ajQ.aja = cart;
            return this;
        }

        public Builder setCurrencyCode(String currencyCode) {
            this.ajQ.aiL = currencyCode;
            return this;
        }

        public Builder setEstimatedTotalPrice(String estimatedTotalPrice) {
            this.ajQ.ajI = estimatedTotalPrice;
            return this;
        }

        public Builder setIsBillingAgreement(boolean isBillingAgreement) {
            this.ajQ.ajL = isBillingAgreement;
            return this;
        }

        public Builder setMerchantName(String merchantName) {
            this.ajQ.ajJ = merchantName;
            return this;
        }

        public Builder setMerchantTransactionId(String merchantTransactionId) {
            this.ajQ.aiR = merchantTransactionId;
            return this;
        }

        public Builder setPhoneNumberRequired(boolean phoneNumberRequired) {
            this.ajQ.ajF = phoneNumberRequired;
            return this;
        }

        public Builder setShippingAddressRequired(boolean shippingAddressRequired) {
            this.ajQ.ajG = shippingAddressRequired;
            return this;
        }

        public Builder setShouldRetrieveWalletObjects(boolean shouldRetrieveWalletObjects) {
            this.ajQ.ajK = shouldRetrieveWalletObjects;
            return this;
        }

        public Builder setUseMinimalBillingAddress(boolean useMinimalBillingAddress) {
            this.ajQ.ajH = useMinimalBillingAddress;
            return this;
        }
    }

    MaskedWalletRequest() {
        this.xM = 3;
        this.ajN = true;
        this.ajO = true;
    }

    MaskedWalletRequest(int versionCode, String merchantTransactionId, boolean phoneNumberRequired, boolean shippingAddressRequired, boolean useMinimalBillingAddress, String estimatedTotalPrice, String currencyCode, String merchantName, Cart cart, boolean shouldRetrieveWalletObjects, boolean isBillingAgreement, CountrySpecification[] allowedShippingCountrySpecifications, boolean allowPrepaidCard, boolean allowDebitCard, ArrayList<CountrySpecification> allowedCountrySpecificationsForShipping) {
        this.xM = versionCode;
        this.aiR = merchantTransactionId;
        this.ajF = phoneNumberRequired;
        this.ajG = shippingAddressRequired;
        this.ajH = useMinimalBillingAddress;
        this.ajI = estimatedTotalPrice;
        this.aiL = currencyCode;
        this.ajJ = merchantName;
        this.aja = cart;
        this.ajK = shouldRetrieveWalletObjects;
        this.ajL = isBillingAgreement;
        this.ajM = allowedShippingCountrySpecifications;
        this.ajN = allowPrepaidCard;
        this.ajO = allowDebitCard;
        this.ajP = allowedCountrySpecificationsForShipping;
    }

    public static Builder newBuilder() {
        MaskedWalletRequest maskedWalletRequest = new MaskedWalletRequest();
        maskedWalletRequest.getClass();
        return new Builder();
    }

    public boolean allowDebitCard() {
        return this.ajO;
    }

    public boolean allowPrepaidCard() {
        return this.ajN;
    }

    public int describeContents() {
        return 0;
    }

    public ArrayList<CountrySpecification> getAllowedCountrySpecificationsForShipping() {
        return this.ajP;
    }

    public CountrySpecification[] getAllowedShippingCountrySpecifications() {
        return this.ajM;
    }

    public Cart getCart() {
        return this.aja;
    }

    public String getCurrencyCode() {
        return this.aiL;
    }

    public String getEstimatedTotalPrice() {
        return this.ajI;
    }

    public String getMerchantName() {
        return this.ajJ;
    }

    public String getMerchantTransactionId() {
        return this.aiR;
    }

    public int getVersionCode() {
        return this.xM;
    }

    public boolean isBillingAgreement() {
        return this.ajL;
    }

    public boolean isPhoneNumberRequired() {
        return this.ajF;
    }

    public boolean isShippingAddressRequired() {
        return this.ajG;
    }

    public boolean shouldRetrieveWalletObjects() {
        return this.ajK;
    }

    public boolean useMinimalBillingAddress() {
        return this.ajH;
    }

    public void writeToParcel(Parcel dest, int flags) {
        l.a(this, dest, flags);
    }
}
