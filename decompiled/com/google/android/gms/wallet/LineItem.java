package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LineItem implements SafeParcelable {
    public static final Creator<LineItem> CREATOR = new i();
    String aiK;
    String aiL;
    String aje;
    String ajf;
    int ajg;
    String description;
    private final int xM;

    public final class Builder {
        final /* synthetic */ LineItem ajh;

        private Builder(LineItem lineItem) {
            this.ajh = lineItem;
        }

        public LineItem build() {
            return this.ajh;
        }

        public Builder setCurrencyCode(String currencyCode) {
            this.ajh.aiL = currencyCode;
            return this;
        }

        public Builder setDescription(String description) {
            this.ajh.description = description;
            return this;
        }

        public Builder setQuantity(String quantity) {
            this.ajh.aje = quantity;
            return this;
        }

        public Builder setRole(int role) {
            this.ajh.ajg = role;
            return this;
        }

        public Builder setTotalPrice(String totalPrice) {
            this.ajh.aiK = totalPrice;
            return this;
        }

        public Builder setUnitPrice(String unitPrice) {
            this.ajh.ajf = unitPrice;
            return this;
        }
    }

    public interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

    LineItem() {
        this.xM = 1;
        this.ajg = 0;
    }

    LineItem(int versionCode, String description, String quantity, String unitPrice, String totalPrice, int role, String currencyCode) {
        this.xM = versionCode;
        this.description = description;
        this.aje = quantity;
        this.ajf = unitPrice;
        this.aiK = totalPrice;
        this.ajg = role;
        this.aiL = currencyCode;
    }

    public static Builder newBuilder() {
        LineItem lineItem = new LineItem();
        lineItem.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getCurrencyCode() {
        return this.aiL;
    }

    public String getDescription() {
        return this.description;
    }

    public String getQuantity() {
        return this.aje;
    }

    public int getRole() {
        return this.ajg;
    }

    public String getTotalPrice() {
        return this.aiK;
    }

    public String getUnitPrice() {
        return this.ajf;
    }

    public int getVersionCode() {
        return this.xM;
    }

    public void writeToParcel(Parcel dest, int flags) {
        i.a(this, dest, flags);
    }
}
