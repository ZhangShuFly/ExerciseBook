package com.ilyzs.exercisebook;

import android.animation.ObjectAnimator;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ilyzs.exercisebook.base.BaseActivity;
import com.ilyzs.exercisebook.fragment.FallsFragment;
import com.ilyzs.exercisebook.fragment.ListFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            if (1 == position) {
                return FallsFragment.newInstance(String.valueOf(position));
            }

            return ListFragment.newInstance(position + 1);
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
