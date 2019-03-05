package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import com.google.android.gms.internal.c.f;
import com.google.android.gms.internal.le.a;
import com.google.android.gms.internal.md;
import com.google.android.gms.tagmanager.cq.c;
import com.google.android.gms.tagmanager.cq.g;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

class cp implements f {
    private final String aet;
    private bg<a> agN;
    private final ExecutorService agU = Executors.newSingleThreadExecutor();
    private final Context mContext;

    cp(Context context, String str) {
        this.mContext = context;
        this.aet = str;
    }

    private c a(ByteArrayOutputStream byteArrayOutputStream) {
        c cVar = null;
        try {
            cVar = ba.bY(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            bh.z("Tried to convert binary resource to string for JSON parsing; not UTF-8 format");
        } catch (JSONException e2) {
            bh.D("Resource is a UTF-8 encoded string but doesn't contain a JSON container");
        }
        return cVar;
    }

    private c k(byte[] bArr) {
        c cVar = null;
        try {
            cVar = cq.b(f.a(bArr));
        } catch (md e) {
            bh.D("Resource doesn't contain a binary container");
        } catch (g e2) {
            bh.D("Resource doesn't contain a binary container");
        }
        return cVar;
    }

    public void a(bg<a> bgVar) {
        this.agN = bgVar;
    }

    public void b(final a aVar) {
        this.agU.execute(new Runnable(this) {
            final /* synthetic */ cp agV;

            public void run() {
                this.agV.c(aVar);
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean c(com.google.android.gms.internal.le.a r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.mm();
        r2 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0016 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0016 }
        r3 = com.google.android.gms.internal.me.d(r5);	 Catch:{ IOException -> 0x0024 }
        r2.write(r3);	 Catch:{ IOException -> 0x0024 }
        r0 = 1;
        r2.close();	 Catch:{ IOException -> 0x001d }
    L_0x0015:
        return r0;
    L_0x0016:
        r1 = move-exception;
        r1 = "Error opening resource file for writing";
        com.google.android.gms.tagmanager.bh.A(r1);
        goto L_0x0015;
    L_0x001d:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.android.gms.tagmanager.bh.D(r1);
        goto L_0x0015;
    L_0x0024:
        r3 = move-exception;
        r3 = "Error writing resource to disk. Removing resource from disk.";
        com.google.android.gms.tagmanager.bh.D(r3);	 Catch:{ all -> 0x0038 }
        r1.delete();	 Catch:{ all -> 0x0038 }
        r2.close();	 Catch:{ IOException -> 0x0031 }
        goto L_0x0015;
    L_0x0031:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.android.gms.tagmanager.bh.D(r1);
        goto L_0x0015;
    L_0x0038:
        r0 = move-exception;
        r2.close();	 Catch:{ IOException -> 0x003d }
    L_0x003c:
        throw r0;
    L_0x003d:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.android.gms.tagmanager.bh.D(r1);
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.cp.c(com.google.android.gms.internal.le$a):boolean");
    }

    public c dn(int i) {
        bh.C("Atttempting to load container from resource ID " + i);
        try {
            InputStream openRawResource = this.mContext.getResources().openRawResource(i);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            cq.b(openRawResource, byteArrayOutputStream);
            c a = a(byteArrayOutputStream);
            return a != null ? a : k(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            bh.D("Error reading default container resource with ID " + i);
            return null;
        } catch (NotFoundException e2) {
            bh.D("No default container resource found.");
            return null;
        }
    }

    public void lw() {
        this.agU.execute(new Runnable(this) {
            final /* synthetic */ cp agV;

            {
                this.agV = r1;
            }

            public void run() {
                this.agV.ml();
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void ml() {
        /*
        r3 = this;
        r0 = r3.agN;
        if (r0 != 0) goto L_0x000c;
    L_0x0004:
        r0 = new java.lang.IllegalStateException;
        r1 = "callback must be set before execute";
        r0.<init>(r1);
        throw r0;
    L_0x000c:
        r0 = r3.agN;
        r0.lv();
        r0 = "Start loading resource from disk ...";
        com.google.android.gms.tagmanager.bh.C(r0);
        r0 = com.google.android.gms.tagmanager.cd.md();
        r0 = r0.me();
        r1 = com.google.android.gms.tagmanager.cd.a.CONTAINER;
        if (r0 == r1) goto L_0x002e;
    L_0x0022:
        r0 = com.google.android.gms.tagmanager.cd.md();
        r0 = r0.me();
        r1 = com.google.android.gms.tagmanager.cd.a.CONTAINER_DEBUG;
        if (r0 != r1) goto L_0x0046;
    L_0x002e:
        r0 = r3.aet;
        r1 = com.google.android.gms.tagmanager.cd.md();
        r1 = r1.getContainerId();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0046;
    L_0x003e:
        r0 = r3.agN;
        r1 = com.google.android.gms.tagmanager.bg.a.NOT_AVAILABLE;
        r0.a(r1);
    L_0x0045:
        return;
    L_0x0046:
        r1 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x006d }
        r0 = r3.mm();	 Catch:{ FileNotFoundException -> 0x006d }
        r1.<init>(r0);	 Catch:{ FileNotFoundException -> 0x006d }
        r0 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x0082 }
        r0.<init>();	 Catch:{ IOException -> 0x0082 }
        com.google.android.gms.tagmanager.cq.b(r1, r0);	 Catch:{ IOException -> 0x0082 }
        r2 = r3.agN;	 Catch:{ IOException -> 0x0082 }
        r0 = r0.toByteArray();	 Catch:{ IOException -> 0x0082 }
        r0 = com.google.android.gms.internal.le.a.l(r0);	 Catch:{ IOException -> 0x0082 }
        r2.i(r0);	 Catch:{ IOException -> 0x0082 }
        r1.close();	 Catch:{ IOException -> 0x007b }
    L_0x0067:
        r0 = "Load resource from disk finished.";
        com.google.android.gms.tagmanager.bh.C(r0);
        goto L_0x0045;
    L_0x006d:
        r0 = move-exception;
        r0 = "resource not on disk";
        com.google.android.gms.tagmanager.bh.z(r0);
        r0 = r3.agN;
        r1 = com.google.android.gms.tagmanager.bg.a.NOT_AVAILABLE;
        r0.a(r1);
        goto L_0x0045;
    L_0x007b:
        r0 = move-exception;
        r0 = "error closing stream for reading resource from disk";
        com.google.android.gms.tagmanager.bh.D(r0);
        goto L_0x0067;
    L_0x0082:
        r0 = move-exception;
        r0 = "error reading resource from disk";
        com.google.android.gms.tagmanager.bh.D(r0);	 Catch:{ all -> 0x009a }
        r0 = r3.agN;	 Catch:{ all -> 0x009a }
        r2 = com.google.android.gms.tagmanager.bg.a.IO_ERROR;	 Catch:{ all -> 0x009a }
        r0.a(r2);	 Catch:{ all -> 0x009a }
        r1.close();	 Catch:{ IOException -> 0x0093 }
        goto L_0x0067;
    L_0x0093:
        r0 = move-exception;
        r0 = "error closing stream for reading resource from disk";
        com.google.android.gms.tagmanager.bh.D(r0);
        goto L_0x0067;
    L_0x009a:
        r0 = move-exception;
        r1.close();	 Catch:{ IOException -> 0x009f }
    L_0x009e:
        throw r0;
    L_0x009f:
        r1 = move-exception;
        r1 = "error closing stream for reading resource from disk";
        com.google.android.gms.tagmanager.bh.D(r1);
        goto L_0x009e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.cp.ml():void");
    }

    File mm() {
        return new File(this.mContext.getDir("google_tagmanager", 0), "resource_" + this.aet);
    }

    public synchronized void release() {
        this.agU.shutdown();
    }
}
