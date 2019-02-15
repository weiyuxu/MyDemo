package com.test.xuweiyu.mydemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.xuweiyu.mydemo.gif.GifDrawableCache;
import com.test.xuweiyu.mydemo.gif.GifDrawableUtil;
import com.test.xuweiyu.mydemo.gif.GifImageLoader;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by xuweiyu on 18-9-11.
 * Email:xuweiyu@xiaomi.com
 */

public class GifActivity extends Activity implements View.OnClickListener {
    private RelativeLayout mParent;
    private GifImageView mGifImg;
    private DownloadProgressView mProgressView;
    private ImageView mBack;
    private TextView mBottom;
    private static String GIF_URL;
    private boolean mShowFlag = true;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        mParent = findViewById(R.id.parent_g);
        mGifImg = findViewById(R.id.gif_image_view_g);
        mProgressView = findViewById(R.id.download_progress_g);
        mBack = findViewById(R.id.back);
        mBottom = findViewById(R.id.bottom_g);
        GIF_URL = getIntent().getStringExtra("url");
        if (GifDrawableCache.isInCache(GIF_URL)) {
            mProgressView.setVisibility(View.GONE);
            showGif(mGifImg, GifDrawableCache.getFromCache(GIF_URL));
        } else {
            GifDrawableUtil.loadGif(GIF_URL, new GifImageLoader.LoadGifCallback() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void loadSuccess(@NonNull byte[] data) {
                    showGif(mGifImg, data);
                    mProgressView.reset();
                }

                @Override
                public void loading(int percent) {
                    mProgressView.setProgress(percent);
                }

                @Override
                public void loadFail() {
                    mProgressView.reset();
                }
            });
        }
        mParent.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showGif(@NonNull GifImageView gifImageView, @NonNull byte[] data) {
        GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
        GifDrawableBuilder builder = new GifDrawableBuilder().with(gifDrawable);
        builder.from(data);
        GifDrawable drawable = null;
        try {
            drawable = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gifImageView.setImageDrawable(drawable);
        if (drawable != null) {
            gifImageView.setBackground(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(50);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        mShowFlag = !mShowFlag;
        if (mShowFlag) {
            mBack.setVisibility(View.VISIBLE);
            mBottom.setVisibility(View.VISIBLE);
        } else {
            mBack.setVisibility(View.GONE);
            mBottom.setVisibility(View.GONE);
        }
    }
}
