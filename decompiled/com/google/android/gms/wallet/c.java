package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;

public class c implements Creator<CountrySpecification> {
    static void a(CountrySpecification countrySpecification, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1, countrySpecification.getVersionCode());
        b.a(parcel, 2, countrySpecification.rf, false);
        b.G(parcel, C);
    }

    public CountrySpecification bS(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    str = a.o(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new CountrySpecification(i, str);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bS(x0);
    }

    public CountrySpecification[] dy(int i) {
        return new CountrySpecification[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return dy(x0);
    }
}
