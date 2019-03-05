package com.google.android.gms.tagmanager;

import cz.msebera.android.httpclient.HttpStatus;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class aw implements bl {
    private HttpURLConnection afP;

    aw() {
    }

    private InputStream a(HttpURLConnection httpURLConnection) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpStatus.SC_OK) {
            return httpURLConnection.getInputStream();
        }
        String str = "Bad response: " + responseCode;
        if (responseCode == 404) {
            throw new FileNotFoundException(str);
        }
        throw new IOException(str);
    }

    private void b(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    public InputStream bV(String str) throws IOException {
        this.afP = bW(str);
        return a(this.afP);
    }

    HttpURLConnection bW(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        return httpURLConnection;
    }

    public void close() {
        b(this.afP);
    }
}
