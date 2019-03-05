package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;

public final class LeaderboardBuffer extends g<Leaderboard> {
    public LeaderboardBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return f(i, i2);
    }

    protected String eZ() {
        return "external_leaderboard_id";
    }

    protected Leaderboard f(int i, int i2) {
        return new LeaderboardRef(this.DG, i, i2);
    }
}
