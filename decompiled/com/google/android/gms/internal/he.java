package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class he {
    private static final Uri GG = Uri.parse("http://plus.google.com/");
    private static final Uri GH = GG.buildUpon().appendPath("circles").appendPath("find").build();

    public static Intent aB(String str) {
        Uri fromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    private static Uri aC(String str) {
        return Uri.parse("market://details").buildUpon().appendQueryParameter("id", str).build();
    }

    public static Intent aD(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(aC(str));
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
        intent.addFlags(524288);
        return intent;
    }

    public static Intent fA() {
        return new Intent("android.settings.DATE_SETTINGS");
    }
}
