package it.bradipao.lib.descharts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;

public class StackedBarChartView extends CartesianView {
    private int mLabelMaxNum = 10;
    private Paint mPnt = new Paint();
    private Paint mPntFill = new Paint();
    private ArrayList<ChartValueSerie> mSeries = new ArrayList();
    private ChartValueSerie mStacked = new ChartValueSerie();
    private int mXnum = 0;

    public StackedBarChartView(Context context) {
        super(context);
        initPaint();
    }

    public StackedBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void onDraw(Canvas cnv) {
        if (this.mBmp == null || this.bRedraw) {
            getViewSizes();
            getXYminmax();
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

    public void addSerie(ChartValueSerie serie) {
        this.mSeries.add(serie);
        this.bRedraw = true;
        postInvalidate();
    }

    public ArrayList<ChartValueSerie> getSeries() {
        return this.mSeries;
    }

    public void setLineVis(int index, boolean show) {
        ((ChartValueSerie) this.mSeries.get(index)).setVisible(show);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, int color, float size) {
        ((ChartValueSerie) this.mSeries.get(index)).setStyle(color, size);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, int color, float size, boolean usedip) {
        ((ChartValueSerie) this.mSeries.get(index)).setStyle(color, size, usedip);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLineStyle(int index, int color, int fillcolor, float size, boolean usedip) {
        ((ChartValueSerie) this.mSeries.get(index)).setStyle(color, fillcolor, size, usedip);
        this.bRedraw = true;
        postInvalidate();
    }

    public void setLabelMaxNum(int maxnum) {
        if (maxnum > 0) {
            this.mLabelMaxNum = maxnum;
            this.bRedraw = true;
            postInvalidate();
        }
    }

    protected void getXYminmax() {
        calcStackedSerie();
        this.mXnum = this.mStacked.getSize();
        this.mYmax = this.mStacked.mYmax;
        this.ii = 0;
        while (this.ii < this.mSeries.size()) {
            ChartValueSerie serie = (ChartValueSerie) this.mSeries.get(this.ii);
            if (this.ii == 0) {
                this.mYmin = serie.mYmin;
            } else if (serie.mYmin < this.mYmin) {
                this.mYmin = serie.mYmin;
            }
            this.ii++;
        }
    }

    protected void drawData() {
        this.ii = 0;
        while (this.ii < this.mSeries.size()) {
            ChartValueSerie serie = (ChartValueSerie) this.mSeries.get(this.ii);
            if (serie.isVisible()) {
                this.mPnt.reset();
                this.mPnt.setStyle(Style.STROKE);
                this.mPnt.setColor(serie.mColor);
                this.mPntFill.reset();
                this.mPntFill.setStyle(Style.FILL);
                this.mPntFill.setColor(serie.mFillColor);
                if (serie.mUseDip) {
                    this.mPnt.setStrokeWidth(dipToPixel(serie.mWidth));
                } else {
                    this.mPnt.setStrokeWidth(serie.mWidth);
                }
                this.mPnt.setAntiAlias(true);
                this.mPntFill.setAntiAlias(false);
                this.jj = 0;
                while (this.jj < this.mStacked.mPointList.size()) {
                    ChartValue v = (ChartValue) this.mStacked.mPointList.get(this.jj);
                    float pY = v.y;
                    if (!Float.isNaN(pY)) {
                        this.mCnv.drawRect(((this.sX + (this.aX / 4.0f)) + (((float) this.jj) * this.aX)) + TextTrackStyle.DEFAULT_FONT_SCALE, this.eY, ((this.sX + (this.aX / 4.0f)) + (this.aX / 2.0f)) + (((float) this.jj) * this.aX), this.eY - ((pY - this.bY) * this.aY), this.mPntFill);
                        this.mCnv.drawRect(((this.sX + (this.aX / 4.0f)) + (((float) this.jj) * this.aX)) + TextTrackStyle.DEFAULT_FONT_SCALE, this.eY, ((this.sX + (this.aX / 4.0f)) + (this.aX / 2.0f)) + (((float) this.jj) * this.aX), this.eY - ((pY - this.bY) * this.aY), this.mPnt);
                    }
                    this.mStacked.updatePoint(this.jj, v.y - serie.getPoint(this.jj).y);
                    this.jj++;
                }
            }
            this.ii++;
        }
    }

    protected void calcXYcoefs() {
        this.aX = this.dX / ((float) this.mXnum);
        this.bX = this.aX / 2.0f;
        this.aY = this.dY / Math.abs(this.mYmaxGrid - this.mYminGrid);
        this.bY = this.mYminGrid;
    }

    protected void drawXlabel() {
        this.mPntText.setTextAlign(Align.CENTER);
        this.mPath.reset();
        ChartValueSerie mLabel = (ChartValueSerie) this.mSeries.get(0);
        int numlab = mLabel.getSize();
        int numdiv = ((numlab - 1) / this.mLabelMaxNum) + 1;
        String label;
        if (this.p_xtext_bottom) {
            this.ii = 0;
            while (this.ii < mLabel.getSize()) {
                this.mPath.moveTo((this.sX + this.bX) + (((float) this.ii) * this.aX), this.eY - 3.0f);
                this.mPath.lineTo((this.sX + this.bX) + (((float) this.ii) * this.aX), this.eY + 3.0f);
                label = ((ChartValue) mLabel.mPointList.get(this.ii)).t;
                if (label != null && this.ii < numlab && this.ii % numdiv == 0) {
                    this.mCnv.drawText(label, (this.sX + this.bX) + (((float) this.ii) * this.aX), (this.eY + this.p_text_size) + 2.0f, this.mPntText);
                }
                this.ii++;
            }
        } else {
            this.ii = 0;
            while (this.ii < mLabel.getSize()) {
                this.mPath.moveTo((this.sX + this.bX) + (((float) this.ii) * this.aX), this.sY - 3.0f);
                this.mPath.lineTo((this.sX + this.bX) + (((float) this.ii) * this.aX), this.sY + 3.0f);
                label = ((ChartValue) mLabel.mPointList.get(this.ii)).t;
                if (label != null && this.ii < numlab && this.ii % numdiv == 0) {
                    this.mCnv.drawText(label, (this.sX + this.bX) + (((float) this.ii) * this.aX), (this.sY - this.p_text_size) + 3.0f, this.mPntText);
                }
                this.ii++;
            }
        }
        this.mCnv.drawPath(this.mPath, this.mPntAxis);
    }

    protected void calcStackedSerie() {
        if (this.mSeries.size() != 0) {
            this.mStacked.clearPointList();
            ChartValueSerie f = (ChartValueSerie) this.mSeries.get(0);
            this.ii = 0;
            while (this.ii < f.getSize()) {
                float acc;
                if (f.isVisible()) {
                    acc = f.getPoint(this.ii).y;
                } else {
                    acc = 0.0f;
                }
                this.jj = 1;
                while (this.jj < this.mSeries.size()) {
                    if (((ChartValueSerie) this.mSeries.get(this.jj)).isVisible() && this.ii < ((ChartValueSerie) this.mSeries.get(this.jj)).getSize()) {
                        acc += ((ChartValueSerie) this.mSeries.get(this.jj)).getPoint(this.ii).y;
                    }
                    this.jj++;
                }
                this.mStacked.addPoint(new ChartValue(null, acc));
                this.ii++;
            }
        }
    }
}
