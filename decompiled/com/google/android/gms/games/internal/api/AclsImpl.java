package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games.BaseGamesApiMethodImpl;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.game.Acls;
import com.google.android.gms.games.internal.game.Acls.LoadAclResult;

public final class AclsImpl implements Acls {

    private static abstract class LoadNotifyAclImpl extends BaseGamesApiMethodImpl<LoadAclResult> {
        private LoadNotifyAclImpl() {
        }

        public /* synthetic */ Result c(Status status) {
            return z(status);
        }

        public LoadAclResult z(Status status) {
            return AclsImpl.x(status);
        }
    }

    private static abstract class UpdateNotifyAclImpl extends BaseGamesApiMethodImpl<Status> {
        private UpdateNotifyAclImpl() {
        }

        public /* synthetic */ Result c(Status status) {
            return d(status);
        }

        public Status d(Status status) {
            return status;
        }
    }

    class AnonymousClass2 extends LoadNotifyAclImpl {
        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.h(this);
        }
    }

    class AnonymousClass3 extends UpdateNotifyAclImpl {
        final /* synthetic */ String Pm;

        protected void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.p(this, this.Pm);
        }
    }

    private static LoadAclResult x(final Status status) {
        return new LoadAclResult() {
            public Status getStatus() {
                return status;
            }

            public void release() {
            }
        };
    }
}
