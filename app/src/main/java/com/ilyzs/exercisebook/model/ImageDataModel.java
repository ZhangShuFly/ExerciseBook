package com.ilyzs.exercisebook.model;


import android.os.Handler;

import com.ilyzs.exercisebook.util.DataCallBack;

/**
 * Created by zhangshu .
 */

public class ImageDataModel {

   public static void getData(String param, final DataCallBack<String[]> callBack,final String[] resultData){

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               callBack.onSuccess(resultData);

               callBack.onComplete();
           }
       },3000);

   }

}
