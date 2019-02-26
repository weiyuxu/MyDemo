package com.test.xuweiyu.mydemo.test;

import java.util.Stack;

import io.reactivex.annotations.NonNull;

/**
 * Created by xuweiyu on 19-2-22.
 * Email:xuweiyu@xiaomi.com
 */
public class MyQueue<T> {
    private Stack<T> mStack1 = new Stack<>();
    private Stack<T> mStack2 = new Stack<>();
    public T pop() {
        if (mStack1.empty()){
            return null;
        }else {
            while (mStack1.size()>1){
                T t = mStack1.pop();
                mStack2.push(t);
            }
            T result = mStack1.pop();
            while (mStack2.size()>0){
                T t = mStack2.pop();
                mStack1.push(t);
            }
            return result;
        }
    }

    public void push(@NonNull T t) {
        mStack1.push(t);
    }

    public boolean isEmpty(){
        return mStack1.isEmpty();
    }
}
