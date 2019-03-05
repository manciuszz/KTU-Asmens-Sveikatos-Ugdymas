package com.androidplot.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import com.androidplot.Plot;
import com.androidplot.PlotListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class PlotStatistics implements PlotListener {
    private boolean annotatePlotEnabled;
    String annotationString = "";
    long avgRenderTimeMs;
    long fps;
    long lastAnnotation;
    long lastLatency = 0;
    long lastStart = 0;
    long latencySamples = 0;
    long latencySum = 0;
    long longestRenderMs = 0;
    long maxRenderTimeMs;
    long minRenderTimeMs;
    private Paint paint = new Paint();
    long shortestRenderMs = 0;
    long updateDelayMs;

    public PlotStatistics(long updateDelayMs, boolean annotatePlotEnabled) {
        this.paint.setTextAlign(Align.CENTER);
        this.paint.setColor(-1);
        this.paint.setTextSize(BitmapDescriptorFactory.HUE_ORANGE);
        resetCounters();
        this.updateDelayMs = updateDelayMs;
        this.annotatePlotEnabled = annotatePlotEnabled;
    }

    public void setAnnotatePlotEnabled(boolean enabled) {
        this.annotatePlotEnabled = enabled;
    }

    private void resetCounters() {
        this.longestRenderMs = 0;
        this.shortestRenderMs = 999999999;
        this.latencySamples = 0;
        this.latencySum = 0;
    }

    private void annotatePlot(Plot source, Canvas canvas) {
        long nowMs = System.currentTimeMillis();
        long msSinceUpdate = nowMs - this.lastAnnotation;
        if (msSinceUpdate >= this.updateDelayMs) {
            float avgLatency = this.latencySamples > 0 ? (float) (this.latencySum / this.latencySamples) : 0.0f;
            String str = "%.2f";
            Object[] objArr = new Object[1];
            objArr[0] = Float.valueOf(this.latencySamples > 0 ? (1000.0f / ((float) msSinceUpdate)) * ((float) this.latencySamples) : 0.0f);
            String overallFPS = String.format(str, objArr);
            str = "%.2f";
            objArr = new Object[1];
            objArr[0] = Float.valueOf(this.latencySamples > 0 ? 1000.0f / avgLatency : 0.0f);
            this.annotationString = "FPS (potential): " + String.format(str, objArr) + " FPS (actual): " + overallFPS + " Latency (ms) Avg: " + this.lastLatency + " \nMin: " + this.shortestRenderMs + " Max: " + this.longestRenderMs;
            this.lastAnnotation = nowMs;
            resetCounters();
        }
        RectF r = source.getDisplayDimensions().canvasRect;
        if (this.annotatePlotEnabled) {
            canvas.drawText(this.annotationString, r.centerX(), r.centerY(), this.paint);
        }
    }

    public void onBeforeDraw(Plot source, Canvas canvas) {
        this.lastStart = System.currentTimeMillis();
    }

    public void onAfterDraw(Plot source, Canvas canvas) {
        this.lastLatency = System.currentTimeMillis() - this.lastStart;
        if (this.lastLatency < this.shortestRenderMs) {
            this.shortestRenderMs = this.lastLatency;
        }
        if (this.lastLatency > this.longestRenderMs) {
            this.longestRenderMs = this.lastLatency;
        }
        this.latencySum += this.lastLatency;
        this.latencySamples++;
        annotatePlot(source, canvas);
    }
}
