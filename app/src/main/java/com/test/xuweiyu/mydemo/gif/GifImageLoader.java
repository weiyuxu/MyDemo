package com.test.xuweiyu.mydemo.gif;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by xuweiyu on 18-9-11.
 * Email:xuweiyu@xiaomi.com
 */

public class GifImageLoader extends AsyncTask<String, Integer, byte[]> {
    private String mUrl;
    private LoadGifCallback mCallback;
    private LoadGifCallback mLoadingCallback;

    GifImageLoader() {
    }

    void setLoadGifCallback(LoadGifCallback call) {
        this.mCallback = call;
        this.mLoadingCallback = null;
    }

    void setLoadingGifCallback(LoadGifCallback call) {
        this.mLoadingCallback = call;
        this.mCallback = null;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        mUrl = params[0];
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            connection = (HttpURLConnection) new URL(mUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if (code == 200) {
                inputStream = connection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                int total = connection.getContentLength();
                byte[] bytes = new byte[10240];
                int len;
                int count = 0;
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                    count += len;
                    int percent = count * 100 / total;
                    publishProgress(percent);
                }
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int percent = values[0];
        if (percent >= 1) {
            if (mCallback != null) {
                mCallback.loading(percent);
            }
            if (mLoadingCallback != null) {
                mLoadingCallback.loading(percent);
            }
        }
    }

    @Override
    protected void onPostExecute(byte[] data) {
        super.onPostExecute(data);
        if (data != null) {
            GifDrawable gifDrawable = null;
            try {
                gifDrawable = new GifDrawable(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (gifDrawable != null) {
                GifDrawableCache.putToCache(mUrl, data);
                if (mCallback != null) {
                    mCallback.loadSuccess(data);
                }
                if (mLoadingCallback != null) {
                    mLoadingCallback.loadSuccess(data);
                }
            } else {
                if (mCallback != null) {
                    mCallback.loadFail();
                }
                if (mLoadingCallback != null) {
                    mLoadingCallback.loadFail();
                }
            }
        } else {
            if (mCallback != null) {
                mCallback.loadFail();
            }
            if (mLoadingCallback != null) {
                mLoadingCallback.loadFail();
            }
        }
        GifDrawableCache.remove(mUrl);
    }

    void removeCallbacks() {
        mCallback = null;
        mLoadingCallback = null;
    }

    public interface LoadGifCallback {
        void loadSuccess(@NonNull byte[] data);

        void loading(int percent);

        void loadFail();
    }
}
