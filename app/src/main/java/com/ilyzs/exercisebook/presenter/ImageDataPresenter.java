package com.ilyzs.exercisebook.presenter;

import com.ilyzs.exercisebook.base.BasePresenter;
import com.ilyzs.exercisebook.model.ImageDataModel;
import com.ilyzs.exercisebook.util.DataCallBack;
import com.ilyzs.exercisebook.view.ImageDataView;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangshu on 2018/2/20.
 */

public class ImageDataPresenter extends BasePresenter<ImageDataView> {

    public void getData(String params){

        //如果没有View引用就不加载数据
        if(!isViewAttach()){
            return;
        }

                //显示正在加载进度条
                if("0".equals(params))
                    getMView().showLoading();

                // 调用Model请求数据
                ImageDataModel.getData(params, new DataCallBack<List<Map<String,String>>>() {
                    @Override
                    public void onSuccess(List<Map<String,String>> list) {
                        if(isViewAttach())
                            getMView().showData(list);
            }

            @Override
            public void onFial(String msg) {
                if(isViewAttach())
                getMView().showToast(msg);
            }

            @Override
            public void onError() {
                if(isViewAttach())
                getMView().showError();
            }

            @Override
            public void onComplete() {
                if (isViewAttach())
                getMView().hideLoading();
            }
        });

    }

    @Override
    public void showData() {

    }
}
