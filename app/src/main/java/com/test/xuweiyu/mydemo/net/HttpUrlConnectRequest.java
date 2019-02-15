package com.test.xuweiyu.mydemo.net;

import android.support.annotation.StringDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by xuweiyu on 19-2-14.
 * Email:xuweiyu@xiaomi.com
 */
public class HttpUrlConnectRequest {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final int TIME_OUT = 15000;

    public static String request(String urlStr, @RequestMethod String method) {
        String result = null;
        try {
            //创建并打开链接
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(TIME_OUT);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuilder builder = new StringBuilder();

//            byte[] bytes = new byte[1024];
//            int length = 0;
//            while ((length = inputStream.read(bytes)) != -1) {
//                String string = new String(bytes, 0, length, Charset.forName("GBK"));
//                builder.append(string);
//            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
            String line;
            while ((line = reader.readLine())!=null){
                builder.append(line);
            }

            result = builder.toString();
            inputStream.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @StringDef({GET, POST})
    public @interface RequestMethod {

    }
}
