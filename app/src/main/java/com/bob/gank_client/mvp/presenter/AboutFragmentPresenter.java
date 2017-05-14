package com.bob.gank_client.mvp.presenter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.view.IAboutView;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.activity.LicenseActivity;
import com.bob.gank_client.ui.chromeviews.CustomFallback;
import com.bob.gank_client.ui.chromeviews.CustomTabActivityHelper;
import com.bob.gank_client.utils.APPUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by bob on 17-5-1.
 */

public class AboutFragmentPresenter extends BasePresenter<IAboutView> {

        private CustomTabsIntent.Builder customTabsIntent;
        private AppCompatActivity activity;

        public AboutFragmentPresenter(AppCompatActivity activity,Context context, IAboutView iView) {
                super(context, iView);
                this.activity = activity;

                customTabsIntent = new CustomTabsIntent.Builder();
                customTabsIntent.setToolbarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                customTabsIntent.setShowTitle(true);
                customTabsIntent.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left);
                customTabsIntent.setExitAnimations(activity, R.anim.slide_in_left,R.anim.slide_out_right);


        }

        @Override
        public void release() {

        }

        public void starInMarket() {
                Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(intent);
                } else {
                        iView.showNoAppStoreError();
                }
        }

        public void openURL(final int res_url,final int share_res) {
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
                                        actionIntent.putExtra(Intent.EXTRA_TEXT, context.getString(share_res));
                                        actionIntent.setType("text/plain");
                                        actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        PendingIntent pi = PendingIntent.getActivity(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        customTabsIntent.setActionButton(bitmap, activity.getString(R.string.share_url_to),pi,true);

                                        CustomTabActivityHelper.openCustomTab(
                                                activity,
                                                customTabsIntent.build(),
                                                context.getString(res_url),
                                                new CustomFallback() {
                                                        @Override
                                                        public void openUri(Activity activity, String url) {
                                                                super.openUri(activity, url);
                                                        }
                                                });
                                }
                        });
        }

        public void contactWithMe() {
                try{
                        Uri uri = Uri.parse(activity.getString(R.string.sendto));
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic));
                        activity.startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                        iView.showNoEmailError();
                }

        }

        public void feedBack() {
                try{
                        Uri uri = Uri.parse(activity.getString(R.string.sendto));
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic));
                        intent.putExtra(Intent.EXTRA_TEXT,
                                activity.getString(R.string.device_model) + Build.MODEL + "\n"
                                        + activity.getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                                        + activity.getString(R.string.app_version));
                        activity.startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                        iView.showNoEmailError();
                }

        }

        public void openAliPay() {
                AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.setTitle(R.string.coffee);
                dialog.setMessage(activity.getString(R.string.donate_content));
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                APPUtil.copyToClipboard(context,activity.getString(R.string.alipay_account));
                        }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.negative), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                });
                dialog.show();

        }

        public void openLicense() {
                activity.startActivity(new Intent(activity, LicenseActivity.class));
        }

}
