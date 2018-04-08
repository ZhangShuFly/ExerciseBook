package com.ilyzs.exercisebook.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by zhangshu on 2018/2/20.
 */

public abstract class BasePresenter<V extends BaseView> {

    private Reference<V> mViewRef;

    /**
     * 绑定view
     * @param mapView
     */
    public void attachView(V mapView) {
        this.mViewRef = new WeakReference<V>( mapView);
    }

    /**
     * 获取view
     * @return
     */
    public V getMView() {
        return mViewRef.get();
    }

    /**
     * 断开view
     */
    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 判断是否绑定View
     * @return
     */
    public boolean isViewAttach(){
        return mViewRef != null && mViewRef.get()!=null;
    }

    //公用的方法，示范
    public abstract void showData();
}
