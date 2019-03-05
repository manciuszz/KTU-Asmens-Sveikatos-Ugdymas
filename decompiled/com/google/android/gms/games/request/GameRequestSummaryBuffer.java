package com.google.android.gms.games.request;

import com.google.android.gms.common.data.DataBuffer;

public final class GameRequestSummaryBuffer extends DataBuffer<GameRequestSummary> {
    public GameRequestSummary cB(int i) {
        return new GameRequestSummaryRef(this.DG, i);
    }

    public /* synthetic */ Object get(int x0) {
        return cB(x0);
    }
}
