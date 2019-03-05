package it.bradipao.lib.descharts;

import android.support.v4.view.ViewCompat;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;

public class ChartValueSerie {
    private boolean mAutoYminmax = true;
    public int mColor = ViewCompat.MEASURED_STATE_MASK;
    public int mFillColor = 0;
    public ArrayList<ChartValue> mPointList = new ArrayList();
    private boolean mShow = true;
    public boolean mUseDip = true;
    public float mWidth = 2.0f;
    public float mYmax = TextTrackStyle.DEFAULT_FONT_SCALE;
    public float mYmin = 0.0f;

    public ChartValueSerie(int color) {
        this.mColor = color;
    }

    public ChartValueSerie(int color, float width) {
        this.mColor = color;
        this.mWidth = width;
    }

    public ChartValueSerie(int color, float width, boolean usedip) {
        this.mColor = color;
        this.mWidth = width;
        this.mUseDip = usedip;
    }

    public ArrayList<ChartValue> getPointList() {
        return this.mPointList;
    }

    public void setPointList(ArrayList<ChartValue> points) {
        this.mPointList = points;
    }

    public void clearPointList() {
        this.mPointList.clear();
    }

    public void addPoint(ChartValue point) {
        if (this.mAutoYminmax) {
            if (this.mPointList.size() <= 0) {
                float f = point.y;
                this.mYmax = f;
                this.mYmin = f;
            } else if (point.y > this.mYmax) {
                this.mYmax = point.y;
            } else if (point.y < this.mYmin) {
                this.mYmin = point.y;
            }
        }
        this.mPointList.add(point);
    }

    public void setPoint(ChartValue point, int pos) {
        if (this.mAutoYminmax) {
            if (this.mPointList.size() <= 0) {
                float f = point.y;
                this.mYmax = f;
                this.mYmin = f;
            } else if (point.y > this.mYmax) {
                this.mYmax = point.y;
            } else if (point.y < this.mYmin) {
                this.mYmin = point.y;
            }
        }
        this.mPointList.set(pos, point);
    }

    public void shiftPoint(ChartValue point, int max) {
        addPoint(point);
        while (this.mPointList.size() > max) {
            this.mPointList.remove(0);
        }
        if (this.mAutoYminmax) {
            calcRanges();
        }
    }

    public void removePoint(ChartValue point) {
        this.mPointList.remove(point);
        if (this.mAutoYminmax) {
            calcRanges();
        }
    }

    public ChartValue getPoint(int index) {
        return (ChartValue) this.mPointList.get(index);
    }

    public void updatePoint(int index, float y) {
        ((ChartValue) this.mPointList.get(index)).y = y;
        if (this.mAutoYminmax) {
            calcRanges();
        }
    }

    public int getSize() {
        return this.mPointList.size();
    }

    private void calcRanges() {
        if (this.mPointList.size() != 0 && this.mAutoYminmax) {
            this.mYmin = ((ChartValue) this.mPointList.get(0)).y;
            this.mYmax = ((ChartValue) this.mPointList.get(0)).y;
            for (int i = 1; i < this.mPointList.size(); i++) {
                if (((ChartValue) this.mPointList.get(i)).y > this.mYmax) {
                    this.mYmax = ((ChartValue) this.mPointList.get(i)).y;
                } else if (((ChartValue) this.mPointList.get(i)).y < this.mYmin) {
                    this.mYmin = ((ChartValue) this.mPointList.get(i)).y;
                }
            }
        }
    }

    public void setAutoMinmax(boolean bAutoY) {
        this.mAutoYminmax = bAutoY;
        if (bAutoY) {
            calcRanges();
        }
    }

    public void setAutoMinmax(boolean bAutoY, float fYmin, float fYmax) {
        this.mAutoYminmax = bAutoY;
        if (!bAutoY) {
            this.mYmin = fYmin;
            this.mYmax = fYmax;
        }
        if (bAutoY) {
            calcRanges();
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

    public void setStyle(int iColor, int iFillColor, float fWidth, boolean bUsedip) {
        this.mColor = iColor;
        this.mFillColor = iFillColor;
        this.mWidth = fWidth;
        this.mUseDip = bUsedip;
    }
}
