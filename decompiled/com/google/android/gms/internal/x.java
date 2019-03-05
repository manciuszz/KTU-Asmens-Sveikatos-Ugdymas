package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

class x implements z {
    private ex le;

    public x(ex exVar) {
        this.le = exVar;
    }

    public void a(ac acVar, boolean z) {
        Map hashMap = new HashMap();
        hashMap.put("isVisible", z ? "1" : "0");
        this.le.a("onAdVisibilityChanged", hashMap);
    }
}
