package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

public final class km implements People {

    private static abstract class a extends com.google.android.gms.plus.Plus.a<LoadPeopleResult> {
        private a() {
        }

        public LoadPeopleResult ao(final Status status) {
            return new LoadPeopleResult(this) {
                final /* synthetic */ a acq;

                public String getNextPageToken() {
                    return null;
                }

                public PersonBuffer getPersonBuffer() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return ao(status);
        }
    }

    public Person getCurrentPerson(GoogleApiClient googleApiClient) {
        return Plus.a(googleApiClient, Plus.yH).getCurrentPerson();
    }

    public PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, final Collection<String> personIds) {
        return googleApiClient.a(new a(this) {
            final /* synthetic */ km acn;

            protected void a(e eVar) {
                eVar.a((d) this, personIds);
            }
        });
    }

    public PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, final String... personIds) {
        return googleApiClient.a(new a(this) {
            final /* synthetic */ km acn;

            protected void a(e eVar) {
                eVar.d(this, personIds);
            }
        });
    }

    public PendingResult<LoadPeopleResult> loadConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a(this) {
            final /* synthetic */ km acn;

            {
                this.acn = r2;
            }

            protected void a(e eVar) {
                eVar.l(this);
            }
        });
    }

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, final int orderBy, final String pageToken) {
        return googleApiClient.a(new a(this) {
            final /* synthetic */ km acn;

            protected void a(e eVar) {
                a(eVar.a((d) this, orderBy, pageToken));
            }
        });
    }

    public PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, final String pageToken) {
        return googleApiClient.a(new a(this) {
            final /* synthetic */ km acn;

            protected void a(e eVar) {
                a(eVar.r(this, pageToken));
            }
        });
    }
}
