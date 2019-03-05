package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;
import java.util.List;

public class h implements Creator<LogicalFilter> {
    static void a(LogicalFilter logicalFilter, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.c(parcel, 1000, logicalFilter.xM);
        b.a(parcel, 1, logicalFilter.KL, i, false);
        b.b(parcel, 2, logicalFilter.KY, false);
        b.G(parcel, C);
    }

    public LogicalFilter aL(Parcel parcel) {
        List list = null;
        int B = a.B(parcel);
        int i = 0;
        Operator operator = null;
        while (parcel.dataPosition() < B) {
            int i2;
            Operator operator2;
            ArrayList c;
            int A = a.A(parcel);
            List list2;
            switch (a.ar(A)) {
                case 1:
                    i2 = i;
                    Operator operator3 = (Operator) a.a(parcel, A, Operator.CREATOR);
                    list2 = list;
                    operator2 = operator3;
                    break;
                case 2:
                    c = a.c(parcel, A, FilterHolder.CREATOR);
                    operator2 = operator;
                    i2 = i;
                    break;
                case 1000:
                    List list3 = list;
                    operator2 = operator;
                    i2 = a.g(parcel, A);
                    list2 = list3;
                    break;
                default:
                    a.b(parcel, A);
                    c = list;
                    operator2 = operator;
                    i2 = i;
                    break;
            }
            i = i2;
            operator = operator2;
            Object obj = c;
        }
        if (parcel.dataPosition() == B) {
            return new LogicalFilter(i, operator, list);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public LogicalFilter[] bH(int i) {
        return new LogicalFilter[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aL(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bH(x0);
    }
}
