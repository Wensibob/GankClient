package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.mvp.view.IBaseView;

/**
 * Created by bob on 17-5-1.
 */

public class SettingPresenter extends BasePresenter<IBaseView> {
        public SettingPresenter(Context context, IBaseView iView) {
                super(context, iView);
        }

        @Override
        public void release() {

        }
}
