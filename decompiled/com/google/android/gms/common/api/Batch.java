package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.a.a;
import com.google.android.gms.common.api.a.c;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends a<BatchResult> {
    private boolean DA;
    private boolean DB;
    private final PendingResult<?>[] DC;
    private int Dz;
    private final Object ls;

    public static final class Builder {
        private List<PendingResult<?>> DE = new ArrayList();
        private Looper DF;

        public Builder(GoogleApiClient googleApiClient) {
            this.DF = googleApiClient.getLooper();
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.DE.size());
            this.DE.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.DE, this.DF);
        }
    }

    private Batch(List<PendingResult<?>> pendingResultList, Looper looper) {
        super(new c(looper));
        this.ls = new Object();
        this.Dz = pendingResultList.size();
        this.DC = new PendingResult[this.Dz];
        for (int i = 0; i < pendingResultList.size(); i++) {
            PendingResult pendingResult = (PendingResult) pendingResultList.get(i);
            this.DC[i] = pendingResult;
            pendingResult.a(new PendingResult.a(this) {
                final /* synthetic */ Batch DD;

                {
                    this.DD = r1;
                }

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void n(com.google.android.gms.common.api.Status r6) {
                    /*
                    r5 = this;
                    r0 = r5.DD;
                    r1 = r0.ls;
                    monitor-enter(r1);
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r0 = r0.isCanceled();	 Catch:{ all -> 0x0039 }
                    if (r0 == 0) goto L_0x0011;
                L_0x000f:
                    monitor-exit(r1);	 Catch:{ all -> 0x0039 }
                L_0x0010:
                    return;
                L_0x0011:
                    r0 = r6.isCanceled();	 Catch:{ all -> 0x0039 }
                    if (r0 == 0) goto L_0x003c;
                L_0x0017:
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r2 = 1;
                    r0.DB = r2;	 Catch:{ all -> 0x0039 }
                L_0x001d:
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r0.Dz = r0.Dz - 1;	 Catch:{ all -> 0x0039 }
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r0 = r0.Dz;	 Catch:{ all -> 0x0039 }
                    if (r0 != 0) goto L_0x0037;
                L_0x002a:
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r0 = r0.DB;	 Catch:{ all -> 0x0039 }
                    if (r0 == 0) goto L_0x0049;
                L_0x0032:
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    super.cancel();	 Catch:{ all -> 0x0039 }
                L_0x0037:
                    monitor-exit(r1);	 Catch:{ all -> 0x0039 }
                    goto L_0x0010;
                L_0x0039:
                    r0 = move-exception;
                    monitor-exit(r1);	 Catch:{ all -> 0x0039 }
                    throw r0;
                L_0x003c:
                    r0 = r6.isSuccess();	 Catch:{ all -> 0x0039 }
                    if (r0 != 0) goto L_0x001d;
                L_0x0042:
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r2 = 1;
                    r0.DA = r2;	 Catch:{ all -> 0x0039 }
                    goto L_0x001d;
                L_0x0049:
                    r0 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r0 = r0.DA;	 Catch:{ all -> 0x0039 }
                    if (r0 == 0) goto L_0x0069;
                L_0x0051:
                    r0 = new com.google.android.gms.common.api.Status;	 Catch:{ all -> 0x0039 }
                    r2 = 13;
                    r0.<init>(r2);	 Catch:{ all -> 0x0039 }
                L_0x0058:
                    r2 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r3 = new com.google.android.gms.common.api.BatchResult;	 Catch:{ all -> 0x0039 }
                    r4 = r5.DD;	 Catch:{ all -> 0x0039 }
                    r4 = r4.DC;	 Catch:{ all -> 0x0039 }
                    r3.<init>(r0, r4);	 Catch:{ all -> 0x0039 }
                    r2.b(r3);	 Catch:{ all -> 0x0039 }
                    goto L_0x0037;
                L_0x0069:
                    r0 = com.google.android.gms.common.api.Status.En;	 Catch:{ all -> 0x0039 }
                    goto L_0x0058;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.Batch.1.n(com.google.android.gms.common.api.Status):void");
                }
            });
        }
    }

    public /* synthetic */ Result c(Status status) {
        return createFailedResult(status);
    }

    public void cancel() {
        super.cancel();
        for (PendingResult cancel : this.DC) {
            cancel.cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.DC);
    }
}
