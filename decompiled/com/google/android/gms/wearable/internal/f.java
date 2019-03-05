package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import java.io.IOException;
import java.io.InputStream;

public final class f implements DataApi {

    public static class a implements DataItemResult {
        private final DataItem alH;
        private final Status yz;

        public a(Status status, DataItem dataItem) {
            this.yz = status;
            this.alH = dataItem;
        }

        public DataItem getDataItem() {
            return this.alH;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    public static class b implements DeleteDataItemsResult {
        private final int alI;
        private final Status yz;

        public b(Status status, int i) {
            this.yz = status;
            this.alI = i;
        }

        public int getNumDeleted() {
            return this.alI;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    public static class c implements GetFdForAssetResult {
        private final ParcelFileDescriptor alJ;
        private final Status yz;

        public c(Status status, ParcelFileDescriptor parcelFileDescriptor) {
            this.yz = status;
            this.alJ = parcelFileDescriptor;
        }

        public ParcelFileDescriptor getFd() {
            return this.alJ;
        }

        public InputStream getInputStream() {
            return new AutoCloseInputStream(this.alJ);
        }

        public Status getStatus() {
            return this.yz;
        }

        public void release() {
            try {
                this.alJ.close();
            } catch (IOException e) {
            }
        }
    }

    private PendingResult<Status> a(GoogleApiClient googleApiClient, final DataListener dataListener, final IntentFilter[] intentFilterArr) {
        return googleApiClient.a(new d<Status>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.a((d) this, dataListener, intentFilterArr);
            }

            public /* synthetic */ Result c(Status status) {
                return d(status);
            }

            public Status d(Status status) {
                return new Status(13);
            }
        });
    }

    private void a(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("asset is null");
        } else if (asset.getDigest() == null) {
            throw new IllegalArgumentException("invalid asset");
        } else if (asset.getData() != null) {
            throw new IllegalArgumentException("invalid asset");
        }
    }

    public PendingResult<Status> addListener(GoogleApiClient client, DataListener listener) {
        return a(client, listener, null);
    }

    public PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient client, final Uri uri) {
        return client.a(new d<DeleteDataItemsResult>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.c(this, uri);
            }

            protected DeleteDataItemsResult as(Status status) {
                return new b(status, 0);
            }

            protected /* synthetic */ Result c(Status status) {
                return as(status);
            }
        });
    }

    public PendingResult<DataItemResult> getDataItem(GoogleApiClient client, final Uri uri) {
        return client.a(new d<DataItemResult>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.a((d) this, uri);
            }

            protected DataItemResult aq(Status status) {
                return new a(status, null);
            }

            protected /* synthetic */ Result c(Status status) {
                return aq(status);
            }
        });
    }

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient client) {
        return client.a(new d<DataItemBuffer>(this) {
            final /* synthetic */ f alC;

            {
                this.alC = r1;
            }

            protected void a(au auVar) throws RemoteException {
                auVar.o(this);
            }

            protected DataItemBuffer ar(Status status) {
                return new DataItemBuffer(DataHolder.af(status.getStatusCode()));
            }

            protected /* synthetic */ Result c(Status status) {
                return ar(status);
            }
        });
    }

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient client, final Uri uri) {
        return client.a(new d<DataItemBuffer>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.b((d) this, uri);
            }

            protected DataItemBuffer ar(Status status) {
                return new DataItemBuffer(DataHolder.af(status.getStatusCode()));
            }

            protected /* synthetic */ Result c(Status status) {
                return ar(status);
            }
        });
    }

    public PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient client, final Asset asset) {
        a(asset);
        return client.a(new d<GetFdForAssetResult>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.a((d) this, asset);
            }

            protected GetFdForAssetResult at(Status status) {
                return new c(status, null);
            }

            protected /* synthetic */ Result c(Status status) {
                return at(status);
            }
        });
    }

    public PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient client, final DataItemAsset asset) {
        return client.a(new d<GetFdForAssetResult>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.a((d) this, asset);
            }

            protected GetFdForAssetResult at(Status status) {
                return new c(status, null);
            }

            protected /* synthetic */ Result c(Status status) {
                return at(status);
            }
        });
    }

    public PendingResult<DataItemResult> putDataItem(GoogleApiClient client, final PutDataRequest request) {
        return client.a(new d<DataItemResult>(this) {
            final /* synthetic */ f alC;

            protected void a(au auVar) throws RemoteException {
                auVar.a((d) this, request);
            }

            public DataItemResult aq(Status status) {
                return new a(status, null);
            }

            public /* synthetic */ Result c(Status status) {
                return aq(status);
            }
        });
    }

    public PendingResult<Status> removeListener(GoogleApiClient client, final DataListener listener) {
        return client.a(new d<Status>(this) {
            final /* synthetic */ f alC;

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
}
