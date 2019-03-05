package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.c;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.e;

public final class ki implements Account {

    private static abstract class a extends com.google.android.gms.plus.Plus.a<Status> {
        private a() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    private static e a(GoogleApiClient googleApiClient, c<e> cVar) {
        boolean z = true;
        hm.b(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        hm.a(googleApiClient.isConnected(), "GoogleApiClient must be connected.");
        e eVar = (e) googleApiClient.a((c) cVar);
        if (eVar == null) {
            z = false;
        }
        hm.a(z, "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return eVar;
    }

    public void clearDefaultAccount(GoogleApiClient googleApiClient) {
        a(googleApiClient, Plus.yH).clearDefaultAccount();
    }

    public String getAccountName(GoogleApiClient googleApiClient) {
        return a(googleApiClient, Plus.yH).getAccountName();
    }

    public PendingResult<Status> revokeAccessAndDisconnect(GoogleApiClient googleApiClient) {
        return googleApiClient.b(new a(this) {
            final /* synthetic */ ki acd;

            {
                this.acd = r2;
            }

            protected void a(e eVar) {
                eVar.m(this);
            }
        });
    }
}
