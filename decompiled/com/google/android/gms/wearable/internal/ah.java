package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;
import com.google.android.gms.wearable.NodeApi.NodeListener;
import java.util.List;

public final class ah implements NodeApi {

    public static class a implements GetConnectedNodesResult {
        private final List<Node> alZ;
        private final Status yz;

        public a(Status status, List<Node> list) {
            this.yz = status;
            this.alZ = list;
        }

        public List<Node> getNodes() {
            return this.alZ;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    public static class b implements GetLocalNodeResult {
        private final Node ama;
        private final Status yz;

        public b(Status status, Node node) {
            this.yz = status;
            this.ama = node;
        }

        public Node getNode() {
            return this.ama;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    public PendingResult<Status> addListener(GoogleApiClient client, final NodeListener listener) {
        return client.a(new d<Status>(this) {
            final /* synthetic */ ah alX;

            protected void a(au auVar) throws RemoteException {
                auVar.a((d) this, listener);
            }

            public /* synthetic */ Result c(Status status) {
                return d(status);
            }

            public Status d(Status status) {
                return new Status(13);
            }
        });
    }

    public PendingResult<GetConnectedNodesResult> getConnectedNodes(GoogleApiClient client) {
        return client.a(new d<GetConnectedNodesResult>(this) {
            final /* synthetic */ ah alX;

            {
                this.alX = r1;
            }

            protected void a(au auVar) throws RemoteException {
                auVar.q(this);
            }

            protected GetConnectedNodesResult aw(Status status) {
                return new a(status, null);
            }

            protected /* synthetic */ Result c(Status status) {
                return aw(status);
            }
        });
    }

    public PendingResult<GetLocalNodeResult> getLocalNode(GoogleApiClient client) {
        return client.a(new d<GetLocalNodeResult>(this) {
            final /* synthetic */ ah alX;

            {
                this.alX = r1;
            }

            protected void a(au auVar) throws RemoteException {
                auVar.p(this);
            }

            protected GetLocalNodeResult av(Status status) {
                return new b(status, null);
            }

            protected /* synthetic */ Result c(Status status) {
                return av(status);
            }
        });
    }

    public PendingResult<Status> removeListener(GoogleApiClient client, final NodeListener listener) {
        return client.a(new d<Status>(this) {
            final /* synthetic */ ah alX;

            protected void a(au auVar) throws RemoteException {
                auVar.b((d) this, listener);
            }

            public /* synthetic */ Result c(Status status) {
                return d(status);
            }

            public Status d(Status status) {
                return new Status(13);
            }
        });
    }
}
