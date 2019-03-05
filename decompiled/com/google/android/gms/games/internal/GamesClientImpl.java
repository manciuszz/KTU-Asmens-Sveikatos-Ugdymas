package com.google.android.gms.games.internal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.api.b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.GamesMetadata.LoadExtendedGamesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameInstancesResult;
import com.google.android.gms.games.GamesMetadata.LoadGameSearchSuggestionsResult;
import com.google.android.gms.games.GamesMetadata.LoadGamesResult;
import com.google.android.gms.games.Notifications.ContactSettingLoadResult;
import com.google.android.gms.games.Notifications.GameMuteStatusChangeResult;
import com.google.android.gms.games.Notifications.GameMuteStatusLoadResult;
import com.google.android.gms.games.Notifications.InboxCountResult;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players.LoadOwnerCoverPhotoUrisResult;
import com.google.android.gms.games.Players.LoadPlayersResult;
import com.google.android.gms.games.Players.LoadXpForGameCategoriesResult;
import com.google.android.gms.games.Players.LoadXpStreamResult;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.games.event.Events.LoadEventsResult;
import com.google.android.gms.games.internal.IGamesService.Stub;
import com.google.android.gms.games.internal.constants.RequestType;
import com.google.android.gms.games.internal.events.EventIncrementCache;
import com.google.android.gms.games.internal.events.EventIncrementManager;
import com.google.android.gms.games.internal.experience.ExperienceEventBuffer;
import com.google.android.gms.games.internal.game.Acls.LoadAclResult;
import com.google.android.gms.games.internal.game.ExtendedGameBuffer;
import com.google.android.gms.games.internal.game.GameInstanceBuffer;
import com.google.android.gms.games.internal.request.RequestUpdateOutcomes;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreEntity;
import com.google.android.gms.games.leaderboard.Leaderboards.LeaderboardMetadataResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult;
import com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations.LoadInvitationsResult;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.ParticipantUtils;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer.ReliableMessageSentCallback;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomBuffer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchBuffer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.CancelMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LeaveMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.LoadMatchesResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.UpdateMatchResult;
import com.google.android.gms.games.quest.Milestone;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestBuffer;
import com.google.android.gms.games.quest.QuestEntity;
import com.google.android.gms.games.quest.QuestUpdateListener;
import com.google.android.gms.games.quest.Quests.AcceptQuestResult;
import com.google.android.gms.games.quest.Quests.ClaimMilestoneResult;
import com.google.android.gms.games.quest.Quests.LoadQuestsResult;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests.LoadRequestSummariesResult;
import com.google.android.gms.games.request.Requests.LoadRequestsResult;
import com.google.android.gms.games.request.Requests.SendRequestResult;
import com.google.android.gms.games.request.Requests.UpdateRequestsResult;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotEntity;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataBuffer;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.SnapshotMetadataEntity;
import com.google.android.gms.games.snapshot.Snapshots.CommitSnapshotResult;
import com.google.android.gms.games.snapshot.Snapshots.DeleteSnapshotResult;
import com.google.android.gms.games.snapshot.Snapshots.LoadSnapshotsResult;
import com.google.android.gms.games.snapshot.Snapshots.OpenSnapshotResult;
import com.google.android.gms.internal.gx;
import com.google.android.gms.internal.hb;
import com.google.android.gms.internal.hb.d;
import com.google.android.gms.internal.hb.e;
import com.google.android.gms.internal.hi;
import com.google.android.gms.internal.hm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class GamesClientImpl extends hb<IGamesService> implements ConnectionCallbacks, OnConnectionFailedListener {
    private final boolean NA;
    private final int NB;
    private final boolean NC;
    private final String ND;
    EventIncrementManager Np = new EventIncrementManager(this) {
        final /* synthetic */ GamesClientImpl NE;

        {
            this.NE = r1;
        }

        public EventIncrementCache hx() {
            return new GameClientEventIncrementCache(this.NE);
        }
    };
    private final String Nq;
    private final Map<String, RealTimeSocket> Nr;
    private PlayerEntity Ns;
    private GameEntity Nt;
    private final PopupManager Nu;
    private boolean Nv = false;
    private boolean Nw = false;
    private int Nx;
    private final Binder Ny;
    private final long Nz;
    private final String yQ;

    private class GameClientEventIncrementCache extends EventIncrementCache {
        final /* synthetic */ GamesClientImpl NE;

        public GameClientEventIncrementCache(GamesClientImpl gamesClientImpl) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl.getContext().getMainLooper(), 1000);
        }

        protected void o(String str, int i) {
            try {
                ((IGamesService) this.NE.ft()).l(str, i);
            } catch (RemoteException e) {
                GamesLog.j("GamesClientImpl", "service died");
            }
        }
    }

    private final class InvitationReceivedCallback extends b<OnInvitationReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final Invitation NP;

        InvitationReceivedCallback(GamesClientImpl gamesClientImpl, OnInvitationReceivedListener listener, Invitation invitation) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.NP = invitation;
        }

        protected void b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationReceived(this.NP);
        }

        protected /* synthetic */ void d(Object obj) {
            b((OnInvitationReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private final class InvitationRemovedCallback extends b<OnInvitationReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final String NQ;

        InvitationRemovedCallback(GamesClientImpl gamesClientImpl, OnInvitationReceivedListener listener, String invitationId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.NQ = invitationId;
        }

        protected void b(OnInvitationReceivedListener onInvitationReceivedListener) {
            onInvitationReceivedListener.onInvitationRemoved(this.NQ);
        }

        protected /* synthetic */ void d(Object obj) {
            b((OnInvitationReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private final class LeftRoomCallback extends b<RoomUpdateListener> {
        private final int CT;
        final /* synthetic */ GamesClientImpl NE;
        private final String NS;

        LeftRoomCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, int statusCode, String roomId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.CT = statusCode;
            this.NS = roomId;
        }

        public void a(RoomUpdateListener roomUpdateListener) {
            roomUpdateListener.onLeftRoom(this.CT, this.NS);
        }

        public /* synthetic */ void d(Object obj) {
            a((RoomUpdateListener) obj);
        }

        protected void fu() {
        }
    }

    private final class MatchRemovedCallback extends b<OnTurnBasedMatchUpdateReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final String Oi;

        MatchRemovedCallback(GamesClientImpl gamesClientImpl, OnTurnBasedMatchUpdateReceivedListener listener, String matchId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Oi = matchId;
        }

        protected void b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchRemoved(this.Oi);
        }

        protected /* synthetic */ void d(Object obj) {
            b((OnTurnBasedMatchUpdateReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private final class MatchUpdateReceivedCallback extends b<OnTurnBasedMatchUpdateReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final TurnBasedMatch Ok;

        MatchUpdateReceivedCallback(GamesClientImpl gamesClientImpl, OnTurnBasedMatchUpdateReceivedListener listener, TurnBasedMatch match) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Ok = match;
        }

        protected void b(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
            onTurnBasedMatchUpdateReceivedListener.onTurnBasedMatchReceived(this.Ok);
        }

        protected /* synthetic */ void d(Object obj) {
            b((OnTurnBasedMatchUpdateReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private final class MessageReceivedCallback extends b<RealTimeMessageReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final RealTimeMessage Ol;

        MessageReceivedCallback(GamesClientImpl gamesClientImpl, RealTimeMessageReceivedListener listener, RealTimeMessage message) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Ol = message;
        }

        public void a(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            if (realTimeMessageReceivedListener != null) {
                realTimeMessageReceivedListener.onRealTimeMessageReceived(this.Ol);
            }
        }

        public /* synthetic */ void d(Object obj) {
            a((RealTimeMessageReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private final class P2PConnectedCallback extends b<RoomStatusUpdateListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final String Oq;

        P2PConnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, String participantId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Oq = participantId;
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PConnected(this.Oq);
            }
        }

        public /* synthetic */ void d(Object obj) {
            a((RoomStatusUpdateListener) obj);
        }

        protected void fu() {
        }
    }

    private final class P2PDisconnectedCallback extends b<RoomStatusUpdateListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final String Oq;

        P2PDisconnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, String participantId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.Oq = participantId;
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener) {
            if (roomStatusUpdateListener != null) {
                roomStatusUpdateListener.onP2PDisconnected(this.Oq);
            }
        }

        public /* synthetic */ void d(Object obj) {
            a((RoomStatusUpdateListener) obj);
        }

        protected void fu() {
        }
    }

    private final class QuestCompletedCallback extends b<QuestUpdateListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final Quest NG;

        QuestCompletedCallback(GamesClientImpl gamesClientImpl, QuestUpdateListener listener, Quest quest) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.NG = quest;
        }

        protected void b(QuestUpdateListener questUpdateListener) {
            questUpdateListener.onQuestCompleted(this.NG);
        }

        protected /* synthetic */ void d(Object obj) {
            b((QuestUpdateListener) obj);
        }

        protected void fu() {
        }
    }

    private final class RealTimeMessageSentCallback extends b<ReliableMessageSentCallback> {
        private final int CT;
        final /* synthetic */ GamesClientImpl NE;
        private final String Ow;
        private final int Ox;

        RealTimeMessageSentCallback(GamesClientImpl gamesClientImpl, ReliableMessageSentCallback listener, int statusCode, int token, String recipientParticipantId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.CT = statusCode;
            this.Ox = token;
            this.Ow = recipientParticipantId;
        }

        public void a(ReliableMessageSentCallback reliableMessageSentCallback) {
            if (reliableMessageSentCallback != null) {
                reliableMessageSentCallback.onRealTimeMessageSent(this.CT, this.Ox, this.Ow);
            }
        }

        public /* synthetic */ void d(Object obj) {
            a((ReliableMessageSentCallback) obj);
        }

        protected void fu() {
        }
    }

    private final class RequestReceivedCallback extends b<OnRequestReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final GameRequest OA;

        RequestReceivedCallback(GamesClientImpl gamesClientImpl, OnRequestReceivedListener listener, GameRequest request) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.OA = request;
        }

        protected void b(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestReceived(this.OA);
        }

        protected /* synthetic */ void d(Object obj) {
            b((OnRequestReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private final class RequestRemovedCallback extends b<OnRequestReceivedListener> {
        final /* synthetic */ GamesClientImpl NE;
        private final String OB;

        RequestRemovedCallback(GamesClientImpl gamesClientImpl, OnRequestReceivedListener listener, String requestId) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener);
            this.OB = requestId;
        }

        protected void b(OnRequestReceivedListener onRequestReceivedListener) {
            onRequestReceivedListener.onRequestRemoved(this.OB);
        }

        protected /* synthetic */ void d(Object obj) {
            b((OnRequestReceivedListener) obj);
        }

        protected void fu() {
        }
    }

    private abstract class AbstractRoomCallback extends d<RoomUpdateListener> {
        final /* synthetic */ GamesClientImpl NE;

        AbstractRoomCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        protected void a(RoomUpdateListener roomUpdateListener, DataHolder dataHolder) {
            a(roomUpdateListener, this.NE.Q(dataHolder), dataHolder.getStatusCode());
        }

        protected abstract void a(RoomUpdateListener roomUpdateListener, Room room, int i);
    }

    private abstract class AbstractRoomStatusCallback extends d<RoomStatusUpdateListener> {
        final /* synthetic */ GamesClientImpl NE;

        AbstractRoomStatusCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, DataHolder dataHolder) {
            a(roomStatusUpdateListener, this.NE.Q(dataHolder));
        }

        protected abstract void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room);
    }

    private static final class AcceptQuestResultImpl extends b implements AcceptQuestResult {
        private final Quest NG;

        AcceptQuestResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            QuestBuffer questBuffer = new QuestBuffer(dataHolder);
            try {
                if (questBuffer.getCount() > 0) {
                    this.NG = new QuestEntity((Quest) questBuffer.get(0));
                } else {
                    this.NG = null;
                }
                questBuffer.close();
            } catch (Throwable th) {
                questBuffer.close();
            }
        }

        public Quest getQuest() {
            return this.NG;
        }
    }

    private static final class CancelMatchResultImpl implements CancelMatchResult {
        private final String NH;
        private final Status yz;

        CancelMatchResultImpl(Status status, String externalMatchId) {
            this.yz = status;
            this.NH = externalMatchId;
        }

        public String getMatchId() {
            return this.NH;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class ClaimMilestoneResultImpl extends b implements ClaimMilestoneResult {
        private final Quest NG;
        private final Milestone NI;

        ClaimMilestoneResultImpl(DataHolder dataHolder, String milestoneId) {
            super(dataHolder);
            QuestBuffer questBuffer = new QuestBuffer(dataHolder);
            try {
                if (questBuffer.getCount() > 0) {
                    this.NG = new QuestEntity((Quest) questBuffer.get(0));
                    List iJ = this.NG.iJ();
                    int size = iJ.size();
                    for (int i = 0; i < size; i++) {
                        if (((Milestone) iJ.get(i)).getMilestoneId().equals(milestoneId)) {
                            this.NI = (Milestone) iJ.get(i);
                            return;
                        }
                    }
                    this.NI = null;
                } else {
                    this.NI = null;
                    this.NG = null;
                }
                questBuffer.close();
            } finally {
                questBuffer.close();
            }
        }

        public Milestone getMilestone() {
            return this.NI;
        }

        public Quest getQuest() {
            return this.NG;
        }
    }

    private static final class CommitSnapshotResultImpl extends b implements CommitSnapshotResult {
        private final SnapshotMetadata NJ;

        CommitSnapshotResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            SnapshotMetadataBuffer snapshotMetadataBuffer = new SnapshotMetadataBuffer(dataHolder);
            try {
                if (snapshotMetadataBuffer.getCount() > 0) {
                    this.NJ = new SnapshotMetadataEntity(snapshotMetadataBuffer.get(0));
                } else {
                    this.NJ = null;
                }
                snapshotMetadataBuffer.close();
            } catch (Throwable th) {
                snapshotMetadataBuffer.close();
            }
        }

        public SnapshotMetadata getSnapshotMetadata() {
            return this.NJ;
        }
    }

    private static final class ContactSettingLoadResultImpl extends b implements ContactSettingLoadResult {
        ContactSettingLoadResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private static final class DeleteSnapshotResultImpl implements DeleteSnapshotResult {
        private final String NK;
        private final Status yz;

        DeleteSnapshotResultImpl(int statusCode, String snapshotId) {
            this.yz = new Status(statusCode);
            this.NK = snapshotId;
        }

        public String getSnapshotId() {
            return this.NK;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class GameMuteStatusChangeResultImpl implements GameMuteStatusChangeResult {
        private final String NL;
        private final boolean NM;
        private final Status yz;

        public GameMuteStatusChangeResultImpl(int statusCode, String externalGameId, boolean isMuted) {
            this.yz = new Status(statusCode);
            this.NL = externalGameId;
            this.NM = isMuted;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class GameMuteStatusLoadResultImpl implements GameMuteStatusLoadResult {
        private final String NL;
        private final boolean NM;
        private final Status yz;

        public GameMuteStatusLoadResultImpl(DataHolder dataHolder) {
            try {
                this.yz = new Status(dataHolder.getStatusCode());
                if (dataHolder.getCount() > 0) {
                    this.NL = dataHolder.c("external_game_id", 0, 0);
                    this.NM = dataHolder.d("muted", 0, 0);
                } else {
                    this.NL = null;
                    this.NM = false;
                }
                dataHolder.close();
            } catch (Throwable th) {
                dataHolder.close();
            }
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class InboxCountResultImpl implements InboxCountResult {
        private final Bundle NN;
        private final Status yz;

        InboxCountResultImpl(Status status, Bundle inboxCounts) {
            this.yz = status;
            this.NN = inboxCounts;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class LeaderboardMetadataResultImpl extends b implements LeaderboardMetadataResult {
        private final LeaderboardBuffer NR;

        LeaderboardMetadataResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NR = new LeaderboardBuffer(dataHolder);
        }

        public LeaderboardBuffer getLeaderboards() {
            return this.NR;
        }
    }

    private static final class LoadAchievementsResultImpl extends b implements LoadAchievementsResult {
        private final AchievementBuffer NT;

        LoadAchievementsResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NT = new AchievementBuffer(dataHolder);
        }

        public AchievementBuffer getAchievements() {
            return this.NT;
        }
    }

    private static final class LoadAclResultImpl extends b implements LoadAclResult {
        LoadAclResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private static final class LoadEventResultImpl extends b implements LoadEventsResult {
        private final EventBuffer NU;

        LoadEventResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NU = new EventBuffer(dataHolder);
        }

        public EventBuffer getEvents() {
            return this.NU;
        }
    }

    private static final class LoadExtendedGamesResultImpl extends b implements LoadExtendedGamesResult {
        private final ExtendedGameBuffer NV;

        LoadExtendedGamesResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NV = new ExtendedGameBuffer(dataHolder);
        }
    }

    private static final class LoadGameInstancesResultImpl extends b implements LoadGameInstancesResult {
        private final GameInstanceBuffer NW;

        LoadGameInstancesResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NW = new GameInstanceBuffer(dataHolder);
        }
    }

    private static final class LoadGameSearchSuggestionsResultImpl extends b implements LoadGameSearchSuggestionsResult {
        LoadGameSearchSuggestionsResultImpl(DataHolder data) {
            super(data);
        }
    }

    private static final class LoadGamesResultImpl extends b implements LoadGamesResult {
        private final GameBuffer NX;

        LoadGamesResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NX = new GameBuffer(dataHolder);
        }

        public GameBuffer getGames() {
            return this.NX;
        }
    }

    private static final class LoadInvitationsResultImpl extends b implements LoadInvitationsResult {
        private final InvitationBuffer NY;

        LoadInvitationsResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.NY = new InvitationBuffer(dataHolder);
        }

        public InvitationBuffer getInvitations() {
            return this.NY;
        }
    }

    private static final class LoadMatchesResultImpl implements LoadMatchesResult {
        private final LoadMatchesResponse NZ;
        private final Status yz;

        LoadMatchesResultImpl(Status status, Bundle matchData) {
            this.yz = status;
            this.NZ = new LoadMatchesResponse(matchData);
        }

        public LoadMatchesResponse getMatches() {
            return this.NZ;
        }

        public Status getStatus() {
            return this.yz;
        }

        public void release() {
            this.NZ.close();
        }
    }

    private static final class LoadOwnerCoverPhotoUrisResultImpl implements LoadOwnerCoverPhotoUrisResult {
        private final Bundle HM;
        private final Status yz;

        LoadOwnerCoverPhotoUrisResultImpl(int statusCode, Bundle bundle) {
            this.yz = new Status(statusCode);
            this.HM = bundle;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class LoadPlayerScoreResultImpl extends b implements LoadPlayerScoreResult {
        private final LeaderboardScoreEntity Oa;

        LoadPlayerScoreResultImpl(DataHolder scoreHolder) {
            super(scoreHolder);
            LeaderboardScoreBuffer leaderboardScoreBuffer = new LeaderboardScoreBuffer(scoreHolder);
            try {
                if (leaderboardScoreBuffer.getCount() > 0) {
                    this.Oa = (LeaderboardScoreEntity) leaderboardScoreBuffer.get(0).freeze();
                } else {
                    this.Oa = null;
                }
                leaderboardScoreBuffer.close();
            } catch (Throwable th) {
                leaderboardScoreBuffer.close();
            }
        }

        public LeaderboardScore getScore() {
            return this.Oa;
        }
    }

    private static final class LoadPlayersResultImpl extends b implements LoadPlayersResult {
        private final PlayerBuffer Ob;

        LoadPlayersResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.Ob = new PlayerBuffer(dataHolder);
        }

        public PlayerBuffer getPlayers() {
            return this.Ob;
        }
    }

    private static final class LoadQuestsResultImpl extends b implements LoadQuestsResult {
        private final DataHolder DG;

        LoadQuestsResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.DG = dataHolder;
        }

        public QuestBuffer getQuests() {
            return new QuestBuffer(this.DG);
        }
    }

    private static final class LoadRequestSummariesResultImpl extends b implements LoadRequestSummariesResult {
        LoadRequestSummariesResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private static final class LoadRequestsResultImpl implements LoadRequestsResult {
        private final Bundle Oc;
        private final Status yz;

        LoadRequestsResultImpl(Status status, Bundle requestData) {
            this.yz = status;
            this.Oc = requestData;
        }

        public GameRequestBuffer getRequests(int requestType) {
            String cm = RequestType.cm(requestType);
            return !this.Oc.containsKey(cm) ? null : new GameRequestBuffer((DataHolder) this.Oc.get(cm));
        }

        public Status getStatus() {
            return this.yz;
        }

        public void release() {
            for (String parcelable : this.Oc.keySet()) {
                DataHolder dataHolder = (DataHolder) this.Oc.getParcelable(parcelable);
                if (dataHolder != null) {
                    dataHolder.close();
                }
            }
        }
    }

    private static final class LoadScoresResultImpl extends b implements LoadScoresResult {
        private final LeaderboardEntity Od;
        private final LeaderboardScoreBuffer Oe;

        LoadScoresResultImpl(DataHolder leaderboard, DataHolder scores) {
            super(scores);
            LeaderboardBuffer leaderboardBuffer = new LeaderboardBuffer(leaderboard);
            try {
                if (leaderboardBuffer.getCount() > 0) {
                    this.Od = (LeaderboardEntity) ((Leaderboard) leaderboardBuffer.get(0)).freeze();
                } else {
                    this.Od = null;
                }
                leaderboardBuffer.close();
                this.Oe = new LeaderboardScoreBuffer(scores);
            } catch (Throwable th) {
                leaderboardBuffer.close();
            }
        }

        public Leaderboard getLeaderboard() {
            return this.Od;
        }

        public LeaderboardScoreBuffer getScores() {
            return this.Oe;
        }
    }

    private static final class LoadSnapshotsResultImpl extends b implements LoadSnapshotsResult {
        LoadSnapshotsResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }

        public SnapshotMetadataBuffer getSnapshots() {
            return new SnapshotMetadataBuffer(this.DG);
        }
    }

    private static final class LoadXpForGameCategoriesResultImpl implements LoadXpForGameCategoriesResult {
        private final List<String> Of;
        private final Bundle Og;
        private final Status yz;

        LoadXpForGameCategoriesResultImpl(Status status, Bundle xpData) {
            this.yz = status;
            this.Of = xpData.getStringArrayList("game_category_list");
            this.Og = xpData;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class LoadXpStreamResultImpl extends b implements LoadXpStreamResult {
        private final ExperienceEventBuffer Oh;

        LoadXpStreamResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.Oh = new ExperienceEventBuffer(dataHolder);
        }
    }

    private static final class OpenSnapshotResultImpl extends b implements OpenSnapshotResult {
        private final Snapshot Om;
        private final String On;
        private final Snapshot Oo;
        private final Contents Op;

        OpenSnapshotResultImpl(DataHolder dataHolder, Contents currentContents) {
            this(dataHolder, null, currentContents, null, null);
        }

        OpenSnapshotResultImpl(DataHolder metadataHolder, String conflictId, Contents currentContents, Contents conflictContents, Contents resolutionContents) {
            boolean z = true;
            super(metadataHolder);
            SnapshotMetadataBuffer snapshotMetadataBuffer = new SnapshotMetadataBuffer(metadataHolder);
            try {
                if (snapshotMetadataBuffer.getCount() == 0) {
                    this.Om = null;
                    this.Oo = null;
                } else if (snapshotMetadataBuffer.getCount() == 1) {
                    if (metadataHolder.getStatusCode() == 4004) {
                        z = false;
                    }
                    gx.A(z);
                    this.Om = new SnapshotEntity(new SnapshotMetadataEntity(snapshotMetadataBuffer.get(0)), currentContents);
                    this.Oo = null;
                } else {
                    this.Om = new SnapshotEntity(new SnapshotMetadataEntity(snapshotMetadataBuffer.get(0)), currentContents);
                    this.Oo = new SnapshotEntity(new SnapshotMetadataEntity(snapshotMetadataBuffer.get(1)), conflictContents);
                }
                snapshotMetadataBuffer.close();
                this.On = conflictId;
                this.Op = resolutionContents;
            } catch (Throwable th) {
                snapshotMetadataBuffer.close();
            }
        }

        public String getConflictId() {
            return this.On;
        }

        public Snapshot getConflictingSnapshot() {
            return this.Oo;
        }

        public Contents getResolutionContents() {
            return this.Op;
        }

        public Snapshot getSnapshot() {
            return this.Om;
        }
    }

    private static final class SendRequestResultImpl extends b implements SendRequestResult {
        private final GameRequest OA;

        SendRequestResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            try {
                if (gameRequestBuffer.getCount() > 0) {
                    this.OA = (GameRequest) ((GameRequest) gameRequestBuffer.get(0)).freeze();
                } else {
                    this.OA = null;
                }
                gameRequestBuffer.close();
            } catch (Throwable th) {
                gameRequestBuffer.close();
            }
        }
    }

    private static final class SubmitScoreResultImpl extends b implements SubmitScoreResult {
        private final ScoreSubmissionData OM;

        public SubmitScoreResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            try {
                this.OM = new ScoreSubmissionData(dataHolder);
            } finally {
                dataHolder.close();
            }
        }

        public ScoreSubmissionData getScoreData() {
            return this.OM;
        }
    }

    private static abstract class TurnBasedMatchResult extends b {
        final TurnBasedMatch Ok;

        TurnBasedMatchResult(DataHolder dataHolder) {
            super(dataHolder);
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    this.Ok = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                } else {
                    this.Ok = null;
                }
                turnBasedMatchBuffer.close();
            } catch (Throwable th) {
                turnBasedMatchBuffer.close();
            }
        }

        public TurnBasedMatch getMatch() {
            return this.Ok;
        }
    }

    private static final class UpdateAchievementResultImpl implements UpdateAchievementResult {
        private final String OT;
        private final Status yz;

        UpdateAchievementResultImpl(int statusCode, String achievementId) {
            this.yz = new Status(statusCode);
            this.OT = achievementId;
        }

        public String getAchievementId() {
            return this.OT;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static final class UpdateRequestsResultImpl extends b implements UpdateRequestsResult {
        private final RequestUpdateOutcomes OU;

        UpdateRequestsResultImpl(DataHolder dataHolder) {
            super(dataHolder);
            this.OU = RequestUpdateOutcomes.U(dataHolder);
        }

        public Set<String> getRequestIds() {
            return this.OU.getRequestIds();
        }

        public int getRequestOutcome(String requestId) {
            return this.OU.getRequestOutcome(requestId);
        }
    }

    private abstract class AbstractPeerStatusCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl NE;
        private final ArrayList<String> NF = new ArrayList();

        AbstractPeerStatusCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
            for (Object add : participantIds) {
                this.NF.add(add);
            }
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            a(roomStatusUpdateListener, room, this.NF);
        }

        protected abstract void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
    }

    private final class AchievementUpdatedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<UpdateAchievementResult> yR;

        AchievementUpdatedBinderCallback(GamesClientImpl gamesClientImpl, a.d<UpdateAchievementResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void e(int i, String str) {
            this.yR.a(new UpdateAchievementResultImpl(i, str));
        }
    }

    private final class AchievementsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadAchievementsResult> yR;

        AchievementsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadAchievementsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void c(DataHolder dataHolder) {
            this.yR.a(new LoadAchievementsResultImpl(dataHolder));
        }
    }

    private final class ConnectedToRoomCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        ConnectedToRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    private final class ContactSettingsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<ContactSettingLoadResult> yR;

        ContactSettingsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<ContactSettingLoadResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void D(DataHolder dataHolder) {
            this.yR.a(new ContactSettingLoadResultImpl(dataHolder));
        }
    }

    private final class ContactSettingsUpdatedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<Status> yR;

        ContactSettingsUpdatedBinderCallback(GamesClientImpl gamesClientImpl, a.d<Status> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void ce(int i) {
            this.yR.a(new Status(i));
        }
    }

    private final class DisconnectedFromRoomCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        DisconnectedFromRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    private final class EventsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadEventsResult> yR;

        EventsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadEventsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void d(DataHolder dataHolder) {
            this.yR.a(new LoadEventResultImpl(dataHolder));
        }
    }

    private final class ExtendedGamesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadExtendedGamesResult> yR;

        ExtendedGamesLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadExtendedGamesResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void j(DataHolder dataHolder) {
            this.yR.a(new LoadExtendedGamesResultImpl(dataHolder));
        }
    }

    private final class GameInstancesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadGameInstancesResult> yR;

        GameInstancesLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadGameInstancesResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void k(DataHolder dataHolder) {
            this.yR.a(new LoadGameInstancesResultImpl(dataHolder));
        }
    }

    private final class GameMuteStatusChangedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<GameMuteStatusChangeResult> yR;

        GameMuteStatusChangedBinderCallback(GamesClientImpl gamesClientImpl, a.d<GameMuteStatusChangeResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void a(int i, String str, boolean z) {
            this.yR.a(new GameMuteStatusChangeResultImpl(i, str, z));
        }
    }

    private final class GameMuteStatusLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<GameMuteStatusLoadResult> yR;

        GameMuteStatusLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<GameMuteStatusLoadResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void B(DataHolder dataHolder) {
            this.yR.a(new GameMuteStatusLoadResultImpl(dataHolder));
        }
    }

    private final class GameSearchSuggestionsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadGameSearchSuggestionsResult> yR;

        GameSearchSuggestionsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadGameSearchSuggestionsResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void l(DataHolder dataHolder) {
            this.yR.a(new LoadGameSearchSuggestionsResultImpl(dataHolder));
        }
    }

    private final class GamesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadGamesResult> yR;

        GamesLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadGamesResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void i(DataHolder dataHolder) {
            this.yR.a(new LoadGamesResultImpl(dataHolder));
        }
    }

    private final class InboxCountsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<InboxCountResult> yR;

        InboxCountsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<InboxCountResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void f(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.yR.a(new InboxCountResultImpl(new Status(i), bundle));
        }
    }

    private static final class InitiateMatchResultImpl extends TurnBasedMatchResult implements InitiateMatchResult {
        InitiateMatchResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private final class InvitationReceivedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final OnInvitationReceivedListener NO;

        InvitationReceivedBinderCallback(GamesClientImpl gamesClientImpl, OnInvitationReceivedListener listener) {
            this.NE = gamesClientImpl;
            this.NO = listener;
        }

        public void n(DataHolder dataHolder) {
            InvitationBuffer invitationBuffer = new InvitationBuffer(dataHolder);
            Invitation invitation = null;
            try {
                if (invitationBuffer.getCount() > 0) {
                    invitation = (Invitation) ((Invitation) invitationBuffer.get(0)).freeze();
                }
                invitationBuffer.close();
                if (invitation != null) {
                    this.NE.a(new InvitationReceivedCallback(this.NE, this.NO, invitation));
                }
            } catch (Throwable th) {
                invitationBuffer.close();
            }
        }

        public void onInvitationRemoved(String invitationId) {
            this.NE.a(new InvitationRemovedCallback(this.NE, this.NO, invitationId));
        }
    }

    private final class InvitationsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadInvitationsResult> yR;

        InvitationsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadInvitationsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void m(DataHolder dataHolder) {
            this.yR.a(new LoadInvitationsResultImpl(dataHolder));
        }
    }

    private final class JoinedRoomCallback extends AbstractRoomCallback {
        final /* synthetic */ GamesClientImpl NE;

        public JoinedRoomCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onJoinedRoom(i, room);
        }
    }

    private final class LeaderboardScoresLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadScoresResult> yR;

        LeaderboardScoresLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadScoresResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void a(DataHolder dataHolder, DataHolder dataHolder2) {
            this.yR.a(new LoadScoresResultImpl(dataHolder, dataHolder2));
        }
    }

    private final class LeaderboardsLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LeaderboardMetadataResult> yR;

        LeaderboardsLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LeaderboardMetadataResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void e(DataHolder dataHolder) {
            this.yR.a(new LeaderboardMetadataResultImpl(dataHolder));
        }
    }

    private static final class LeaveMatchResultImpl extends TurnBasedMatchResult implements LeaveMatchResult {
        LeaveMatchResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private static final class LoadMatchResultImpl extends TurnBasedMatchResult implements LoadMatchResult {
        LoadMatchResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private final class MatchUpdateReceivedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final OnTurnBasedMatchUpdateReceivedListener Oj;

        MatchUpdateReceivedBinderCallback(GamesClientImpl gamesClientImpl, OnTurnBasedMatchUpdateReceivedListener listener) {
            this.NE = gamesClientImpl;
            this.Oj = listener;
        }

        public void onTurnBasedMatchRemoved(String matchId) {
            this.NE.a(new MatchRemovedCallback(this.NE, this.Oj, matchId));
        }

        public void t(DataHolder dataHolder) {
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            TurnBasedMatch turnBasedMatch = null;
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    turnBasedMatch = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                }
                turnBasedMatchBuffer.close();
                if (turnBasedMatch != null) {
                    this.NE.a(new MatchUpdateReceivedCallback(this.NE, this.Oj, turnBasedMatch));
                }
            } catch (Throwable th) {
                turnBasedMatchBuffer.close();
            }
        }
    }

    private final class NotifyAclLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadAclResult> yR;

        NotifyAclLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadAclResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void C(DataHolder dataHolder) {
            this.yR.a(new LoadAclResultImpl(dataHolder));
        }
    }

    private final class NotifyAclUpdatedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<Status> yR;

        NotifyAclUpdatedBinderCallback(GamesClientImpl gamesClientImpl, a.d<Status> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void cd(int i) {
            this.yR.a(new Status(i));
        }
    }

    private final class OwnerCoverPhotoUrisLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadOwnerCoverPhotoUrisResult> yR;

        OwnerCoverPhotoUrisLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadOwnerCoverPhotoUrisResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void d(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.yR.a(new LoadOwnerCoverPhotoUrisResultImpl(i, bundle));
        }
    }

    private final class PlayerLeaderboardScoreLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadPlayerScoreResult> yR;

        PlayerLeaderboardScoreLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadPlayerScoreResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void E(DataHolder dataHolder) {
            this.yR.a(new LoadPlayerScoreResultImpl(dataHolder));
        }
    }

    private final class PlayerXpForGameCategoriesLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadXpForGameCategoriesResult> yR;

        PlayerXpForGameCategoriesLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadXpForGameCategoriesResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void e(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.yR.a(new LoadXpForGameCategoriesResultImpl(new Status(i), bundle));
        }
    }

    final class PlayerXpStreamLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadXpStreamResult> yR;

        PlayerXpStreamLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadXpStreamResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void P(DataHolder dataHolder) {
            this.yR.a(new LoadXpStreamResultImpl(dataHolder));
        }
    }

    private final class PlayersLoadedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadPlayersResult> yR;

        PlayersLoadedBinderCallback(GamesClientImpl gamesClientImpl, a.d<LoadPlayersResult> holder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) holder, (Object) "Holder must not be null");
        }

        public void g(DataHolder dataHolder) {
            this.yR.a(new LoadPlayersResultImpl(dataHolder));
        }

        public void h(DataHolder dataHolder) {
            this.yR.a(new LoadPlayersResultImpl(dataHolder));
        }
    }

    private final class QuestAcceptedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<AcceptQuestResult> Or;

        public QuestAcceptedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<AcceptQuestResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.Or = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void L(DataHolder dataHolder) {
            this.Or.a(new AcceptQuestResultImpl(dataHolder));
        }
    }

    private final class QuestMilestoneClaimBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<ClaimMilestoneResult> Os;
        private final String Ot;

        public QuestMilestoneClaimBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<ClaimMilestoneResult> resultHolder, String milestoneId) {
            this.NE = gamesClientImpl;
            this.Os = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
            this.Ot = (String) hm.b((Object) milestoneId, (Object) "MilestoneId must not be null");
        }

        public void K(DataHolder dataHolder) {
            this.Os.a(new ClaimMilestoneResultImpl(dataHolder, this.Ot));
        }
    }

    private final class QuestUpdateBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final QuestUpdateListener Ou;

        QuestUpdateBinderCallback(GamesClientImpl gamesClientImpl, QuestUpdateListener listener) {
            this.NE = gamesClientImpl;
            this.Ou = listener;
        }

        private Quest R(DataHolder dataHolder) {
            QuestBuffer questBuffer = new QuestBuffer(dataHolder);
            Quest quest = null;
            try {
                if (questBuffer.getCount() > 0) {
                    quest = (Quest) ((Quest) questBuffer.get(0)).freeze();
                }
                questBuffer.close();
                return quest;
            } catch (Throwable th) {
                questBuffer.close();
            }
        }

        public void M(DataHolder dataHolder) {
            Quest R = R(dataHolder);
            if (R != null) {
                this.NE.a(new QuestCompletedCallback(this.NE, this.Ou, R));
            }
        }
    }

    private final class QuestsLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadQuestsResult> Ov;

        public QuestsLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LoadQuestsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.Ov = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void O(DataHolder dataHolder) {
            this.Ov.a(new LoadQuestsResultImpl(dataHolder));
        }
    }

    private final class RealTimeReliableMessageBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        final ReliableMessageSentCallback Oy;

        public RealTimeReliableMessageBinderCallbacks(GamesClientImpl gamesClientImpl, ReliableMessageSentCallback messageSentCallbacks) {
            this.NE = gamesClientImpl;
            this.Oy = messageSentCallbacks;
        }

        public void b(int i, int i2, String str) {
            this.NE.a(new RealTimeMessageSentCallback(this.NE, this.Oy, i, i2, str));
        }
    }

    private final class RequestReceivedBinderCallback extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final OnRequestReceivedListener Oz;

        RequestReceivedBinderCallback(GamesClientImpl gamesClientImpl, OnRequestReceivedListener listener) {
            this.NE = gamesClientImpl;
            this.Oz = listener;
        }

        public void o(DataHolder dataHolder) {
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            GameRequest gameRequest = null;
            try {
                if (gameRequestBuffer.getCount() > 0) {
                    gameRequest = (GameRequest) ((GameRequest) gameRequestBuffer.get(0)).freeze();
                }
                gameRequestBuffer.close();
                if (gameRequest != null) {
                    this.NE.a(new RequestReceivedCallback(this.NE, this.Oz, gameRequest));
                }
            } catch (Throwable th) {
                gameRequestBuffer.close();
            }
        }

        public void onRequestRemoved(String requestId) {
            this.NE.a(new RequestRemovedCallback(this.NE, this.Oz, requestId));
        }
    }

    private final class RequestSentBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<SendRequestResult> OC;

        public RequestSentBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<SendRequestResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OC = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void G(DataHolder dataHolder) {
            this.OC.a(new SendRequestResultImpl(dataHolder));
        }
    }

    private final class RequestSummariesLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadRequestSummariesResult> OD;

        public RequestSummariesLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LoadRequestSummariesResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OD = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void H(DataHolder dataHolder) {
            this.OD.a(new LoadRequestSummariesResultImpl(dataHolder));
        }
    }

    private final class RequestsLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadRequestsResult> OE;

        public RequestsLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LoadRequestsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OE = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void c(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.OE.a(new LoadRequestsResultImpl(new Status(i), bundle));
        }
    }

    private final class RequestsUpdatedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<UpdateRequestsResult> OF;

        public RequestsUpdatedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<UpdateRequestsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OF = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void F(DataHolder dataHolder) {
            this.OF.a(new UpdateRequestsResultImpl(dataHolder));
        }
    }

    private final class RoomAutoMatchingCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        RoomAutoMatchingCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    private final class RoomBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final RoomUpdateListener OG;
        private final RoomStatusUpdateListener OH;
        private final RealTimeMessageReceivedListener OI;

        public RoomBinderCallbacks(GamesClientImpl gamesClientImpl, RoomUpdateListener roomCallbacks) {
            this.NE = gamesClientImpl;
            this.OG = (RoomUpdateListener) hm.b((Object) roomCallbacks, (Object) "Callbacks must not be null");
            this.OH = null;
            this.OI = null;
        }

        public RoomBinderCallbacks(GamesClientImpl gamesClientImpl, RoomUpdateListener roomCallbacks, RoomStatusUpdateListener roomStatusCallbacks, RealTimeMessageReceivedListener realTimeMessageReceivedCallbacks) {
            this.NE = gamesClientImpl;
            this.OG = (RoomUpdateListener) hm.b((Object) roomCallbacks, (Object) "Callbacks must not be null");
            this.OH = roomStatusCallbacks;
            this.OI = realTimeMessageReceivedCallbacks;
        }

        public void A(DataHolder dataHolder) {
            this.NE.a(new DisconnectedFromRoomCallback(this.NE, this.OH, dataHolder));
        }

        public void a(DataHolder dataHolder, String[] strArr) {
            this.NE.a(new PeerInvitedToRoomCallback(this.NE, this.OH, dataHolder, strArr));
        }

        public void b(DataHolder dataHolder, String[] strArr) {
            this.NE.a(new PeerJoinedRoomCallback(this.NE, this.OH, dataHolder, strArr));
        }

        public void c(DataHolder dataHolder, String[] strArr) {
            this.NE.a(new PeerLeftRoomCallback(this.NE, this.OH, dataHolder, strArr));
        }

        public void d(DataHolder dataHolder, String[] strArr) {
            this.NE.a(new PeerDeclinedCallback(this.NE, this.OH, dataHolder, strArr));
        }

        public void e(DataHolder dataHolder, String[] strArr) {
            this.NE.a(new PeerConnectedCallback(this.NE, this.OH, dataHolder, strArr));
        }

        public void f(DataHolder dataHolder, String[] strArr) {
            this.NE.a(new PeerDisconnectedCallback(this.NE, this.OH, dataHolder, strArr));
        }

        public void onLeftRoom(int statusCode, String externalRoomId) {
            this.NE.a(new LeftRoomCallback(this.NE, this.OG, statusCode, externalRoomId));
        }

        public void onP2PConnected(String participantId) {
            this.NE.a(new P2PConnectedCallback(this.NE, this.OH, participantId));
        }

        public void onP2PDisconnected(String participantId) {
            this.NE.a(new P2PDisconnectedCallback(this.NE, this.OH, participantId));
        }

        public void onRealTimeMessageReceived(RealTimeMessage message) {
            this.NE.a(new MessageReceivedCallback(this.NE, this.OI, message));
        }

        public void u(DataHolder dataHolder) {
            this.NE.a(new RoomCreatedCallback(this.NE, this.OG, dataHolder));
        }

        public void v(DataHolder dataHolder) {
            this.NE.a(new JoinedRoomCallback(this.NE, this.OG, dataHolder));
        }

        public void w(DataHolder dataHolder) {
            this.NE.a(new RoomConnectingCallback(this.NE, this.OH, dataHolder));
        }

        public void x(DataHolder dataHolder) {
            this.NE.a(new RoomAutoMatchingCallback(this.NE, this.OH, dataHolder));
        }

        public void y(DataHolder dataHolder) {
            this.NE.a(new RoomConnectedCallback(this.NE, this.OG, dataHolder));
        }

        public void z(DataHolder dataHolder) {
            this.NE.a(new ConnectedToRoomCallback(this.NE, this.OH, dataHolder));
        }
    }

    private final class RoomConnectedCallback extends AbstractRoomCallback {
        final /* synthetic */ GamesClientImpl NE;

        RoomConnectedCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomConnected(i, room);
        }
    }

    private final class RoomConnectingCallback extends AbstractRoomStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        RoomConnectingCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    private final class RoomCreatedCallback extends AbstractRoomCallback {
        final /* synthetic */ GamesClientImpl NE;

        public RoomCreatedCallback(GamesClientImpl gamesClientImpl, RoomUpdateListener listener, DataHolder dataHolder) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder);
        }

        public void a(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomCreated(i, room);
        }
    }

    private final class SignOutCompleteBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<Status> yR;

        public SignOutCompleteBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<Status> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void dT() {
            this.yR.a(new Status(0));
        }
    }

    private final class SnapshotCommittedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<CommitSnapshotResult> OJ;

        public SnapshotCommittedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<CommitSnapshotResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OJ = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void J(DataHolder dataHolder) {
            this.OJ.a(new CommitSnapshotResultImpl(dataHolder));
        }
    }

    final class SnapshotDeletedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<DeleteSnapshotResult> yR;

        public SnapshotDeletedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<DeleteSnapshotResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void g(int i, String str) {
            this.yR.a(new DeleteSnapshotResultImpl(i, str));
        }
    }

    private final class SnapshotOpenedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<OpenSnapshotResult> OK;

        public SnapshotOpenedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<OpenSnapshotResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OK = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void a(DataHolder dataHolder, Contents contents) {
            this.OK.a(new OpenSnapshotResultImpl(dataHolder, contents));
        }

        public void a(DataHolder dataHolder, String str, Contents contents, Contents contents2, Contents contents3) {
            this.OK.a(new OpenSnapshotResultImpl(dataHolder, str, contents, contents2, contents3));
        }
    }

    private final class SnapshotsLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadSnapshotsResult> OL;

        public SnapshotsLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LoadSnapshotsResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OL = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void I(DataHolder dataHolder) {
            this.OL.a(new LoadSnapshotsResultImpl(dataHolder));
        }
    }

    private final class SubmitScoreBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<SubmitScoreResult> yR;

        public SubmitScoreBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<SubmitScoreResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.yR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void f(DataHolder dataHolder) {
            this.yR.a(new SubmitScoreResultImpl(dataHolder));
        }
    }

    private final class TurnBasedMatchCanceledBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<CancelMatchResult> ON;

        public TurnBasedMatchCanceledBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<CancelMatchResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.ON = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void f(int i, String str) {
            this.ON.a(new CancelMatchResultImpl(new Status(i), str));
        }
    }

    private final class TurnBasedMatchInitiatedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<InitiateMatchResult> OO;

        public TurnBasedMatchInitiatedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<InitiateMatchResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OO = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void q(DataHolder dataHolder) {
            this.OO.a(new InitiateMatchResultImpl(dataHolder));
        }
    }

    private final class TurnBasedMatchLeftBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LeaveMatchResult> OP;

        public TurnBasedMatchLeftBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LeaveMatchResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OP = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void s(DataHolder dataHolder) {
            this.OP.a(new LeaveMatchResultImpl(dataHolder));
        }
    }

    private final class TurnBasedMatchLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadMatchResult> OQ;

        public TurnBasedMatchLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LoadMatchResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OQ = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void p(DataHolder dataHolder) {
            this.OQ.a(new LoadMatchResultImpl(dataHolder));
        }
    }

    private final class TurnBasedMatchUpdatedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<UpdateMatchResult> OR;

        public TurnBasedMatchUpdatedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<UpdateMatchResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OR = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void r(DataHolder dataHolder) {
            this.OR.a(new UpdateMatchResultImpl(dataHolder));
        }
    }

    private final class TurnBasedMatchesLoadedBinderCallbacks extends AbstractGamesCallbacks {
        final /* synthetic */ GamesClientImpl NE;
        private final a.d<LoadMatchesResult> OS;

        public TurnBasedMatchesLoadedBinderCallbacks(GamesClientImpl gamesClientImpl, a.d<LoadMatchesResult> resultHolder) {
            this.NE = gamesClientImpl;
            this.OS = (a.d) hm.b((Object) resultHolder, (Object) "Holder must not be null");
        }

        public void b(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.OS.a(new LoadMatchesResultImpl(new Status(i), bundle));
        }
    }

    private static final class UpdateMatchResultImpl extends TurnBasedMatchResult implements UpdateMatchResult {
        UpdateMatchResultImpl(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    private final class PeerConnectedCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        PeerConnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    private final class PeerDeclinedCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        PeerDeclinedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    private final class PeerDisconnectedCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        PeerDisconnectedCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    private final class PeerInvitedToRoomCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        PeerInvitedToRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    private final class PeerJoinedRoomCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        PeerJoinedRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    private final class PeerLeftRoomCallback extends AbstractPeerStatusCallback {
        final /* synthetic */ GamesClientImpl NE;

        PeerLeftRoomCallback(GamesClientImpl gamesClientImpl, RoomStatusUpdateListener listener, DataHolder dataHolder, String[] participantIds) {
            this.NE = gamesClientImpl;
            super(gamesClientImpl, listener, dataHolder, participantIds);
        }

        protected void a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    public GamesClientImpl(Context context, Looper looper, String gamePackageName, String accountName, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener, String[] scopes, int gravity, View gamesContentView, boolean isHeadless, boolean showConnectingPopup, int connectingPopupGravity, boolean retryingSignIn, int sdkVariant, String forceResolveAccountKey) {
        super(context, looper, connectedListener, connectionFailedListener, scopes);
        this.Nq = gamePackageName;
        this.yQ = (String) hm.f(accountName);
        this.Ny = new Binder();
        this.Nr = new HashMap();
        this.Nu = PopupManager.a(this, gravity);
        f(gamesContentView);
        this.Nw = showConnectingPopup;
        this.Nx = connectingPopupGravity;
        this.Nz = (long) hashCode();
        this.NA = isHeadless;
        this.NC = retryingSignIn;
        this.NB = sdkVariant;
        this.ND = forceResolveAccountKey;
        registerConnectionCallbacks((ConnectionCallbacks) this);
        registerConnectionFailedListener((OnConnectionFailedListener) this);
    }

    private Room Q(DataHolder dataHolder) {
        RoomBuffer roomBuffer = new RoomBuffer(dataHolder);
        Room room = null;
        try {
            if (roomBuffer.getCount() > 0) {
                room = (Room) ((Room) roomBuffer.get(0)).freeze();
            }
            roomBuffer.close();
            return room;
        } catch (Throwable th) {
            roomBuffer.close();
        }
    }

    private RealTimeSocket aT(String str) {
        try {
            ParcelFileDescriptor bb = ((IGamesService) ft()).bb(str);
            RealTimeSocket libjingleNativeSocket;
            if (bb != null) {
                GamesLog.i("GamesClientImpl", "Created native libjingle socket.");
                libjingleNativeSocket = new LibjingleNativeSocket(bb);
                this.Nr.put(str, libjingleNativeSocket);
                return libjingleNativeSocket;
            }
            GamesLog.i("GamesClientImpl", "Unable to create native libjingle socket, resorting to old socket.");
            String aW = ((IGamesService) ft()).aW(str);
            if (aW == null) {
                return null;
            }
            LocalSocket localSocket = new LocalSocket();
            try {
                localSocket.connect(new LocalSocketAddress(aW));
                libjingleNativeSocket = new RealTimeSocketImpl(localSocket, str);
                this.Nr.put(str, libjingleNativeSocket);
                return libjingleNativeSocket;
            } catch (IOException e) {
                GamesLog.k("GamesClientImpl", "connect() call failed on socket: " + e.getMessage());
                return null;
            }
        } catch (RemoteException e2) {
            GamesLog.k("GamesClientImpl", "Unable to create socket. Service died.");
            return null;
        }
    }

    private void gY() {
        this.Ns = null;
    }

    private void hv() {
        for (RealTimeSocket close : this.Nr.values()) {
            try {
                close.close();
            } catch (Throwable e) {
                GamesLog.b("GamesClientImpl", "IOException:", e);
            }
        }
        this.Nr.clear();
    }

    public int a(ReliableMessageSentCallback reliableMessageSentCallback, byte[] bArr, String str, String str2) {
        try {
            return ((IGamesService) ft()).a(new RealTimeReliableMessageBinderCallbacks(this, reliableMessageSentCallback), bArr, str, str2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int a(byte[] bArr, String str, String[] strArr) {
        hm.b((Object) strArr, (Object) "Participant IDs must not be null");
        try {
            return ((IGamesService) ft()).b(bArr, str, strArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public Intent a(int i, int i2, boolean z) {
        try {
            return ((IGamesService) ft()).a(i, i2, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(int i, byte[] bArr, int i2, Bitmap bitmap, String str) {
        try {
            Intent a = ((IGamesService) ft()).a(i, bArr, i2, str);
            hm.b((Object) bitmap, (Object) "Must provide a non null icon");
            a.putExtra("com.google.android.gms.games.REQUEST_ITEM_ICON", bitmap);
            return a;
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(Room room, int i) {
        try {
            return ((IGamesService) ft()).a((RoomEntity) room.freeze(), i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(String str, boolean z, boolean z2, int i) {
        try {
            return ((IGamesService) ft()).a(str, z, z2, i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent a(int[] iArr) {
        try {
            return ((IGamesService) ft()).a(iArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null) {
            this.Nv = bundle.getBoolean("show_welcome_popup");
        }
        super.a(i, iBinder, bundle);
    }

    public void a(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                ((IGamesService) ft()).a(iBinder, bundle);
            } catch (RemoteException e) {
                GamesLog.j("GamesClientImpl", "service died");
            }
        }
    }

    public void a(a.d<LoadRequestsResult> dVar, int i, int i2, int i3) {
        try {
            ((IGamesService) ft()).a(new RequestsLoadedBinderCallbacks(this, dVar), i, i2, i3);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadExtendedGamesResult> dVar, int i, int i2, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).a(new ExtendedGamesLoadedBinderCallback(this, dVar), i, i2, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).a(new PlayersLoadedBinderCallback(this, dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadMatchesResult> dVar, int i, int[] iArr) {
        try {
            ((IGamesService) ft()).a(new TurnBasedMatchesLoadedBinderCallbacks(this, dVar), i, iArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadScoresResult> dVar, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        try {
            ((IGamesService) ft()).a(new LeaderboardScoresLoadedBinderCallback(this, dVar), leaderboardScoreBuffer.iA().iB(), i, i2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<InitiateMatchResult> dVar, TurnBasedMatchConfig turnBasedMatchConfig) {
        try {
            ((IGamesService) ft()).a(new TurnBasedMatchInitiatedBinderCallbacks(this, dVar), turnBasedMatchConfig.getVariant(), turnBasedMatchConfig.iH(), turnBasedMatchConfig.getInvitedPlayerIds(), turnBasedMatchConfig.getAutoMatchCriteria());
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<CommitSnapshotResult> dVar, Snapshot snapshot, SnapshotMetadataChange snapshotMetadataChange) {
        Contents contents = snapshot.getContents();
        hm.b((Object) contents, (Object) "Must provide a previously opened Snapshot");
        com.google.android.gms.common.data.a iN = snapshotMetadataChange.iN();
        if (iN != null) {
            iN.a(getContext().getCacheDir());
        }
        snapshot.iM();
        try {
            ((IGamesService) ft()).a(new SnapshotCommittedBinderCallbacks(this, dVar), snapshot.getMetadata().getSnapshotId(), snapshotMetadataChange, contents);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, String str) {
        try {
            ((IGamesService) ft()).a(new PlayersLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<UpdateAchievementResult> dVar, String str, int i) {
        try {
            ((IGamesService) ft()).a(dVar == null ? null : new AchievementUpdatedBinderCallback(this, dVar), str, i, this.Nu.hN(), this.Nu.hM());
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadScoresResult> dVar, String str, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) ft()).a(new LeaderboardScoresLoadedBinderCallback(this, dVar), str, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, String str, int i, boolean z) {
        try {
            ((IGamesService) ft()).a(new PlayersLoadedBinderCallback(this, dVar), str, i, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, String str, int i, boolean z, boolean z2) {
        if (str.equals("played_with")) {
            try {
                ((IGamesService) ft()).d(new PlayersLoadedBinderCallback(this, dVar), str, i, z, z2);
                return;
            } catch (RemoteException e) {
                GamesLog.j("GamesClientImpl", "service died");
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player collection: " + str);
    }

    public void a(a.d<LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        try {
            ((IGamesService) ft()).a(new ExtendedGamesLoadedBinderCallback(this, dVar), str, i, z, z2, z3, z4);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadMatchesResult> dVar, String str, int i, int[] iArr) {
        try {
            ((IGamesService) ft()).a(new TurnBasedMatchesLoadedBinderCallbacks(this, dVar), str, i, iArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<SubmitScoreResult> dVar, String str, long j, String str2) {
        try {
            ((IGamesService) ft()).a(dVar == null ? null : new SubmitScoreBinderCallbacks(this, dVar), str, j, str2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LeaveMatchResult> dVar, String str, String str2) {
        try {
            ((IGamesService) ft()).c(new TurnBasedMatchLeftBinderCallbacks(this, dVar), str, str2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayerScoreResult> dVar, String str, String str2, int i, int i2) {
        try {
            ((IGamesService) ft()).a(new PlayerLeaderboardScoreLoadedBinderCallback(this, dVar), str, str2, i, i2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadRequestsResult> dVar, String str, String str2, int i, int i2, int i3) {
        try {
            ((IGamesService) ft()).a(new RequestsLoadedBinderCallbacks(this, dVar), str, str2, i, i2, i3);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadScoresResult> dVar, String str, String str2, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) ft()).a(new LeaderboardScoresLoadedBinderCallback(this, dVar), str, str2, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, String str, String str2, int i, boolean z, boolean z2) {
        if (str.equals("played_with") || str.equals("circled")) {
            try {
                ((IGamesService) ft()).a(new PlayersLoadedBinderCallback(this, dVar), str, str2, i, z, z2);
                return;
            } catch (RemoteException e) {
                GamesLog.j("GamesClientImpl", "service died");
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player collection: " + str);
    }

    public void a(a.d<OpenSnapshotResult> dVar, String str, String str2, SnapshotMetadataChange snapshotMetadataChange, Contents contents) {
        com.google.android.gms.common.data.a iN = snapshotMetadataChange.iN();
        if (iN != null) {
            iN.a(getContext().getCacheDir());
        }
        try {
            ((IGamesService) ft()).a(new SnapshotOpenedBinderCallbacks(this, dVar), str, str2, snapshotMetadataChange, contents);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LeaderboardMetadataResult> dVar, String str, String str2, boolean z) {
        try {
            ((IGamesService) ft()).b(new LeaderboardsLoadedBinderCallback(this, dVar), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadQuestsResult> dVar, String str, String str2, boolean z, String[] strArr) {
        try {
            ((IGamesService) ft()).a(new QuestsLoadedBinderCallbacks(this, dVar), str, str2, strArr, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadQuestsResult> dVar, String str, String str2, int[] iArr, int i, boolean z) {
        try {
            ((IGamesService) ft()).a(new QuestsLoadedBinderCallbacks(this, dVar), str, str2, iArr, i, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<UpdateRequestsResult> dVar, String str, String str2, String[] strArr) {
        try {
            ((IGamesService) ft()).a(new RequestsUpdatedBinderCallbacks(this, dVar), str, str2, strArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LeaderboardMetadataResult> dVar, String str, boolean z) {
        try {
            ((IGamesService) ft()).c(new LeaderboardsLoadedBinderCallback(this, dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<UpdateMatchResult> dVar, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        try {
            ((IGamesService) ft()).a(new TurnBasedMatchUpdatedBinderCallbacks(this, dVar), str, bArr, str2, participantResultArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<UpdateMatchResult> dVar, String str, byte[] bArr, ParticipantResult[] participantResultArr) {
        try {
            ((IGamesService) ft()).a(new TurnBasedMatchUpdatedBinderCallbacks(this, dVar), str, bArr, participantResultArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<SendRequestResult> dVar, String str, String[] strArr, int i, byte[] bArr, int i2) {
        try {
            ((IGamesService) ft()).a(new RequestSentBinderCallbacks(this, dVar), str, strArr, i, bArr, i2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, boolean z) {
        try {
            ((IGamesService) ft()).c(new PlayersLoadedBinderCallback(this, dVar), z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<Status> dVar, boolean z, Bundle bundle) {
        try {
            ((IGamesService) ft()).a(new ContactSettingsUpdatedBinderCallback(this, dVar), z, bundle);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadEventsResult> dVar, boolean z, String... strArr) {
        try {
            this.Np.flush();
            ((IGamesService) ft()).a(new EventsLoadedBinderCallback(this, dVar), z, strArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadQuestsResult> dVar, int[] iArr, int i, boolean z) {
        try {
            this.Np.flush();
            ((IGamesService) ft()).a(new QuestsLoadedBinderCallbacks(this, dVar), iArr, i, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(a.d<LoadPlayersResult> dVar, String[] strArr) {
        try {
            ((IGamesService) ft()).c(new PlayersLoadedBinderCallback(this, dVar), strArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(OnInvitationReceivedListener onInvitationReceivedListener) {
        try {
            ((IGamesService) ft()).a(new InvitationReceivedBinderCallback(this, onInvitationReceivedListener), this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(RoomConfig roomConfig) {
        try {
            ((IGamesService) ft()).a(new RoomBinderCallbacks(this, roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), this.Ny, roomConfig.getVariant(), roomConfig.getInvitedPlayerIds(), roomConfig.getAutoMatchCriteria(), roomConfig.isSocketEnabled(), this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(RoomUpdateListener roomUpdateListener, String str) {
        try {
            ((IGamesService) ft()).c(new RoomBinderCallbacks(this, roomUpdateListener), str);
            hv();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(OnTurnBasedMatchUpdateReceivedListener onTurnBasedMatchUpdateReceivedListener) {
        try {
            ((IGamesService) ft()).b(new MatchUpdateReceivedBinderCallback(this, onTurnBasedMatchUpdateReceivedListener), this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(QuestUpdateListener questUpdateListener) {
        try {
            ((IGamesService) ft()).d(new QuestUpdateBinderCallback(this, questUpdateListener), this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(OnRequestReceivedListener onRequestReceivedListener) {
        try {
            ((IGamesService) ft()).c(new RequestReceivedBinderCallback(this, onRequestReceivedListener), this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void a(Snapshot snapshot) {
        Contents contents = snapshot.getContents();
        hm.b((Object) contents, (Object) "Must provide a previously opened Snapshot");
        snapshot.iM();
        try {
            ((IGamesService) ft()).a(contents);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    protected void a(hi hiVar, e eVar) throws RemoteException {
        String locale = getContext().getResources().getConfiguration().locale.toString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.NA);
        bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.Nw);
        bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.Nx);
        bundle.putBoolean("com.google.android.gms.games.key.retryingSignIn", this.NC);
        bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.NB);
        bundle.putString("com.google.android.gms.games.key.forceResolveAccountKey", this.ND);
        hiVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.yQ, fs(), this.Nq, this.Nu.hN(), locale, bundle);
    }

    public Intent aR(String str) {
        try {
            return ((IGamesService) ft()).aR(str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public void aS(String str) {
        try {
            ((IGamesService) ft()).ba(str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public Intent aU(String str) {
        try {
            return ((IGamesService) ft()).aU(str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    protected IGamesService ah(IBinder iBinder) {
        return Stub.aj(iBinder);
    }

    public Intent b(int i, int i2, boolean z) {
        try {
            return ((IGamesService) ft()).b(i, i2, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public void b(a.d<Status> dVar) {
        try {
            this.Np.flush();
            ((IGamesService) ft()).a(new SignOutCompleteBinderCallbacks(this, dVar));
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).b(new PlayersLoadedBinderCallback(this, dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<UpdateAchievementResult> dVar, String str) {
        if (dVar == null) {
            IGamesCallbacks iGamesCallbacks = null;
        } else {
            Object achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(this, dVar);
        }
        try {
            ((IGamesService) ft()).a(iGamesCallbacks, str, this.Nu.hN(), this.Nu.hM());
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<UpdateAchievementResult> dVar, String str, int i) {
        try {
            ((IGamesService) ft()).b(dVar == null ? null : new AchievementUpdatedBinderCallback(this, dVar), str, i, this.Nu.hN(), this.Nu.hM());
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LoadScoresResult> dVar, String str, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) ft()).b(new LeaderboardScoresLoadedBinderCallback(this, dVar), str, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).a(new ExtendedGamesLoadedBinderCallback(this, dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<ClaimMilestoneResult> dVar, String str, String str2) {
        try {
            this.Np.flush();
            ((IGamesService) ft()).f(new QuestMilestoneClaimBinderCallbacks(this, dVar, str2), str, str2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LoadScoresResult> dVar, String str, String str2, int i, int i2, int i3, boolean z) {
        try {
            ((IGamesService) ft()).b(new LeaderboardScoresLoadedBinderCallback(this, dVar), str, str2, i, i2, i3, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LoadAchievementsResult> dVar, String str, String str2, boolean z) {
        try {
            ((IGamesService) ft()).a(new AchievementsLoadedBinderCallback(this, dVar), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<OpenSnapshotResult> dVar, String str, boolean z) {
        try {
            ((IGamesService) ft()).e(new SnapshotOpenedBinderCallbacks(this, dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LeaderboardMetadataResult> dVar, boolean z) {
        try {
            ((IGamesService) ft()).b(new LeaderboardsLoadedBinderCallback(this, dVar), z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<LoadQuestsResult> dVar, boolean z, String[] strArr) {
        try {
            ((IGamesService) ft()).a(new QuestsLoadedBinderCallbacks(this, dVar), strArr, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(a.d<UpdateRequestsResult> dVar, String[] strArr) {
        try {
            ((IGamesService) ft()).a(new RequestsUpdatedBinderCallbacks(this, dVar), strArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void b(RoomConfig roomConfig) {
        try {
            ((IGamesService) ft()).a(new RoomBinderCallbacks(this, roomConfig.getRoomUpdateListener(), roomConfig.getRoomStatusUpdateListener(), roomConfig.getMessageReceivedListener()), this.Ny, roomConfig.getInvitationId(), roomConfig.isSocketEnabled(), this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    protected void b(String... strArr) {
        int i = 0;
        boolean z = false;
        for (String str : strArr) {
            if (str.equals(Scopes.GAMES)) {
                z = true;
            } else if (str.equals("https://www.googleapis.com/auth/games.firstparty")) {
                i = 1;
            }
        }
        if (i != 0) {
            hm.a(!z, "Cannot have both %s and %s!", Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty");
            return;
        }
        hm.a(z, "Games APIs requires %s to function.", Scopes.GAMES);
    }

    protected String bu() {
        return "com.google.android.gms.games.service.START";
    }

    protected String bv() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public void c(a.d<LoadInvitationsResult> dVar, int i) {
        try {
            ((IGamesService) ft()).a(new InvitationsLoadedBinderCallback(this, dVar), i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).c(new PlayersLoadedBinderCallback(this, dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<UpdateAchievementResult> dVar, String str) {
        if (dVar == null) {
            IGamesCallbacks iGamesCallbacks = null;
        } else {
            Object achievementUpdatedBinderCallback = new AchievementUpdatedBinderCallback(this, dVar);
        }
        try {
            ((IGamesService) ft()).b(iGamesCallbacks, str, this.Nu.hN(), this.Nu.hM());
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<LoadXpStreamResult> dVar, String str, int i) {
        try {
            ((IGamesService) ft()).b(new PlayerXpStreamLoadedBinderCallback(this, dVar), str, i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).e(new ExtendedGamesLoadedBinderCallback(this, dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<InitiateMatchResult> dVar, String str, String str2) {
        try {
            ((IGamesService) ft()).d(new TurnBasedMatchInitiatedBinderCallbacks(this, dVar), str, str2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<LoadSnapshotsResult> dVar, String str, String str2, boolean z) {
        try {
            ((IGamesService) ft()).c(new SnapshotsLoadedBinderCallbacks(this, dVar), str, str2, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<LeaderboardMetadataResult> dVar, String str, boolean z) {
        try {
            ((IGamesService) ft()).d(new LeaderboardsLoadedBinderCallback(this, dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<LoadAchievementsResult> dVar, boolean z) {
        try {
            ((IGamesService) ft()).a(new AchievementsLoadedBinderCallback(this, dVar), z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void c(a.d<UpdateRequestsResult> dVar, String[] strArr) {
        try {
            ((IGamesService) ft()).b(new RequestsUpdatedBinderCallbacks(this, dVar), strArr);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void cg(int i) {
        this.Nu.setGravity(i);
    }

    public void ch(int i) {
        try {
            ((IGamesService) ft()).ch(i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void connect() {
        gY();
        super.connect();
    }

    public int d(byte[] bArr, String str) {
        try {
            return ((IGamesService) ft()).b(bArr, str, null);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public void d(a.d<LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).e(new PlayersLoadedBinderCallback(this, dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<InitiateMatchResult> dVar, String str) {
        try {
            ((IGamesService) ft()).l(new TurnBasedMatchInitiatedBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<LoadXpStreamResult> dVar, String str, int i) {
        try {
            ((IGamesService) ft()).c(new PlayerXpStreamLoadedBinderCallback(this, dVar), str, i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).f(new ExtendedGamesLoadedBinderCallback(this, dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<InitiateMatchResult> dVar, String str, String str2) {
        try {
            ((IGamesService) ft()).e(new TurnBasedMatchInitiatedBinderCallbacks(this, dVar), str, str2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<GameMuteStatusChangeResult> dVar, String str, boolean z) {
        try {
            ((IGamesService) ft()).a(new GameMuteStatusChangedBinderCallback(this, dVar), str, z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void d(a.d<LoadEventsResult> dVar, boolean z) {
        try {
            this.Np.flush();
            ((IGamesService) ft()).f(new EventsLoadedBinderCallback(this, dVar), z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void disconnect() {
        this.Nv = false;
        if (isConnected()) {
            try {
                IGamesService iGamesService = (IGamesService) ft();
                iGamesService.hw();
                this.Np.flush();
                iGamesService.q(this.Nz);
            } catch (RemoteException e) {
                GamesLog.j("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        hv();
        super.disconnect();
    }

    public void e(a.d<LoadPlayersResult> dVar, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).d(new PlayersLoadedBinderCallback(this, dVar), i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void e(a.d<InitiateMatchResult> dVar, String str) {
        try {
            ((IGamesService) ft()).m(new TurnBasedMatchInitiatedBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void e(a.d<LoadInvitationsResult> dVar, String str, int i) {
        try {
            ((IGamesService) ft()).b(new InvitationsLoadedBinderCallback(this, dVar), str, i, false);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void e(a.d<LoadExtendedGamesResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).c(new ExtendedGamesLoadedBinderCallback(this, dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void e(a.d<LoadSnapshotsResult> dVar, boolean z) {
        try {
            ((IGamesService) ft()).d(new SnapshotsLoadedBinderCallbacks(this, dVar), z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public Bundle ef() {
        try {
            Bundle ef = ((IGamesService) ft()).ef();
            if (ef == null) {
                return ef;
            }
            ef.setClassLoader(GamesClientImpl.class.getClassLoader());
            return ef;
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public void f(View view) {
        this.Nu.g(view);
    }

    public void f(a.d<LoadGamesResult> dVar) {
        try {
            ((IGamesService) ft()).d(new GamesLoadedBinderCallback(this, dVar));
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void f(a.d<LeaveMatchResult> dVar, String str) {
        try {
            ((IGamesService) ft()).o(new TurnBasedMatchLeftBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void f(a.d<LoadRequestSummariesResult> dVar, String str, int i) {
        try {
            ((IGamesService) ft()).a(new RequestSummariesLoadedBinderCallbacks(this, dVar), str, i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void f(a.d<LoadPlayersResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).b(new PlayersLoadedBinderCallback(this, dVar), str, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void f(a.d<ContactSettingLoadResult> dVar, boolean z) {
        try {
            ((IGamesService) ft()).e(new ContactSettingsLoadedBinderCallback(this, dVar), z);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void g(a.d<LoadOwnerCoverPhotoUrisResult> dVar) {
        try {
            ((IGamesService) ft()).j(new OwnerCoverPhotoUrisLoadedBinderCallback(this, dVar));
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void g(a.d<CancelMatchResult> dVar, String str) {
        try {
            ((IGamesService) ft()).n(new TurnBasedMatchCanceledBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void g(a.d<LoadPlayersResult> dVar, String str, int i, boolean z, boolean z2) {
        try {
            ((IGamesService) ft()).b(new PlayersLoadedBinderCallback(this, dVar), str, null, i, z, z2);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public String gZ() {
        try {
            return ((IGamesService) ft()).gZ();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public void h(a.d<LoadAclResult> dVar) {
        try {
            ((IGamesService) ft()).h(new NotifyAclLoadedBinderCallback(this, dVar));
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void h(a.d<LoadMatchResult> dVar, String str) {
        try {
            ((IGamesService) ft()).p(new TurnBasedMatchLoadedBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public String ha() {
        try {
            return ((IGamesService) ft()).ha();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Player hb() {
        cn();
        synchronized (this) {
            if (this.Ns == null) {
                PlayerBuffer playerBuffer;
                try {
                    playerBuffer = new PlayerBuffer(((IGamesService) ft()).hy());
                    if (playerBuffer.getCount() > 0) {
                        this.Ns = (PlayerEntity) playerBuffer.get(0).freeze();
                    }
                    playerBuffer.close();
                } catch (RemoteException e) {
                    GamesLog.j("GamesClientImpl", "service died");
                } catch (Throwable th) {
                    playerBuffer.close();
                }
            }
        }
        return this.Ns;
    }

    public Game hc() {
        cn();
        synchronized (this) {
            if (this.Nt == null) {
                GameBuffer gameBuffer;
                try {
                    gameBuffer = new GameBuffer(((IGamesService) ft()).hA());
                    if (gameBuffer.getCount() > 0) {
                        this.Nt = (GameEntity) gameBuffer.get(0).freeze();
                    }
                    gameBuffer.close();
                } catch (RemoteException e) {
                    GamesLog.j("GamesClientImpl", "service died");
                } catch (Throwable th) {
                    gameBuffer.close();
                }
            }
        }
        return this.Nt;
    }

    public Intent hd() {
        try {
            return ((IGamesService) ft()).hd();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent he() {
        try {
            return ((IGamesService) ft()).he();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent hf() {
        try {
            return ((IGamesService) ft()).hf();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent hg() {
        try {
            return ((IGamesService) ft()).hg();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public void hh() {
        try {
            ((IGamesService) ft()).r(this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void hi() {
        try {
            ((IGamesService) ft()).s(this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void hj() {
        try {
            ((IGamesService) ft()).u(this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void hk() {
        try {
            ((IGamesService) ft()).t(this.Nz);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public Intent hl() {
        try {
            return ((IGamesService) ft()).hl();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public Intent hm() {
        try {
            return ((IGamesService) ft()).hm();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public int hn() {
        try {
            return ((IGamesService) ft()).hn();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return 4368;
        }
    }

    public String ho() {
        try {
            return ((IGamesService) ft()).ho();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public int hp() {
        try {
            return ((IGamesService) ft()).hp();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public Intent hq() {
        try {
            return ((IGamesService) ft()).hq();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return null;
        }
    }

    public int hr() {
        try {
            return ((IGamesService) ft()).hr();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int hs() {
        try {
            return ((IGamesService) ft()).hs();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int ht() {
        try {
            return ((IGamesService) ft()).ht();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public int hu() {
        try {
            return ((IGamesService) ft()).hu();
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
            return -1;
        }
    }

    public void hw() {
        if (isConnected()) {
            try {
                ((IGamesService) ft()).hw();
            } catch (RemoteException e) {
                GamesLog.j("GamesClientImpl", "service died");
            }
        }
    }

    @Deprecated
    public void i(a.d<ContactSettingLoadResult> dVar) {
        try {
            ((IGamesService) ft()).e(new ContactSettingsLoadedBinderCallback(this, dVar), false);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void i(a.d<AcceptQuestResult> dVar, String str) {
        try {
            this.Np.flush();
            ((IGamesService) ft()).u(new QuestAcceptedBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void j(a.d<InboxCountResult> dVar) {
        try {
            ((IGamesService) ft()).t(new InboxCountsLoadedBinderCallback(this, dVar), null);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void j(a.d<DeleteSnapshotResult> dVar, String str) {
        try {
            ((IGamesService) ft()).r(new SnapshotDeletedBinderCallbacks(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void k(a.d<LoadExtendedGamesResult> dVar, String str) {
        try {
            ((IGamesService) ft()).e(new ExtendedGamesLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public RealTimeSocket l(String str, String str2) {
        if (str2 == null || !ParticipantUtils.bn(str2)) {
            throw new IllegalArgumentException("Bad participant ID");
        }
        RealTimeSocket realTimeSocket = (RealTimeSocket) this.Nr.get(str2);
        return (realTimeSocket == null || realTimeSocket.isClosed()) ? aT(str2) : realTimeSocket;
    }

    public void l(a.d<LoadGameInstancesResult> dVar, String str) {
        try {
            ((IGamesService) ft()).f(new GameInstancesLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void l(String str, int i) {
        this.Np.l(str, i);
    }

    public void m(a.d<LoadGameSearchSuggestionsResult> dVar, String str) {
        try {
            ((IGamesService) ft()).q(new GameSearchSuggestionsLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void m(String str, int i) {
        try {
            ((IGamesService) ft()).m(str, i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void n(a.d<LoadXpForGameCategoriesResult> dVar, String str) {
        try {
            ((IGamesService) ft()).s(new PlayerXpForGameCategoriesLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void n(String str, int i) {
        try {
            ((IGamesService) ft()).n(str, i);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void o(a.d<LoadInvitationsResult> dVar, String str) {
        try {
            ((IGamesService) ft()).k(new InvitationsLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void onConnected(Bundle connectionHint) {
        if (this.Nv) {
            this.Nu.hL();
            this.Nv = false;
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        this.Nv = false;
    }

    public void onConnectionSuspended(int cause) {
    }

    public void p(a.d<Status> dVar, String str) {
        try {
            ((IGamesService) ft()).j(new NotifyAclUpdatedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    public void q(a.d<GameMuteStatusLoadResult> dVar, String str) {
        try {
            ((IGamesService) ft()).i(new GameMuteStatusLoadedBinderCallback(this, dVar), str);
        } catch (RemoteException e) {
            GamesLog.j("GamesClientImpl", "service died");
        }
    }

    protected /* synthetic */ IInterface x(IBinder iBinder) {
        return ah(iBinder);
    }
}
