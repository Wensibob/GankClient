package com.bob.gank_client.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.view.IWebView;
import com.bob.gank_client.utils.APPUtil;
import com.bob.gank_client.utils.ShareUtil;

import java.net.URI;

/**
 * Created by bob on 17-5-1.
 */

public class WebViewPresenter extends BasePresenter<IWebView> {
        //TODO 添加类似Chrome的效果，将网页以及视频播放使用同一个Presenter

        public WebViewPresenter(Context context, IWebView iView) {
                super(context, iView);
        }

        @Override
        public void release() {
        }

        public void setWebViewSettings(WebView webView, String url) {
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setLoadWithOverviewMode(true);
                settings.setAppCacheEnabled(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settings.setSupportZoom(true);
                webView.setWebChromeClient(new ChromeClient());
                webView.setWebViewClient(new GankWebClient());
                webView.loadUrl(url);
        }

        public void refresh(WebView webView) {
                webView.reload();
        }

        public void copyUrl(View view, String text) {
                APPUtil.copyToClipboard(view,text, context.getString(R.string.copy_successed));
        }

        public void openInBrowser(String url) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                } else {
                        iView.openFailed();
                }
        }

        public void moreOperation(Gank gank) {
                if (gank != null) {
                        ShareUtil.shareGank(context,gank);
                }
        }


        private class ChromeClient extends WebChromeClient {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        iView.showProgress(newProgress);
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        iView.setWebTitle(title);
                }
        }

        private class GankWebClient extends WebViewClient {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url ) {
                        if (url != null) {
                                view.loadUrl(url);
                        }
                        return true;
                }
        }


}

