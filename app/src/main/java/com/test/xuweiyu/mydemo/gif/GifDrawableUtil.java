package com.test.xuweiyu.mydemo.gif;

import android.support.annotation.NonNull;

/**
 * Created by xuweiyu on 18-9-11.
 * Email:xuweiyu@xiaomi.com
 */

public class GifDrawableUtil {
    public static void loadGif(@NonNull String url, @NonNull GifImageLoader.LoadGifCallback callback) {
        byte[] data = GifDrawableCache.getFromCache(url);
        if (data != null) {
            callback.loadSuccess(data);
        } else {
            if (!GifDrawableCache.isInLoading(url)) {
                GifImageLoader loader = new GifImageLoader();
                GifDrawableCache.putToLoadingMap(url, loader);
                loader.setLoadGifCallback(callback);
                loader.execute(url);
            }else {
                GifImageLoader loader = GifDrawableCache.getLoaderFromLoadingMap(url);
                loader.setLoadingGifCallback(callback);
            }
        }
    }
}
