package com.test.xuweiyu.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuweiyu on 19-2-18.
 * Email:xuweiyu@xiaomi.com
 */
public class HorizontalScrollViewEx extends ViewGroup {
    public HorizontalScrollViewEx(Context context) {
        super(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int measureWidth = child.getMeasuredWidth();
                int measureHeight = child.getMeasuredHeight();
                child.layout(childLeft, t, childLeft + measureWidth, t + measureHeight);
                childLeft += measureWidth;
            }
        }
    }
}
