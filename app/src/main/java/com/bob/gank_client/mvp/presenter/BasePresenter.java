package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.mvp.view.IBaseView;

import io.reactivex.disposables.Disposable;

/**
 * Created by bob on 17-5-1.
 */

public abstract class BasePresenter <T extends IBaseView>{
        protected Disposable disposable;
        protected Context context;
        protected T iView;

        public BasePresenter(Context context, T iView) {
                this.context = context;
                this.iView = iView;
        }

        public void init() {
                iView.init();
        }

        public abstract void release();
}
