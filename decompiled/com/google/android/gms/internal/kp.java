package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.hy.a;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class kp extends hy implements SafeParcelable, Moment {
    public static final kq CREATOR = new kq();
    private static final HashMap<String, a<?, ?>> acr = new HashMap();
    private final Set<Integer> acs;
    private String adf;
    private kn adn;
    private kn ado;
    private String qX;
    private String xG;
    private final int xM;

    static {
        acr.put("id", a.j("id", 2));
        acr.put("result", a.a("result", 4, kn.class));
        acr.put("startDate", a.j("startDate", 5));
        acr.put("target", a.a("target", 6, kn.class));
        acr.put("type", a.j("type", 7));
    }

    public kp() {
        this.xM = 1;
        this.acs = new HashSet();
    }

    kp(Set<Integer> set, int i, String str, kn knVar, String str2, kn knVar2, String str3) {
        this.acs = set;
        this.xM = i;
        this.xG = str;
        this.adn = knVar;
        this.adf = str2;
        this.ado = knVar2;
        this.qX = str3;
    }

    public kp(Set<Integer> set, String str, kn knVar, String str2, kn knVar2, String str3) {
        this.acs = set;
        this.xM = 1;
        this.xG = str;
        this.adn = knVar;
        this.adf = str2;
        this.ado = knVar2;
        this.qX = str3;
    }

    protected boolean a(a aVar) {
        return this.acs.contains(Integer.valueOf(aVar.fN()));
    }

    protected Object aF(String str) {
        return null;
    }

    protected boolean aG(String str) {
        return false;
    }

    protected Object b(a aVar) {
        switch (aVar.fN()) {
            case 2:
                return this.xG;
            case 4:
                return this.adn;
            case 5:
                return this.adf;
            case 6:
                return this.ado;
            case 7:
                return this.qX;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.fN());
        }
    }

    public int describeContents() {
        kq kqVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof kp)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        kp kpVar = (kp) obj;
        for (a aVar : acr.values()) {
            if (a(aVar)) {
                if (!kpVar.a(aVar)) {
                    return false;
                }
                if (!b(aVar).equals(kpVar.b(aVar))) {
                    return false;
                }
            } else if (kpVar.a(aVar)) {
                return false;
            }
        }
        return true;
    }

    public HashMap<String, a<?, ?>> fG() {
        return acr;
    }

    public /* synthetic */ Object freeze() {
        return kD();
    }

    public String getId() {
        return this.xG;
    }

    public ItemScope getResult() {
        return this.adn;
    }

    public String getStartDate() {
        return this.adf;
    }

    public ItemScope getTarget() {
        return this.ado;
    }

    public String getType() {
        return this.qX;
    }

    int getVersionCode() {
        return this.xM;
    }

    public boolean hasId() {
        return this.acs.contains(Integer.valueOf(2));
    }

    public boolean hasResult() {
        return this.acs.contains(Integer.valueOf(4));
    }

    public boolean hasStartDate() {
        return this.acs.contains(Integer.valueOf(5));
    }

    public boolean hasTarget() {
        return this.acs.contains(Integer.valueOf(6));
    }

    public boolean hasType() {
        return this.acs.contains(Integer.valueOf(7));
    }

    public int hashCode() {
        int i = 0;
        for (a aVar : acr.values()) {
            int hashCode;
            if (a(aVar)) {
                hashCode = b(aVar).hashCode() + (i + aVar.fN());
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    public boolean isDataValid() {
        return true;
    }

    kn kB() {
        return this.adn;
    }

    kn kC() {
        return this.ado;
    }

    public kp kD() {
        return this;
    }

    Set<Integer> kk() {
        return this.acs;
    }

    public void writeToParcel(Parcel out, int flags) {
        kq kqVar = CREATOR;
        kq.a(this, out, flags);
    }
}
