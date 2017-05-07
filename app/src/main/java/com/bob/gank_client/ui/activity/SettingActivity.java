package com.bob.gank_client.ui.activity;

import com.bob.gank_client.mvp.presenter.SettingPresenter;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.base.ToolBarActivity;

/**
 * Created by bob on 17-5-2.
 */

public class SettingActivity extends ToolBarActivity<SettingPresenter> implements IBaseView {
        @Override
        public void init() {

        }

        @Override
        protected int provideContentViewId() {
                return 0;
        }

        @Override
        protected void initPresenter() {

        }
}
