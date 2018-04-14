package com.ilyzs.exercisebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.ilyzs.exercisebook.base.BaseActivity;
import com.ilyzs.exercisebook.util.MarkMoveUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollingActivity extends BaseActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        mBaiduMap = mMapView.getMap();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(21f);
        mBaiduMap.setMapStatus(msu);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.clear();

        //定义Maker坐标点
        LatLng point = new LatLng(39.982307,116.362426);
        LatLng pointEnd = new LatLng(39.983316,116.399795);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);

        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions()
                .position(point)
                .perspective(true)
                .icon(bitmap);

        //在地图上添加Marker，并显示
       Marker marker = (Marker)mBaiduMap.addOverlay(option);
        Marker marker2 = (Marker)mBaiduMap.addOverlay(option);

        MarkMoveUtil markMoveUtil = new MarkMoveUtil(point.longitude,point.latitude,20000,mMapView,marker,this);
        markMoveUtil.start(pointEnd);
        //markMoveUtil.drawPolyLine();
        markMoveUtil.moveLooper();


        MarkMoveUtil markMoveUtil2 = new MarkMoveUtil(point.longitude,point.latitude,10000,mMapView,marker2,this);
        markMoveUtil2.start(pointEnd);
        //markMoveUtil2.drawPolyLine();
        markMoveUtil2.moveLooper();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
