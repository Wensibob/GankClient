package com.bob.gank_client.mvp.presenter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.chromeviews.CustomFallback;
import com.bob.gank_client.ui.chromeviews.CustomTabActivityHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bob on 17-5-11.
 */

public class ChromeViewPresenter extends BasePresenter<IBaseView>{

        private AppCompatActivity activity;
        private CustomTabsIntent.Builder customTabsIntent;
        private Gank gank;


        public ChromeViewPresenter(final AppCompatActivity activity, Context context, IBaseView iView) {
                super(context, iView);
                this.activity = activity;
                customTabsIntent = new CustomTabsIntent.Builder();
                customTabsIntent.setToolbarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                customTabsIntent.setShowTitle(true);
                customTabsIntent.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left);
                customTabsIntent.setExitAnimations(activity, R.anim.slide_in_left,R.anim.slide_out_right);

                //set the share action button
                Observable.create(new ObservableOnSubscribe() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                                e.onNext(android.R.drawable.ic_menu_share);
                        }
                }).subscribeOn(Schedulers.newThread())
                    .map(new Function<Integer, Bitmap>() {
                        @Override
                        public Bitmap apply(@NonNull Integer integer) throws Exception {
                                return BitmapFactory.decodeResource(activity.getResources(), integer);
                        }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Bitmap>() {
                                @Override
                                public void accept(@NonNull Bitmap bitmap) throws Exception {
                                        Intent actionIntent = new Intent(Intent.ACTION_SEND);
                                        actionIntent.putExtra(Intent.EXTRA_TEXT, "gank" );
                                        actionIntent.setType("text/plain");
                                        actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        PendingIntent pi = PendingIntent.getActivity(activity, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        customTabsIntent.setActionButton(bitmap, activity.getString(R.string.share_gank_to),pi,true);
                                }
                        });
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
                                        ChromeViewPresenter.this.gank = gank;
                                        Log.d("Gank", gank.toString());
                                        super.openUri(activity, gank);
                                }
                        });
        }
}
