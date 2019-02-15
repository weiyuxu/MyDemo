package com.test.xuweiyu.mydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuweiyu on 18-9-11.
 * Email:xuweiyu@xiaomi.com
 */

public class DownloadProgressView extends View {
    int mProgress;
    int mDownloadedColor;
    int mDefaultColor;
    int mNotDownloadedColor;
    String mText;
    int mTextColor;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    boolean flag = false;
    private static final int PADDING = 5;
    RectF rect = new RectF();

    public DownloadProgressView(Context context) {
        this(context, null);
    }

    public DownloadProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DownloadProgressView);
        init(typedArray);
    }

    private void init(TypedArray typedArray) {
        mDefaultColor = typedArray.getColor(R.styleable.DownloadProgressView_default_color, Color.GRAY);
        mDownloadedColor = typedArray.getColor(R.styleable.DownloadProgressView_downloaded_color, Color.GRAY);
        mNotDownloadedColor = typedArray.getColor(R.styleable.DownloadProgressView_not_downloaded_color, Color.RED);
        mText = typedArray.getString(R.styleable.DownloadProgressView_text);
        mTextColor = typedArray.getColor(R.styleable.DownloadProgressView_text_color, Color.RED);
        typedArray.recycle();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
        flag = true;
        invalidate();
    }

    public void reset() {
        mProgress = 0;
        setVisibility(GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        rect.left = PADDING;
        rect.top = PADDING;
        rect.right = width - 2 * PADDING;
        rect.bottom = height - 2 * PADDING;
        if (flag) {
            mPaint.setColor(mDownloadedColor);
            float downloadedArc = mProgress * 360 / 100.0f;
            canvas.drawArc(rect, 0, downloadedArc, true, mPaint);
            mPaint.setColor(mNotDownloadedColor);
            canvas.drawArc(rect, downloadedArc, 360 - downloadedArc, true, mPaint);
        } else {
            mPaint.setColor(mDefaultColor);
            canvas.drawOval(rect, mPaint);
            if (!TextUtils.isEmpty(mText)) {
                mPaint.setColor(mTextColor);
                mPaint.setTextSize(50);
                mPaint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
                canvas.drawText(mText, rect.centerX(), baseLineY, mPaint);
            }
        }
    }
}
