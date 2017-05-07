package com.bob.gank_client.ui.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.presenter.BasePresenter;

import butterknife.Bind;

/**
 * Created by bob on 17-5-2.
 */

public abstract class ToolBarActivity <T extends BasePresenter>extends BaseActivity {
        protected ActionBar actionBar;
        protected T presenter;
        protected boolean isToolBarHiding = false;
        public  static  Toolbar toolbar;

        @Bind(R.id.app_bar)
        protected AppBarLayout appBarLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                initToolBar();
        }

        protected boolean canBack() {
                return true;
        }

        protected void initToolBar() {
                setSupportActionBar(toolbar);
                //TODO 需要在子类实现
//                actionBar = getSupportActionBar();
//                if (actionBar != null) {
//                        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//                        actionBar.setDisplayHomeAsUpEnabled(canBack());
//                }
        }

        protected void hideOrShowToolBar() {
                appBarLayout.animate()
                        .translationY(isToolBarHiding?0:-appBarLayout.getHeight())
                        .setInterpolator(new DecelerateInterpolator(2))
                        .start();
                isToolBarHiding = !isToolBarHiding;
        }
}
