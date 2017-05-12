package com.bob.gank_client.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.chromeviews.CustomFallback;
import com.bob.gank_client.ui.chromeviews.CustomTabActivityHelper;

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
                customTabsIntent.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left);
                customTabsIntent.setExitAnimations(activity, R.anim.slide_in_left,R.anim.slide_out_right);

        }

        @Override
        public void release() {
        }

        public void openWebView(Gank gank) {
                CustomTabActivityHelper.openCustomTab(
                        activity,
                        customTabsIntent.build(),
                        gank,
                        new CustomFallback() {
                                @Override
                                public void openUri(Activity activity, Gank gank) {
                                        Log.d("Gank", gank.toString());
                                        super.openUri(activity, gank);
                                }
                        });
        }
}
