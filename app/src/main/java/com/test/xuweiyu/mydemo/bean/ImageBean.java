package com.test.xuweiyu.mydemo.bean;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import java.io.Serializable;

/**
 * Created by xuweiyu on 19-2-13.
 * Email:xuweiyu@xiaomi.com
 */
public class ImageBean implements Serializable {
    public static final int IMAGE = 0;
    public static final int TEXT = 1;
    public Bitmap bitmap;
    public String name;
    public String url;
    public int type;
    public String province;

    public ImageBean(@ImageType int type) {
        this.type = type;
    }

    @IntDef({IMAGE, TEXT})
    public @interface ImageType {
    }
}
