package com.test.xuweiyu.mydemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.test.xuweiyu.mydemo.gif.GifDrawableCache;
import com.test.xuweiyu.mydemo.gif.GifDrawableUtil;
import com.test.xuweiyu.mydemo.gif.GifImageLoader;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mLunchFaceBook;
    private Button mLunchInstagram;
    private Button mLunchTwitter;
    private Button mLunchLinkedIn;
    private VoteView mVoteView;
    private GifImageView mGifImg;
    private DownloadProgressView mProgressView;
    private static final String GIF_URL = "https://injoy.enjoyui.com/res/fine/gif_nwm/20180823/15350189203203529.gif";
    private Button mJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        listenViews();
    }

    private void listenViews() {
        mLunchFaceBook.setOnClickListener(this);
        mLunchInstagram.setOnClickListener(this);
        mLunchTwitter.setOnClickListener(this);
        mLunchLinkedIn.setOnClickListener(this);
        mProgressView.setOnClickListener(this);
        mJump.setOnClickListener(this);
    }

    private void initViews() {
        mLunchFaceBook = (Button) findViewById(R.id.lunch_facebook);
        mLunchInstagram = (Button) findViewById(R.id.lunch_instagram);
        mLunchTwitter = (Button) findViewById(R.id.lunch_twitter);
        mLunchLinkedIn = (Button) findViewById(R.id.lunch_linkedIn);
        mGifImg = findViewById(R.id.gif_image_view);
        mProgressView = findViewById(R.id.download_progress);
        mVoteView = findViewById(R.id.vote_view);
        mJump = findViewById(R.id.jump);
        mVoteView.refresh(0.25f);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.lunch_facebook:
                if (isAppInstalled("com.facebook.katana")) {
                    String pageId = "343785549079076";//查询方法在fb的主页查看源代码然后搜索page_id
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + pageId));
                    startActivity(intent);
                } else {
                    String userName = "xiaomichinhhangtphcm";//使用pageId也可以
                    Uri uri = Uri.parse("https://www.facebook.com/" + userName);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                break;
            case R.id.lunch_instagram:
                String mInstagramId = "xiaomi_news";//这一个要是你的Ins用户名
                if (isAppInstalled("com.instagram.android")) {
                    Uri uri = Uri.parse("http://instagram.com/_u/" + mInstagramId);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Uri uri = Uri.parse("http://instagram.com/" + mInstagramId);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                break;
            case R.id.lunch_twitter:
                String userName = "xiaomi";//Twitter的用户名
                if (isAppInstalled("com.twitter.android")) {
                    Uri uri = Uri.parse("twitter://user?screen_name=" + userName);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Uri uri = Uri.parse("https://twitter.com/" + userName);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                break;
            case R.id.lunch_linkedIn:
                String profileId = "xiaomi-technology";
                if (isAppInstalled("com.linkedin.android")) {
                    Uri uri = Uri.parse("linkedin://profile/company/" + profileId);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Uri uri = Uri.parse("https://www.linkedin.com/company/" + profileId);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                break;
            case R.id.download_progress:
                playGif();
                break;
            case R.id.jump:
                Intent intent = new Intent(this, GifActivity.class);
                intent.putExtra("url", GIF_URL);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 50) {
            if (requestCode == 100) {
                playGif();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void playGif() {
        if (GifDrawableCache.isInCache(GIF_URL)) {
            mProgressView.setVisibility(View.GONE);
            showGif(mGifImg, GifDrawableCache.getFromCache(GIF_URL));
        } else {
            mProgressView.setProgress(1);
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

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return pm.getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
