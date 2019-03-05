package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class dn extends em implements com.google.android.gms.internal.dp.a, com.google.android.gms.internal.ey.a {
    private final bt kB;
    private final ex lN;
    private final Object ls = new Object();
    private final Context mContext;
    private bm nf;
    private final k pA;
    private em pB;
    private du pC;
    private boolean pD = false;
    private bk pE;
    private bq pF;
    private final com.google.android.gms.internal.dm.a px;
    private final Object py = new Object();
    private final com.google.android.gms.internal.ds.a pz;

    private static final class a extends Exception {
        private final int pJ;

        public a(String str, int i) {
            super(str);
            this.pJ = i;
        }

        public int getErrorCode() {
            return this.pJ;
        }
    }

    public dn(Context context, com.google.android.gms.internal.ds.a aVar, k kVar, ex exVar, bt btVar, com.google.android.gms.internal.dm.a aVar2) {
        this.kB = btVar;
        this.px = aVar2;
        this.lN = exVar;
        this.mContext = context;
        this.pz = aVar;
        this.pA = kVar;
    }

    private al a(ds dsVar) throws a {
        if (this.pC.qj == null) {
            throw new a("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] split = this.pC.qj.split("x");
        if (split.length != 2) {
            throw new a("Could not parse the ad size from the ad response: " + this.pC.qj, 0);
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            for (al alVar : dsVar.kT.mg) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i = alVar.width == -1 ? (int) (((float) alVar.widthPixels) / f) : alVar.width;
                int i2 = alVar.height == -2 ? (int) (((float) alVar.heightPixels) / f) : alVar.height;
                if (parseInt == i && parseInt2 == i2) {
                    return new al(alVar, dsVar.kT.mg);
                }
            }
            throw new a("The ad size from the ad response was not one of the requested sizes: " + this.pC.qj, 0);
        } catch (NumberFormatException e) {
            throw new a("Could not parse the ad size from the ad response: " + this.pC.qj, 0);
        }
    }

    private void a(ds dsVar, long j) throws a {
        synchronized (this.py) {
            this.pE = new bk(this.mContext, dsVar, this.kB, this.nf);
        }
        this.pF = this.pE.a(j, 60000);
        switch (this.pF.nL) {
            case 0:
                return;
            case 1:
                throw new a("No fill from any mediation ad networks.", 3);
            default:
                throw new a("Unexpected mediation result: " + this.pF.nL, 0);
        }
    }

    private void bn() throws a {
        if (this.pC.errorCode != -3) {
            if (TextUtils.isEmpty(this.pC.qe)) {
                throw new a("No fill from ad server.", 3);
            } else if (this.pC.qg) {
                try {
                    this.nf = new bm(this.pC.qe);
                } catch (JSONException e) {
                    throw new a("Could not parse mediation config: " + this.pC.qe, 0);
                }
            }
        }
    }

    private boolean c(long j) throws a {
        long elapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.ls.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            throw new a("Ad request cancelled.", -1);
        }
    }

    private void e(long j) throws a {
        et.sv.post(new Runnable(this) {
            final /* synthetic */ dn pG;

            {
                this.pG = r1;
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r7 = this;
                r0 = r7.pG;
                r6 = r0.ls;
                monitor-enter(r6);
                r0 = r7.pG;	 Catch:{ all -> 0x005f }
                r0 = r0.pC;	 Catch:{ all -> 0x005f }
                r0 = r0.errorCode;	 Catch:{ all -> 0x005f }
                r1 = -2;
                if (r0 == r1) goto L_0x0014;
            L_0x0012:
                monitor-exit(r6);	 Catch:{ all -> 0x005f }
            L_0x0013:
                return;
            L_0x0014:
                r0 = r7.pG;	 Catch:{ all -> 0x005f }
                r0 = r0.lN;	 Catch:{ all -> 0x005f }
                r0 = r0.cb();	 Catch:{ all -> 0x005f }
                r1 = r7.pG;	 Catch:{ all -> 0x005f }
                r0.a(r1);	 Catch:{ all -> 0x005f }
                r0 = r7.pG;	 Catch:{ all -> 0x005f }
                r0 = r0.pC;	 Catch:{ all -> 0x005f }
                r0 = r0.errorCode;	 Catch:{ all -> 0x005f }
                r1 = -3;
                if (r0 != r1) goto L_0x0062;
            L_0x002e:
                r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005f }
                r0.<init>();	 Catch:{ all -> 0x005f }
                r1 = "Loading URL in WebView: ";
                r0 = r0.append(r1);	 Catch:{ all -> 0x005f }
                r1 = r7.pG;	 Catch:{ all -> 0x005f }
                r1 = r1.pC;	 Catch:{ all -> 0x005f }
                r1 = r1.oA;	 Catch:{ all -> 0x005f }
                r0 = r0.append(r1);	 Catch:{ all -> 0x005f }
                r0 = r0.toString();	 Catch:{ all -> 0x005f }
                com.google.android.gms.internal.eu.C(r0);	 Catch:{ all -> 0x005f }
                r0 = r7.pG;	 Catch:{ all -> 0x005f }
                r0 = r0.lN;	 Catch:{ all -> 0x005f }
                r1 = r7.pG;	 Catch:{ all -> 0x005f }
                r1 = r1.pC;	 Catch:{ all -> 0x005f }
                r1 = r1.oA;	 Catch:{ all -> 0x005f }
                r0.loadUrl(r1);	 Catch:{ all -> 0x005f }
            L_0x005d:
                monitor-exit(r6);	 Catch:{ all -> 0x005f }
                goto L_0x0013;
            L_0x005f:
                r0 = move-exception;
                monitor-exit(r6);	 Catch:{ all -> 0x005f }
                throw r0;
            L_0x0062:
                r0 = "Loading HTML in WebView.";
                com.google.android.gms.internal.eu.C(r0);	 Catch:{ all -> 0x005f }
                r0 = r7.pG;	 Catch:{ all -> 0x005f }
                r0 = r0.lN;	 Catch:{ all -> 0x005f }
                r1 = r7.pG;	 Catch:{ all -> 0x005f }
                r1 = r1.pC;	 Catch:{ all -> 0x005f }
                r1 = r1.oA;	 Catch:{ all -> 0x005f }
                r1 = com.google.android.gms.internal.eo.v(r1);	 Catch:{ all -> 0x005f }
                r2 = r7.pG;	 Catch:{ all -> 0x005f }
                r2 = r2.pC;	 Catch:{ all -> 0x005f }
                r2 = r2.qe;	 Catch:{ all -> 0x005f }
                r3 = "text/html";
                r4 = "UTF-8";
                r5 = 0;
                r0.loadDataWithBaseURL(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x005f }
                goto L_0x005d;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.dn.3.run():void");
            }
        });
        h(j);
    }

    private void g(long j) throws a {
        while (c(j)) {
            if (this.pC != null) {
                synchronized (this.py) {
                    this.pB = null;
                }
                if (this.pC.errorCode != -2 && this.pC.errorCode != -3) {
                    throw new a("There was a problem getting an ad response. ErrorCode: " + this.pC.errorCode, this.pC.errorCode);
                }
                return;
            }
        }
        throw new a("Timed out waiting for ad response.", 2);
    }

    private void h(long j) throws a {
        while (c(j)) {
            if (this.pD) {
                return;
            }
        }
        throw new a("Timed out waiting for WebView to finish loading.", 2);
    }

    public void a(du duVar) {
        synchronized (this.ls) {
            eu.z("Received ad response.");
            this.pC = duVar;
            this.ls.notify();
        }
    }

    public void a(ex exVar) {
        synchronized (this.ls) {
            eu.z("WebView finished loading.");
            this.pD = true;
            this.ls.notify();
        }
    }

    public void bh() {
        synchronized (this.ls) {
            long j;
            al alVar;
            final ef efVar;
            eu.z("AdLoaderBackgroundTask started.");
            g z = this.pA.z();
            ds dsVar = new ds(this.pz, z.b(this.mContext), z.a(this.mContext));
            al alVar2 = null;
            int i = -2;
            long j2 = -1;
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                em a = dp.a(this.mContext, dsVar, this);
                synchronized (this.py) {
                    this.pB = a;
                    if (this.pB == null) {
                        throw new a("Could not start the ad request service.", 0);
                    }
                }
                g(elapsedRealtime);
                j2 = SystemClock.elapsedRealtime();
                bn();
                if (dsVar.kT.mg != null) {
                    alVar2 = a(dsVar);
                }
                if (this.pC.qg) {
                    a(dsVar, elapsedRealtime);
                } else if (this.pC.qm) {
                    f(elapsedRealtime);
                } else {
                    e(elapsedRealtime);
                }
                j = j2;
                alVar = alVar2;
            } catch (a e) {
                i = e.getErrorCode();
                if (i == 3 || i == -1) {
                    eu.B(e.getMessage());
                } else {
                    eu.D(e.getMessage());
                }
                if (this.pC == null) {
                    this.pC = new du(i);
                } else {
                    this.pC = new du(i, this.pC.nx);
                }
                et.sv.post(new Runnable(this) {
                    final /* synthetic */ dn pG;

                    {
                        this.pG = r1;
                    }

                    public void run() {
                        this.pG.onStop();
                    }
                });
                j = j2;
                alVar = alVar2;
            }
            if (!TextUtils.isEmpty(this.pC.qo)) {
                try {
                    JSONObject jSONObject = new JSONObject(this.pC.qo);
                } catch (Throwable e2) {
                    eu.b("Error parsing the JSON for Active View.", e2);
                }
                efVar = new ef(dsVar.pX, this.lN, this.pC.nt, i, this.pC.nu, this.pC.qi, this.pC.orientation, this.pC.nx, dsVar.qa, this.pC.qg, this.pF == null ? this.pF.nM : null, this.pF == null ? this.pF.nN : null, this.pF == null ? this.pF.nO : null, this.nf, this.pF == null ? this.pF.nP : null, this.pC.qh, alVar, this.pC.qf, j, this.pC.qk, this.pC.ql, r29);
                et.sv.post(new Runnable(this) {
                    final /* synthetic */ dn pG;

                    public void run() {
                        synchronized (this.pG.ls) {
                            this.pG.px.a(efVar);
                        }
                    }
                });
            }
            JSONObject jSONObject2 = null;
            if (this.pF == null) {
            }
            if (this.pF == null) {
            }
            if (this.pF == null) {
            }
            if (this.pF == null) {
            }
            efVar = new ef(dsVar.pX, this.lN, this.pC.nt, i, this.pC.nu, this.pC.qi, this.pC.orientation, this.pC.nx, dsVar.qa, this.pC.qg, this.pF == null ? this.pF.nM : null, this.pF == null ? this.pF.nN : null, this.pF == null ? this.pF.nO : null, this.nf, this.pF == null ? this.pF.nP : null, this.pC.qh, alVar, this.pC.qf, j, this.pC.qk, this.pC.ql, jSONObject2);
            et.sv.post(/* anonymous class already generated */);
        }
    }

    protected void f(long j) throws a {
        int i;
        int i2;
        al V = this.lN.V();
        if (V.mf) {
            i = this.mContext.getResources().getDisplayMetrics().widthPixels;
            i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        } else {
            i = V.widthPixels;
            i2 = V.heightPixels;
        }
        final do doVar = new do(this, this.lN, i, i2);
        et.sv.post(new Runnable(this) {
            final /* synthetic */ dn pG;

            public void run() {
                synchronized (this.pG.ls) {
                    if (this.pG.pC.errorCode != -2) {
                        return;
                    }
                    this.pG.lN.cb().a(this.pG);
                    doVar.b(this.pG.pC);
                }
            }
        });
        h(j);
        if (doVar.bq()) {
            eu.z("Ad-Network indicated no fill with passback URL.");
            throw new a("AdNetwork sent passback url", 3);
        } else if (!doVar.br()) {
            throw new a("AdNetwork timed out", 2);
        }
    }

    public void onStop() {
        synchronized (this.py) {
            if (this.pB != null) {
                this.pB.cancel();
            }
            this.lN.stopLoading();
            eo.a(this.lN);
            if (this.pE != null) {
                this.pE.cancel();
            }
        }
    }
}
