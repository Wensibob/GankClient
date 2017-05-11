package com.bob.gank_client.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.presenter.ChromeViewPresenter;
import com.bob.gank_client.mvp.presenter.MainPresenter;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.adapter.MainPagerAdapter;
import com.bob.gank_client.ui.base.ToolBarActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by bob on 17-5-2.
 */

public class MainActivity extends ToolBarActivity<MainPresenter> implements IBaseView{

        public ChromeViewPresenter chromeViewPresenter;

        //init drawer_layout
        @Bind(R.id.drawer_layout)
        DrawerLayout drawer;

        //init tab_layout
        @Bind(R.id.tab_layout)
        TabLayout tabLayout;

        //init viewpager
        @Bind(R.id.view_pager_main)
        ViewPager viewPager;

        //init navigation_view
        @Bind(R.id.nav_view)
        NavigationView navigationView;

        //init fab&click
        public static  FloatingActionButton fab;
        @OnClick(R.id.fab_main)
        void fabClick() {
                presenter.toDailyActivity();
        }


        @Override
        protected int provideContentViewId() {
                return R.layout.activity_main;
        }

        @Override
        protected void initPresenter() {
                chromeViewPresenter = new ChromeViewPresenter(MainActivity.this, this, this);
                presenter = new MainPresenter(this, this);
                presenter.init();
        }

        @Override
        public void init() {
                fab = (FloatingActionButton) findViewById(R.id.fab_main);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(Color.TRANSPARENT);
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                int id = item.getItemId();
                                switch (id){
                                        case R.id.nav_settings:
                                                drawer.closeDrawer(GravityCompat.START);
//                                                presenter.toSettingActivity();

                                        case R.id.nav_about:
                                                drawer.closeDrawer(GravityCompat.START);
//                                                presenter.toAboutActivity();
                                        default:
                                                drawer.closeDrawer(GravityCompat.START);
                                                break;
                                }
                                return true;
                        }
                });

                MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),chromeViewPresenter);
                viewPager.setAdapter(mainPagerAdapter);
                viewPager.setOffscreenPageLimit(5);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                tabLayout.setupWithViewPager(viewPager);
        }

        @Override
        public void onBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                } else {
                        super.onBackPressed();
                }
        }

        @Override
        protected void onDestroy() {
                super.onDestroy();
                presenter.release();
        }


          @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                        case android.R.id.home:
                                drawer.openDrawer(GravityCompat.START);
                                return true;
                }
                return super.onOptionsItemSelected(item);
        }
}
