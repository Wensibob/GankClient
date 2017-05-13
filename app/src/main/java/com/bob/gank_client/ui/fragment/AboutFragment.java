package com.bob.gank_client.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.presenter.AboutFragmentPresenter;
import com.bob.gank_client.mvp.view.IAboutView;
import com.bob.gank_client.utils.SnackBarUtil;

/**
 * Created by bob on 17-5-13.
 */

public class AboutFragment  extends PreferenceFragmentCompat implements IAboutView{

        private Toolbar toolbar;
        private AboutFragmentPresenter presenter;
        public  AppCompatActivity activity;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
                addPreferencesFromResource(R.xml.about_preference_fragment);
                presenter = new AboutFragmentPresenter(activity,getContext(), this);
                presenter.init();
        }

        @Override
        public void init() {
                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                findPreference("star").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.starInMarket();
                                return false;
                        }
                });

                findPreference("go_to_my_blog").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.openURL(R.string.blog_url,R.string.share_blog_url);
                                return false;
                        }
                });

                findPreference("follow_me_on_github").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.openURL(R.string.github_url,R.string.share_follow_me_on_github);
                                return false;
                        }
                });

                findPreference("contact_with_me").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.contactWithMe();
                                return false;
                        }
                });

                findPreference("feedback").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.feedBack();
                                return false;
                        }
                });

                findPreference("coffee").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.openAliPay();
                                return false;
                        }
                });

                findPreference("daimajia").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.openURL(R.string.daimajia_url,R.string.share_daimajia_url);
                                return false;
                        }
                });
                findPreference("gank_lu").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.openURL(R.string.Gank_lu_url,R.string.share_Gank_lu_url);
                                return false;
                        }
                });

                findPreference("open_source_license").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                                presenter.openLicense();
                                return false;
                        }
                });
        }

        @Override
        public void showNoAppStoreError() {
                SnackBarUtil.showTipWithoutAction(toolbar,getString(R.string.no_app_store));

        }

        @Override
        public void showNoEmailError() {
                SnackBarUtil.showTipWithoutAction(toolbar,getString(R.string.no_email));

        }

}
