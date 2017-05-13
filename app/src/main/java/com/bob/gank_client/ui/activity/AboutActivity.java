package com.bob.gank_client.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.presenter.AboutFragmentPresenter;
import com.bob.gank_client.mvp.presenter.BasePresenter;
import com.bob.gank_client.mvp.view.IBaseView;
import com.bob.gank_client.ui.base.BaseActivity;
import com.bob.gank_client.ui.base.ToolBarActivity;
import com.bob.gank_client.ui.fragment.AboutFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bob on 17-5-2.
 */

public class AboutActivity extends AppCompatActivity {

        @Bind(R.id.fragment_about)
        FrameLayout frameLayout;
        @Bind(R.id.toolbar)
        Toolbar toolbar;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_about);
                ButterKnife.bind(this);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                AboutFragment fragment = new AboutFragment();
                fragment.activity = AboutActivity.this;
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_about, fragment)
                        .commit();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                        case android.R.id.home:
                                onBackPressed();
                }
                return super.onOptionsItemSelected(item);
        }
}
