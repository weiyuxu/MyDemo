package com.test.xuweiyu.mydemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by xuweiyu on 19-2-14.
 * Email:xuweiyu@xiaomi.com
 */
public class ImageUtils {
    public static void showImage(Context context, String url, ImageView imageView, boolean supportCache) {
        RequestOptions options = RequestOptions.skipMemoryCacheOf(!supportCache).diskCacheStrategy(supportCache ? DiskCacheStrategy.AUTOMATIC : DiskCacheStrategy.NONE);
        Glide.with(imageView.getContext()).applyDefaultRequestOptions(options).load(url).into(imageView);
    }
}
