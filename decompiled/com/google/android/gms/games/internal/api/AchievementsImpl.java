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
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult;
import com.google.android.gms.games.internal.GamesClientImpl;

public final class AchievementsImpl implements Achievements {

    private static abstract class LoadImpl extends BaseGamesApiMethodImpl<LoadAchievementsResult> {
        private LoadImpl() {
        }

        public /* synthetic */ Result c(Status status) {
            return v(status);
        }

        public LoadAchievementsResult v(final Status status) {
            return new LoadAchievementsResult(this) {
                final /* synthetic */ LoadImpl Pk;

                public AchievementBuffer getAchievements() {
                    return new AchievementBuffer(DataHolder.af(14));
                }

                public Status getStatus() {
                    return status;
                }

                public void release() {
                }
            };
        }
    }

    private static abstract class UpdateImpl extends BaseGamesApiMethodImpl<UpdateAchievementResult> {
        private final String xG;

        public UpdateImpl(String id) {
            this.xG = id;
        }

        public /* synthetic */ Result c(Status status) {
            return w(status);
        }

        public UpdateAchievementResult w(final Status status) {
            return new UpdateAchievementResult(this) {
                final /* synthetic */ UpdateImpl Pl;

                public String getAchievementId() {
                    return this.Pl.xG;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    class AnonymousClass10 extends LoadImpl {
        final /* synthetic */ boolean Pe;
        final /* synthetic */ String Pg;
        final /* synthetic */ String Ph;

        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b((d) this, this.Pg, this.Ph, this.Pe);
        }
    }

    public Intent getAchievementsIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).he();
    }

    public void increment(GoogleApiClient apiClient, final String id, final int numSteps) {
        apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(null, id, numSteps);
            }
        });
    }

    public PendingResult<UpdateAchievementResult> incrementImmediate(GoogleApiClient apiClient, final String id, final int numSteps) {
        return apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((d) this, id, numSteps);
            }
        });
    }

    public PendingResult<LoadAchievementsResult> load(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadImpl(this) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c((d) this, forceReload);
            }
        });
    }

    public void reveal(GoogleApiClient apiClient, final String id) {
        apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(null, id);
            }
        });
    }

    public PendingResult<UpdateAchievementResult> revealImmediate(GoogleApiClient apiClient, final String id) {
        return apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b((d) this, id);
            }
        });
    }

    public void setSteps(GoogleApiClient apiClient, final String id, final int numSteps) {
        apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(null, id, numSteps);
            }
        });
    }

    public PendingResult<UpdateAchievementResult> setStepsImmediate(GoogleApiClient apiClient, final String id, final int numSteps) {
        return apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b((d) this, id, numSteps);
            }
        });
    }

    public void unlock(GoogleApiClient apiClient, final String id) {
        apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(null, id);
            }
        });
    }

    public PendingResult<UpdateAchievementResult> unlockImmediate(GoogleApiClient apiClient, final String id) {
        return apiClient.b(new UpdateImpl(this, id) {
            final /* synthetic */ AchievementsImpl Pf;

            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c((d) this, id);
            }
        });
    }
}
