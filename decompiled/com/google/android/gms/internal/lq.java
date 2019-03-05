package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Payments;
import com.google.android.gms.wallet.Wallet.b;

public class lq implements Payments {
    public void changeMaskedWallet(GoogleApiClient googleApiClient, final String googleTransactionId, final String merchantTransactionId, final int requestCode) {
        googleApiClient.a(new b(this) {
            final /* synthetic */ lq akG;

            protected void a(lr lrVar) {
                lrVar.d(googleTransactionId, merchantTransactionId, requestCode);
                b(Status.En);
            }
        });
    }

    public void checkForPreAuthorization(GoogleApiClient googleApiClient, final int requestCode) {
        googleApiClient.a(new b(this) {
            final /* synthetic */ lq akG;

            protected void a(lr lrVar) {
                lrVar.dQ(requestCode);
                b(Status.En);
            }
        });
    }

    public void loadFullWallet(GoogleApiClient googleApiClient, final FullWalletRequest request, final int requestCode) {
        googleApiClient.a(new b(this) {
            final /* synthetic */ lq akG;

            protected void a(lr lrVar) {
                lrVar.a(request, requestCode);
                b(Status.En);
            }
        });
    }

    public void loadMaskedWallet(GoogleApiClient googleApiClient, final MaskedWalletRequest request, final int requestCode) {
        googleApiClient.a(new b(this) {
            final /* synthetic */ lq akG;

            protected void a(lr lrVar) {
                lrVar.a(request, requestCode);
                b(Status.En);
            }
        });
    }

    public void notifyTransactionStatus(GoogleApiClient googleApiClient, final NotifyTransactionStatusRequest request) {
        googleApiClient.a(new b(this) {
            final /* synthetic */ lq akG;

            protected void a(lr lrVar) {
                lrVar.a(request);
                b(Status.En);
            }
        });
    }
}
