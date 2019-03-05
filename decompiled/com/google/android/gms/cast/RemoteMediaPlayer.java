package com.google.android.gms.cast;

import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.gh;
import com.google.android.gms.internal.go;
import com.google.android.gms.internal.gp;
import com.google.android.gms.internal.gq;
import java.io.IOException;
import org.json.JSONObject;

public class RemoteMediaPlayer implements MessageReceivedCallback {
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_REPLACED = 4;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 3;
    private final go AW = new go(this) {
        final /* synthetic */ RemoteMediaPlayer Ba;

        {
            this.Ba = r1;
        }

        protected void onMetadataUpdated() {
            this.Ba.onMetadataUpdated();
        }

        protected void onStatusUpdated() {
            this.Ba.onStatusUpdated();
        }
    };
    private final a AX = new a(this);
    private OnMetadataUpdatedListener AY;
    private OnStatusUpdatedListener AZ;
    private final Object ls = new Object();

    public interface OnMetadataUpdatedListener {
        void onMetadataUpdated();
    }

    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    public interface MediaChannelResult extends Result {
        JSONObject getCustomData();
    }

    private class a implements gp {
        final /* synthetic */ RemoteMediaPlayer Ba;
        private GoogleApiClient Bm;
        private long Bn = 0;

        private final class a implements ResultCallback<Status> {
            private final long Bo;
            final /* synthetic */ a Bp;

            a(a aVar, long j) {
                this.Bp = aVar;
                this.Bo = j;
            }

            public void k(Status status) {
                if (!status.isSuccess()) {
                    this.Bp.Ba.AW.a(this.Bo, status.getStatusCode());
                }
            }

            public /* synthetic */ void onResult(Result x0) {
                k((Status) x0);
            }
        }

        public a(RemoteMediaPlayer remoteMediaPlayer) {
            this.Ba = remoteMediaPlayer;
        }

        public void a(String str, String str2, long j, String str3) throws IOException {
            if (this.Bm == null) {
                throw new IOException("No GoogleApiClient available");
            }
            Cast.CastApi.sendMessage(this.Bm, str, str2).setResultCallback(new a(this, j));
        }

        public void b(GoogleApiClient googleApiClient) {
            this.Bm = googleApiClient;
        }

        public long eb() {
            long j = this.Bn + 1;
            this.Bn = j;
            return j;
        }
    }

    private static final class c implements MediaChannelResult {
        private final JSONObject AA;
        private final Status yz;

        c(Status status, JSONObject jSONObject) {
            this.yz = status;
            this.AA = jSONObject;
        }

        public JSONObject getCustomData() {
            return this.AA;
        }

        public Status getStatus() {
            return this.yz;
        }
    }

    private static abstract class b extends a<MediaChannelResult> {
        gq Bq = new gq(this) {
            final /* synthetic */ b Br;

            {
                this.Br = r1;
            }

            public void a(long j, int i, JSONObject jSONObject) {
                this.Br.b(new c(new Status(i), jSONObject));
            }

            public void n(long j) {
                this.Br.b(this.Br.l(new Status(4)));
            }
        };

        b() {
        }

        public /* synthetic */ Result c(Status status) {
            return l(status);
        }

        public MediaChannelResult l(final Status status) {
            return new MediaChannelResult(this) {
                final /* synthetic */ b Br;

                public JSONObject getCustomData() {
                    return null;
                }

                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    public RemoteMediaPlayer() {
        this.AW.a(this.AX);
    }

    private void onMetadataUpdated() {
        if (this.AY != null) {
            this.AY.onMetadataUpdated();
        }
    }

    private void onStatusUpdated() {
        if (this.AZ != null) {
            this.AZ.onStatusUpdated();
        }
    }

    public long getApproximateStreamPosition() {
        long approximateStreamPosition;
        synchronized (this.ls) {
            approximateStreamPosition = this.AW.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.ls) {
            mediaInfo = this.AW.getMediaInfo();
        }
        return mediaInfo;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.ls) {
            mediaStatus = this.AW.getMediaStatus();
        }
        return mediaStatus;
    }

    public String getNamespace() {
        return this.AW.getNamespace();
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.ls) {
            streamDuration = this.AW.getStreamDuration();
        }
        return streamDuration;
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo) {
        return load(apiClient, mediaInfo, true, 0, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay) {
        return load(apiClient, mediaInfo, autoplay, 0, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition) {
        return load(apiClient, mediaInfo, autoplay, playPosition, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition, JSONObject customData) {
        final GoogleApiClient googleApiClient = apiClient;
        final MediaInfo mediaInfo2 = mediaInfo;
        final boolean z = autoplay;
        final long j = playPosition;
        final JSONObject jSONObject = customData;
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(googleApiClient);
                    try {
                        this.Ba.AW.a(this.Bq, mediaInfo2, z, j, jSONObject);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        this.AW.ai(message);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient apiClient) {
        return pause(apiClient, null);
    }

    public PendingResult<MediaChannelResult> pause(final GoogleApiClient apiClient, final JSONObject customData) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.a(this.Bq, customData);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient apiClient) {
        return play(apiClient, null);
    }

    public PendingResult<MediaChannelResult> play(final GoogleApiClient apiClient, final JSONObject customData) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.c(this.Bq, customData);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> requestStatus(final GoogleApiClient apiClient) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.a(this.Bq);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position) {
        return seek(apiClient, position, 0, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState) {
        return seek(apiClient, position, resumeState, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState, JSONObject customData) {
        final GoogleApiClient googleApiClient = apiClient;
        final long j = position;
        final int i = resumeState;
        final JSONObject jSONObject = customData;
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(googleApiClient);
                    try {
                        this.Ba.AW.a(this.Bq, j, i, jSONObject);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> setActiveMediaTracks(final GoogleApiClient apiClient, final long[] trackIds) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.a(this.Bq, trackIds);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener listener) {
        this.AY = listener;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener listener) {
        this.AZ = listener;
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient apiClient, boolean muteState) {
        return setStreamMute(apiClient, muteState, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(final GoogleApiClient apiClient, final boolean muteState, final JSONObject customData) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.a(this.Bq, muteState, customData);
                        this.Ba.AX.b(null);
                    } catch (IllegalStateException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (IOException e2) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume) throws IllegalArgumentException {
        return setStreamVolume(apiClient, volume, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume, JSONObject customData) throws IllegalArgumentException {
        if (Double.isInfinite(volume) || Double.isNaN(volume)) {
            throw new IllegalArgumentException("Volume cannot be " + volume);
        }
        final GoogleApiClient googleApiClient = apiClient;
        final double d = volume;
        final JSONObject jSONObject = customData;
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(googleApiClient);
                    try {
                        this.Ba.AW.a(this.Bq, d, jSONObject);
                        this.Ba.AX.b(null);
                    } catch (IllegalStateException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (IllegalArgumentException e2) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (IOException e3) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> setTextTrackStyle(final GoogleApiClient apiClient, final TextTrackStyle trackStyle) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.a(this.Bq, trackStyle);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient apiClient) {
        return stop(apiClient, null);
    }

    public PendingResult<MediaChannelResult> stop(final GoogleApiClient apiClient, final JSONObject customData) {
        return apiClient.b(new b(this) {
            final /* synthetic */ RemoteMediaPlayer Ba;

            protected void a(gh ghVar) {
                synchronized (this.Ba.ls) {
                    this.Ba.AX.b(apiClient);
                    try {
                        this.Ba.AW.b(this.Bq, customData);
                        this.Ba.AX.b(null);
                    } catch (IOException e) {
                        b(l(new Status(1)));
                        this.Ba.AX.b(null);
                    } catch (Throwable th) {
                        this.Ba.AX.b(null);
                    }
                }
            }
        });
    }
}
