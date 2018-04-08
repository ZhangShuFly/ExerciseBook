package com.ilyzs.exercisebook.model;


import android.os.Handler;

import com.ilyzs.exercisebook.util.DataCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangshu .
 */

public class ImageDataModel {

   private static String[] name = {"马云","朱元璋","成吉思汗","奥巴马","屠呦呦","贝克汉姆","史泰龙"};
   private static String[] url = {"https://ws1.sinaimg.cn/large/610dc034ly1fjgfyxgwgnj20u00gvgmt.jpg","https://ws1.sinaimg.cn/large/610dc034ly1fjndz4dh39j20u00u0ada.jpg","https://ws1.sinaimg.cn/large/610dc034ly1fjppsiclufj20u011igo5.jpg","https://ws1.sinaimg.cn/large/610dc034ly1fjfae1hjslj20u00tyq4x.jpg","https://ws1.sinaimg.cn/large/610dc034ly1fjs25xfq48j20u00mhtb6.jpg","https://ws1.sinaimg.cn/large/610dc034ly1fk05lf9f4cj20u011h423.jpg","https://ws1.sinaimg.cn/large/610dc034ly1fjxu5qqdjoj20qo0xc0wk.jpg"};

   public static void getData(final String index, final DataCallBack<List<Map<String,String>>> callBack){

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               List<Map<String,String>> list = new ArrayList<>();
               for (int i=0;i<5;i++){
                   Map<String,String> map = new HashMap<>();
                   StringBuilder builder = new StringBuilder(name[(int)Math.floor(Math.random()*name.length)]);
                   builder.append("_");
                   builder.append(Integer.valueOf(index)*5 + i);
                   map.put("name",builder.toString());
                   map.put("url",url[(int)Math.floor(Math.random()*url.length)]);
                   list.add(map);
               }

               callBack.onSuccess(list);

               callBack.onComplete();
           }
       },3000);

   }

}
