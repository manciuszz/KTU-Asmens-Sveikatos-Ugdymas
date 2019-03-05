package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;

public final class kl implements Moments {

    private static abstract class a extends com.google.android.gms.plus.Plus.a<LoadMomentsResult> {
        private a() {
        }

        public LoadMomentsResult an(final Status status) {
            return new LoadMomentsResult(this) {
                final /* synthetic */ a acl;

                public MomentBuffer getMomentBuffer() {
                    return null;
                }

                public String getNextPageToken() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }

                public String getUpdated() {
                    return null;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return an(status);
        }
    }

    private static abstract class b extends com.google.android.gms.plus.Plus.a<Status> {
        private b() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    private static abstract class c extends com.google.android.gms.plus.Plus.a<Status> {
        private c() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    public PendingResult<LoadMomentsResult> load(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a(this) {
            final /* synthetic */ kl ace;

            {
                this.ace = r2;
            }

            protected void a(e eVar) {
                eVar.k(this);
            }
        });
    }

    public PendingResult<LoadMomentsResult> load(GoogleApiClient googleApiClient, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
        final int i = maxResults;
        final String str = pageToken;
        final Uri uri = targetUrl;
        final String str2 = type;
        final String str3 = userId;
        return googleApiClient.a(new a(this) {
            final /* synthetic */ kl ace;

            protected void a(e eVar) {
                eVar.a(this, i, str, uri, str2, str3);
            }
        });
    }

    public PendingResult<Status> remove(GoogleApiClient googleApiClient, final String momentId) {
        return googleApiClient.b(new b(this) {
            final /* synthetic */ kl ace;

            protected void a(e eVar) {
                eVar.removeMoment(momentId);
                b(Status.En);
            }
        });
    }

    public PendingResult<Status> write(GoogleApiClient googleApiClient, final Moment moment) {
        return googleApiClient.b(new c(this) {
            final /* synthetic */ kl ace;

            protected void a(e eVar) {
                eVar.a((d) this, moment);
            }
        });
    }
}
