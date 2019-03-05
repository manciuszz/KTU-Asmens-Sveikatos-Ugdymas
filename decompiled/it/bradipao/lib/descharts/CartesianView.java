package it.bradipao.lib.descharts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.google.android.gms.cast.TextTrackStyle;

public class CartesianView extends View {
    float aX;
    float aY;
    boolean bRedraw = false;
    float bX;
    float bY;
    float dX;
    float dY;
    float eX;
    float eY;
    float ff;
    int ii;
    int jj;
    Bitmap mBmp = null;
    Canvas mCnv = null;
    Typeface mFontText = Typeface.create("sans-serif-condensed", 0);
    Path mPath = new Path();
    Paint mPntAxis = new Paint();
    Paint mPntBorder = new Paint();
    Paint mPntGrid = new Paint();
    Paint mPntText = new Paint();
    float mXdivGrid;
    int mXgridNum;
    float mXmax;
    float mXmaxGrid;
    float mXmin;
    float mXminGrid;
    float mYdivGrid;
    int mYgridNum;
    float mYmax;
    float mYmaxGrid;
    float mYmin;
    float mYminGrid;
    int p_axis_color = ViewCompat.MEASURED_STATE_MASK;
    boolean p_axis_vis = true;
    float p_axis_width = dipToPixel(TextTrackStyle.DEFAULT_FONT_SCALE);
    int p_background_color = -1;
    int p_border_color = ViewCompat.MEASURED_STATE_MASK;
    boolean p_border_vis = true;
    float p_border_width = dipToPixel(TextTrackStyle.DEFAULT_FONT_SCALE);
    boolean p_grid_aa = true;
    int p_grid_color = ViewCompat.MEASURED_STATE_MASK;
    boolean p_grid_vis = true;
    float p_grid_width = dipToPixel(TextTrackStyle.DEFAULT_FONT_SCALE);
    int p_height = 0;
    int p_paddbottom = 8;
    int p_paddleft = 8;
    int p_paddright = 8;
    int p_paddtop = 8;
    int p_text_color = ViewCompat.MEASURED_STATE_MASK;
    float p_text_size = dipToPixel(7.0f);
    int p_width = 0;
    boolean p_xscale_auto = true;
    boolean p_xtext_bottom = true;
    boolean p_xtext_vis = true;
    boolean p_yscale_auto = true;
    boolean p_ytext_left = true;
    boolean p_ytext_vis = true;
    float sX;
    float sY;

    public CartesianView(Context context) {
        super(context);
        initPaint();
    }

    public CartesianView(Context context, AttributeSet attrs) {
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
            this.bRedraw = false;
        }
        cnv.drawBitmap(this.mBmp, 0.0f, 0.0f, null);
    }

    public void setPadding(int pad) {
        this.p_paddtop = pad;
        this.p_paddright = pad;
        this.p_paddbottom = pad;
        this.p_paddleft = pad;
    }

    public void setPaddingDip(int pad) {
        this.p_paddtop = (int) dipToPixel((float) pad);
        this.p_paddright = (int) dipToPixel((float) pad);
        this.p_paddbottom = (int) dipToPixel((float) pad);
        this.p_paddleft = (int) dipToPixel((float) pad);
    }

    public void setPadding(int paddtop, int padright, int paddbot, int padleft) {
        this.p_paddtop = paddtop;
        this.p_paddright = padright;
        this.p_paddbottom = paddbot;
        this.p_paddleft = padleft;
    }

    public void setPaddingDip(int paddtop, int padright, int paddbot, int padleft) {
        this.p_paddtop = (int) dipToPixel((float) paddtop);
        this.p_paddright = (int) dipToPixel((float) padright);
        this.p_paddbottom = (int) dipToPixel((float) paddbot);
        this.p_paddleft = (int) dipToPixel((float) padleft);
    }

    public void setBackgroundColor(int color) {
        this.p_background_color = color;
        super.setBackgroundColor(color);
    }

    public void setXgrid(boolean autoXscale, float xmin, float xmax, int num) {
        this.p_xscale_auto = autoXscale;
        if (!autoXscale) {
            this.mXminGrid = xmin;
            this.mXmaxGrid = xmax;
            this.mXgridNum = num;
            this.mXdivGrid = (xmax - xmin) / ((float) num);
        }
        postInvalidate();
    }

    public void setYgrid(boolean autoYscale, float ymin, float ymax, int num) {
        this.p_yscale_auto = autoYscale;
        if (!autoYscale) {
            this.mYminGrid = ymin;
            this.mYmaxGrid = ymax;
            this.mYgridNum = num;
            this.mYdivGrid = (ymax - ymin) / ((float) num);
        }
        postInvalidate();
    }

    public void setGridVis(boolean bBorderShow, boolean bGridShow, boolean bAxisShow) {
        this.p_border_vis = bBorderShow;
        this.p_grid_vis = bGridShow;
        this.p_axis_vis = bAxisShow;
        this.bRedraw = true;
        postInvalidate();
    }

    public void setGridColor(int borderColor, int gridColor, int axisColor) {
        this.p_border_color = borderColor;
        this.p_grid_color = gridColor;
        this.p_axis_color = axisColor;
        initPaint();
        this.bRedraw = true;
        postInvalidate();
    }

    public void setGridWidth(float borderWidth, float gridWidth, float axisWidth) {
        this.p_border_width = borderWidth;
        this.p_grid_width = gridWidth;
        this.p_axis_width = axisWidth;
        initPaint();
        this.bRedraw = true;
        postInvalidate();
    }

    public void setGridWidthDip(float borderWidth, float gridWidth, float axisWidth) {
        this.p_border_width = dipToPixel(borderWidth);
        this.p_grid_width = dipToPixel(gridWidth);
        this.p_axis_width = dipToPixel(axisWidth);
        initPaint();
        this.bRedraw = true;
        postInvalidate();
    }

    public void setGridAA(boolean antialias) {
        this.p_grid_aa = antialias;
        initPaint();
        this.bRedraw = true;
        postInvalidate();
    }

    public void setTextVis(boolean xtext, boolean ytext, boolean xbottom, boolean yleft) {
        this.p_xtext_vis = xtext;
        this.p_ytext_vis = ytext;
        this.p_xtext_bottom = xbottom;
        this.p_ytext_left = yleft;
        this.bRedraw = true;
        postInvalidate();
    }

    public void setTextStyle(int color, float size) {
        this.p_text_color = color;
        this.p_text_size = dipToPixel(size);
        initPaint();
        this.bRedraw = true;
        postInvalidate();
    }

    protected void initPaint() {
        this.mPntBorder.setStyle(Style.STROKE);
        this.mPntBorder.setColor(this.p_border_color);
        this.mPntBorder.setStrokeWidth(this.p_border_width);
        this.mPntBorder.setAntiAlias(this.p_grid_aa);
        this.mPntGrid.setStyle(Style.STROKE);
        this.mPntGrid.setColor(this.p_grid_color);
        this.mPntGrid.setStrokeWidth(this.p_grid_width);
        this.mPntGrid.setPathEffect(new DashPathEffect(new float[]{2.0f, 2.0f}, 0.0f));
        this.mPntGrid.setAntiAlias(this.p_grid_aa);
        this.mPntAxis.setStyle(Style.STROKE);
        this.mPntAxis.setColor(this.p_axis_color);
        this.mPntAxis.setStrokeWidth(this.p_axis_width);
        this.mPntAxis.setAntiAlias(this.p_grid_aa);
        this.mPntText.setColor(this.p_text_color);
        this.mPntText.setTypeface(this.mFontText);
        this.mPntText.setTextSize(this.p_text_size);
        this.mPntText.setStyle(Style.FILL);
        this.mPntText.setAntiAlias(true);
        setBackgroundColor(this.p_background_color);
    }

    protected void getViewSizes() {
        this.p_width = getWidth();
        this.p_height = getHeight();
        this.sX = (float) this.p_paddleft;
        this.sY = (float) this.p_paddtop;
        this.eX = (float) (this.p_width - this.p_paddright);
        this.eY = (float) (this.p_height - this.p_paddbottom);
        if (this.p_ytext_vis && this.p_ytext_left) {
            this.sX += this.p_text_size * 3.0f;
        }
        if (this.p_ytext_vis && !this.p_ytext_left) {
            this.eX -= this.p_text_size * 3.0f;
        }
        if (this.p_xtext_vis && this.p_xtext_bottom) {
            this.eY -= this.p_text_size + 2.0f;
        }
        if (this.p_xtext_vis && !this.p_xtext_bottom) {
            this.sY += this.p_text_size + 2.0f;
        }
        this.dX = this.eX - this.sX;
        this.dY = this.eY - this.sY;
    }

    protected void getXYminmax() {
        this.mXmin = -9.0f;
        this.mXmax = 9.0f;
        this.mYmin = -90.0f;
        this.mYmax = 90.0f;
    }

    protected void calcXgridRange() {
        this.mXdivGrid = (float) Math.pow(10.0d, Math.floor(Math.log10((double) Math.abs(this.mXmax - this.mXmin))));
        this.mXminGrid = (float) (((double) this.mXdivGrid) * Math.floor((double) (this.mXmin / this.mXdivGrid)));
        this.mXmaxGrid = (float) (((double) this.mXdivGrid) * Math.ceil((double) (this.mXmax / this.mXdivGrid)));
        this.mXgridNum = (int) ((this.mXmaxGrid - this.mXminGrid) / this.mXdivGrid);
        if (((double) (this.dX / this.dY)) < 1.2d) {
            if (this.mXgridNum <= 2) {
                this.mXgridNum *= 5;
            } else if (this.mXgridNum == 3) {
                this.mXgridNum *= 3;
            } else if (this.mXgridNum <= 5) {
                this.mXgridNum *= 2;
            }
        } else if (this.mXgridNum <= 2) {
            this.mXgridNum *= 6;
        } else if (this.mXgridNum == 3) {
            this.mXgridNum *= 4;
        } else if (this.mXgridNum == 4) {
            this.mXgridNum *= 3;
        } else if (this.mXgridNum <= 6) {
            this.mXgridNum *= 2;
        }
    }

    protected void calcYgridRange() {
        this.mYdivGrid = (float) Math.pow(10.0d, Math.floor(Math.log10((double) Math.abs(this.mYmax - this.mYmin))));
        this.mYminGrid = (float) (((double) this.mYdivGrid) * Math.floor((double) (this.mYmin / this.mYdivGrid)));
        this.mYmaxGrid = (float) (((double) this.mYdivGrid) * Math.ceil((double) (this.mYmax / this.mYdivGrid)));
        this.mYgridNum = (int) ((this.mYmaxGrid - this.mYminGrid) / this.mYdivGrid);
        if (((double) (this.dY / this.dX)) < 1.2d) {
            if (this.mYgridNum <= 2) {
                this.mYgridNum *= 5;
            } else if (this.mYgridNum <= 3) {
                this.mYgridNum *= 3;
            } else if (this.mYgridNum <= 5) {
                this.mYgridNum *= 2;
            }
        } else if (this.mYgridNum <= 2) {
            this.mYgridNum *= 6;
        } else if (this.mYgridNum == 3) {
            this.mYgridNum *= 4;
        } else if (this.mYgridNum == 4) {
            this.mYgridNum *= 3;
        } else if (this.mYgridNum <= 6) {
            this.mYgridNum *= 2;
        }
    }

    protected void calcXYcoefs() {
        this.aX = this.dX / Math.abs(this.mXmaxGrid - this.mXminGrid);
        this.bX = this.mXminGrid;
        this.aY = this.dY / Math.abs(this.mYmaxGrid - this.mYminGrid);
        this.bY = this.mYminGrid;
    }

    protected void drawGrid() {
        this.mPath.reset();
        this.ii = 1;
        while (this.ii < this.mXgridNum) {
            this.mPath.moveTo(this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), this.sY);
            this.mPath.lineTo(this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), this.eY);
            this.ii++;
        }
        this.ii = 1;
        while (this.ii < this.mYgridNum) {
            this.mPath.moveTo(this.sX, this.sY + (((float) this.ii) * (this.dY / ((float) this.mYgridNum))));
            this.mPath.lineTo(this.eX, this.sY + (((float) this.ii) * (this.dY / ((float) this.mYgridNum))));
            this.ii++;
        }
        this.mCnv.drawPath(this.mPath, this.mPntGrid);
    }

    protected void drawXlabel() {
        this.mPntText.setTextAlign(Align.CENTER);
        this.mPath.reset();
        if (this.p_xtext_bottom) {
            this.ii = 1;
            while (this.ii < this.mXgridNum) {
                this.mPath.moveTo(this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), this.eY - 3.0f);
                this.mPath.lineTo(this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), this.eY + 3.0f);
                this.ff = this.mXminGrid + ((((float) this.ii) * (this.mXmaxGrid - this.mXminGrid)) / ((float) this.mXgridNum));
                this.mCnv.drawText(String.format("%.1f", new Object[]{Float.valueOf(this.ff)}), this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), (this.eY + this.p_text_size) + 2.0f, this.mPntText);
                this.ii++;
            }
        } else {
            this.ii = 1;
            while (this.ii < this.mXgridNum) {
                this.mPath.moveTo(this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), this.sY - 3.0f);
                this.mPath.lineTo(this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), this.sY + 3.0f);
                this.ff = this.mXminGrid + ((((float) this.ii) * (this.mXmaxGrid - this.mXminGrid)) / ((float) this.mXgridNum));
                this.mCnv.drawText(String.format("%.1f", new Object[]{Float.valueOf(this.ff)}), this.sX + (((float) this.ii) * (this.dX / ((float) this.mXgridNum))), (this.sY - this.p_text_size) + 2.0f, this.mPntText);
                this.ii++;
            }
        }
        this.mCnv.drawPath(this.mPath, this.mPntAxis);
    }

    protected void drawYlabel() {
        if (this.p_ytext_left) {
            this.mPntText.setTextAlign(Align.RIGHT);
        } else {
            this.mPntText.setTextAlign(Align.LEFT);
        }
        this.mPath.reset();
        if (this.p_ytext_left) {
            this.ii = 1;
            while (this.ii < this.mYgridNum) {
                this.mPath.moveTo(this.sX - 3.0f, this.eY - (((float) this.ii) * (this.dY / ((float) this.mYgridNum))));
                this.mPath.lineTo(this.sX + 3.0f, this.eY - (((float) this.ii) * (this.dY / ((float) this.mYgridNum))));
                this.ff = this.mYminGrid + ((((float) this.ii) * (this.mYmaxGrid - this.mYminGrid)) / ((float) this.mYgridNum));
                this.mCnv.drawText(String.format("%.1f", new Object[]{Float.valueOf(this.ff)}), this.sX - 6.0f, (this.eY - (((float) this.ii) * (this.dY / ((float) this.mYgridNum)))) + (this.p_text_size / 2.0f), this.mPntText);
                this.ii++;
            }
        } else {
            this.ii = 1;
            while (this.ii < this.mYgridNum) {
                this.mPath.moveTo(this.eX - 3.0f, this.eY - (((float) this.ii) * (this.dY / ((float) this.mYgridNum))));
                this.mPath.lineTo(this.eX + 3.0f, this.eY - (((float) this.ii) * (this.dY / ((float) this.mYgridNum))));
                this.ff = this.mYminGrid + ((((float) this.ii) * (this.mYmaxGrid - this.mYminGrid)) / ((float) this.mYgridNum));
                this.mCnv.drawText(String.format("%.1f", new Object[]{Float.valueOf(this.ff)}), this.eX + 6.0f, (this.eY - (((float) this.ii) * (this.dY / ((float) this.mYgridNum)))) + (this.p_text_size / 2.0f), this.mPntText);
                this.ii++;
            }
        }
        this.mCnv.drawPath(this.mPath, this.mPntAxis);
    }

    protected void drawBorder() {
        this.mPath.reset();
        this.mPath.moveTo(this.sX, this.sY);
        this.mPath.lineTo(this.eX, this.sY);
        this.mPath.lineTo(this.eX, this.eY);
        this.mPath.lineTo(this.sX, this.eY);
        this.mPath.lineTo(this.sX, this.sY);
        this.mCnv.drawPath(this.mPath, this.mPntBorder);
    }

    protected void drawAxis() {
        this.mPath.reset();
        this.mPath.moveTo(this.sX - (this.bX * this.aX), this.sY);
        this.mPath.lineTo(this.sX - (this.bX * this.aX), this.eY);
        this.mPath.moveTo(this.sX, this.eY + (this.bY * this.aY));
        this.mPath.lineTo(this.eX, this.eY + (this.bY * this.aY));
        this.mCnv.drawPath(this.mPath, this.mPntAxis);
    }

    protected float dipToPixel(float dips) {
        return TypedValue.applyDimension(1, dips, getResources().getDisplayMetrics());
    }
}
