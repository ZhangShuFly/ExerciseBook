package com.ilyzs.exercisebook.base;

/**
 * Created by zhangshu on 2018/2/20.
 */

public class BasePresenter<V extends BaseView> {

    private V mView;

    /**
     * 绑定view
     * @param mapView
     */
    public void attachView(V mapView) {
        this.mView = mapView;
    }

    /**
     * 获取view
     * @return
     */
    public V getMView() {
        return mView;
    }

    /**
     * 断开view
     */
    public void detachView(){
        this.mView = null;
    }

    /**
     * 判断是否绑定View
     * @return
     */
    public boolean isViewAttach(){
        return mView != null;
    }
}
