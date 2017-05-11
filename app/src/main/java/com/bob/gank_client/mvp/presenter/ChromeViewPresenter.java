package com.bob.gank_client.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.chromeviews.CustomFallback;
import com.bob.gank_client.ui.chromeviews.CustomTabActivityHelper;
import com.bob.gank_client.utils.SharePrefrenceUtil;

/**
 * Created by bob on 17-5-11.
 */

public class ChromeViewPresenter extends BasePresenter<IBaseView>{

        private AppCompatActivity activity;
        private CustomTabsIntent.Builder customTabsIntent;


        public ChromeViewPresenter(AppCompatActivity activity,Context context, IBaseView iView) {
                super(context, iView);
                this.activity = activity;
                customTabsIntent = new CustomTabsIntent.Builder();
                customTabsIntent.setToolbarColor(activity.getResources().getColor(R.color.colorPrimary));
                customTabsIntent.setShowTitle(true);
        }

        @Override
        public void release() {
        }

        public void openWebView(Gank gank) {
                CustomTabActivityHelper.openCustomTab(
                        activity,
                        customTabsIntent.build(),
                        Uri.parse(gank.url),
                        new CustomFallback() {
                                @Override
                                public void openUri(Activity activity, Uri uri) {
                                        super.openUri(activity, uri);
                                }
                        });
        }
}
