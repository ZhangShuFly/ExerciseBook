package com.ilyzs.exercisebook.view;

import com.ilyzs.exercisebook.base.BaseView;

import java.util.List;

/**
 * Created by zhangshu .
 */

public interface ImageDataView<T>   extends BaseView{

    void showData(List<T> Data);

}
