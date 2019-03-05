package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.internal.hb.e;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class gh extends hb<gl> {
    private static final gn BG = new gn("CastClientImpl");
    private static final Object Ca = new Object();
    private static final Object Cb = new Object();
    private double AP = 0.0d;
    private boolean AQ = false;
    private final Listener Ae;
    private ApplicationMetadata BH = null;
    private final CastDevice BI;
    private final gm BJ;
    private final Map<String, MessageReceivedCallback> BK = new HashMap();
    private final long BL;
    private String BM = null;
    private boolean BN;
    private boolean BO;
    private boolean BP = false;
    private AtomicBoolean BQ = new AtomicBoolean(false);
    private int BR = -1;
    private final AtomicLong BS = new AtomicLong(0);
    private String BT;
    private String BU;
    private Bundle BV;
    private Map<Long, d<Status>> BW = new HashMap();
    private b BX = new b();
    private d<ApplicationConnectionResult> BY;
    private d<Status> BZ;
    private final Handler mHandler;

    private static final class a implements ApplicationConnectionResult {
        private final ApplicationMetadata Ci;
        private final String Cj;
        private final boolean Ck;
        private final String rR;
        private final Status yz;

        public a(Status status) {
            this(status, null, null, null, false);
        }

        public a(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
            this.yz = status;
            this.Ci = applicationMetadata;
            this.Cj = str;
            this.rR = str2;
            this.Ck = z;
        }

        public ApplicationMetadata getApplicationMetadata() {
            return this.Ci;
        }

        public String getApplicationStatus() {
            return this.Cj;
        }

        public String getSessionId() {
            return this.rR;
        }

        public Status getStatus() {
            return this.yz;
        }

        public boolean getWasLaunched() {
            return this.Ck;
        }
    }

    private class b implements OnConnectionFailedListener {
        final /* synthetic */ gh Cc;

        private b(gh ghVar) {
            this.Cc = ghVar;
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.Cc.ei();
        }
    }

    public gh(Context context, Looper looper, CastDevice castDevice, long j, Listener listener, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, (String[]) null);
        this.BI = castDevice;
        this.Ae = listener;
        this.BL = j;
        this.mHandler = new Handler(looper);
        registerConnectionFailedListener(this.BX);
        this.BJ = new com.google.android.gms.internal.gm.a(this) {
            final /* synthetic */ gh Cc;

            {
                this.Cc = r1;
            }

            private boolean X(int i) {
                synchronized (gh.Cb) {
                    if (this.Cc.BZ != null) {
                        this.Cc.BZ.a(new Status(i));
                        this.Cc.BZ = null;
                        return true;
                    }
                    return false;
                }
            }

            private void b(long j, int i) {
                synchronized (this.Cc.BW) {
                    d dVar = (d) this.Cc.BW.remove(Long.valueOf(j));
                }
                if (dVar != null) {
                    dVar.a(new Status(i));
                }
            }

            public void T(int i) {
                gh.BG.b("ICastDeviceControllerListener.onDisconnected: %d", Integer.valueOf(i));
                this.Cc.BP = false;
                this.Cc.BQ.set(false);
                this.Cc.BH = null;
                if (i != 0) {
                    this.Cc.an(2);
                }
            }

            public void U(int i) {
                synchronized (gh.Ca) {
                    if (this.Cc.BY != null) {
                        this.Cc.BY.a(new a(new Status(i)));
                        this.Cc.BY = null;
                    }
                }
            }

            public void V(int i) {
                X(i);
            }

            public void W(int i) {
                X(i);
            }

            public void a(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
                this.Cc.BH = applicationMetadata;
                this.Cc.BT = applicationMetadata.getApplicationId();
                this.Cc.BU = str2;
                synchronized (gh.Ca) {
                    if (this.Cc.BY != null) {
                        this.Cc.BY.a(new a(new Status(0), applicationMetadata, str, str2, z));
                        this.Cc.BY = null;
                    }
                }
            }

            public void a(String str, double d, boolean z) {
                gh.BG.b("Deprecated callback: \"onStatusreceived\"", new Object[0]);
            }

            public void a(String str, long j) {
                b(j, 0);
            }

            public void a(String str, long j, int i) {
                b(j, i);
            }

            public void b(final ge geVar) {
                gh.BG.b("onApplicationStatusChanged", new Object[0]);
                this.Cc.mHandler.post(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 Ce;

                    public void run() {
                        this.Ce.Cc.a(geVar);
                    }
                });
            }

            public void b(final gj gjVar) {
                gh.BG.b("onDeviceStatusChanged", new Object[0]);
                this.Cc.mHandler.post(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 Ce;

                    public void run() {
                        this.Ce.Cc.a(gjVar);
                    }
                });
            }

            public void b(String str, byte[] bArr) {
                gh.BG.b("IGNORING: Receive (type=binary, ns=%s) <%d bytes>", str, Integer.valueOf(bArr.length));
            }

            public void g(final String str, final String str2) {
                gh.BG.b("Receive (type=text, ns=%s) %s", str, str2);
                this.Cc.mHandler.post(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 Ce;

                    public void run() {
                        synchronized (this.Ce.Cc.BK) {
                            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.Ce.Cc.BK.get(str);
                        }
                        if (messageReceivedCallback != null) {
                            messageReceivedCallback.onMessageReceived(this.Ce.Cc.BI, str, str2);
                            return;
                        }
                        gh.BG.b("Discarded message for unknown namespace '%s'", str);
                    }
                });
            }

            public void onApplicationDisconnected(final int statusCode) {
                this.Cc.BT = null;
                this.Cc.BU = null;
                X(statusCode);
                if (this.Cc.Ae != null) {
                    this.Cc.mHandler.post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 Ce;

                        public void run() {
                            if (this.Ce.Cc.Ae != null) {
                                this.Ce.Cc.Ae.onApplicationDisconnected(statusCode);
                            }
                        }
                    });
                }
            }
        };
    }

    private void a(ge geVar) {
        boolean z;
        String ec = geVar.ec();
        if (gi.a(ec, this.BM)) {
            z = false;
        } else {
            this.BM = ec;
            z = true;
        }
        BG.b("hasChanged=%b, mFirstApplicationStatusUpdate=%b", Boolean.valueOf(z), Boolean.valueOf(this.BN));
        if (this.Ae != null && (z || this.BN)) {
            this.Ae.onApplicationStatusChanged();
        }
        this.BN = false;
    }

    private void a(gj gjVar) {
        boolean z;
        double eh = gjVar.eh();
        if (eh == Double.NaN || eh == this.AP) {
            z = false;
        } else {
            this.AP = eh;
            z = true;
        }
        boolean en = gjVar.en();
        if (en != this.AQ) {
            this.AQ = en;
            z = true;
        }
        BG.b("hasVolumeChanged=%b, mFirstDeviceStatusUpdate=%b", Boolean.valueOf(z), Boolean.valueOf(this.BO));
        if (this.Ae != null && (z || this.BO)) {
            this.Ae.onVolumeChanged();
        }
        int eo = gjVar.eo();
        if (eo != this.BR) {
            this.BR = eo;
            z = true;
        } else {
            z = false;
        }
        BG.b("hasActiveInputChanged=%b, mFirstDeviceStatusUpdate=%b", Boolean.valueOf(z), Boolean.valueOf(this.BO));
        if (this.Ae != null && (z || this.BO)) {
            this.Ae.O(this.BR);
        }
        this.BO = false;
    }

    private void c(d<ApplicationConnectionResult> dVar) {
        synchronized (Ca) {
            if (this.BY != null) {
                this.BY.a(new a(new Status(2002)));
            }
            this.BY = dVar;
        }
    }

    private void e(d<Status> dVar) {
        synchronized (Cb) {
            if (this.BZ != null) {
                dVar.a(new Status(2001));
                return;
            }
            this.BZ = dVar;
        }
    }

    private void ei() {
        BG.b("removing all MessageReceivedCallbacks", new Object[0]);
        synchronized (this.BK) {
            this.BK.clear();
        }
    }

    private void ej() throws IllegalStateException {
        if (!this.BP || this.BQ.get()) {
            throw new IllegalStateException("Not connected to a device");
        }
    }

    protected gl G(IBinder iBinder) {
        return com.google.android.gms.internal.gl.a.H(iBinder);
    }

    public void a(double d) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        ((gl) ft()).a(d, this.AP, this.AQ);
    }

    protected void a(int i, IBinder iBinder, Bundle bundle) {
        BG.b("in onPostInitHandler; statusCode=%d", Integer.valueOf(i));
        if (i == 0 || i == 1001) {
            this.BP = true;
            this.BN = true;
            this.BO = true;
        } else {
            this.BP = false;
        }
        if (i == 1001) {
            this.BV = new Bundle();
            this.BV.putBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING, true);
            i = 0;
        }
        super.a(i, iBinder, bundle);
    }

    protected void a(hi hiVar, e eVar) throws RemoteException {
        Bundle bundle = new Bundle();
        BG.b("getServiceFromBroker(): mLastApplicationId=%s, mLastSessionId=%s", this.BT, this.BU);
        this.BI.putInBundle(bundle);
        bundle.putLong("com.google.android.gms.cast.EXTRA_CAST_FLAGS", this.BL);
        if (this.BT != null) {
            bundle.putString("last_application_id", this.BT);
            if (this.BU != null) {
                bundle.putString("last_session_id", this.BU);
            }
        }
        hiVar.a((hh) eVar, (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.BJ.asBinder(), bundle);
    }

    public void a(String str, MessageReceivedCallback messageReceivedCallback) throws IllegalArgumentException, IllegalStateException, RemoteException {
        gi.ak(str);
        aj(str);
        if (messageReceivedCallback != null) {
            synchronized (this.BK) {
                this.BK.put(str, messageReceivedCallback);
            }
            ((gl) ft()).an(str);
        }
    }

    public void a(String str, LaunchOptions launchOptions, d<ApplicationConnectionResult> dVar) throws IllegalStateException, RemoteException {
        c((d) dVar);
        ((gl) ft()).a(str, launchOptions);
    }

    public void a(String str, d<Status> dVar) throws IllegalStateException, RemoteException {
        e((d) dVar);
        ((gl) ft()).am(str);
    }

    public void a(String str, String str2, d<Status> dVar) throws IllegalArgumentException, IllegalStateException, RemoteException {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("The message payload cannot be null or empty");
        } else if (str2.length() > 65536) {
            throw new IllegalArgumentException("Message exceeds maximum size");
        } else {
            gi.ak(str);
            ej();
            long incrementAndGet = this.BS.incrementAndGet();
            ((gl) ft()).a(str, str2, incrementAndGet);
            this.BW.put(Long.valueOf(incrementAndGet), dVar);
        }
    }

    public void a(String str, boolean z, d<ApplicationConnectionResult> dVar) throws IllegalStateException, RemoteException {
        c((d) dVar);
        ((gl) ft()).e(str, z);
    }

    public void aj(String str) throws IllegalArgumentException, RemoteException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Channel namespace cannot be null or empty");
        }
        synchronized (this.BK) {
            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.BK.remove(str);
        }
        if (messageReceivedCallback != null) {
            try {
                ((gl) ft()).ao(str);
            } catch (Throwable e) {
                BG.a(e, "Error unregistering namespace (%s): %s", str, e.getMessage());
            }
        }
    }

    public void b(String str, String str2, d<ApplicationConnectionResult> dVar) throws IllegalStateException, RemoteException {
        c((d) dVar);
        ((gl) ft()).h(str, str2);
    }

    protected String bu() {
        return "com.google.android.gms.cast.service.BIND_CAST_DEVICE_CONTROLLER_SERVICE";
    }

    protected String bv() {
        return "com.google.android.gms.cast.internal.ICastDeviceController";
    }

    public void d(d<Status> dVar) throws IllegalStateException, RemoteException {
        e((d) dVar);
        ((gl) ft()).ep();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disconnect() {
        /*
        r6 = this;
        r5 = 1;
        r4 = 0;
        r0 = BG;
        r1 = "disconnect(); mDisconnecting=%b, isConnected=%b";
        r2 = 2;
        r2 = new java.lang.Object[r2];
        r3 = r6.BQ;
        r3 = r3.get();
        r3 = java.lang.Boolean.valueOf(r3);
        r2[r4] = r3;
        r3 = r6.isConnected();
        r3 = java.lang.Boolean.valueOf(r3);
        r2[r5] = r3;
        r0.b(r1, r2);
        r0 = r6.BQ;
        r0 = r0.getAndSet(r5);
        if (r0 == 0) goto L_0x0034;
    L_0x002a:
        r0 = BG;
        r1 = "mDisconnecting is set, so short-circuiting";
        r2 = new java.lang.Object[r4];
        r0.b(r1, r2);
    L_0x0033:
        return;
    L_0x0034:
        r6.ei();
        r0 = r6.isConnected();	 Catch:{ RemoteException -> 0x0050 }
        if (r0 != 0) goto L_0x0043;
    L_0x003d:
        r0 = r6.isConnecting();	 Catch:{ RemoteException -> 0x0050 }
        if (r0 == 0) goto L_0x004c;
    L_0x0043:
        r0 = r6.ft();	 Catch:{ RemoteException -> 0x0050 }
        r0 = (com.google.android.gms.internal.gl) r0;	 Catch:{ RemoteException -> 0x0050 }
        r0.disconnect();	 Catch:{ RemoteException -> 0x0050 }
    L_0x004c:
        super.disconnect();
        goto L_0x0033;
    L_0x0050:
        r0 = move-exception;
        r1 = BG;	 Catch:{ all -> 0x0066 }
        r2 = "Error while disconnecting the controller interface: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0066 }
        r4 = 0;
        r5 = r0.getMessage();	 Catch:{ all -> 0x0066 }
        r3[r4] = r5;	 Catch:{ all -> 0x0066 }
        r1.a(r0, r2, r3);	 Catch:{ all -> 0x0066 }
        super.disconnect();
        goto L_0x0033;
    L_0x0066:
        r0 = move-exception;
        super.disconnect();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gh.disconnect():void");
    }

    public Bundle ef() {
        if (this.BV == null) {
            return super.ef();
        }
        Bundle bundle = this.BV;
        this.BV = null;
        return bundle;
    }

    public void eg() throws IllegalStateException, RemoteException {
        ((gl) ft()).eg();
    }

    public double eh() throws IllegalStateException {
        ej();
        return this.AP;
    }

    public ApplicationMetadata getApplicationMetadata() throws IllegalStateException {
        ej();
        return this.BH;
    }

    public String getApplicationStatus() throws IllegalStateException {
        ej();
        return this.BM;
    }

    public boolean isMute() throws IllegalStateException {
        ej();
        return this.AQ;
    }

    protected /* synthetic */ IInterface x(IBinder iBinder) {
        return G(iBinder);
    }

    public void y(boolean z) throws IllegalStateException, RemoteException {
        ((gl) ft()).a(z, this.AP, this.AQ);
    }
}
