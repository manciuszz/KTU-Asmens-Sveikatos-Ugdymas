package com.google.android.gms.games.quest;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

public class QuestEntityCreator implements Creator<QuestEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(QuestEntity questEntity, Parcel parcel, int i) {
        int C = b.C(parcel);
        b.a(parcel, 1, questEntity.getGame(), i, false);
        b.a(parcel, 2, questEntity.getQuestId(), false);
        b.a(parcel, 3, questEntity.getAcceptedTimestamp());
        b.a(parcel, 4, questEntity.getBannerImageUri(), i, false);
        b.a(parcel, 5, questEntity.getBannerImageUrl(), false);
        b.a(parcel, 6, questEntity.getDescription(), false);
        b.a(parcel, 7, questEntity.getEndTimestamp());
        b.a(parcel, 8, questEntity.getLastUpdatedTimestamp());
        b.a(parcel, 9, questEntity.getIconImageUri(), i, false);
        b.a(parcel, 10, questEntity.getIconImageUrl(), false);
        b.a(parcel, 12, questEntity.getName(), false);
        b.a(parcel, 13, questEntity.iK());
        b.a(parcel, 14, questEntity.getStartTimestamp());
        b.c(parcel, 15, questEntity.getState());
        b.b(parcel, 17, questEntity.iJ(), false);
        b.c(parcel, 16, questEntity.getType());
        b.c(parcel, 1000, questEntity.getVersionCode());
        b.G(parcel, C);
    }

    public QuestEntity createFromParcel(Parcel parcel) {
        int B = a.B(parcel);
        int i = 0;
        GameEntity gameEntity = null;
        String str = null;
        long j = 0;
        Uri uri = null;
        String str2 = null;
        String str3 = null;
        long j2 = 0;
        long j3 = 0;
        Uri uri2 = null;
        String str4 = null;
        String str5 = null;
        long j4 = 0;
        long j5 = 0;
        int i2 = 0;
        int i3 = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < B) {
            int A = a.A(parcel);
            switch (a.ar(A)) {
                case 1:
                    gameEntity = (GameEntity) a.a(parcel, A, GameEntity.CREATOR);
                    break;
                case 2:
                    str = a.o(parcel, A);
                    break;
                case 3:
                    j = a.i(parcel, A);
                    break;
                case 4:
                    uri = (Uri) a.a(parcel, A, Uri.CREATOR);
                    break;
                case 5:
                    str2 = a.o(parcel, A);
                    break;
                case 6:
                    str3 = a.o(parcel, A);
                    break;
                case 7:
                    j2 = a.i(parcel, A);
                    break;
                case 8:
                    j3 = a.i(parcel, A);
                    break;
                case 9:
                    uri2 = (Uri) a.a(parcel, A, Uri.CREATOR);
                    break;
                case 10:
                    str4 = a.o(parcel, A);
                    break;
                case 12:
                    str5 = a.o(parcel, A);
                    break;
                case 13:
                    j4 = a.i(parcel, A);
                    break;
                case 14:
                    j5 = a.i(parcel, A);
                    break;
                case 15:
                    i2 = a.g(parcel, A);
                    break;
                case 16:
                    i3 = a.g(parcel, A);
                    break;
                case 17:
                    arrayList = a.c(parcel, A, MilestoneEntity.CREATOR);
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
            return new QuestEntity(i, gameEntity, str, j, uri, str2, str3, j2, j3, uri2, str4, str5, j4, j5, i2, i3, arrayList);
        }
        throw new a.a("Overread allowed size end=" + B, parcel);
    }

    public QuestEntity[] newArray(int size) {
        return new QuestEntity[size];
    }
}
