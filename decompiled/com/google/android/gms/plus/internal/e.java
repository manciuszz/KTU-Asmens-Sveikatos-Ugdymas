package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.hb;
import com.google.android.gms.internal.hg;
import com.google.android.gms.internal.hi;
import com.google.android.gms.internal.ie;
import com.google.android.gms.internal.kp;
import com.google.android.gms.internal.ks;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class e extends hb<d> {
    private Person abJ;
    private final h abK;

    final class d extends b<com.google.android.gms.common.api.a.d<Status>> {
        final /* synthetic */ e abM;
        private final Status yz;

        public d(e eVar, com.google.android.gms.common.api.a.d<Status> dVar, Status status) {
            this.abM = eVar;
            super(eVar, dVar);
            this.yz = status;
        }

        protected /* synthetic */ void d(Object obj) {
            n((com.google.android.gms.common.api.a.d) obj);
        }

        protected void fu() {
        }

        protected void n(com.google.android.gms.common.api.a.d<Status> dVar) {
            if (dVar != null) {
                dVar.a(this.yz);
            }
        }
    }

    final class h extends b<com.google.android.gms.common.api.a.d<Status>> {
        final /* synthetic */ e abM;
        private final Status yz;

        public h(e eVar, com.google.android.gms.common.api.a.d<Status> dVar, Status status) {
            this.abM = eVar;
            super(eVar, dVar);
            this.yz = status;
        }

        protected /* synthetic */ void d(Object obj) {
            n((com.google.android.gms.common.api.a.d) obj);
        }

        protected void fu() {
        }

        protected void n(com.google.android.gms.common.api.a.d<Status> dVar) {
            this.abM.disconnect();
            if (dVar != null) {
                dVar.a(this.yz);
            }
        }
    }

    final class c extends com.google.android.gms.internal.hb.d<com.google.android.gms.common.api.a.d<LoadMomentsResult>> implements LoadMomentsResult {
        private final String HS;
        final /* synthetic */ e abM;
        private final String abN;
        private MomentBuffer abO;
        private final Status yz;

        public c(e eVar, com.google.android.gms.common.api.a.d<LoadMomentsResult> dVar, Status status, DataHolder dataHolder, String str, String str2) {
            this.abM = eVar;
            super(eVar, dVar, dataHolder);
            this.yz = status;
            this.HS = str;
            this.abN = str2;
        }

        protected void a(com.google.android.gms.common.api.a.d<LoadMomentsResult> dVar, DataHolder dataHolder) {
            this.abO = dataHolder != null ? new MomentBuffer(dataHolder) : null;
            dVar.a(this);
        }

        public MomentBuffer getMomentBuffer() {
            return this.abO;
        }

        public String getNextPageToken() {
            return this.HS;
        }

        public Status getStatus() {
            return this.yz;
        }

        public String getUpdated() {
            return this.abN;
        }

        public void release() {
            if (this.abO != null) {
                this.abO.close();
            }
        }
    }

    final class f extends com.google.android.gms.internal.hb.d<com.google.android.gms.common.api.a.d<LoadPeopleResult>> implements LoadPeopleResult {
        private final String HS;
        final /* synthetic */ e abM;
        private PersonBuffer abP;
        private final Status yz;

        public f(e eVar, com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar, Status status, DataHolder dataHolder, String str) {
            this.abM = eVar;
            super(eVar, dVar, dataHolder);
            this.yz = status;
            this.HS = str;
        }

        protected void a(com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar, DataHolder dataHolder) {
            this.abP = dataHolder != null ? new PersonBuffer(dataHolder) : null;
            dVar.a(this);
        }

        public String getNextPageToken() {
            return this.HS;
        }

        public PersonBuffer getPersonBuffer() {
            return this.abP;
        }

        public Status getStatus() {
            return this.yz;
        }

        public void release() {
            if (this.abP != null) {
                this.abP.close();
            }
        }
    }

    final class a extends a {
        private final com.google.android.gms.common.api.a.d<Status> abL;
        final /* synthetic */ e abM;

        public a(e eVar, com.google.android.gms.common.api.a.d<Status> dVar) {
            this.abM = eVar;
            this.abL = dVar;
        }

        public void am(Status status) {
            this.abM.a(new d(this.abM, this.abL, status));
        }
    }

    final class b extends a {
        private final com.google.android.gms.common.api.a.d<LoadMomentsResult> abL;
        final /* synthetic */ e abM;

        public b(e eVar, com.google.android.gms.common.api.a.d<LoadMomentsResult> dVar) {
            this.abM = eVar;
            this.abL = dVar;
        }

        public void a(DataHolder dataHolder, String str, String str2) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.eU() != null ? (PendingIntent) dataHolder.eU().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            this.abM.a(new c(this.abM, this.abL, status, dataHolder2, str, str2));
        }
    }

    final class e extends a {
        private final com.google.android.gms.common.api.a.d<LoadPeopleResult> abL;
        final /* synthetic */ e abM;

        public e(e eVar, com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar) {
            this.abM = eVar;
            this.abL = dVar;
        }

        public void a(DataHolder dataHolder, String str) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.eU() != null ? (PendingIntent) dataHolder.eU().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            this.abM.a(new f(this.abM, this.abL, status, dataHolder2, str));
        }
    }

    final class g extends a {
        private final com.google.android.gms.common.api.a.d<Status> abL;
        final /* synthetic */ e abM;

        public g(e eVar, com.google.android.gms.common.api.a.d<Status> dVar) {
            this.abM = eVar;
            this.abL = dVar;
        }

        public void h(int i, Bundle bundle) {
            this.abM.a(new h(this.abM, this.abL, new Status(i, null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null)));
        }
    }

    public e(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, h hVar) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, hVar.jZ());
        this.abK = hVar;
    }

    @Deprecated
    public e(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, h hVar) {
        this(context, context.getMainLooper(), new com.google.android.gms.internal.hb.c(connectionCallbacks), new com.google.android.gms.internal.hb.g(onConnectionFailedListener), hVar);
    }

    public hg a(com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar, int i, String str) {
        cn();
        Object eVar = new e(this, dVar);
        try {
            return ((d) ft()).a(eVar, 1, i, -1, str);
        } catch (RemoteException e) {
            eVar.a(DataHolder.af(8), null);
            return null;
        }
    }

    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.abJ = ks.i(bundle.getByteArray("loaded_person"));
        }
        super.a(i, iBinder, bundle);
    }

    public void a(com.google.android.gms.common.api.a.d<LoadMomentsResult> dVar, int i, String str, Uri uri, String str2, String str3) {
        cn();
        Object bVar = dVar != null ? new b(this, dVar) : null;
        try {
            ((d) ft()).a(bVar, i, str, uri, str2, str3);
        } catch (RemoteException e) {
            bVar.a(DataHolder.af(8), null, null);
        }
    }

    public void a(com.google.android.gms.common.api.a.d<Status> dVar, Moment moment) {
        cn();
        b aVar = dVar != null ? new a(this, dVar) : null;
        try {
            ((d) ft()).a(aVar, ie.a((kp) moment));
        } catch (Throwable e) {
            if (aVar == null) {
                throw new IllegalStateException(e);
            }
            aVar.am(new Status(8, null, null));
        }
    }

    public void a(com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar, Collection<String> collection) {
        cn();
        b eVar = new e(this, dVar);
        try {
            ((d) ft()).a(eVar, new ArrayList(collection));
        } catch (RemoteException e) {
            eVar.a(DataHolder.af(8), null);
        }
    }

    protected void a(hi hiVar, com.google.android.gms.internal.hb.e eVar) throws RemoteException {
        Bundle kh = this.abK.kh();
        kh.putStringArray("request_visible_actions", this.abK.ka());
        hiVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, this.abK.kd(), this.abK.kc(), fs(), this.abK.getAccountName(), kh);
    }

    protected d bn(IBinder iBinder) {
        return com.google.android.gms.plus.internal.d.a.bm(iBinder);
    }

    protected String bu() {
        return "com.google.android.gms.plus.service.START";
    }

    protected String bv() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    public boolean by(String str) {
        return Arrays.asList(fs()).contains(str);
    }

    public void clearDefaultAccount() {
        cn();
        try {
            this.abJ = null;
            ((d) ft()).clearDefaultAccount();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void d(com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar, String[] strArr) {
        a((com.google.android.gms.common.api.a.d) dVar, Arrays.asList(strArr));
    }

    public String getAccountName() {
        cn();
        try {
            return ((d) ft()).getAccountName();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public Person getCurrentPerson() {
        cn();
        return this.abJ;
    }

    public void k(com.google.android.gms.common.api.a.d<LoadMomentsResult> dVar) {
        a(dVar, 20, null, null, null, "me");
    }

    public void l(com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar) {
        cn();
        Object eVar = new e(this, dVar);
        try {
            ((d) ft()).a(eVar, 2, 1, -1, null);
        } catch (RemoteException e) {
            eVar.a(DataHolder.af(8), null);
        }
    }

    public void m(com.google.android.gms.common.api.a.d<Status> dVar) {
        cn();
        clearDefaultAccount();
        Object gVar = new g(this, dVar);
        try {
            ((d) ft()).b(gVar);
        } catch (RemoteException e) {
            gVar.h(8, null);
        }
    }

    public hg r(com.google.android.gms.common.api.a.d<LoadPeopleResult> dVar, String str) {
        return a((com.google.android.gms.common.api.a.d) dVar, 0, str);
    }

    public void removeMoment(String momentId) {
        cn();
        try {
            ((d) ft()).removeMoment(momentId);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    protected /* synthetic */ IInterface x(IBinder iBinder) {
        return bn(iBinder);
    }
}
