package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.http.GankRetrofitClient;
import com.bob.gank_client.mvp.model.MeiziData;
import com.bob.gank_client.mvp.model.RestVideoData;
import com.bob.gank_client.mvp.view.IDailyView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bob on 17-5-1.
 */

public class DailyPresenter extends BasePresenter<IDailyView> {

        public DailyPresenter(Context context, IDailyView iView) {
                super(context, iView);
        }
        @Override
        public void release() {
                if (disposable != null&&!disposable.isDisposed()) {
                        disposable.dispose();
                }
        }

        /**
         * 获取妹子图
         * @param page
         */
        public void fetchMeiZiData(int page) {
                iView.showProgress();
                disposable= Observable.zip(
                        GankRetrofitClient.getGankRetrofitInstance().getMeiziData(page),
                        GankRetrofitClient.getGankRetrofitInstance().getRestViideoData(page),
                        new BiFunction<MeiziData, RestVideoData, MeiziData>() {
                                @Override
                                public MeiziData apply( MeiziData meiziData,  RestVideoData restVideoData) throws Exception {
                                        return createMeiZiDataWithRestVideoDesc(meiziData,restVideoData);
                                }
                        }
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<MeiziData>() {
                                           @Override
                                           public void accept(@NonNull MeiziData meiziData) throws Exception {
                                                   if (meiziData.results.size() == 0) {
                                                           iView.showNoMoreData();
                                                   } else {
                                                           iView.showMeiZiList(meiziData.results);
                                                   }
                                                   iView.hideProgress();
                                           }
                                   }, new Consumer<Throwable>() {
                                           @Override
                                           public void accept(@NonNull Throwable throwable) throws Exception {
                                                   iView.showErrorView();
                                                   iView.hideProgress();
                                           }
                                   }
                        );
        }

        /**
         * 将妹子图的信息以及休息视频的信息相结合
         * @param meiziData
         * @param restVideoData
         * @return
         */
        private MeiziData createMeiZiDataWithRestVideoDesc(MeiziData meiziData, RestVideoData restVideoData) {
                int size = Math.min(meiziData.results.size(), restVideoData.results.size());
                for(int i=0;i<size;i++) {
                        meiziData.results.get(i).desc = meiziData.results.get(i).desc + "，" + restVideoData.results.get(i).desc;
                        meiziData.results.get(i).who = restVideoData.results.get(i).who;
                }
                return meiziData;
        }
}
