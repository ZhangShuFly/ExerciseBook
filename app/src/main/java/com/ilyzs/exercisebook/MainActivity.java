package com.ilyzs.exercisebook;

import android.animation.ObjectAnimator;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ilyzs.exercisebook.base.BaseActivity;
import com.ilyzs.exercisebook.fragment.FallsFragment;
import com.ilyzs.exercisebook.fragment.ListFragment;
import com.ilyzs.exercisebook.fragment.WebViewFragment;
import com.ilyzs.exercisebook.service.JobSchedulerService;
import com.ilyzs.exercisebook.service.TestService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @BindView(R.id.container)
    public ViewPager mViewPager;

    @BindView(R.id.tablayout)
    public TabLayout mTabLayout;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, TestService.class));

        JobScheduler jobScheduler  = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(getPackageName(), JobSchedulerService.class.getName()));
        builder.setOverrideDeadline(5000).setMinimumLatency(3000);


        if(jobScheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE ) {
            Log.e(TAG, "onCreate: jobScheduler is error");
        }

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        TabLayout.Tab one = mTabLayout.getTabAt(0);
        one.setIcon(R.drawable.book);

        TabLayout.Tab two = mTabLayout.getTabAt(1);
        two.setIcon(R.drawable.find);

        TabLayout.Tab three = mTabLayout.getTabAt(2);
        three.setIcon(R.drawable.me);

        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.support.v4.R.color.primary_text_default_material_dark));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    private void open(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, -355, -315);
        animator.setDuration(500);
        animator.start();
        view.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
    }

    private void close(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", -315, 40, 0);
        animator.setDuration(500);
        animator.start();
        view.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String[] titles = new String[]{"关注", "推荐", "热榜"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (0== position) {
                return FallsFragment.newInstance(String.valueOf(position+1));
            }else if(1 == position){
                return WebViewFragment.newInstance("http://www.baidu.com");
            }else if(2 == position  ){
                return ListFragment.newInstance(position+1);
            }
                return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
