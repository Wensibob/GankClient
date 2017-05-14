package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.mvp.view.ILicenseView;

/**
 * Created by bob on 17-5-14.
 */

public class LicenseFragmentPresenter extends BasePresenter<ILicenseView> {

        public LicenseFragmentPresenter(Context context, ILicenseView iView) {
                super(context, iView);
        }

        @Override
        public void release() {

        }
}
