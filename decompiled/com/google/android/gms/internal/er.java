package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebSettings;

public final class er {
    public static void a(Context context, WebSettings webSettings) {
        eq.a(context, webSettings);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
    }

    public static String getDefaultUserAgent(Context context) {
        return WebSettings.getDefaultUserAgent(context);
    }
}
