package com.test.xuweiyu.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by xuweiyu on 19-2-26.
 * Email:xuweiyu@xiaomi.com
 */

public class MyViewGroup extends ViewGroup {
    private Scroller mScroller = new Scroller(getContext());

    public MyViewGroup(Context context) {
        super(context);
        init();
    }

    private void init() {
        MyTestView testView = new MyTestView(getContext());
        MyTestView testView1 = new MyTestView(getContext());
        MyTestView testView2 = new MyTestView(getContext());
        MyTestView testView3 = new MyTestView(getContext());
        MyTestView testView4 = new MyTestView(getContext());
        MyTestView testView5 = new MyTestView(getContext());
        MyTestView testView6 = new MyTestView(getContext());
        addView(testView);
        addView(testView1);
        addView(testView2);
        addView(testView3);
        addView(testView4);
        addView(testView5);
        addView(testView6);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int width = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            LayoutParams params = getLayoutParams();
            params.width = 100 * (i + 1);
            params.height = 200 * (i + 1);
            width += params.width;
            view.setLayoutParams(params);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            view.layout(left, t, left + width, b);
            left += width;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            scrollTo(x, y);
            postInvalidate();
        }
    }

    public void smoothScrollToEnd(int dx, int dy) {
        Log.d("smoothScrollToEnd","x = "+getX()+", y = "+getY()+", transX = "+getTranslationX()+", transY = "+getTranslationY());
        int x = mScroller.getCurrX();
        int y = (int) getY();
        mScroller.startScroll(x, y, x + dx,  dy, 3000);
        postInvalidate();
    }

    int mDownX;
    int mDownY;
    int mUpX;
    int mUpY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction()==MotionEvent.ACTION_UP){
//            smoothScrollToEnd(getMeasuredWidth(),0);
//        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mUpX = (int) event.getX();
                mUpY = (int) event.getY();
                int changX = mDownX - mUpX;
                int changY = mDownY - mUpY;
                if (Math.abs(changX) > Math.abs(changY)) {
                    smoothScrollX(changX);
                } else {
                    smoothScrollY(changY);
                }
                break;
        }
        return true;
    }

    private void smoothScrollX(int changX) {
        smoothScrollToEnd(changX,0);
    }

    private void smoothScrollY(int changY) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
