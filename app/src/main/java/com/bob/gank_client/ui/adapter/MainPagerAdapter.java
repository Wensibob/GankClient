package com.bob.gank_client.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bob.gank_client.ui.fragment.MainActivityFragment;

/**
 * Created by bob on 17-5-3.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

        String[] title = {"Android", "iOS", "前端", "瞎推荐", "拓展资源", "App"};
        public MainPagerAdapter(FragmentManager fm) {
                super(fm);
        }

        @Override
        public Fragment getItem(int position) {
                return MainActivityFragment.newInstance(title[position]);
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
