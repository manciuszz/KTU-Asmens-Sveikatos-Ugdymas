package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

public abstract class hb<T extends IInterface> implements com.google.android.gms.common.api.Api.a, com.google.android.gms.internal.hc.b {
    public static final String[] Gh = new String[]{"service_esmobile", "service_googleme"};
    private final Looper DF;
    private final hc DS;
    private T Gb;
    private final ArrayList<b<?>> Gc;
    private f Gd;
    private volatile int Ge;
    private final String[] Gf;
    boolean Gg;
    private final Context mContext;
    final Handler mHandler;

    final class a extends Handler {
        final /* synthetic */ hb Gi;

        public a(hb hbVar, Looper looper) {
            this.Gi = hbVar;
            super(looper);
        }

        public void handleMessage(Message msg) {
            b bVar;
            if (msg.what == 1 && !this.Gi.isConnecting()) {
                bVar = (b) msg.obj;
                bVar.fu();
                bVar.unregister();
            } else if (msg.what == 3) {
                this.Gi.DS.a(new ConnectionResult(((Integer) msg.obj).intValue(), null));
            } else if (msg.what == 4) {
                this.Gi.am(1);
                this.Gi.Gb = null;
                this.Gi.DS.ao(((Integer) msg.obj).intValue());
            } else if (msg.what == 2 && !this.Gi.isConnected()) {
                bVar = (b) msg.obj;
                bVar.fu();
                bVar.unregister();
            } else if (msg.what == 2 || msg.what == 1) {
                ((b) msg.obj).fv();
            } else {
                Log.wtf("GmsClient", "Don't know how to handle this message.");
            }
        }
    }

    protected abstract class b<TListener> {
        final /* synthetic */ hb Gi;
        private boolean Gj = false;
        private TListener mListener;

        public b(hb hbVar, TListener tListener) {
            this.Gi = hbVar;
            this.mListener = tListener;
        }

        protected abstract void d(TListener tListener);

        protected abstract void fu();

        public void fv() {
            synchronized (this) {
                Object obj = this.mListener;
                if (this.Gj) {
                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                }
            }
            if (obj != null) {
                try {
                    d(obj);
                } catch (RuntimeException e) {
                    fu();
                    throw e;
                }
            }
            fu();
            synchronized (this) {
                this.Gj = true;
            }
            unregister();
        }

        public void fw() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        public void unregister() {
            fw();
            synchronized (this.Gi.Gc) {
                this.Gi.Gc.remove(this);
            }
        }
    }

    final class f implements ServiceConnection {
        final /* synthetic */ hb Gi;

        f(hb hbVar) {
            this.Gi = hbVar;
        }

        public void onServiceConnected(ComponentName component, IBinder binder) {
            this.Gi.I(binder);
        }

        public void onServiceDisconnected(ComponentName component) {
            this.Gi.mHandler.sendMessage(this.Gi.mHandler.obtainMessage(4, Integer.valueOf(1)));
        }
    }

    public static final class c implements ConnectionCallbacks {
        private final GooglePlayServicesClient.ConnectionCallbacks Gk;

        public c(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
            this.Gk = connectionCallbacks;
        }

        public boolean equals(Object other) {
            return other instanceof c ? this.Gk.equals(((c) other).Gk) : this.Gk.equals(other);
        }

        public void onConnected(Bundle connectionHint) {
            this.Gk.onConnected(connectionHint);
        }

        public void onConnectionSuspended(int cause) {
            this.Gk.onDisconnected();
        }
    }

    public abstract class d<TListener> extends b<TListener> {
        private final DataHolder DG;
        final /* synthetic */ hb Gi;

        public d(hb hbVar, TListener tListener, DataHolder dataHolder) {
            this.Gi = hbVar;
            super(hbVar, tListener);
            this.DG = dataHolder;
        }

        protected abstract void a(TListener tListener, DataHolder dataHolder);

        protected final void d(TListener tListener) {
            a(tListener, this.DG);
        }

        protected void fu() {
            if (this.DG != null) {
                this.DG.close();
            }
        }

        public /* bridge */ /* synthetic */ void fv() {
            super.fv();
        }

        public /* bridge */ /* synthetic */ void fw() {
            super.fw();
        }

        public /* bridge */ /* synthetic */ void unregister() {
            super.unregister();
        }
    }

    protected final class h extends b<Boolean> {
        final /* synthetic */ hb Gi;
        public final Bundle Gn;
        public final IBinder Go;
        public final int statusCode;

        public h(hb hbVar, int i, IBinder iBinder, Bundle bundle) {
            this.Gi = hbVar;
            super(hbVar, Boolean.valueOf(true));
            this.statusCode = i;
            this.Go = iBinder;
            this.Gn = bundle;
        }

        protected void b(Boolean bool) {
            if (bool == null) {
                this.Gi.am(1);
                return;
            }
            switch (this.statusCode) {
                case 0:
                    try {
                        if (this.Gi.bv().equals(this.Go.getInterfaceDescriptor())) {
                            this.Gi.Gb = this.Gi.x(this.Go);
                            if (this.Gi.Gb != null) {
                                this.Gi.am(3);
                                this.Gi.DS.cp();
                                return;
                            }
                        }
                    } catch (RemoteException e) {
                    }
                    hd.E(this.Gi.mContext).b(this.Gi.bu(), this.Gi.Gd);
                    this.Gi.Gd = null;
                    this.Gi.am(1);
                    this.Gi.Gb = null;
                    this.Gi.DS.a(new ConnectionResult(8, null));
                    return;
                case 10:
                    this.Gi.am(1);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                default:
                    PendingIntent pendingIntent = this.Gn != null ? (PendingIntent) this.Gn.getParcelable("pendingIntent") : null;
                    if (this.Gi.Gd != null) {
                        hd.E(this.Gi.mContext).b(this.Gi.bu(), this.Gi.Gd);
                        this.Gi.Gd = null;
                    }
                    this.Gi.am(1);
                    this.Gi.Gb = null;
                    this.Gi.DS.a(new ConnectionResult(this.statusCode, pendingIntent));
                    return;
            }
        }

        protected /* synthetic */ void d(Object obj) {
            b((Boolean) obj);
        }

        protected void fu() {
        }
    }

    public static final class e extends com.google.android.gms.internal.hh.a {
        private hb Gl;

        public e(hb hbVar) {
            this.Gl = hbVar;
        }

        public void b(int i, IBinder iBinder, Bundle bundle) {
            hm.b((Object) "onPostInitComplete can be called only once per call to getServiceFromBroker", this.Gl);
            this.Gl.a(i, iBinder, bundle);
            this.Gl = null;
        }
    }

    public static final class g implements OnConnectionFailedListener {
        private final GooglePlayServicesClient.OnConnectionFailedListener Gm;

        public g(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.Gm = onConnectionFailedListener;
        }

        public boolean equals(Object other) {
            return other instanceof g ? this.Gm.equals(((g) other).Gm) : this.Gm.equals(other);
        }

        public void onConnectionFailed(ConnectionResult result) {
            this.Gm.onConnectionFailed(result);
        }
    }

    protected hb(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this.Gc = new ArrayList();
        this.Ge = 1;
        this.Gg = false;
        this.mContext = (Context) hm.f(context);
        this.DF = (Looper) hm.b((Object) looper, (Object) "Looper must not be null");
        this.DS = new hc(context, looper, this);
        this.mHandler = new a(this, looper);
        b(strArr);
        this.Gf = strArr;
        registerConnectionCallbacks((ConnectionCallbacks) hm.f(connectionCallbacks));
        registerConnectionFailedListener((OnConnectionFailedListener) hm.f(onConnectionFailedListener));
    }

    @Deprecated
    protected hb(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this(context, context.getMainLooper(), new c(connectionCallbacks), new g(onConnectionFailedListener), strArr);
    }

    private void am(int i) {
        int i2 = this.Ge;
        this.Ge = i;
        if (i2 == i) {
            return;
        }
        if (i == 3) {
            onConnected();
        } else if (i2 == 3 && i == 1) {
            onDisconnected();
        }
    }

    protected final void I(IBinder iBinder) {
        try {
            a(com.google.android.gms.internal.hi.a.L(iBinder), new e(this));
        } catch (RemoteException e) {
            Log.w("GmsClient", "service died");
        }
    }

    protected void a(int i, IBinder iBinder, Bundle bundle) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new h(this, i, iBinder, bundle)));
    }

    @Deprecated
    public final void a(b<?> bVar) {
        synchronized (this.Gc) {
            this.Gc.add(bVar);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, bVar));
    }

    protected abstract void a(hi hiVar, e eVar) throws RemoteException;

    public void an(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.valueOf(i)));
    }

    protected void b(String... strArr) {
    }

    protected abstract String bu();

    protected abstract String bv();

    protected final void cn() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public void connect() {
        this.Gg = true;
        am(2);
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            am(1);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(isGooglePlayServicesAvailable)));
            return;
        }
        if (this.Gd != null) {
            Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
            this.Gb = null;
            hd.E(this.mContext).b(bu(), this.Gd);
        }
        this.Gd = new f(this);
        if (!hd.E(this.mContext).a(bu(), this.Gd)) {
            Log.e("GmsClient", "unable to connect to service: " + bu());
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(9)));
        }
    }

    public void disconnect() {
        this.Gg = false;
        synchronized (this.Gc) {
            int size = this.Gc.size();
            for (int i = 0; i < size; i++) {
                ((b) this.Gc.get(i)).fw();
            }
            this.Gc.clear();
        }
        am(1);
        this.Gb = null;
        if (this.Gd != null) {
            hd.E(this.mContext).b(bu(), this.Gd);
            this.Gd = null;
        }
    }

    public boolean eO() {
        return this.Gg;
    }

    public Bundle ef() {
        return null;
    }

    public final String[] fs() {
        return this.Gf;
    }

    public final T ft() {
        cn();
        return this.Gb;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.DF;
    }

    public boolean isConnected() {
        return this.Ge == 3;
    }

    public boolean isConnecting() {
        return this.Ge == 2;
    }

    @Deprecated
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
        return this.DS.isConnectionCallbacksRegistered(new c(listener));
    }

    @Deprecated
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        return this.DS.isConnectionFailedListenerRegistered(listener);
    }

    protected void onConnected() {
    }

    protected void onDisconnected() {
    }

    @Deprecated
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.DS.registerConnectionCallbacks(new c(listener));
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        this.DS.registerConnectionCallbacks(listener);
    }

    @Deprecated
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.DS.registerConnectionFailedListener(listener);
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        this.DS.registerConnectionFailedListener(listener);
    }

    @Deprecated
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
        this.DS.unregisterConnectionCallbacks(new c(listener));
    }

    @Deprecated
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
        this.DS.unregisterConnectionFailedListener(listener);
    }

    protected abstract T x(IBinder iBinder);
}
