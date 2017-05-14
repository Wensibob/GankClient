package com.bob.gank_client.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bob.gank_client.R;
import com.bob.gank_client.ui.fragment.LicenseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bob on 17-5-14.
 */

public class LicenseActivity extends AppCompatActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_open_license);

                LicenseFragment fragment = LicenseFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.licence_container,fragment).commit();

        }
}
