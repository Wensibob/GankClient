package com.bob.gank_client.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.presenter.WebViewPresenter;
import com.bob.gank_client.mvp.view.IWebView;
import com.bob.gank_client.ui.base.ToolBarActivity;
import com.bob.gank_client.utils.SnackBarUtil;

import butterknife.Bind;

/**
 * Created by bob on 17-5-11.
 */

public class WebViewActivity extends ToolBarActivity<WebViewPresenter> implements IWebView {

        private Gank gank;
        WebViewPresenter presenter;

        @Bind(R.id.toolbar)
        Toolbar toolbar;
        @Bind(R.id.web_view)
        WebView webView;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.web_ll)
        LinearLayout linearLayout;


        @Override
        public void onCreate( Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }

        @Override
        public void init() {
                gank = (Gank) getIntent().getSerializableExtra(GankConfig.GANK);
                setTitle(gank.desc);
                presenter.setWebViewSettings(webView, gank.url);
        }

        @Override
        protected void initToolBar() {
                super.initToolBar();
                actionBar = getSupportActionBar();
                if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(canBack());
                }
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                                case KeyEvent.KEYCODE_BACK:
                                        if (webView.canGoBack()) {
                                                webView.goBack();
                                        } else {
                                                finish();
                                        }
                                        return true;
                        }
                }
                return super.onKeyDown(keyCode, event);
        }



        @Override
        public void showProgress(int progress) {
                if (progressBar == null) {
                        return;
                }
                if (progress == 100) {
                        progressBar.setVisibility(View.GONE);
                } else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(progress);
                }

        }

        @Override
        public void setWebTitle(String title) {
                setTitle(title);
        }

        @Override
        public void openFailed() {
                SnackBarUtil.showTipWithoutAction(webView,getString(R.string.open_url_failed));

        }

        @Override
        protected int provideContentViewId() {
                return R.layout.activity_web;
        }

        @Override
        protected void initPresenter() {
                presenter = new WebViewPresenter(this, this);
                presenter.init();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.webview_menu,menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                        case R.id.action_refresh:
                                presenter.refresh(webView);
                                break;
                        case R.id.action_copy_url:
                                presenter.copyUrl(webView, webView.getUrl());
                                break;
                        case R.id.action_open_in_browser:
                                presenter.openInBrowser(webView.getUrl());
                                break;
                        case R.id.action_share_url:
                                presenter.shareGank(gank);
                                break;
                }
                return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onResume() {
                if (webView != null) {
                        webView.onResume();
                }
                super.onResume();
        }

        @Override
        protected void onPause() {
                if (webView != null) {
                        webView.onPause();
                }
                super.onPause();
        }

        @Override
        protected void onDestroy() {
                super.onDestroy();
                if (webView != null) {
                        webView.removeView(webView);
                        webView.removeAllViews();
                        webView.destroy();
                        webView = null;
                }
                presenter.release();
        }
}
