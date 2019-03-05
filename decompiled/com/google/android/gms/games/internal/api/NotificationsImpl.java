package com.google.android.gms.games.internal.api;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.Notifications.ContactSettingLoadResult;
import com.google.android.gms.games.Notifications.GameMuteStatusChangeResult;
import com.google.android.gms.games.Notifications.GameMuteStatusLoadResult;
import com.google.android.gms.games.Notifications.InboxCountResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class NotificationsImpl implements Notifications {

    class AnonymousClass1 extends BaseGamesApiMethodImpl<GameMuteStatusChangeResult> {
        final /* synthetic */ String PV;

        public GameMuteStatusChangeResult K(final Status status) {
            return new GameMuteStatusChangeResult(this) {
                final /* synthetic */ AnonymousClass1 PW;

                public Status getStatus() {
                    return status;
                }
            };
        }

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d((d) this, this.PV, true);
        }

        public /* synthetic */ Result c(Status status) {
            return K(status);
        }
    }

    class AnonymousClass2 extends BaseGamesApiMethodImpl<GameMuteStatusChangeResult> {
        final /* synthetic */ String PV;

        public GameMuteStatusChangeResult K(final Status status) {
            return new GameMuteStatusChangeResult(this) {
                final /* synthetic */ AnonymousClass2 PX;

                public Status getStatus() {
                    return status;
                }
            };
        }

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d((d) this, this.PV, false);
        }

        public /* synthetic */ Result c(Status status) {
            return K(status);
        }
    }

    class AnonymousClass3 extends BaseGamesApiMethodImpl<GameMuteStatusLoadResult> {
        final /* synthetic */ String PV;

        public GameMuteStatusLoadResult L(final Status status) {
            return new GameMuteStatusLoadResult(this) {
                final /* synthetic */ AnonymousClass3 PY;

                public Status getStatus() {
                    return status;
                }
            };
        }

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.q(this, this.PV);
        }

        public /* synthetic */ Result c(Status status) {
            return L(status);
        }
    }

    private static abstract class ContactSettingLoadImpl extends BaseGamesApiMethodImpl<ContactSettingLoadResult> {
        private ContactSettingLoadImpl() {
        }

        public ContactSettingLoadResult M(final Status status) {
            return new ContactSettingLoadResult(this) {
                final /* synthetic */ ContactSettingLoadImpl Qb;

                public Status getStatus() {
                    return status;
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return M(status);
        }
    }

    private static abstract class ContactSettingUpdateImpl extends BaseGamesApiMethodImpl<Status> {
        private ContactSettingUpdateImpl() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    private static abstract class InboxCountImpl extends BaseGamesApiMethodImpl<InboxCountResult> {
        private InboxCountImpl() {
        }

        public InboxCountResult N(final Status status) {
            return new InboxCountResult(this) {
                final /* synthetic */ InboxCountImpl Qc;

                public Status getStatus() {
                    return status;
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return N(status);
        }
    }

    class AnonymousClass4 extends ContactSettingLoadImpl {
        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.i(this);
        }
    }

    class AnonymousClass5 extends ContactSettingLoadImpl {
        final /* synthetic */ boolean Pe;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.f((d) this, this.Pe);
        }
    }

    class AnonymousClass6 extends ContactSettingUpdateImpl {
        final /* synthetic */ boolean PZ;
        final /* synthetic */ Bundle Qa;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.PZ, this.Qa);
        }
    }

    class AnonymousClass7 extends InboxCountImpl {
        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.j(this);
        }
    }

    public void clear(GoogleApiClient apiClient, int notificationTypes) {
        Games.c(apiClient).ch(notificationTypes);
    }

    public void clearAll(GoogleApiClient apiClient) {
        clear(apiClient, 31);
    }
}
