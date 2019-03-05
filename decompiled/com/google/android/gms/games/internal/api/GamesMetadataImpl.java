package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.d;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.GamesMetadata.LoadExtendedGamesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameInstancesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameSearchSuggestionsResult;
import com.google.android.gms.games.GamesMetadata.LoadGamesResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class GamesMetadataImpl implements GamesMetadata {

    private static abstract class LoadExtendedGamesImpl extends BaseGamesApiMethodImpl<LoadExtendedGamesResult> {
        private LoadExtendedGamesImpl() {
        }

        public LoadExtendedGamesResult B(final Status status) {
            return new LoadExtendedGamesResult(this) {
                final /* synthetic */ LoadExtendedGamesImpl PA;

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return B(status);
        }
    }

    private static abstract class LoadGameInstancesImpl extends BaseGamesApiMethodImpl<LoadGameInstancesResult> {
        private LoadGameInstancesImpl() {
        }

        public LoadGameInstancesResult C(final Status status) {
            return new LoadGameInstancesResult(this) {
                final /* synthetic */ LoadGameInstancesImpl PB;

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return C(status);
        }
    }

    private static abstract class LoadGameSearchSuggestionsImpl extends BaseGamesApiMethodImpl<LoadGameSearchSuggestionsResult> {
        private LoadGameSearchSuggestionsImpl() {
        }

        public LoadGameSearchSuggestionsResult D(final Status status) {
            return new LoadGameSearchSuggestionsResult(this) {
                final /* synthetic */ LoadGameSearchSuggestionsImpl PC;

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return D(status);
        }
    }

    private static abstract class LoadGamesImpl extends BaseGamesApiMethodImpl<LoadGamesResult> {
        private LoadGamesImpl() {
        }

        public LoadGamesResult E(final Status status) {
            return new LoadGamesResult(this) {
                final /* synthetic */ LoadGamesImpl PD;

                public GameBuffer getGames() {
                    return new GameBuffer(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return E(status);
        }
    }

    class AnonymousClass10 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Pu;
        final /* synthetic */ int Pv;
        final /* synthetic */ boolean Pw;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Pu, this.Pv, false, true, false, this.Pw);
        }
    }

    class AnonymousClass11 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Pg;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.Pg, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass12 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Pg;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.Pg, this.Pv, true, false);
        }
    }

    class AnonymousClass13 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Pg;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Pg, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass14 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Pg;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Pg, this.Pv, true, false);
        }
    }

    class AnonymousClass15 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Pu;
        final /* synthetic */ int Pv;
        final /* synthetic */ boolean Pw;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Pu, this.Pv, true, false, this.Pe, this.Pw);
        }
    }

    class AnonymousClass16 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Pu;
        final /* synthetic */ int Pv;
        final /* synthetic */ boolean Pw;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Pu, this.Pv, true, true, false, this.Pw);
        }
    }

    class AnonymousClass17 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;
        final /* synthetic */ String Px;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.e(this, this.Px, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass18 extends LoadExtendedGamesImpl {
        final /* synthetic */ int Pv;
        final /* synthetic */ String Px;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.e(this, this.Px, this.Pv, true, false);
        }
    }

    class AnonymousClass19 extends LoadGameSearchSuggestionsImpl {
        final /* synthetic */ String Px;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.m((d) this, this.Px);
        }
    }

    class AnonymousClass2 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Ph;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.k(this, this.Ph);
        }
    }

    class AnonymousClass3 extends LoadGameInstancesImpl {
        final /* synthetic */ String Ph;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.l((d) this, this.Ph);
        }
    }

    class AnonymousClass4 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, null, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass5 extends LoadExtendedGamesImpl {
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, null, this.Pv, true, false);
        }
    }

    class AnonymousClass6 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Pg;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.Pg, this.Pv, false, this.Pe);
        }
    }

    class AnonymousClass7 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Pg;
        final /* synthetic */ int Pv;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.Pg, this.Pv, true, false);
        }
    }

    class AnonymousClass8 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ int Pv;
        final /* synthetic */ int Py;
        final /* synthetic */ boolean Pz;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Pv, this.Py, this.Pz, this.Pe);
        }
    }

    class AnonymousClass9 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Pu;
        final /* synthetic */ int Pv;
        final /* synthetic */ boolean Pw;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Pu, this.Pv, false, false, this.Pe, this.Pw);
        }
    }

    public Game getCurrentGame(GoogleApiClient apiClient) {
        return Games.c(apiClient).hc();
    }

    public PendingResult<LoadGamesResult> loadGame(GoogleApiClient apiClient) {
        return apiClient.a(new LoadGamesImpl(this) {
            final /* synthetic */ GamesMetadataImpl Pt;

            {
                this.Pt = r2;
            }

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.f((d) this);
            }
        });
    }
}
