package com.loopj.android.http;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.util.Log;

public class LogHandler implements LogInterface {
    boolean mLoggingEnabled = true;
    int mLoggingLevel = 2;

    public boolean isLoggingEnabled() {
        return this.mLoggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.mLoggingEnabled = loggingEnabled;
    }

    public int getLoggingLevel() {
        return this.mLoggingLevel;
    }

    public void setLoggingLevel(int loggingLevel) {
        this.mLoggingLevel = loggingLevel;
    }

    public boolean shouldLog(int logLevel) {
        return logLevel >= this.mLoggingLevel;
    }

    public void log(int logLevel, String tag, String msg) {
        logWithThrowable(logLevel, tag, msg, null);
    }

    public void logWithThrowable(int logLevel, String tag, String msg, Throwable t) {
        if (isLoggingEnabled() && shouldLog(logLevel)) {
            switch (logLevel) {
                case 2:
                    Log.v(tag, msg, t);
                    return;
                case 3:
                    Log.d(tag, msg, t);
                    return;
                case 4:
                    Log.i(tag, msg, t);
                    return;
                case 5:
                    Log.w(tag, msg, t);
                    return;
                case 6:
                    Log.e(tag, msg, t);
                    return;
                case 8:
                    if (Integer.valueOf(VERSION.SDK).intValue() > 8) {
                        checkedWtf(tag, msg, t);
                        return;
                    } else {
                        Log.e(tag, msg, t);
                        return;
                    }
                default:
                    return;
            }
        }
    }

    @TargetApi(8)
    private void checkedWtf(String tag, String msg, Throwable t) {
        Log.wtf(tag, msg, t);
    }

    public void v(String tag, String msg) {
        log(2, tag, msg);
    }

    public void v(String tag, String msg, Throwable t) {
        logWithThrowable(2, tag, msg, t);
    }

    public void d(String tag, String msg) {
        log(2, tag, msg);
    }

    public void d(String tag, String msg, Throwable t) {
        logWithThrowable(3, tag, msg, t);
    }

    public void i(String tag, String msg) {
        log(4, tag, msg);
    }

    public void i(String tag, String msg, Throwable t) {
        logWithThrowable(4, tag, msg, t);
    }

    public void w(String tag, String msg) {
        log(5, tag, msg);
    }

    public void w(String tag, String msg, Throwable t) {
        logWithThrowable(5, tag, msg, t);
    }

    public void e(String tag, String msg) {
        log(6, tag, msg);
    }

    public void e(String tag, String msg, Throwable t) {
        logWithThrowable(6, tag, msg, t);
    }

    public void wtf(String tag, String msg) {
        log(8, tag, msg);
    }

    public void wtf(String tag, String msg, Throwable t) {
        logWithThrowable(8, tag, msg, t);
    }
}
