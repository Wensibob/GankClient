package com.bob.gank_client.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.presenter.LicenseFragmentPresenter;
import com.bob.gank_client.mvp.view.ILicenseView;
import com.bob.gank_client.ui.activity.LicenseActivity;

/**
 * Created by bob on 17-5-14.
 */

public class LicenseFragment extends Fragment implements ILicenseView {

        private static LicenseFragment licenseFragment;
        private LicenseFragmentPresenter presenter;
        private View view;
        private Toolbar toolbar;
        private WebView webView;

        public LicenseFragment() {
        }

        public static LicenseFragment newInstance() {
                if (licenseFragment == null) {
                        licenseFragment = new LicenseFragment();
                }
                return licenseFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                view = inflater.inflate(R.layout.fragment_license, container, false);
                setHasOptionsMenu(true);
                presenter = new LicenseFragmentPresenter(this.getActivity(), this);
                presenter.init();
                loadLicense("file:///android_asset/license.html");
                return view;
        }

        @Override
        public void init() {
                toolbar = (Toolbar) view.findViewById(R.id.toolbar);
                webView = (WebView) view.findViewById(R.id.web_view_license);
                AppCompatActivity activity = (LicenseActivity)getActivity();
                activity.setSupportActionBar(toolbar);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public void loadLicense(String path) {
//                WebSettings settings = webView.getSettings();
//                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.loadUrl(path);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                        case android.R.id.home:
                                getActivity().onBackPressed();
                                Log.d("TAG----------------", "点了点了点了点了");
                }
                return true;
        }
}
