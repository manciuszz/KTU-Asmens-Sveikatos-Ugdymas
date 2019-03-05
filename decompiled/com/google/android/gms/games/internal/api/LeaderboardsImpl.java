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
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.Leaderboards.LeaderboardMetadataResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;

public final class LeaderboardsImpl implements Leaderboards {

    private static abstract class LoadMetadataImpl extends BaseGamesApiMethodImpl<LeaderboardMetadataResult> {
        private LoadMetadataImpl() {
        }

        public LeaderboardMetadataResult G(final Status status) {
            return new LeaderboardMetadataResult(this) {
                final /* synthetic */ LoadMetadataImpl PR;

                public LeaderboardBuffer getLeaderboards() {
                    return new LeaderboardBuffer(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return G(status);
        }
    }

    private static abstract class LoadPlayerScoreImpl extends BaseGamesApiMethodImpl<LoadPlayerScoreResult> {
        private LoadPlayerScoreImpl() {
        }

        public LoadPlayerScoreResult H(final Status status) {
            return new LoadPlayerScoreResult(this) {
                final /* synthetic */ LoadPlayerScoreImpl PS;

                public LeaderboardScore getScore() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return H(status);
        }
    }

    private static abstract class LoadScoresImpl extends BaseGamesApiMethodImpl<LoadScoresResult> {
        private LoadScoresImpl() {
        }

        public LoadScoresResult I(final Status status) {
            return new LoadScoresResult(this) {
                final /* synthetic */ LoadScoresImpl PT;

                public Leaderboard getLeaderboard() {
                    return null;
                }

                public LeaderboardScoreBuffer getScores() {
                    return new LeaderboardScoreBuffer(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return I(status);
        }
    }

    protected static abstract class SubmitScoreImpl extends BaseGamesApiMethodImpl<SubmitScoreResult> {
        protected SubmitScoreImpl() {
        }

        public SubmitScoreResult J(final Status status) {
            return new SubmitScoreResult(this) {
                final /* synthetic */ SubmitScoreImpl PU;

                public ScoreSubmissionData getScoreData() {
                    return new ScoreSubmissionData(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }

        public /* synthetic */ Result c(Status status) {
            return J(status);
        }
    }

    class AnonymousClass10 extends LoadScoresImpl {
        final /* synthetic */ String PJ;
        final /* synthetic */ int PK;
        final /* synthetic */ int PL;
        final /* synthetic */ int PM;
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Ph;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Ph, this.PJ, this.PK, this.PL, this.PM, this.Pe);
        }
    }

    class AnonymousClass11 extends LoadScoresImpl {
        final /* synthetic */ String PJ;
        final /* synthetic */ int PK;
        final /* synthetic */ int PL;
        final /* synthetic */ int PM;
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Ph;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.Ph, this.PJ, this.PK, this.PL, this.PM, this.Pe);
        }
    }

    class AnonymousClass8 extends LoadMetadataImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Ph;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c((d) this, this.Ph, this.Pe);
        }
    }

    class AnonymousClass9 extends LoadMetadataImpl {
        final /* synthetic */ String PJ;
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Ph;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((d) this, this.Ph, this.PJ, this.Pe);
        }
    }

    public Intent getAllLeaderboardsIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).hd();
    }

    public Intent getLeaderboardIntent(GoogleApiClient apiClient, String leaderboardId) {
        return Games.c(apiClient).aR(leaderboardId);
    }

    public PendingResult<LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient apiClient, final String leaderboardId, final int span, final int leaderboardCollection) {
        return apiClient.a(new LoadPlayerScoreImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, null, leaderboardId, span, leaderboardCollection);
            }
        });
    }

    public PendingResult<LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient apiClient, final String leaderboardId, final boolean forceReload) {
        return apiClient.a(new LoadMetadataImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, leaderboardId, forceReload);
            }
        });
    }

    public PendingResult<LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadMetadataImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b((d) this, forceReload);
            }
        });
    }

    public PendingResult<LoadScoresResult> loadMoreScores(GoogleApiClient apiClient, final LeaderboardScoreBuffer buffer, final int maxResults, final int pageDirection) {
        return apiClient.a(new LoadScoresImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, buffer, maxResults, pageDirection);
            }
        });
    }

    public PendingResult<LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
        return loadPlayerCenteredScores(apiClient, leaderboardId, span, leaderboardCollection, maxResults, false);
    }

    public PendingResult<LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
        final String str = leaderboardId;
        final int i = span;
        final int i2 = leaderboardCollection;
        final int i3 = maxResults;
        final boolean z = forceReload;
        return apiClient.a(new LoadScoresImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, str, i, i2, i3, z);
            }
        });
    }

    public PendingResult<LoadScoresResult> loadTopScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
        return loadTopScores(apiClient, leaderboardId, span, leaderboardCollection, maxResults, false);
    }

    public PendingResult<LoadScoresResult> loadTopScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
        final String str = leaderboardId;
        final int i = span;
        final int i2 = leaderboardCollection;
        final int i3 = maxResults;
        final boolean z = forceReload;
        return apiClient.a(new LoadScoresImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, str, i, i2, i3, z);
            }
        });
    }

    public void submitScore(GoogleApiClient apiClient, String leaderboardId, long score) {
        submitScore(apiClient, leaderboardId, score, null);
    }

    public void submitScore(GoogleApiClient apiClient, String leaderboardId, long score, String scoreTag) {
        Games.c(apiClient).a(null, leaderboardId, score, scoreTag);
    }

    public PendingResult<SubmitScoreResult> submitScoreImmediate(GoogleApiClient apiClient, String leaderboardId, long score) {
        return submitScoreImmediate(apiClient, leaderboardId, score, null);
    }

    public PendingResult<SubmitScoreResult> submitScoreImmediate(GoogleApiClient apiClient, String leaderboardId, long score, String scoreTag) {
        final String str = leaderboardId;
        final long j = score;
        final String str2 = scoreTag;
        return apiClient.b(new SubmitScoreImpl(this) {
            final /* synthetic */ LeaderboardsImpl PI;

            protected void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, str, j, str2);
            }
        });
    }
}
