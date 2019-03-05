package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;

public final class GameRequestBuffer extends g<GameRequest> {
    public GameRequestBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return k(i, i2);
    }

    protected String eZ() {
        return "external_request_id";
    }

    protected GameRequest k(int i, int i2) {
        return new GameRequestRef(this.DG, i, i2);
    }
}
