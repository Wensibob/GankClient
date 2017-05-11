package com.bob.gank_client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.R;
import com.bob.gank_client.ShareElement;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.model.entity.Meizi;
import com.bob.gank_client.mvp.presenter.ChromeViewPresenter;
import com.bob.gank_client.mvp.presenter.GankPresenter;
import com.bob.gank_client.ui.adapter.GankAdapter;
import com.bob.gank_client.ui.base.ToolBarActivity;
import com.bob.gank_client.mvp.view.IGankView;
import com.bob.gank_client.utils.APPUtil;
import com.bob.gank_client.utils.DateUtil;
import com.bob.gank_client.utils.ShareUtil;
import com.bob.gank_client.utils.SnackBarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by bob on 17-5-4.
 */

public class GankActivity extends ToolBarActivity<GankPresenter> implements IGankView {

        private Meizi meizi;
        private List<Gank> gankList;
        private GankAdapter adapter;
        private Calendar calendar;
        private ChromeViewPresenter chromeViewPresenter;

        //TODO 需不需要加一个进度条
        @Bind(R.id.toolbar_layout)
        CollapsingToolbarLayout toolbarLayout;
        @Bind(R.id.iv_head)
        ImageView imageView;
        @Bind(R.id.recycler_view_gank)
        RecyclerView recyclerView;
        @Bind(R.id.fab_gank)
        FloatingActionButton gankFab;
        @OnClick(R.id.fab_gank)
        void fabClick() {
                if (APPUtil.isNetWorkAvaliable(getApplicationContext())) {
                        if (APPUtil.isWifiConnected(getApplicationContext())) {
                                showVideo();
                        } else {
                                SnackBarUtil.showTipWithAction(gankFab, getString(R.string.wifi_unavaliable), getString(R.string.wifi_continue), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                showVideo();
                                        }
                                });
                        }

                } else {
                        //TODO 添加一个动作事件，前往网络设置页面
                        SnackBarUtil.showTipWithoutAction(gankFab,getString(R.string.network_unavaliable));
                }

        }

        private void showVideo() {
                if (gankList.size() > 0 && gankList.get(0).type.equals("休息视频")) {
                        Intent intent = new Intent(GankActivity.this, WebViewActivity.class);
                        intent.putExtra(GankConfig.GANK, gankList.get(0));
                        startActivity(intent);
                } else {
                        SnackBarUtil.showTipWithoutAction(gankFab,getString(R.string.error_happened));
                }
        }

        @Override
        public void onCreate( Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }

        @Override
        public void init() {
                meizi = (Meizi) getIntent().getSerializableExtra(GankConfig.MEIZI);
                calendar = Calendar.getInstance();
                calendar.setTime(meizi.publishedAt);
                presenter.fetchGankData(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
                gankList = new ArrayList<>();
                adapter = new GankAdapter(chromeViewPresenter,gankList, this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                setTitle(DateUtil.toDateTimeStr(meizi.publishedAt));
                imageView.setImageDrawable(ShareElement.shareDrawable);
                ViewCompat.setTransitionName(imageView, GankConfig.TRANSLATE_GIRL_VIEW);
                gankFab.setClickable(false);
        }

        @Override
        protected void initToolBar() {
                super.initToolBar();
                actionBar = getSupportActionBar();
                if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(canBack());
                }
        }

        @Override
        public void showGankList(List<Gank> gankList) {
                this.gankList.clear();
                this.gankList.addAll(gankList);
                adapter.notifyDataSetChanged();
                if (!gankList.get(0).type.equals("休息视频")) {
                        gankFab.setVisibility(View.GONE);
                } else {
                        gankFab.setClickable(true);
                }
                gankFab.setClickable(true);

        }

        @Override
        public void showProgress() {

        }

        @Override
        public void hideProgress() {

        }

        @Override
        public void showErrorView() {
                SnackBarUtil.showTipWithAction(gankFab,getString(R.string.load_fail),getString(R.string.retry),new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                                presenter.fetchGankData(calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH) + 1,
                                        calendar.get(Calendar.DAY_OF_MONTH));
                        }
                });
        }

        @Override
        protected int provideContentViewId() {
                return R.layout.activity_gank;
        }

        @Override
        protected void initPresenter() {
                chromeViewPresenter = new ChromeViewPresenter(GankActivity.this, this, this);
                presenter = new GankPresenter(this, this);
                presenter.init();
        }

        @Override
        protected void onDestroy() {
                super.onDestroy();
                ShareElement.shareDrawable = null;
                gankList = null;
                presenter.release();
                presenter = null;
        }
}
