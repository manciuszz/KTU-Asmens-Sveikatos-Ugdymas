package it.bradipao.lib.descharts;

import android.support.v4.view.ViewCompat;

public class StyledChartPoint {
    public int fillColor = 0;
    public int lineColor = ViewCompat.MEASURED_STATE_MASK;
    public int markColor = 0;
    public float markSize = 0.0f;
    public float x = 0.0f;
    public float y = 0.0f;

    public StyledChartPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public StyledChartPoint(float x, float y, int lineColor) {
        this.x = x;
        this.y = y;
        this.lineColor = lineColor;
    }

    public StyledChartPoint(float x, float y, int lineColor, int fillColor) {
        this.x = x;
        this.y = y;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    public StyledChartPoint(float x, float y, int lineColor, int fillColor, int markColor, float markSize) {
        this.x = x;
        this.y = y;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.markColor = markColor;
        this.markSize = markSize;
    }
}
