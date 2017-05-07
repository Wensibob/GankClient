package com.bob.gank_client.mvp.view;

import com.bob.gank_client.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by bob on 17-5-1.
 */

public interface IMainFragmentView extends IBaseView{
        void showProgress();

        void hideProgress();

        void showErrorView();

        void showNoMoreData();

        void showListView(List<Gank> gankList);
}
