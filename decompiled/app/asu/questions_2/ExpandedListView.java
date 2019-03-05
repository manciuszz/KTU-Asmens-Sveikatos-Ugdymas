package app.asu.questions_2;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

public class ExpandedListView extends ListView {
    private int old_count = 0;
    private LayoutParams params;

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        if (getCount() != this.old_count) {
            this.old_count = getCount();
            this.params = getLayoutParams();
            LayoutParams layoutParams = this.params;
            int count = getCount();
            if (this.old_count > 0) {
                i = getChildAt(0).getHeight();
            }
            layoutParams.height = i * count;
            setLayoutParams(this.params);
        }
        super.onDraw(canvas);
    }
}
