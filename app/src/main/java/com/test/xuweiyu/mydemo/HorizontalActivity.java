package com.test.xuweiyu.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.test.xuweiyu.mydemo.test.MyQueue;
import com.test.xuweiyu.mydemo.test.MyStack;

/**
 * Created by xuweiyu on 19-2-18.
 * Email:xuweiyu@xiaomi.com
 */
public class HorizontalActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        initViews();
        initData();
    }

    private void initData() {
//        MyStack<String> stack = new MyStack<>();
//        for (int i=0;i<6;i++){
//            stack.push("stack "+i);
//        }
//        while (!stack.isEmpty()){
//            Log.d("HorizontalActivity","data = "+stack.pop());
//        }
        MyQueue<String> queue = new MyQueue<>();
        for (int i=0;i<6;i++){
            queue.push("stack "+i);
        }
        while (!queue.isEmpty()){
            Log.d("HorizontalActivity","data = "+queue.pop());
        }
    }

    private void initViews() {

    }


}
