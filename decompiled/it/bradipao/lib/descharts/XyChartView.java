package it.bradipao.lib.descharts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.Iterator;

public class XyChartView extends CartesianView {
    private Paint mPnt = new Paint();
    private ArrayList<ChartPointSerie> mSeries = new ArrayList();

    public XyChartView(Context context) {
        super(context);
        initPaint();
    }

    public XyChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void onDraw(Canvas cnv) {
        if (this.mBmp == null || this.bRedraw) {
            getViewSizes();
            getXYminmax();
            if (this.p_xscale_auto) {
                calcXgridRange();
            }
            if (this.p_yscale_auto) {
                calcYgridRange();
            }
            calcXYcoefs();
            this.mBmp = Bitmap.createBitmap(this.p_width, this.p_height, Config.ARGB_8888);
            this.mCnv = new Canvas(this.mBmp);
            if (this.p_grid_vis) {
                drawGrid();
            }
            if (this.p_xtext_vis) {
                drawXlabel();
            }
            if (this.p_ytext_vis) {
                drawYlabel();
            }
            if (this.p_border_vis) {
                drawBorder();
            }
            if (this.p_axis_vis) {
                drawAxis();
            }
            drawData();
            this.bRedraw = false;
        }
        cnv.drawBitmap(this.mBmp, 0.0f, 0.0f, null);
    }

    public void clearSeries() {
        while (this.mSeries.size() > 0) {
            this.mSeries.remove(0);
        }
        this.bRedraw = true;
        postInvalidate();
    }

    public void addSerie(ChartPointSerie serie) {
        this.mSeries.add(serie);
        this.bRedraw = true;
        postInvalidate();
    }

    public ArrayList<ChartPointSerie> getSeries() {
        return this.mSeries;
    }

    public void setLineVis(int index, boolean show) {
        ((ChartPointSerie) this.mSeries.get(index)).setVisible(show);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, int color, float size) {
        ((ChartPointSerie) this.mSeries.get(index)).setStyle(color, size);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, int color, float size, boolean usedip) {
        ((ChartPointSerie) this.mSeries.get(index)).setStyle(color, size, usedip);
        this.bRedraw = true;
        postInvalidate();
    }

    protected void getXYminmax() {
        this.ii = 0;
        while (this.ii < this.mSeries.size()) {
            ChartPointSerie serie = (ChartPointSerie) this.mSeries.get(this.ii);
            if (this.ii == 0) {
                this.mXmin = serie.mXmin;
                this.mXmax = serie.mXmax;
                this.mYmin = serie.mYmin;
                this.mYmax = serie.mYmax;
            } else {
                if (serie.mXmin < this.mXmin) {
                    this.mXmin = serie.mXmin;
                }
                if (serie.mXmax > this.mXmax) {
                    this.mXmax = serie.mXmax;
                }
                if (serie.mYmin < this.mYmin) {
                    this.mYmin = serie.mYmin;
                }
                if (serie.mYmax > this.mYmax) {
                    this.mYmax = serie.mYmax;
                }
            }
            this.ii++;
        }
    }

    protected void drawData() {
        Iterator it = this.mSeries.iterator();
        while (it.hasNext()) {
            ChartPointSerie serie = (ChartPointSerie) it.next();
            if (serie.isVisible()) {
                this.mPnt.reset();
                this.mPnt.setStyle(Style.STROKE);
                this.mPnt.setColor(serie.mColor);
                if (serie.mUseDip) {
                    this.mPnt.setStrokeWidth(dipToPixel(serie.mWidth));
                } else {
                    this.mPnt.setStrokeWidth(serie.mWidth);
                }
                this.mPnt.setAntiAlias(true);
                boolean pValid = false;
                this.mPath.reset();
                this.ii = 0;
                while (this.ii < serie.mPointList.size()) {
                    ChartPoint point = (ChartPoint) serie.mPointList.get(this.ii);
                    float pX = point.x;
                    float pY = point.y;
                    if (Float.isNaN(pX) || Float.isNaN(pY)) {
                        pValid = false;
                    } else if (pValid) {
                        this.mPath.lineTo(this.sX + ((pX - this.bX) * this.aX), this.eY - ((pY - this.bY) * this.aY));
                    } else {
                        this.mPath.moveTo(this.sX + ((pX - this.bX) * this.aX), this.eY - ((pY - this.bY) * this.aY));
                        pValid = true;
                    }
                    this.ii++;
                }
                this.mCnv.drawPath(this.mPath, this.mPnt);
            }
        }
    }
}
