package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.http.GankRetrofitClient;
import com.bob.gank_client.mvp.model.MainData;
import com.bob.gank_client.mvp.view.IMainFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bob on 17-5-1.
 */

public class MainFragmentPresenter extends BasePresenter<IMainFragmentView> {

        public MainFragmentPresenter(Context context, IMainFragmentView iView) {
                super(context, iView);
        }

        @Override
        public void release() {
                if (disposable != null&&!disposable.isDisposed()) {
                        disposable.dispose();
                }
        }

        public void loadGank(String type, int page) {
                iView.showProgress();
                 disposable=GankRetrofitClient.getGankRetrofitInstance().getMainData(type, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<MainData>() {
                                @Override
                                public void accept(@NonNull MainData mainData) throws Exception {
                                        iView.hideProgress();
                                        if (mainData.results == null) {
                                                iView.showNoMoreData();
                                        } else {
                                                iView.showListView(mainData.results);
                                        }
                                }
                        }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                        iView.hideProgress();
                                        iView.showErrorView();
                                }
                        });
        }
}
