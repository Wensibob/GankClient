package com.bob.gank_client.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.R;
import com.bob.gank_client.ShareElement;
import com.bob.gank_client.mvp.model.entity.Meizi;
import com.bob.gank_client.mvp.presenter.MeiZiPresenter;
import com.bob.gank_client.ui.base.ToolBarActivity;
import com.bob.gank_client.mvp.view.IMeiZiView;
import com.bob.gank_client.utils.DateUtil;
import com.bob.gank_client.utils.FileUtil;
import com.bob.gank_client.utils.SnackBarUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by bob on 17-5-4.
 */

public class MeiZiActivity extends ToolBarActivity<MeiZiPresenter> implements IMeiZiView {
        Meizi meizi;
        MeiZiPresenter presenter;
        PhotoViewAttacher attacher;
        Bitmap bitmap;

        @Bind(R.id.iv_picture_meizi)
        ImageView imageView;

        @Override
        public void init() {
                appBarLayout.setAlpha(0.6f);
                meizi = (Meizi) getIntent().getSerializableExtra(GankConfig.MEIZI);
                imageView.setImageDrawable(ShareElement.shareDrawable);
                ViewCompat.setTransitionName(imageView,GankConfig.TRANSLATE_GIRL_VIEW);
                attacher = new PhotoViewAttacher(imageView);
                Glide.with(this)
                        .load(meizi.url)
                        .asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imageView.setImageBitmap(resource);
                                attacher.update();
                                bitmap = resource;
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                imageView.setImageDrawable(errorDrawable);
                        }
                });
                attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                        @Override
                        public void onPhotoTap(View view, float x, float y) {
                                hideOrShowToolBar();
                        }
                });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.menu_meizi, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                        case R.id.meizi_save:
                                if (!FileUtil.isSDCardEnable() || bitmap == null) {
                                        SnackBarUtil.showTipWithoutAction(imageView, getString(R.string.save_meizi_error));
                                } else {
                                        presenter.saveMeiZiImage(bitmap, DateUtil.toDateTimeStr(meizi.publishedAt).toString());
                                }
                                break;
                        case R.id.meizi_share:
                                presenter.shareGirlImage(bitmap, DateUtil.toDateTimeStr(meizi.publishedAt).toString());
                                break;
                }
                return super.onOptionsItemSelected(item);
        }

        @Override
        public void showSaveGirlResult(String result) {
                SnackBarUtil.showTipWithoutAction(imageView,result);

        }

        @Override
        protected int provideContentViewId() {
                return R.layout.meizi_activity;
        }

        @Override
        protected void initPresenter() {
                presenter = new MeiZiPresenter(this, this);
                presenter.init();
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
        protected void onDestroy() {
                super.onDestroy();
                ShareElement.shareDrawable = null;
                presenter.release();
                attacher.cleanup();
        }
}
