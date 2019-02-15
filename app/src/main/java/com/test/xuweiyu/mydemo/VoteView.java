package com.test.xuweiyu.mydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuweiyu on 18-8-10.
 * Email:xuweiyu@xiaomi.com
 */

public class VoteView extends View {
    private int mLeftColor;
    private int mRightColor;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mRatio = -1;

    private static final int useTime = 500;

    public VoteView(Context context) {
        this(context, null);
    }

    public VoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoteView);
        init(typedArray);
    }

    private void init(TypedArray typedArray) {
        mLeftColor = typedArray.getColor(R.styleable.VoteView_vote_left_color, Color.GRAY);
        mRightColor = typedArray.getColor(R.styleable.VoteView_vote_right_color, Color.RED);
        typedArray.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRatio != -1) {
            int width = getWidth();
            int height = getHeight();
            float corner = height / 2.0f;
            Path path = new Path();
            path.addRoundRect(0, 0, width, height, corner, corner, Path.Direction.CW);
            canvas.clipPath(path);//将画布裁剪成圆角矩形区域
            mPaint.setColor(mLeftColor);
            int leftWidth = (int) (mRatio * width);
            canvas.drawRect(0, 0, leftWidth, height, mPaint);
            mPaint.setColor(mRightColor);
            canvas.drawRect(leftWidth, 0, width, height, mPaint);
        }
    }

    public void refresh(float ratio) {
        mRatio = ratio;
        invalidate();
    }
}
