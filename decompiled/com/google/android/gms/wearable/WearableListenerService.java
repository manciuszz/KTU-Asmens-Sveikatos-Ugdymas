package com.google.android.gms.wearable;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.NodeApi.NodeListener;
import com.google.android.gms.wearable.internal.af;
import com.google.android.gms.wearable.internal.ai;

public abstract class WearableListenerService extends Service implements DataListener, MessageListener, NodeListener {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private IBinder GC;
    private volatile int alq = -1;
    private Handler alr;
    private Object als = new Object();
    private boolean alt;
    private String xQ;

    private class a extends com.google.android.gms.wearable.internal.ac.a {
        final /* synthetic */ WearableListenerService alu;

        private a(WearableListenerService wearableListenerService) {
            this.alu = wearableListenerService;
        }

        public void Y(final DataHolder dataHolder) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onDataItemChanged: " + this.alu.xQ + ": " + dataHolder);
            }
            this.alu.nn();
            synchronized (this.alu.als) {
                if (this.alu.alt) {
                    dataHolder.close();
                    return;
                }
                this.alu.alr.post(new Runnable(this) {
                    final /* synthetic */ a alw;

                    public void run() {
                        DataEventBuffer dataEventBuffer = new DataEventBuffer(dataHolder);
                        try {
                            this.alw.alu.onDataChanged(dataEventBuffer);
                        } finally {
                            dataEventBuffer.release();
                        }
                    }
                });
            }
        }

        public void a(final af afVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onMessageReceived: " + afVar);
            }
            this.alu.nn();
            synchronized (this.alu.als) {
                if (this.alu.alt) {
                    return;
                }
                this.alu.alr.post(new Runnable(this) {
                    final /* synthetic */ a alw;

                    public void run() {
                        this.alw.alu.onMessageReceived(afVar);
                    }
                });
            }
        }

        public void a(final ai aiVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerConnected: " + this.alu.xQ + ": " + aiVar);
            }
            this.alu.nn();
            synchronized (this.alu.als) {
                if (this.alu.alt) {
                    return;
                }
                this.alu.alr.post(new Runnable(this) {
                    final /* synthetic */ a alw;

                    public void run() {
                        this.alw.alu.onPeerConnected(aiVar);
                    }
                });
            }
        }

        public void b(final ai aiVar) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", "onPeerDisconnected: " + this.alu.xQ + ": " + aiVar);
            }
            this.alu.nn();
            synchronized (this.alu.als) {
                if (this.alu.alt) {
                    return;
                }
                this.alu.alr.post(new Runnable(this) {
                    final /* synthetic */ a alw;

                    public void run() {
                        this.alw.alu.onPeerDisconnected(aiVar);
                    }
                });
            }
        }
    }

    private boolean ed(int i) {
        String str = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
        String[] packagesForUid = getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        for (Object equals : packagesForUid) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private void nn() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.alq) {
            if (GooglePlayServicesUtil.b(getPackageManager(), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE) && ed(callingUid)) {
                this.alq = callingUid;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices");
        }
    }

    public final IBinder onBind(Intent intent) {
        return BIND_LISTENER_INTENT_ACTION.equals(intent.getAction()) ? this.GC : null;
    }

    public void onCreate() {
        super.onCreate();
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", "onCreate: " + getPackageName());
        }
        this.xQ = getPackageName();
        HandlerThread handlerThread = new HandlerThread("WearableListenerService");
        handlerThread.start();
        this.alr = new Handler(handlerThread.getLooper());
        this.GC = new a();
    }

    public void onDataChanged(DataEventBuffer dataEvents) {
    }

    public void onDestroy() {
        synchronized (this.als) {
            this.alt = true;
            this.alr.getLooper().quit();
        }
        super.onDestroy();
    }

    public void onMessageReceived(MessageEvent messageEvent) {
    }

    public void onPeerConnected(Node peer) {
    }

    public void onPeerDisconnected(Node peer) {
    }
}
