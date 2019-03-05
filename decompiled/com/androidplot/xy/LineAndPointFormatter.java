package com.androidplot.xy;

import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.util.PixelUtils;
import com.google.android.gms.cast.TextTrackStyle;

public class LineAndPointFormatter extends XYSeriesFormatter<XYRegionFormatter> {
    private static final float DEFAULT_LINE_STROKE_WIDTH_DP = 1.5f;
    private static final float DEFAULT_VERTEX_STROKE_WIDTH_DP = 4.5f;
    protected FillDirection fillDirection;
    protected Paint fillPaint;
    protected Paint linePaint;
    private PointLabelFormatter pointLabelFormatter;
    private PointLabeler pointLabeler;
    protected Paint vertexPaint;

    public FillDirection getFillDirection() {
        return this.fillDirection;
    }

    public void setFillDirection(FillDirection fillDirection) {
        this.fillDirection = fillDirection;
    }

    public LineAndPointFormatter() {
        this(Integer.valueOf(SupportMenu.CATEGORY_MASK), Integer.valueOf(-16711936), Integer.valueOf(-16776961), null);
    }

    public LineAndPointFormatter(Integer lineColor, Integer vertexColor, Integer fillColor, PointLabelFormatter plf) {
        this(lineColor, vertexColor, fillColor, plf, FillDirection.BOTTOM);
    }

    public LineAndPointFormatter(Integer lineColor, Integer vertexColor, Integer fillColor, PointLabelFormatter plf, FillDirection fillDir) {
        this.pointLabeler = new PointLabeler() {
            public String getLabel(XYSeries series, int index) {
                return series.getY(index) + "";
            }
        };
        this.fillDirection = FillDirection.BOTTOM;
        initLinePaint(Integer.valueOf(ViewCompat.MEASURED_STATE_MASK));
        initLinePaint(lineColor);
        initVertexPaint(vertexColor);
        initFillPaint(fillColor);
        setFillDirection(fillDir);
        setPointLabelFormatter(plf);
    }

    public Class<? extends SeriesRenderer> getRendererClass() {
        return LineAndPointRenderer.class;
    }

    public SeriesRenderer getRendererInstance(XYPlot plot) {
        return new LineAndPointRenderer(plot);
    }

    protected void initLinePaint(Integer lineColor) {
        if (lineColor == null) {
            this.linePaint = null;
            return;
        }
        this.linePaint = new Paint();
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStrokeWidth(PixelUtils.dpToPix(DEFAULT_LINE_STROKE_WIDTH_DP));
        this.linePaint.setColor(lineColor.intValue());
        this.linePaint.setStyle(Style.STROKE);
    }

    protected void initVertexPaint(Integer vertexColor) {
        if (vertexColor == null) {
            this.vertexPaint = null;
            return;
        }
        this.vertexPaint = new Paint();
        this.vertexPaint.setAntiAlias(true);
        this.vertexPaint.setStrokeWidth(PixelUtils.dpToPix(DEFAULT_VERTEX_STROKE_WIDTH_DP));
        this.vertexPaint.setColor(vertexColor.intValue());
        this.vertexPaint.setStrokeCap(Cap.ROUND);
    }

    protected void initFillPaint(Integer fillColor) {
        if (fillColor == null) {
            this.fillPaint = null;
            return;
        }
        this.fillPaint = new Paint();
        this.fillPaint.setAntiAlias(true);
        this.fillPaint.setColor(fillColor.intValue());
    }

    public void enableShadows() {
        this.linePaint.setShadowLayer(TextTrackStyle.DEFAULT_FONT_SCALE, 3.0f, 3.0f, ViewCompat.MEASURED_STATE_MASK);
        this.vertexPaint.setShadowLayer(TextTrackStyle.DEFAULT_FONT_SCALE, 3.0f, 3.0f, ViewCompat.MEASURED_STATE_MASK);
    }

    public void disableShadows() {
        this.linePaint.setShadowLayer(0.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        this.vertexPaint.setShadowLayer(0.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
    }

    public Paint getLinePaint() {
        return this.linePaint;
    }

    public void setLinePaint(Paint linePaint) {
        this.linePaint = linePaint;
    }

    public Paint getVertexPaint() {
        return this.vertexPaint;
    }

    public void setVertexPaint(Paint vertexPaint) {
        this.vertexPaint = vertexPaint;
    }

    public Paint getFillPaint() {
        return this.fillPaint;
    }

    public void setFillPaint(Paint fillPaint) {
        this.fillPaint = fillPaint;
    }

    public PointLabelFormatter getPointLabelFormatter() {
        return this.pointLabelFormatter;
    }

    public void setPointLabelFormatter(PointLabelFormatter pointLabelFormatter) {
        this.pointLabelFormatter = pointLabelFormatter;
    }

    public PointLabeler getPointLabeler() {
        return this.pointLabeler;
    }

    public void setPointLabeler(PointLabeler pointLabeler) {
        this.pointLabeler = pointLabeler;
    }
}
