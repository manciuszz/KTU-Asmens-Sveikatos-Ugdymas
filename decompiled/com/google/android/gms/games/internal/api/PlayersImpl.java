package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.Players.LoadOwnerCoverPhotoUrisResult;
import com.google.android.gms.games.Players.LoadPlayersResult;
import com.google.android.gms.games.Players.LoadXpForGameCategoriesResult;
import com.google.android.gms.games.Players.LoadXpForGamesResult;
import com.google.android.gms.games.Players.LoadXpStreamResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class PlayersImpl implements Players {

    private static abstract class LoadOwnerCoverPhotoUrisImpl extends BaseGamesApiMethodImpl<LoadOwnerCoverPhotoUrisResult> {
        private LoadOwnerCoverPhotoUrisImpl() {
        }

        public LoadOwnerCoverPhotoUrisResult O(final Status status) {
            return new LoadOwnerCoverPhotoUrisResult(this) {
                final /* synthetic */ LoadOwnerCoverPhotoUrisImpl Qj;

                public Status getStatus() {
                    return status;
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return O(status);
        }
    }

    private static abstract class LoadPlayersImpl extends BaseGamesApiMethodImpl<LoadPlayersResult> {
        private LoadPlayersImpl() {
        }

        public LoadPlayersResult P(final Status status) {
            return new LoadPlayersResult(this) {
                final /* synthetic */ LoadPlayersImpl Qk;

                public PlayerBuffer getPlayers() {
                    return new PlayerBuffer(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return P(status);
        }
    }

    private static abstract class LoadXpForGameCategoriesResultImpl extends BaseGamesApiMethodImpl<LoadXpForGameCategoriesResult> {
        private LoadXpForGameCategoriesResultImpl() {
        }

        public LoadXpForGameCategoriesResult Q(final Status status) {
            return new LoadXpForGameCategoriesResult(this) {
                final /* synthetic */ LoadXpForGameCategoriesResultImpl Ql;

                public Status getStatus() {
                    return status;
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return Q(status);
        }
    }

    private static abstract class LoadXpForGameResultImpl extends BaseGamesApiMethodImpl<LoadXpForGamesResult> {

        class AnonymousClass1 implements LoadXpForGamesResult {
            final /* synthetic */ Status yJ;

            public Status getStatus() {
                return this.yJ;
            }
        }

        private LoadXpForGameResultImpl() {
        }
    }

    private static abstract class LoadXpStreamResultImpl extends BaseGamesApiMethodImpl<LoadXpStreamResult> {
        private LoadXpStreamResultImpl() {
        }

        public LoadXpStreamResult R(final Status status) {
            return new LoadXpStreamResult(this) {
                final /* synthetic */ LoadXpStreamResultImpl Qm;

                public Status getStatus() {
                    return status;
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return R(status);
        }
    }

    class AnonymousClass10 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b((d) this, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass11 extends LoadPlayersImpl {
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b((d) this, this.Pv, true, false);
        }
    }

    class AnonymousClass12 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c((d) this, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass13 extends LoadPlayersImpl {
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c((d) this, this.Pv, true, false);
        }
    }

    class AnonymousClass14 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass15 extends LoadPlayersImpl {
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Pv, true, false);
        }
    }

    class AnonymousClass16 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;
        final /* synthetic */ String Px;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.f(this, this.Px, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass17 extends LoadPlayersImpl {
        final /* synthetic */ int Pv;
        final /* synthetic */ String Px;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.f(this, this.Px, this.Pv, true, false);
        }
    }

    class AnonymousClass18 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Ph;
        final /* synthetic */ int Qe;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Ph, this.Qe, this.Pe);
        }
    }

    class AnonymousClass19 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.e(this, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass20 extends LoadPlayersImpl {
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.e(this, this.Pv, true, false);
        }
    }

    class AnonymousClass21 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;
        final /* synthetic */ String Qg;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.g(this, this.Qg, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass22 extends LoadPlayersImpl {
        final /* synthetic */ int Pv;
        final /* synthetic */ String Qg;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.g(this, this.Qg, this.Pv, true, false);
        }
    }

    class AnonymousClass23 extends LoadOwnerCoverPhotoUrisImpl {
        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.g(this);
        }
    }

    class AnonymousClass24 extends LoadXpForGameCategoriesResultImpl {
        final /* synthetic */ String Qh;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.n((d) this, this.Qh);
        }
    }

    class AnonymousClass25 extends LoadXpStreamResultImpl {
        final /* synthetic */ String Qh;
        final /* synthetic */ int Qi;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c((d) this, this.Qh, this.Qi);
        }
    }

    class AnonymousClass26 extends LoadXpStreamResultImpl {
        final /* synthetic */ String Qh;
        final /* synthetic */ int Qi;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d((d) this, this.Qh, this.Qi);
        }
    }

    class AnonymousClass2 extends LoadPlayersImpl {
        final /* synthetic */ String[] Qf;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Qf);
        }
    }

    class AnonymousClass7 extends LoadPlayersImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Ph;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, "played_with", this.Ph, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass8 extends LoadPlayersImpl {
        final /* synthetic */ String Ph;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, "played_with", this.Ph, this.Pv, true, false);
        }
    }

    public Player getCurrentPlayer(GoogleApiClient apiClient) {
        return Games.c(apiClient).hb();
    }

    public String getCurrentPlayerId(GoogleApiClient apiClient) {
        return Games.c(apiClient).ha();
    }

    public Intent getPlayerSearchIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).hl();
    }

    public PendingResult<LoadPlayersResult> loadConnectedPlayers(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadPlayersImpl(this) {
            final /* synthetic */ PlayersImpl Qd;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, forceReload);
            }
        });
    }

    public PendingResult<LoadPlayersResult> loadInvitablePlayers(GoogleApiClient apiClient, final int pageSize, final boolean forceReload) {
        return apiClient.a(new LoadPlayersImpl(this) {
            final /* synthetic */ PlayersImpl Qd;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, pageSize, false, forceReload);
            }
        });
    }

    public PendingResult<LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient apiClient, final int pageSize) {
        return apiClient.a(new LoadPlayersImpl(this) {
            final /* synthetic */ PlayersImpl Qd;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, pageSize, true, false);
            }
        });
    }

    public PendingResult<LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient apiClient, final int pageSize) {
        return apiClient.a(new LoadPlayersImpl(this) {
            final /* synthetic */ PlayersImpl Qd;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, "played_with", pageSize, true, false);
            }
        });
    }

    public PendingResult<LoadPlayersResult> loadPlayer(GoogleApiClient apiClient, final String playerId) {
        return apiClient.a(new LoadPlayersImpl(this) {
            final /* synthetic */ PlayersImpl Qd;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, playerId);
            }
        });
    }

    public PendingResult<LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient apiClient, final int pageSize, final boolean forceReload) {
        return apiClient.a(new LoadPlayersImpl(this) {
            final /* synthetic */ PlayersImpl Qd;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, "played_with", pageSize, false, forceReload);
            }
        });
    }
}
