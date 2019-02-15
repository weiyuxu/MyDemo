package com.test.xuweiyu.mydemo.gif;

import android.support.annotation.NonNull;
import android.util.LruCache;

import java.util.HashMap;

/**
 * Created by xuweiyu on 18-9-11.
 * Email:xuweiyu@xiaomi.com
 */

public class GifDrawableCache {
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
    private static HashMap<String, GifImageLoader> sLoadingImageLoaders = new HashMap<>();
    private static LruCache<String, byte[]> sGifCaches = new LruCache<>(MAX_CACHE_SIZE);

    public static boolean isInCache(@NonNull String url) {
        byte[] data = sGifCaches.get(url);
        return data != null;
    }

    static void putToCache(@NonNull String url, @NonNull byte[] data) {
        sGifCaches.put(url, data);
    }

    public static byte[] getFromCache(@NonNull String url) {
        return sGifCaches.get(url);
    }


    static boolean isInLoading(@NonNull String url) {
        return sLoadingImageLoaders.containsKey(url);
    }

    static void putToLoadingMap(@NonNull String url, @NonNull GifImageLoader loader) {
        sLoadingImageLoaders.put(url, loader);
    }

    static GifImageLoader getLoaderFromLoadingMap(@NonNull String url) {
        return sLoadingImageLoaders.get(url);
    }

    public static void registerLoadingCallback(@NonNull String url, @NonNull GifImageLoader.LoadGifCallback callback) {
        if (sLoadingImageLoaders.containsKey(url)) {
            GifImageLoader loader = sLoadingImageLoaders.get(url);
            if (loader != null) {
                loader.setLoadingGifCallback(callback);
            }
        }
    }

    public static void remove(@NonNull String url) {
        if (isInLoading(url)) {
            GifImageLoader loader = sLoadingImageLoaders.get(url);
            loader.removeCallbacks();
            loader.cancel(true);
            sLoadingImageLoaders.remove(url);
        }
    }
}
