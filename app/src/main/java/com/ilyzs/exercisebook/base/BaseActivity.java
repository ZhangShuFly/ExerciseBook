package com.ilyzs.exercisebook.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ilyzs.exercisebook.MainActivity;
import com.ilyzs.exercisebook.NavigationDrawerActivity;
import com.ilyzs.exercisebook.R;
import com.ilyzs.exercisebook.ScrollingActivity;

/**
 * Created by zhangshu on 2018/1/6.
 */

public class BaseActivity extends AppCompatActivity {

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

}
