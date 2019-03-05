package com.google.android.gms.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.purchase.InAppPurchaseActivity;
import com.google.android.gms.internal.dd.a;

public class cu extends a implements ServiceConnection {
    private dg oX;
    private cr oY;
    private final cx oZ;
    private final Activity og;
    private cz pb;
    private Context ph;
    private db pi;
    private cv pj;
    private String pk = null;

    public cu(Activity activity) {
        this.og = activity;
        this.oZ = cx.k(this.og.getApplicationContext());
    }

    public static void a(Context context, boolean z, cq cqVar) {
        Intent intent = new Intent();
        intent.setClassName(context, InAppPurchaseActivity.CLASS_NAME);
        intent.putExtra("com.google.android.gms.ads.internal.purchase.useClientJar", z);
        cq.a(intent, cqVar);
        context.startActivity(intent);
    }

    private void a(String str, boolean z, int i, Intent intent) {
        try {
            this.oX.a(new cw(this.ph, str, z, i, intent, this.pj));
        } catch (RemoteException e) {
            eu.D("Fail to invoke PlayStorePurchaseListener.");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            try {
                int c = cy.c(data);
                if (resultCode != -1 || c != 0) {
                    this.oZ.a(this.pj);
                    a(this.pi.getProductId(), false, resultCode, data);
                } else if (this.pb.a(this.pk, resultCode, data)) {
                    a(this.pi.getProductId(), true, resultCode, data);
                } else {
                    a(this.pi.getProductId(), false, resultCode, data);
                }
                this.pi.recordPlayBillingResolution(c);
            } catch (RemoteException e) {
                eu.D("Fail to process purchase result.");
            } finally {
                this.pk = null;
                this.og.finish();
            }
        }
    }

    public void onCreate() {
        cq b = cq.b(this.og.getIntent());
        this.oX = b.kX;
        this.pb = b.kZ;
        this.pi = b.oT;
        this.oY = new cr(this.og.getApplicationContext());
        this.ph = b.oU;
        Activity activity = this.og;
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        this.og.getApplicationContext();
        activity.bindService(intent, this, 1);
    }

    public void onDestroy() {
        this.og.unbindService(this);
        this.oY.destroy();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        Throwable e;
        this.oY.o(service);
        try {
            this.pk = this.pb.bm();
            Bundle a = this.oY.a(this.og.getPackageName(), this.pi.getProductId(), this.pk);
            PendingIntent pendingIntent = (PendingIntent) a.getParcelable("BUY_INTENT");
            if (pendingIntent == null) {
                int a2 = cy.a(a);
                this.pi.recordPlayBillingResolution(a2);
                a(this.pi.getProductId(), false, a2, null);
                this.og.finish();
                return;
            }
            this.pj = new cv(this.pi.getProductId(), this.pk);
            this.oZ.b(this.pj);
            this.og.startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue());
        } catch (RemoteException e2) {
            e = e2;
            eu.c("Error when connecting in-app billing service", e);
            this.og.finish();
        } catch (SendIntentException e3) {
            e = e3;
            eu.c("Error when connecting in-app billing service", e);
            this.og.finish();
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        eu.B("In-app billing service disconnected.");
        this.oY.destroy();
    }
}
