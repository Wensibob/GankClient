package com.bob.gank_client.mvp.view;

import com.bob.gank_client.mvp.model.entity.Gank;

import java.util.List;

/**
 * Created by bob on 17-5-1.
 * 点击MeiziActivity的信息之后会跳转到当天的干货信息界面
 */

public interface IGankView extends IBaseView{
        void showGankList(List<Gank> gankList);

        void showProgress();

        void hideProgress();

        void showErrorView();
}
