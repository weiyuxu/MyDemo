package com.test.xuweiyu.mydemo.test;

import java.util.LinkedList;

import io.reactivex.annotations.NonNull;

/**
 * Created by xuweiyu on 19-2-22.
 * Email:xuweiyu@xiaomi.com
 */
public class MyStack<T> {
    private LinkedList<T> mLinkedList1 = new LinkedList<>();
    private LinkedList<T> mLinkedList2 = new LinkedList<>();
    public void push(@NonNull T t){
        if (mLinkedList1.size()==0){
            mLinkedList1.push(t);
        }else {
            T top = mLinkedList1.pop();
            mLinkedList2.push(top);
            mLinkedList1.push(t);
        }
    }
    public T pop() {
        if (mLinkedList1.size() == 0) {
            return null;
        } else {
            T result = mLinkedList1.pop();
            while (!mLinkedList2.isEmpty()) {
                T top = mLinkedList2.pop();
                mLinkedList1.push(top);
            }
            while (mLinkedList1.size() > 1) {
                T top = mLinkedList1.pop();
                mLinkedList2.push(top);
            }
            return result;
        }
    }
}
