package com.ilyzs.exercisebook.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ilyzs.exercisebook.MainActivity;
import com.ilyzs.exercisebook.NavigationDrawerActivity;
import com.ilyzs.exercisebook.R;
import com.ilyzs.exercisebook.ScrollingActivity;

/**
 * Created by zhangshu on 2018/1/6.
 */

public class BaseActivity extends AppCompatActivity implements BaseView{

    private ProgressDialog progressDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main  && !(this instanceof MainActivity)) {
            startActivity(new Intent(this, MainActivity.class));
        }else if(id == R.id.action_scoll && !(this instanceof ScrollingActivity)){
            startActivity(new Intent(this, ScrollingActivity.class));
        }else if(id == R.id.action_navigation_drawer && !(this instanceof NavigationDrawerActivity)){
            startActivity(new Intent(this, NavigationDrawerActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    @Override
    public void showLoading() {
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        showToast(getResources().getString(R.string.api_error_msg));
    }

    @Override
    public Context getContext() {
        return this;
    }
}
