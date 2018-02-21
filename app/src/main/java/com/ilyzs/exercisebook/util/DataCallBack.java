package com.ilyzs.exercisebook.util;

/**
 * Created by zhangshu .
 */

public interface DataCallBack<T> {

    void onSuccess(T data);

    void onFial(String msg);

    void onError();

    void onComplete();
}
