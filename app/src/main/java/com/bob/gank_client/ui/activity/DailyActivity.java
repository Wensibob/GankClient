package com.bob.gank_client.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Meizi;
import com.bob.gank_client.mvp.presenter.DailyPresenter;
import com.bob.gank_client.mvp.view.IDailyView;
import com.bob.gank_client.ui.adapter.MeiziAdapter;
import com.bob.gank_client.ui.base.ToolBarActivity;
import com.bob.gank_client.ui.widget.AutoRecyclerView;
import com.bob.gank_client.utils.SnackBarUtil;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by bob on 17-5-2.
 */

public class DailyActivity extends ToolBarActivity<DailyPresenter> implements IDailyView ,PullToRefreshView.OnRefreshListener,AutoRecyclerView.LoadMoreListener{

        private List<Meizi> meizis;
        private MeiziAdapter adapter;
        private DailyPresenter presenter;
        private int page = 1;
        private boolean isRefresh = true;
        private boolean canLoading = true;

        @Bind(R.id.toolbar)
        Toolbar toolbar;
        @Bind(R.id.recycler_view)
        AutoRecyclerView recyclerView;
        @Bind(R.id.pull_to_refresh)
        PullToRefreshView pullToRefreshView;

        @Override
        public void init() {
//                setDragEdge(SwipeBackLayout.DragEdge.LEFT);
                meizis = new ArrayList<>();
//                meizis = new SharePrefrenceUtil<Meizi>().getData(getApplicationContext(), "meizi");
//                if (meizis == null) {
//                }
                adapter = new MeiziAdapter(meizis,this);
                //TODO 可以设置为GridLayoutManager
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
                recyclerView.setLoadMoreListener(this);
                pullToRefreshView.setOnRefreshListener(this);
                pullToRefreshView.post(new Runnable() {
                        @Override
                        public void run() {
                                pullToRefreshView.setRefreshing(true);
                                presenter.fetchMeiZiData( page);
                        }
                });
                toolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                recyclerView.smoothScrollToPosition(0);
                        }
                });
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
        public void showProgress() {
                if (!pullToRefreshView.isShown()) {
                        pullToRefreshView.setRefreshing(true);
                }
        }

        @Override
        public void hideProgress() {
                if (pullToRefreshView.isShown()) {
                        pullToRefreshView.setRefreshing(false);
                }
        }

        @Override
        public void showErrorView() {
                canLoading = true;
                SnackBarUtil.showTipWithAction(recyclerView,getString(R.string.load_fail),getString(R.string.retry), new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                                presenter.fetchMeiZiData(page);
                        }
                });
        }

        @Override
        public void showNoMoreData() {
                canLoading=false;
                SnackBarUtil.showTipWithoutAction(recyclerView,getString(R.string.load_finished));
        }

        @Override
        public void showMeiZiList(List<Meizi> meiziList) {
                canLoading = true;
                page++;
                if (isRefresh) {
//                        new SharePrefrenceUtil<Meizi>().saveData(getApplicationContext(), meiziList, "meizi");
                        meizis.clear();
                        meizis.addAll(meiziList);
                        adapter.notifyDataSetChanged();
                        isRefresh = false;
                }
        }

        @Override
        protected int provideContentViewId() {
                return R.layout.activity_daily;
        }

        @Override
        protected void initPresenter() {
                presenter = new DailyPresenter(this, this);
                presenter.init();
        }

        @Override
        public void loadMore() {
                if (canLoading) {
                        presenter.fetchMeiZiData(page);
                        canLoading = false;
                }
        }

        @Override
        public void onRefresh() {
                isRefresh = true;
                page=1;
                presenter.fetchMeiZiData(page);
        }
}
