package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class UserAddressRequest implements SafeParcelable {
    public static final Creator<UserAddressRequest> CREATOR = new a();
    List<CountrySpecification> UE;
    private final int xM;

    public final class Builder {
        final /* synthetic */ UserAddressRequest UF;

        private Builder(UserAddressRequest userAddressRequest) {
            this.UF = userAddressRequest;
        }

        public Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (this.UF.UE == null) {
                this.UF.UE = new ArrayList();
            }
            this.UF.UE.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecifications(Collection<CountrySpecification> countrySpecifications) {
            if (this.UF.UE == null) {
                this.UF.UE = new ArrayList();
            }
            this.UF.UE.addAll(countrySpecifications);
            return this;
        }

        public UserAddressRequest build() {
            if (this.UF.UE != null) {
                this.UF.UE = Collections.unmodifiableList(this.UF.UE);
            }
            return this.UF;
        }
    }

    UserAddressRequest() {
        this.xM = 1;
    }

    UserAddressRequest(int versionCode, List<CountrySpecification> allowedCountrySpecifications) {
        this.xM = versionCode;
        this.UE = allowedCountrySpecifications;
    }

    public static Builder newBuilder() {
        UserAddressRequest userAddressRequest = new UserAddressRequest();
        userAddressRequest.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xM;
    }

    public void writeToParcel(Parcel out, int flags) {
        a.a(this, out, flags);
    }
}
