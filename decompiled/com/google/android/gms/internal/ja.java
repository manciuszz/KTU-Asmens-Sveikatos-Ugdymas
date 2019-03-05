package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionApi;

public class ja implements ActivityRecognitionApi {

    private static abstract class a extends com.google.android.gms.location.ActivityRecognition.a<Status> {
        private a() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    public PendingResult<Status> removeActivityUpdates(GoogleApiClient client, final PendingIntent callbackIntent) {
        return client.b(new a(this) {
            final /* synthetic */ ja Vv;

            protected void a(jg jgVar) throws RemoteException {
                jgVar.removeActivityUpdates(callbackIntent);
                b(Status.En);
            }
        });
    }

    public PendingResult<Status> requestActivityUpdates(GoogleApiClient client, final long detectionIntervalMillis, final PendingIntent callbackIntent) {
        return client.b(new a(this) {
            final /* synthetic */ ja Vv;

            protected void a(jg jgVar) throws RemoteException {
                jgVar.requestActivityUpdates(detectionIntervalMillis, callbackIntent);
                b(Status.En);
            }
        });
    }
}
