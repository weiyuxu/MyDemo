package com.test.xuweiyu.mydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuweiyu on 19-2-26.
 * Email:xuweiyu@xiaomi.com
 */

public class MyTestView extends View {
    private Paint mPaint = new Paint();

    public MyTestView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int radus = Math.min(width, height)/2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(0, 0, radus, radus, mPaint);
        }
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r , b );
    }

}
