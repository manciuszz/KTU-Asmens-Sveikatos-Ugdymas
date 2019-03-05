package com.androidplot.util;

import android.graphics.RectF;
import com.google.android.gms.cast.TextTrackStyle;

public class DisplayDimensions {
    private static final RectF initRect = new RectF(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
    public final RectF canvasRect;
    public final RectF marginatedRect;
    public final RectF paddedRect;

    public DisplayDimensions() {
        this(initRect, initRect, initRect);
    }

    public DisplayDimensions(RectF canvasRect, RectF marginatedRect, RectF paddedRect) {
        this.canvasRect = canvasRect;
        this.marginatedRect = marginatedRect;
        this.paddedRect = paddedRect;
    }
}
