package com.bob.gank_client.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bob.gank_client.mvp.presenter.ChromeViewPresenter;
import com.bob.gank_client.ui.fragment.MainActivityFragment;

/**
 * Created by bob on 17-5-3.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {
        private ChromeViewPresenter presenter;

        String[] title = {"Android", "iOS", "前端", "瞎推荐", "拓展资源", "App"};
        public MainPagerAdapter(FragmentManager fm, ChromeViewPresenter presenter) {
                super(fm);
                this.presenter = presenter;
        }

        @Override
        public Fragment getItem(int position) {
                return MainActivityFragment.newInstance(title[position],presenter);
        }

        @Override
        public int getCount() {
                return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return title[position];
        }
}
