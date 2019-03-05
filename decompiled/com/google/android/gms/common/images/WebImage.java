package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import app.asu.SettingsActivity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.hk;
import com.google.android.gms.plus.PlusShare;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage implements SafeParcelable {
    public static final Creator<WebImage> CREATOR = new b();
    private final Uri Fu;
    private final int ku;
    private final int kv;
    private final int xM;

    WebImage(int versionCode, Uri url, int width, int height) {
        this.xM = versionCode;
        this.Fu = url;
        this.ku = width;
        this.kv = height;
    }

    public WebImage(Uri url) throws IllegalArgumentException {
        this(url, 0, 0);
    }

    public WebImage(Uri url, int width, int height) throws IllegalArgumentException {
        this(1, url, width, height);
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (width < 0 || height < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject json) throws IllegalArgumentException {
        this(c(json), json.optInt("width", 0), json.optInt(SettingsActivity.HEIGHT, 0));
    }

    private static Uri c(JSONObject jSONObject) {
        Uri uri = null;
        if (jSONObject.has(PlusShare.KEY_CALL_TO_ACTION_URL)) {
            try {
                uri = Uri.parse(jSONObject.getString(PlusShare.KEY_CALL_TO_ACTION_URL));
            } catch (JSONException e) {
            }
        }
        return uri;
    }

    public JSONObject dZ() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PlusShare.KEY_CALL_TO_ACTION_URL, this.Fu.toString());
            jSONObject.put("width", this.ku);
            jSONObject.put(SettingsActivity.HEIGHT, this.kv);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) other;
        return hk.equal(this.Fu, webImage.Fu) && this.ku == webImage.ku && this.kv == webImage.kv;
    }

    public int getHeight() {
        return this.kv;
    }

    public Uri getUrl() {
        return this.Fu;
    }

    int getVersionCode() {
        return this.xM;
    }

    public int getWidth() {
        return this.ku;
    }

    public int hashCode() {
        return hk.hashCode(this.Fu, Integer.valueOf(this.ku), Integer.valueOf(this.kv));
    }

    public String toString() {
        return String.format("Image %dx%d %s", new Object[]{Integer.valueOf(this.ku), Integer.valueOf(this.kv), this.Fu.toString()});
    }

    public void writeToParcel(Parcel out, int flags) {
        b.a(this, out, flags);
    }
}
