package com.bob.gank_client.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.view.IMeiZiView;
import com.bob.gank_client.utils.FileUtil;
import com.bob.gank_client.utils.ShareUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bob on 17-5-1.
 */

public class MeiZiPresenter extends BasePresenter<IMeiZiView> {

        public MeiZiPresenter(Context context, IMeiZiView iView) {
                super(context, iView);
        }

        @Override
        public void release() {
                if (disposable != null&&!disposable.isDisposed()) {
                        disposable.dispose();
                }
        }

        public void saveMeiZiImage(final Bitmap bitmap, final String title) {
                disposable= Observable.create(new ObservableOnSubscribe<Uri>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Uri> e) throws Exception {
                                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                                if (uri == null) {
                                        e.onError(new Exception(context.getString(R.string.save_girl_failly)));
                                } else {
                                        e.onNext(uri);
                                        e.onComplete();
                                }
                        }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Uri>() {
                                @Override
                                public void accept(@NonNull Uri uri) throws Exception {
                                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                                        context.sendBroadcast(scannerIntent);
                                        iView.showSaveGirlResult(context.getString(R.string.save_girl_successfully));
                                }
                        }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                        iView.showSaveGirlResult(throwable.getMessage());
                                }
                        });
        }

        public void shareGirlImage(final Bitmap bitmap, final String title) {
                Observable.create(
                        new ObservableOnSubscribe<Uri>() {
                                @Override
                                public void subscribe(@NonNull ObservableEmitter<Uri> e) throws Exception {
                                        Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                                        if (uri == null) {
                                                Log.d("log", "uri is null");
                                                e.onError(new Exception(context.getString(R.string.save_girl_failly)));
                                        } else {
                                                e.onNext(uri);
                                                e.onComplete();
                                        }
                                }
                        }

                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Uri>() {
                                @Override
                                public void accept(@NonNull Uri uri) throws Exception {
                                        ShareUtil.shareImage(context,uri,context.getString(R.string.share_girl_to));
                                }
                        }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                        Log.d("log", "can not share the image");
                                        iView.showSaveGirlResult(throwable.getMessage());
                                }
                        });
        }

}
