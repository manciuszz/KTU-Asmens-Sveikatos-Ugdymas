package com.google.android.gms.wearable.internal;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.hb;
import com.google.android.gms.internal.hb.e;
import com.google.android.gms.internal.hi;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;
import com.google.android.gms.wearable.NodeApi.NodeListener;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.f.b;
import com.google.android.gms.wearable.internal.f.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class au extends hb<ad> {
    private final ExecutorService agU = Executors.newCachedThreadPool();
    private final HashMap<DataListener, av> ame = new HashMap();
    private final HashMap<MessageListener, av> amf = new HashMap();
    private final HashMap<NodeListener, av> amg = new HashMap();

    private static class a extends a {
        private final List<FutureTask<Boolean>> amk;
        private final d<DataItemResult> yR;

        a(d<DataItemResult> dVar, List<FutureTask<Boolean>> list) {
            this.yR = dVar;
            this.amk = list;
        }

        public void a(am amVar) {
            this.yR.a(new com.google.android.gms.wearable.internal.f.a(new Status(amVar.statusCode), amVar.alO));
            if (amVar.statusCode != 0) {
                for (FutureTask cancel : this.amk) {
                    cancel.cancel(true);
                }
            }
        }
    }

    public au(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
    }

    private FutureTask<Boolean> a(final ParcelFileDescriptor parcelFileDescriptor, final byte[] bArr) {
        return new FutureTask(new Callable<Boolean>(this) {
            final /* synthetic */ au amh;

            public /* synthetic */ Object call() throws Exception {
                return nt();
            }

            public java.lang.Boolean nt() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00bf in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r5 = this;
                r1 = 3;
                r0 = "WearableClient";
                r0 = android.util.Log.isLoggable(r0, r1);
                if (r0 == 0) goto L_0x0023;
            L_0x0009:
                r0 = "WearableClient";
                r1 = new java.lang.StringBuilder;
                r1.<init>();
                r2 = "processAssets: writing data to FD : ";
                r1 = r1.append(r2);
                r2 = r3;
                r1 = r1.append(r2);
                r1 = r1.toString();
                android.util.Log.d(r0, r1);
            L_0x0023:
                r1 = new android.os.ParcelFileDescriptor$AutoCloseOutputStream;
                r0 = r3;
                r1.<init>(r0);
                r0 = r4;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r1.write(r0);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r1.flush();	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r0 = "WearableClient";	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = 3;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r0 = android.util.Log.isLoggable(r0, r2);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                if (r0 == 0) goto L_0x0055;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
            L_0x003b:
                r0 = "WearableClient";	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2.<init>();	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r3 = "processAssets: wrote data: ";	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = r2.append(r3);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r3 = r3;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = r2.append(r3);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = r2.toString();	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                android.util.Log.d(r0, r2);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
            L_0x0055:
                r0 = 1;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = "WearableClient";	 Catch:{ IOException -> 0x00f4 }
                r3 = 3;	 Catch:{ IOException -> 0x00f4 }
                r2 = android.util.Log.isLoggable(r2, r3);	 Catch:{ IOException -> 0x00f4 }
                if (r2 == 0) goto L_0x007d;	 Catch:{ IOException -> 0x00f4 }
            L_0x0063:
                r2 = "WearableClient";	 Catch:{ IOException -> 0x00f4 }
                r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00f4 }
                r3.<init>();	 Catch:{ IOException -> 0x00f4 }
                r4 = "processAssets: closing: ";	 Catch:{ IOException -> 0x00f4 }
                r3 = r3.append(r4);	 Catch:{ IOException -> 0x00f4 }
                r4 = r3;	 Catch:{ IOException -> 0x00f4 }
                r3 = r3.append(r4);	 Catch:{ IOException -> 0x00f4 }
                r3 = r3.toString();	 Catch:{ IOException -> 0x00f4 }
                android.util.Log.d(r2, r3);	 Catch:{ IOException -> 0x00f4 }
            L_0x007d:
                r1.close();	 Catch:{ IOException -> 0x00f4 }
            L_0x0080:
                return r0;
            L_0x0081:
                r0 = move-exception;
                r0 = "WearableClient";	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2.<init>();	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r3 = "processAssets: writing data failed: ";	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = r2.append(r3);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r3 = r3;	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = r2.append(r3);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r2 = r2.toString();	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                android.util.Log.w(r0, r2);	 Catch:{ IOException -> 0x0081, all -> 0x00c8 }
                r0 = "WearableClient";
                r2 = 3;
                r0 = android.util.Log.isLoggable(r0, r2);
                if (r0 == 0) goto L_0x00bf;
            L_0x00a5:
                r0 = "WearableClient";
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "processAssets: closing: ";
                r2 = r2.append(r3);
                r3 = r3;
                r2 = r2.append(r3);
                r2 = r2.toString();
                android.util.Log.d(r0, r2);
            L_0x00bf:
                r1.close();
            L_0x00c2:
                r0 = 0;
                r0 = java.lang.Boolean.valueOf(r0);
                goto L_0x0080;
            L_0x00c8:
                r0 = move-exception;
                r2 = "WearableClient";	 Catch:{ IOException -> 0x00f0 }
                r3 = 3;	 Catch:{ IOException -> 0x00f0 }
                r2 = android.util.Log.isLoggable(r2, r3);	 Catch:{ IOException -> 0x00f0 }
                if (r2 == 0) goto L_0x00ec;	 Catch:{ IOException -> 0x00f0 }
            L_0x00d2:
                r2 = "WearableClient";	 Catch:{ IOException -> 0x00f0 }
                r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00f0 }
                r3.<init>();	 Catch:{ IOException -> 0x00f0 }
                r4 = "processAssets: closing: ";	 Catch:{ IOException -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ IOException -> 0x00f0 }
                r4 = r3;	 Catch:{ IOException -> 0x00f0 }
                r3 = r3.append(r4);	 Catch:{ IOException -> 0x00f0 }
                r3 = r3.toString();	 Catch:{ IOException -> 0x00f0 }
                android.util.Log.d(r2, r3);	 Catch:{ IOException -> 0x00f0 }
            L_0x00ec:
                r1.close();	 Catch:{ IOException -> 0x00f0 }
            L_0x00ef:
                throw r0;
            L_0x00f0:
                r1 = move-exception;
                goto L_0x00ef;
            L_0x00f2:
                r0 = move-exception;
                goto L_0x00c2;
            L_0x00f4:
                r1 = move-exception;
                goto L_0x0080;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.internal.au.11.nt():java.lang.Boolean");
            }
        });
    }

    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.d("WearableClient", "onPostInitHandler: statusCode " + i);
        }
        if (i == 0) {
            try {
                ab anonymousClass1 = new a(this) {
                    final /* synthetic */ au amh;

                    {
                        this.amh = r1;
                    }

                    public void a(Status status) {
                    }
                };
                if (Log.isLoggable("WearableClient", 2)) {
                    Log.d("WearableClient", "onPostInitHandler: service " + iBinder);
                }
                ad by = com.google.android.gms.wearable.internal.ad.a.by(iBinder);
                for (Entry entry : this.ame.entrySet()) {
                    if (Log.isLoggable("WearableClient", 2)) {
                        Log.d("WearableClient", "onPostInitHandler: adding Data listener " + entry.getValue());
                    }
                    by.a(anonymousClass1, new b((av) entry.getValue()));
                }
                for (Entry entry2 : this.amf.entrySet()) {
                    if (Log.isLoggable("WearableClient", 2)) {
                        Log.d("WearableClient", "onPostInitHandler: adding Message listener " + entry2.getValue());
                    }
                    by.a(anonymousClass1, new b((av) entry2.getValue()));
                }
                for (Entry entry22 : this.amg.entrySet()) {
                    if (Log.isLoggable("WearableClient", 2)) {
                        Log.d("WearableClient", "onPostInitHandler: adding Node listener " + entry22.getValue());
                    }
                    by.a(anonymousClass1, new b((av) entry22.getValue()));
                }
            } catch (Throwable e) {
                Log.d("WearableClient", "WearableClientImpl.onPostInitHandler: error while adding listener", e);
            }
        }
        Log.d("WearableClient", "WearableClientImpl.onPostInitHandler: done");
        super.a(i, iBinder, bundle);
    }

    public void a(final d<DataItemResult> dVar, Uri uri) throws RemoteException {
        ((ad) ft()).a(new a(this) {
            final /* synthetic */ au amh;

            public void a(v vVar) {
                dVar.a(new com.google.android.gms.wearable.internal.f.a(new Status(vVar.statusCode), vVar.alO));
            }
        }, uri);
    }

    public void a(final d<GetFdForAssetResult> dVar, Asset asset) throws RemoteException {
        ((ad) ft()).a(new a(this) {
            final /* synthetic */ au amh;

            public void a(x xVar) {
                dVar.a(new c(new Status(xVar.statusCode), xVar.alP));
            }
        }, asset);
    }

    public void a(d<Status> dVar, DataListener dataListener) throws RemoteException {
        synchronized (this.ame) {
            ac acVar = (ac) this.ame.remove(dataListener);
        }
        if (acVar == null) {
            dVar.a(new Status(4002));
        } else {
            a((d) dVar, acVar);
        }
    }

    public void a(final d<Status> dVar, final DataListener dataListener, IntentFilter[] intentFilterArr) throws RemoteException {
        av a = av.a(dataListener, intentFilterArr);
        synchronized (this.ame) {
            if (this.ame.get(dataListener) != null) {
                dVar.a(new Status(4001));
                return;
            }
            this.ame.put(dataListener, a);
            ((ad) ft()).a(new a(this) {
                final /* synthetic */ au amh;

                public void a(Status status) {
                    if (!status.isSuccess()) {
                        synchronized (this.amh.ame) {
                            this.amh.ame.remove(dataListener);
                        }
                    }
                    dVar.a(status);
                }
            }, new b(a));
        }
    }

    public void a(d<GetFdForAssetResult> dVar, DataItemAsset dataItemAsset) throws RemoteException {
        a((d) dVar, Asset.createFromRef(dataItemAsset.getId()));
    }

    public void a(d<Status> dVar, MessageListener messageListener) throws RemoteException {
        synchronized (this.amf) {
            ac acVar = (ac) this.amf.remove(messageListener);
            if (acVar == null) {
                dVar.a(new Status(4002));
            } else {
                a((d) dVar, acVar);
            }
        }
    }

    public void a(final d<Status> dVar, final MessageListener messageListener, IntentFilter[] intentFilterArr) throws RemoteException {
        av a = av.a(messageListener, intentFilterArr);
        synchronized (this.amf) {
            if (this.amf.get(messageListener) != null) {
                dVar.a(new Status(4001));
                return;
            }
            this.amf.put(messageListener, a);
            ((ad) ft()).a(new a(this) {
                final /* synthetic */ au amh;

                public void a(Status status) {
                    if (!status.isSuccess()) {
                        synchronized (this.amh.amf) {
                            this.amh.amf.remove(messageListener);
                        }
                    }
                    dVar.a(status);
                }
            }, new b(a));
        }
    }

    public void a(final d<Status> dVar, final NodeListener nodeListener) throws RemoteException, RemoteException {
        av a = av.a(nodeListener);
        synchronized (this.amg) {
            if (this.amg.get(nodeListener) != null) {
                dVar.a(new Status(4001));
                return;
            }
            this.amg.put(nodeListener, a);
            ((ad) ft()).a(new a(this) {
                final /* synthetic */ au amh;

                public void a(Status status) {
                    if (!status.isSuccess()) {
                        synchronized (this.amh.amg) {
                            this.amh.amg.remove(nodeListener);
                        }
                    }
                    dVar.a(status);
                }
            }, new b(a));
        }
    }

    public void a(d<DataItemResult> dVar, PutDataRequest putDataRequest) throws RemoteException {
        for (Entry value : putDataRequest.getAssets().entrySet()) {
            Asset asset = (Asset) value.getValue();
            if (asset.getData() == null && asset.getDigest() == null && asset.getFd() == null && asset.getUri() == null) {
                throw new IllegalArgumentException("Put for " + putDataRequest.getUri() + " contains invalid asset: " + asset);
            }
        }
        PutDataRequest k = PutDataRequest.k(putDataRequest.getUri());
        k.setData(putDataRequest.getData());
        List arrayList = new ArrayList();
        for (Entry value2 : putDataRequest.getAssets().entrySet()) {
            Asset asset2 = (Asset) value2.getValue();
            if (asset2.getData() == null) {
                k.putAsset((String) value2.getKey(), (Asset) value2.getValue());
            } else {
                try {
                    ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                    if (Log.isLoggable("WearableClient", 3)) {
                        Log.d("WearableClient", "processAssets: replacing data with FD in asset: " + asset2 + " read:" + createPipe[0] + " write:" + createPipe[1]);
                    }
                    k.putAsset((String) value2.getKey(), Asset.createFromFd(createPipe[0]));
                    Runnable a = a(createPipe[1], asset2.getData());
                    arrayList.add(a);
                    this.agU.submit(a);
                } catch (Throwable e) {
                    throw new IllegalStateException("Unable to create ParcelFileDescriptor for asset in request: " + putDataRequest, e);
                }
            }
        }
        try {
            ((ad) ft()).a(new a(dVar, arrayList), k);
        } catch (Throwable e2) {
            throw new IllegalStateException("Unable to putDataItem: " + putDataRequest, e2);
        }
    }

    public void a(final d<Status> dVar, ac acVar) throws RemoteException {
        ((ad) ft()).a(new a(this) {
            final /* synthetic */ au amh;

            public void a(Status status) {
                dVar.a(status);
            }
        }, new ao(acVar));
    }

    public void a(final d<SendMessageResult> dVar, String str, String str2, byte[] bArr) throws RemoteException {
        ((ad) ft()).a(new a(this) {
            final /* synthetic */ au amh;

            public void a(aq aqVar) {
                dVar.a(new com.google.android.gms.wearable.internal.ae.a(new Status(aqVar.statusCode), aqVar.amc));
            }
        }, str, str2, bArr);
    }

    protected void a(hi hiVar, e eVar) throws RemoteException {
        hiVar.e(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName());
    }

    public void b(final d<DataItemBuffer> dVar, Uri uri) throws RemoteException {
        ((ad) ft()).b(new a(this) {
            final /* synthetic */ au amh;

            public void Z(DataHolder dataHolder) {
                dVar.a(new DataItemBuffer(dataHolder));
            }
        }, uri);
    }

    public void b(d<Status> dVar, NodeListener nodeListener) throws RemoteException {
        synchronized (this.amg) {
            ac acVar = (ac) this.amg.remove(nodeListener);
            if (acVar == null) {
                dVar.a(new Status(4002));
            } else {
                a((d) dVar, acVar);
            }
        }
    }

    protected String bu() {
        return "com.google.android.gms.wearable.BIND";
    }

    protected String bv() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }

    protected ad bz(IBinder iBinder) {
        return com.google.android.gms.wearable.internal.ad.a.by(iBinder);
    }

    public void c(final d<DeleteDataItemsResult> dVar, Uri uri) throws RemoteException {
        ((ad) ft()).c(new a(this) {
            final /* synthetic */ au amh;

            public void a(p pVar) {
                dVar.a(new b(new Status(pVar.statusCode), pVar.alL));
            }
        }, uri);
    }

    public void disconnect() {
        super.disconnect();
        this.ame.clear();
        this.amf.clear();
        this.amg.clear();
    }

    public void o(final d<DataItemBuffer> dVar) throws RemoteException {
        ((ad) ft()).d(new a(this) {
            final /* synthetic */ au amh;

            public void Z(DataHolder dataHolder) {
                dVar.a(new DataItemBuffer(dataHolder));
            }
        });
    }

    public void p(final d<GetLocalNodeResult> dVar) throws RemoteException {
        ((ad) ft()).e(new a(this) {
            final /* synthetic */ au amh;

            public void a(z zVar) {
                dVar.a(new ah.b(new Status(zVar.statusCode), zVar.alQ));
            }
        });
    }

    public void q(final d<GetConnectedNodesResult> dVar) throws RemoteException {
        ((ad) ft()).f(new a(this) {
            final /* synthetic */ au amh;

            public void a(t tVar) {
                List arrayList = new ArrayList();
                arrayList.addAll(tVar.alN);
                dVar.a(new com.google.android.gms.wearable.internal.ah.a(new Status(tVar.statusCode), arrayList));
            }
        });
    }

    protected /* synthetic */ IInterface x(IBinder iBinder) {
        return bz(iBinder);
    }
}
