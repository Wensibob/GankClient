package com.bob.gank_client.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.presenter.ChromeViewPresenter;
import com.bob.gank_client.mvp.presenter.MainFragmentPresenter;
import com.bob.gank_client.mvp.view.IMainFragmentView;
import com.bob.gank_client.ui.activity.MainActivity;
import com.bob.gank_client.ui.adapter.MainRecyclerViewAdapter;
import com.bob.gank_client.ui.base.BaseFragment;
import com.bob.gank_client.ui.widget.AutoRecyclerView;
import com.bob.gank_client.utils.SnackBarUtil;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by bob on 17-5-3.
 */

public class MainActivityFragment extends BaseFragment<MainFragmentPresenter> implements IMainFragmentView,PullToRefreshView.OnRefreshListener,AutoRecyclerView.LoadMoreListener{

        private static final String TYPE = "type";
        private String type;
        private MainRecyclerViewAdapter mainRecyclerViewAdapter;
        private List<Gank> gankList;
        private int page = 1;
        private boolean isRefresh = true;
        private boolean canLoading = true;
        private static ChromeViewPresenter chromeViewPresenter;

        @Bind(R.id.recycler_view)
        AutoRecyclerView recyclerView;
        @Bind(R.id.pull_to_refresh)
        PullToRefreshView pullToRefreshView;

        public MainActivityFragment() {
        }

        public static MainActivityFragment newInstance(String type, ChromeViewPresenter presenter) {
                chromeViewPresenter = presenter;
                MainActivityFragment fragment = new MainActivityFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TYPE, type);
                fragment.setArguments(bundle);
                return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (getArguments() != null) {
                        type = getArguments().getString(TYPE);
                }
        }

        @Override
        public void init() {
                gankList = new ArrayList<>();
                mainRecyclerViewAdapter = new MainRecyclerViewAdapter(chromeViewPresenter,gankList, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(mainRecyclerViewAdapter);
                recyclerView.setLoadMoreListener(this);
                recyclerView.applyFloatingActionButton(MainActivity.fab);
                pullToRefreshView.setOnRefreshListener(this);
                pullToRefreshView.post(new Runnable() {
                        @Override
                        public void run() {
                                pullToRefreshView.setRefreshing(true);
                                presenter.loadGank(type, page);
                        }
                });
                MainActivity. toolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                //TODO viewpager中的Toolbar的点击事件无法调用recyclerview
                                SnackBarUtil.showTipWithoutAction(recyclerView,"点击了toolbar也没用==~~==");
                                recyclerView.scrollToPosition(0);
//                                recyclerView.smoothScrollToPosition(2);
                        }
                });

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
                                presenter.loadGank(type,page);
                        }
                });

        }

        @Override
        public void showNoMoreData() {
                canLoading=false;
                SnackBarUtil.showTipWithoutAction(recyclerView,getString(R.string.load_finished));

        }

        /**
         * 上拉刷新
         * @param gankList
         */
        @Override
        public void showListView(List<Gank> gankList) {
                canLoading = true;
                page++;
                if (isRefresh) {
                        //下拉刷新
                        this.gankList.clear();
                        this.gankList.addAll(gankList);
                        mainRecyclerViewAdapter.notifyDataSetChanged();
                        isRefresh = false;
                } else {
                        //上拉刷新
                        this.gankList.addAll(gankList);
                        mainRecyclerViewAdapter.notifyDataSetChanged();
                }


        }

        @Override
        protected int provideViewLayout() {
                return R.layout.activity_fragment_main;
        }

        @Override
        protected void initPresenter() {
                presenter = new MainFragmentPresenter(getContext(), this);
                presenter.init();
        }

        /**
         * 下拉刷新
         */
        @Override
        public void onRefresh() {
                isRefresh = true;
                page=1;
                presenter.loadGank(type, page);

        }

        /**
         * 上拉刷新
         */
        @Override
        public void loadMore() {
                if (canLoading) {
                        presenter.loadGank(type, page);
                        canLoading = false;
                }
        }

        @Override
        public void onDestroyView() {
                super.onDestroyView();
                presenter.release();
        }
}
