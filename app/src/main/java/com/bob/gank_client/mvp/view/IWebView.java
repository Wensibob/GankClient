package com.bob.gank_client.mvp.view;

/**
 * Created by bob on 17-5-1.
 */

public interface IWebView extends IBaseView{
        void showProgress(int progress);

        void setWebTitle(String title);

        void openFailed();
}
