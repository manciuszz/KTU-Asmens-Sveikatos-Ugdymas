package com.google.android.gms.internal;

import java.util.Map;

public final class dz {
    private ex lN;
    private final Object ls = new Object();
    private int pJ = -2;
    private String qB;
    private eb qC;
    public final bc qD = new bc(this) {
        final /* synthetic */ dz qF;

        {
            this.qF = r1;
        }

        public void b(ex exVar, Map<String, String> map) {
            synchronized (this.qF.ls) {
                eb ebVar = new eb(map);
                eu.D("Invalid " + ebVar.getType() + " request error: " + ebVar.by());
                this.qF.pJ = 1;
                this.qF.ls.notify();
            }
        }
    };
    public final bc qE = new bc(this) {
        final /* synthetic */ dz qF;

        {
            this.qF = r1;
        }

        public void b(ex exVar, Map<String, String> map) {
            synchronized (this.qF.ls) {
                eb ebVar = new eb(map);
                String url = ebVar.getUrl();
                if (url == null) {
                    eu.D("URL missing in loadAdUrl GMSG.");
                    return;
                }
                if (url.contains("%40mediation_adapters%40")) {
                    eu.C("Ad request URL modified to " + url.replaceAll("%40mediation_adapters%40", el.a(exVar.getContext(), (String) map.get("check_adapters"), this.qF.qB)));
                }
                this.qF.qC = ebVar;
                this.qF.ls.notify();
            }
        }
    };

    public dz(String str) {
        this.qB = str;
    }

    public void b(ex exVar) {
        synchronized (this.ls) {
            this.lN = exVar;
        }
    }

    public eb bx() {
        eb ebVar;
        synchronized (this.ls) {
            while (this.qC == null && this.pJ == -2) {
                try {
                    this.ls.wait();
                } catch (InterruptedException e) {
                    eu.D("Ad request service was interrupted.");
                    ebVar = null;
                }
            }
            ebVar = this.qC;
        }
        return ebVar;
    }

    public int getErrorCode() {
        int i;
        synchronized (this.ls) {
            i = this.pJ;
        }
        return i;
    }
}
