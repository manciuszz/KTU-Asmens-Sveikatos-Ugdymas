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

public class StyledXyChartView extends CartesianView {
    private Paint mFillPnt = new Paint();
    private Paint mLinePnt = new Paint();
    private Paint mMarkPnt = new Paint();
    private ArrayList<StyledChartPointSerie> mSeries = new ArrayList();

    public StyledXyChartView(Context context) {
        super(context);
        initPaint();
    }

    public StyledXyChartView(Context context, AttributeSet attrs) {
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
            drawData();
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

    public void addSerie(StyledChartPointSerie serie) {
        this.mSeries.add(serie);
        this.bRedraw = true;
        postInvalidate();
    }

    public ArrayList<StyledChartPointSerie> getSeries() {
        return this.mSeries;
    }

    public void setLineVis(int index, boolean show) {
        ((StyledChartPointSerie) this.mSeries.get(index)).setVisible(show);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, float size) {
        ((StyledChartPointSerie) this.mSeries.get(index)).setStyle(size);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, float size, boolean usedip) {
        ((StyledChartPointSerie) this.mSeries.get(index)).setStyle(size, usedip);
        this.bRedraw = true;
        postInvalidate();
    }

    protected void getXYminmax() {
        this.ii = 0;
        while (this.ii < this.mSeries.size()) {
            StyledChartPointSerie serie = (StyledChartPointSerie) this.mSeries.get(this.ii);
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
        float qX = 0.0f;
        float qY = 0.0f;
        this.mLinePnt.reset();
        this.mFillPnt.reset();
        this.mMarkPnt.reset();
        this.mLinePnt.setStyle(Style.STROKE);
        this.mFillPnt.setStyle(Style.FILL);
        this.mMarkPnt.setStyle(Style.FILL);
        this.mLinePnt.setAntiAlias(true);
        this.mMarkPnt.setAntiAlias(false);
        this.mFillPnt.setAntiAlias(false);
        Iterator it = this.mSeries.iterator();
        while (it.hasNext()) {
            StyledChartPointSerie serie = (StyledChartPointSerie) it.next();
            if (serie.isVisible()) {
                if (serie.mUseDip) {
                    this.mLinePnt.setStrokeWidth(dipToPixel(serie.mWidth));
                } else {
                    this.mLinePnt.setStrokeWidth(serie.mWidth);
                }
                boolean pValid = false;
                this.ii = 0;
                while (this.ii < serie.mPointList.size()) {
                    StyledChartPoint point = (StyledChartPoint) serie.mPointList.get(this.ii);
                    float pX = point.x;
                    float pY = point.y;
                    if (Float.isNaN(pX) || Float.isNaN(pY)) {
                        pValid = false;
                    } else if (pValid) {
                        if (point.fillColor != 0) {
                            this.mFillPnt.setColor(point.fillColor);
                            this.mPath.reset();
                            this.mPath.moveTo(this.sX + ((qX - this.bX) * this.aX), this.eY);
                            this.mPath.lineTo(this.sX + ((qX - this.bX) * this.aX), this.eY - ((qY - this.bY) * this.aY));
                            this.mPath.lineTo(this.sX + ((pX - this.bX) * this.aX), this.eY - ((pY - this.bY) * this.aY));
                            this.mPath.lineTo(this.sX + ((pX - this.bX) * this.aX), this.eY);
                            this.mPath.close();
                            this.mCnv.drawPath(this.mPath, this.mFillPnt);
                        }
                        this.mLinePnt.setColor(point.lineColor);
                        this.mCnv.drawLine(this.sX + ((qX - this.bX) * this.aX), this.eY - ((qY - this.bY) * this.aY), this.sX + ((pX - this.bX) * this.aX), this.eY - ((pY - this.bY) * this.aY), this.mLinePnt);
                        if (point.markColor != 0) {
                            this.mMarkPnt.setColor(point.markColor);
                            this.mCnv.drawCircle(this.sX + ((pX - this.bX) * this.aX), this.eY - ((pY - this.bY) * this.aY), point.markSize, this.mMarkPnt);
                        }
                    } else {
                        if (point.markColor != 0) {
                            this.mMarkPnt.setColor(point.markColor);
                            this.mCnv.drawCircle(this.sX + ((pX - this.bX) * this.aX), this.eY - ((pY - this.bY) * this.aY), point.markSize, this.mMarkPnt);
                        }
                        pValid = true;
                    }
                    qX = pX;
                    qY = pY;
                    this.ii++;
                }
            }
        }
    }
}
