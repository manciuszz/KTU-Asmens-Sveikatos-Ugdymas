package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.a;

public class b implements Creator<ParcelableEventList> {
    static void a(ParcelableEventList parcelableEventList, Parcel parcel, int i) {
        int C = com.google.android.gms.common.internal.safeparcel.b.C(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, parcelableEventList.xM);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, parcelableEventList.LB, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, parcelableEventList.LC, i, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, parcelableEventList.LD);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, parcelableEventList.LE, i, false);
        com.google.android.gms.common.internal.safeparcel.b.G(parcel, C);
    }

    public ParcelableEventList aU(Parcel parcel) {
        boolean z = false;
        ParcelableObjectChangedEvent[] parcelableObjectChangedEventArr = null;
        int B = a.B(parcel);
        DataHolder dataHolder = null;
        ParcelableEvent[] parcelableEventArr = null;
        int i = 0;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    i = a.g(parcel, A);
                    break;
                case 2:
                    parcelableEventArr = (ParcelableEvent[]) a.b(parcel, A, ParcelableEvent.CREATOR);
                    break;
                case 3:
                    dataHolder = (DataHolder) a.a(parcel, A, DataHolder.CREATOR);
                    break;
                case 4:
                    z = a.c(parcel, A);
                    break;
                case 5:
                    parcelableObjectChangedEventArr = (ParcelableObjectChangedEvent[]) a.b(parcel, A, ParcelableObjectChangedEvent.CREATOR);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new ParcelableEventList(i, parcelableEventArr, dataHolder, z, parcelableObjectChangedEventArr);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public ParcelableEventList[] bR(int i) {
        return new ParcelableEventList[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return aU(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return bR(x0);
    }
}
