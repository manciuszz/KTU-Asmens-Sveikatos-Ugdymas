package com.google.android.gms.games.multiplayer.turnbased;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;

public final class TurnBasedMatchBuffer extends g<TurnBasedMatch> {
    public TurnBasedMatchBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return i(i, i2);
    }

    protected String eZ() {
        return "external_match_id";
    }

    protected TurnBasedMatch i(int i, int i2) {
        return new TurnBasedMatchRef(this.DG, i, i2);
    }
}
