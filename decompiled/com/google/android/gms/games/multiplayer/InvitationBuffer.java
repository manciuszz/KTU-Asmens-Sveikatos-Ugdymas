package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.g;

public final class InvitationBuffer extends g<Invitation> {
    public InvitationBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object c(int i, int i2) {
        return g(i, i2);
    }

    protected String eZ() {
        return "external_invitation_id";
    }

    protected Invitation g(int i, int i2) {
        return new InvitationRef(this.DG, i, i2);
    }
}
