package com.test.xuweiyu.mydemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by xuweiyu on 19-2-12.
 * Email:xuweiyu@xiaomi.com
 */
public class MyApp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
