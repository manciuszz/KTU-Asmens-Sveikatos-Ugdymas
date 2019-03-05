package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

public class p implements DriveApi {

    public static class a implements ContentsResult {
        private final Contents HG;
        private final Status yz;

        public a(Status status, Contents contents) {
            this.yz = status;
            this.HG = contents;
        }

        public Contents getContents() {
            return this.HG;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    static class c implements DriveIdResult {
        private final DriveId Hz;
        private final Status yz;

        public c(Status status, DriveId driveId) {
            this.yz = status;
            this.Hz = driveId;
        }

        public DriveId getDriveId() {
            return this.Hz;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    static class e implements MetadataBufferResult {
        private final MetadataBuffer IO;
        private final boolean IP;
        private final Status yz;

        public e(Status status, MetadataBuffer metadataBuffer, boolean z) {
            this.yz = status;
            this.IO = metadataBuffer;
            this.IP = z;
        }

        public MetadataBuffer getMetadataBuffer() {
            return this.IO;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static class b extends c {
        private final com.google.android.gms.common.api.a.d<DriveIdResult> yR;

        public b(com.google.android.gms.common.api.a.d<DriveIdResult> dVar) {
            this.yR = dVar;
        }

        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.yR.a(new c(Status.En, onDriveIdResponse.getDriveId()));
        }

        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.yR.a(new c(Status.En, new l(onMetadataResponse.gB()).getDriveId()));
        }

        public void o(Status status) throws RemoteException {
            this.yR.a(new c(status, null));
        }
    }

    private static class f extends c {
        private final com.google.android.gms.common.api.a.d<ContentsResult> yR;

        public f(com.google.android.gms.common.api.a.d<ContentsResult> dVar) {
            this.yR = dVar;
        }

        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.yR.a(new a(Status.En, onContentsResponse.gt()));
        }

        public void o(Status status) throws RemoteException {
            this.yR.a(new a(status, null));
        }
    }

    static class h extends c {
        private final com.google.android.gms.common.api.a.d<MetadataBufferResult> yR;

        public h(com.google.android.gms.common.api.a.d<MetadataBufferResult> dVar) {
            this.yR = dVar;
        }

        public void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
            this.yR.a(new e(Status.En, new MetadataBuffer(onListEntriesResponse.gy(), null), onListEntriesResponse.gz()));
        }

        public void o(Status status) throws RemoteException {
            this.yR.a(new e(status, null, false));
        }
    }

    abstract class d extends q<DriveIdResult> {
        final /* synthetic */ p IL;

        d(p pVar) {
            this.IL = pVar;
        }

        public /* synthetic */ Result c(Status status) {
            return p(status);
        }

        public DriveIdResult p(Status status) {
            return new c(status, null);
        }
    }

    abstract class g extends q<ContentsResult> {
        final /* synthetic */ p IL;

        g(p pVar) {
            this.IL = pVar;
        }

        public /* synthetic */ Result c(Status status) {
            return q(status);
        }

        public ContentsResult q(Status status) {
            return new a(status, null);
        }
    }

    abstract class i extends q<MetadataBufferResult> {
        final /* synthetic */ p IL;

        i(p pVar) {
            this.IL = pVar;
        }

        public /* synthetic */ Result c(Status status) {
            return r(status);
        }

        public MetadataBufferResult r(Status status) {
            return new e(status, null, false);
        }
    }

    static abstract class j extends q<Status> {
        j() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    abstract class l extends q<Status> {
        final /* synthetic */ p IL;

        l(p pVar) {
            this.IL = pVar;
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    static class k extends j {
        k(GoogleApiClient googleApiClient, Status status) {
            a(new com.google.android.gms.common.api.a.c(((r) googleApiClient.a(Drive.yH)).getLooper()));
            b(status);
        }

        protected void a(r rVar) {
        }
    }

    public PendingResult<Status> discardContents(GoogleApiClient apiClient, final Contents contents) {
        return apiClient.b(new j(this) {
            final /* synthetic */ p IL;

            protected void a(r rVar) throws RemoteException {
                rVar.gp().a(new CloseContentsRequest(contents, false), new aw(this));
            }
        });
    }

    public PendingResult<DriveIdResult> fetchDriveId(GoogleApiClient apiClient, final String resourceId) {
        return apiClient.a(new d(this) {
            final /* synthetic */ p IL;

            protected void a(r rVar) throws RemoteException {
                rVar.gp().a(new GetMetadataRequest(DriveId.aL(resourceId)), new b(this));
            }
        });
    }

    public DriveFolder getAppFolder(GoogleApiClient apiClient) {
        if (apiClient.isConnected()) {
            DriveId gr = ((r) apiClient.a(Drive.yH)).gr();
            return gr != null ? new u(gr) : null;
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFile getFile(GoogleApiClient apiClient, DriveId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (apiClient.isConnected()) {
            return new s(id);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFolder getFolder(GoogleApiClient apiClient, DriveId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (apiClient.isConnected()) {
            return new u(id);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public DriveFolder getRootFolder(GoogleApiClient apiClient) {
        if (apiClient.isConnected()) {
            return new u(((r) apiClient.a(Drive.yH)).gq());
        }
        throw new IllegalStateException("Client must be connected");
    }

    public PendingResult<ContentsResult> newContents(GoogleApiClient apiClient) {
        return apiClient.a(new g(this) {
            final /* synthetic */ p IL;

            {
                this.IL = r1;
            }

            protected void a(r rVar) throws RemoteException {
                rVar.gp().a(new CreateContentsRequest(), new f(this));
            }
        });
    }

    public CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    public OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    public PendingResult<MetadataBufferResult> query(GoogleApiClient apiClient, final Query query) {
        if (query != null) {
            return apiClient.a(new i(this) {
                final /* synthetic */ p IL;

                protected void a(r rVar) throws RemoteException {
                    rVar.gp().a(new QueryRequest(query), new h(this));
                }
            });
        }
        throw new IllegalArgumentException("Query must be provided.");
    }

    public PendingResult<Status> requestSync(GoogleApiClient client) {
        return client.b(new l(this) {
            final /* synthetic */ p IL;

            {
                this.IL = r1;
            }

            protected void a(r rVar) throws RemoteException {
                rVar.gp().a(new aw(this));
            }
        });
    }
}
