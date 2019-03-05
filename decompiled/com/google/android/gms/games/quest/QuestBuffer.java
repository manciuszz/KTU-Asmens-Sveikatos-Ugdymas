package com.google.android.gms.games.quest;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;

public final class QuestBuffer extends g<Quest> {
    public QuestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return j(i, i2);
    }

    protected String eZ() {
        return "external_quest_id";
    }

    protected Quest j(int i, int i2) {
        return new QuestRef(this.DG, i, i2);
    }
}
