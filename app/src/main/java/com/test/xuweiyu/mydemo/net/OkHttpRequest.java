package com.test.xuweiyu.mydemo.net;

import com.bumptech.glide.RequestBuilder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xuweiyu on 19-2-18.
 * Email:xuweiyu@xiaomi.com
 */
public class OkHttpRequest {
    public static void request(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static String getRequest(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            //同步请求
            return client.newCall(request).execute().body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void postRequest(String url, Map<String,String> params){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("key","value").build();
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
