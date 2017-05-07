package com.bob.gank_client.mvp.presenter;

import android.content.Context;

import com.bob.gank_client.mvp.view.IWebView;

/**
 * Created by bob on 17-5-1.
 */

public class WebViewPresenter extends BasePresenter<IWebView> {

        public WebViewPresenter(Context context, IWebView iView) {
                super(context, iView);
        }

        @Override
        public void release() {

        }

        //TODO 添加类似Chrome的效果，将网页以及视频播放使用同一个Presenter
}
