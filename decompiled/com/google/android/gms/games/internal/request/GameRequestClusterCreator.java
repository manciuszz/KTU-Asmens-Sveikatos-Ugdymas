package com.google.android.gms.games.internal.request;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.request.GameRequestEntity;
import java.util.ArrayList;

public class GameRequestClusterCreator implements Creator<GameRequestCluster> {
    static void a(GameRequestCluster gameRequestCluster, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.b(parcel, 1, gameRequestCluster.iw(), false);
        b.c(parcel, 1000, gameRequestCluster.getVersionCode());
        b.G(parcel, C);
    }

    public GameRequestCluster bk(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    arrayList = a.c(parcel, A, GameRequestEntity.CREATOR);
                    break;
                case 1000:
                    i = a.g(parcel, A);
                    break;
                default:
                    a.b(parcel, A);
                    break;
            }
        }
        if (parcel.dataPosition() == B) {
            return new GameRequestCluster(i, arrayList);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return bk(x0);
    }

    public GameRequestCluster[] cv(int i) {
        return new GameRequestCluster[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return cv(x0);
    }
}
