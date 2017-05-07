package com.bob.gank_client.mvp.view;

import com.bob.gank_client.mvp.model.entity.Meizi;

import java.util.List;

/**
 * Created by bob on 17-5-1.
 * 显示妹子图以及休息视频的每日界面
 */

public interface IDailyView extends IBaseView{
        void showProgress();

        void hideProgress();

        void showErrorView();

        void showNoMoreData();

        void showMeiZiList(List<Meizi> meiziList);
}
