package com.ilyzs.exercisebook.base;

import android.content.Context;

/**
 * Created by zhangshu
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showToast(String msg);

    void showError();

    Context getContext();

}
