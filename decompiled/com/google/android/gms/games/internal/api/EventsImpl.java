package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.event.Events.LoadEventsResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class EventsImpl implements Events {

    private static abstract class LoadImpl extends BaseGamesApiMethodImpl<LoadEventsResult> {
        private LoadImpl() {
        }

        public LoadEventsResult A(final Status status) {
            return new LoadEventsResult(this) {
                final /* synthetic */ LoadImpl Pr;

                public EventBuffer getEvents() {
                    return new EventBuffer(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return A(status);
        }
    }

    private static abstract class UpdateImpl extends BaseGamesApiMethodImpl<Result> {
        private UpdateImpl() {
        }

        public Result c(final Status status) {
            return new Result(this) {
                final /* synthetic */ UpdateImpl Ps;

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    public void increment(GoogleApiClient apiClient, final String eventId, final int incrementAmount) {
        GamesClientImpl d = Games.d(apiClient);
        if (d.isConnected()) {
            d.l(eventId, incrementAmount);
        } else {
            apiClient.b(new UpdateImpl(this) {
                final /* synthetic */ EventsImpl Po;

                public void a(GamesClientImpl gamesClientImpl) {
                    gamesClientImpl.l(eventId, incrementAmount);
                }
            });
        }
    }

    public PendingResult<LoadEventsResult> load(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadImpl(this) {
            final /* synthetic */ EventsImpl Po;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.d((d) this, forceReload);
            }
        });
    }

    public PendingResult<LoadEventsResult> loadByIds(GoogleApiClient apiClient, final boolean forceReload, final String... eventIds) {
        return apiClient.a(new LoadImpl(this) {
            final /* synthetic */ EventsImpl Po;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, forceReload, eventIds);
            }
        });
    }
}
