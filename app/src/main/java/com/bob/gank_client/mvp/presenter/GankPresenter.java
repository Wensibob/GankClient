package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.http.GankRetrofitClient;
import com.bob.gank_client.mvp.model.GankData;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.view.IGankView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bob on 17-5-1.
 * 干货界面GankActivity的Presenter
 */

public class GankPresenter extends BasePresenter<IGankView> {

        public GankPresenter(Context context, IGankView iView) {
                super(context, iView);
        }

        @Override
        public void release() {
                if (disposable != null&&!disposable.isDisposed()) {
                        disposable.dispose();
                }
        }

        public void fetchGankData(int year, int month, int day) {
                iView.showProgress();
                disposable = GankRetrofitClient.getGankRetrofitInstance().getDailyData(year, month, day)
                        .map(new Function<GankData, List<Gank>>() {
                                @Override
                                public List<Gank> apply(@NonNull GankData gankData) throws Exception {
                                        return addAllResults(gankData.results);
                                }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Gank>>() {
                                @Override
                                public void accept(@NonNull List<Gank> gankList) throws Exception {
                                        iView.showGankList(gankList);
                                        iView.hideProgress();
                                }
                        }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                        iView.hideProgress();
                                        iView.showErrorView();
                                }
                        });
        }


        private List<Gank> addAllResults(GankData.Result results) {
                //由于一般情况下的干货数量为10-13个，Arraylist的默认大小为10，
                // 所以这里不显示制定其大小，如果是干货数量与默认大小相差较大的情况的话，那么最好是指定其大小，这样可以提高效率
                List<Gank> gankList = new ArrayList<>();
                if (results.restVideoList != null) {
                        gankList.addAll(results.restVideoList);
                }
                if (results.androidList != null) {
                        gankList.addAll(results.androidList);
                }
                if (results.iosList != null) {
                        gankList.addAll(results.iosList);
                }
                if (results.webList != null) {
                        gankList.addAll(results.webList);
                }
                if (results.appList != null) {
                        gankList.addAll(results.appList);
                }
                if (results.extendResourceList != null) {
                        gankList.addAll(results.extendResourceList);
                }
                if (results.suggestionList != null) {
                        gankList.addAll(results.suggestionList);
                }
                return gankList;
        }

}
