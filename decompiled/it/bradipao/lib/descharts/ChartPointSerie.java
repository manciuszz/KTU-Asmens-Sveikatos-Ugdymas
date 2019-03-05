package it.bradipao.lib.descharts;

import android.support.v4.view.ViewCompat;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;

public class ChartPointSerie {
    private boolean mAutoXminmax = true;
    private boolean mAutoYminmax = true;
    public int mColor = ViewCompat.MEASURED_STATE_MASK;
    private boolean mOrderonx = false;
    public ArrayList<ChartPoint> mPointList = new ArrayList();
    private boolean mShow = true;
    public boolean mUseDip = true;
    public float mWidth = 2.0f;
    public float mXmax = TextTrackStyle.DEFAULT_FONT_SCALE;
    public float mXmin = 0.0f;
    public float mYmax = TextTrackStyle.DEFAULT_FONT_SCALE;
    public float mYmin = 0.0f;

    public ChartPointSerie(int color) {
        this.mColor = color;
    }

    public ChartPointSerie(int color, float width) {
        this.mColor = color;
        this.mWidth = width;
    }

    public ChartPointSerie(int color, float width, boolean usedip) {
        this.mColor = color;
        this.mWidth = width;
        this.mUseDip = usedip;
    }

    public ArrayList<ChartPoint> getPointList() {
        return this.mPointList;
    }

    public void setPointList(ArrayList<ChartPoint> points) {
        this.mPointList = points;
    }

    public void clearPointList() {
        this.mPointList.clear();
    }

    public void addPoint(ChartPoint point) {
        if (this.mAutoXminmax) {
            if (this.mPointList.size() <= 0) {
                float f = point.x;
                this.mXmax = f;
                this.mXmin = f;
            } else if (point.x > this.mXmax) {
                this.mXmax = point.x;
            } else if (point.x < this.mXmin) {
                this.mXmin = point.x;
            }
        }
        if (this.mAutoYminmax) {
            if (this.mPointList.size() <= 0) {
                f = point.y;
                this.mYmax = f;
                this.mYmin = f;
            } else if (point.y > this.mYmax) {
                this.mYmax = point.y;
            } else if (point.y < this.mYmin) {
                this.mYmin = point.y;
            }
        }
        if (this.mOrderonx) {
            for (int i = 0; i < this.mPointList.size(); i++) {
                if (point.x < ((ChartPoint) this.mPointList.get(i)).x) {
                    this.mPointList.add(i, point);
                    return;
                }
            }
            this.mPointList.add(point);
            return;
        }
        this.mPointList.add(point);
    }

    public void shiftPoint(ChartPoint point, int max) {
        addPoint(point);
        while (this.mPointList.size() > max) {
            this.mPointList.remove(0);
        }
        if (this.mAutoXminmax || this.mAutoYminmax) {
            calcRanges();
        }
    }

    public void removePoint(ChartPoint point) {
        this.mPointList.remove(point);
        if (this.mAutoXminmax || this.mAutoYminmax) {
            calcRanges();
        }
    }

    public ChartPoint getPoint(int index) {
        return (ChartPoint) this.mPointList.get(index);
    }

    public int getSize() {
        return this.mPointList.size();
    }

    private void calcRanges() {
        if (this.mPointList.size() != 0) {
            int i;
            if (this.mAutoXminmax) {
                this.mXmin = ((ChartPoint) this.mPointList.get(0)).x;
                this.mXmax = ((ChartPoint) this.mPointList.get(0)).x;
                for (i = 1; i < this.mPointList.size(); i++) {
                    if (((ChartPoint) this.mPointList.get(i)).x > this.mXmax) {
                        this.mXmax = ((ChartPoint) this.mPointList.get(i)).x;
                    } else if (((ChartPoint) this.mPointList.get(i)).x < this.mXmin) {
                        this.mXmin = ((ChartPoint) this.mPointList.get(i)).x;
                    }
                }
            }
            if (this.mAutoYminmax) {
                this.mYmin = ((ChartPoint) this.mPointList.get(0)).y;
                this.mYmax = ((ChartPoint) this.mPointList.get(0)).y;
                for (i = 1; i < this.mPointList.size(); i++) {
                    if (((ChartPoint) this.mPointList.get(i)).y > this.mYmax) {
                        this.mYmax = ((ChartPoint) this.mPointList.get(i)).y;
                    } else if (((ChartPoint) this.mPointList.get(i)).y < this.mYmin) {
                        this.mYmin = ((ChartPoint) this.mPointList.get(i)).y;
                    }
                }
            }
        }
    }

    public void setAutoMinmax(boolean bAutoX, boolean bAutoY) {
        this.mAutoXminmax = bAutoX;
        this.mAutoYminmax = bAutoY;
        if (bAutoX || bAutoY) {
            calcRanges();
        }
    }

    public void setAutoMinmax(boolean bAutoX, boolean bAutoY, float fXmin, float fXmax, float fYmin, float fYmax) {
        this.mAutoXminmax = bAutoX;
        this.mAutoYminmax = bAutoY;
        if (!bAutoX) {
            this.mXmin = fXmin;
            this.mXmax = fXmax;
        }
        if (!bAutoY) {
            this.mYmin = fYmin;
            this.mYmax = fYmax;
        }
        if (bAutoX || bAutoY) {
            calcRanges();
        }
    }

    public void setOrderOnX(boolean bOrderonx) {
        if (this.mPointList.size() <= 0) {
            this.mOrderonx = bOrderonx;
        }
    }

    public void setVisible(boolean bShow) {
        this.mShow = bShow;
    }

    public boolean isVisible() {
        return this.mShow;
    }

    public void setStyle(int iColor, float fWidth) {
        this.mColor = iColor;
        this.mWidth = fWidth;
    }

    public void setStyle(int iColor, float fWidth, boolean bUsedip) {
        this.mColor = iColor;
        this.mWidth = fWidth;
        this.mUseDip = bUsedip;
    }
}
