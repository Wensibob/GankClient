/*
 * Copyright 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bob.gank_client.ui.chromeviews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.ui.activity.WebViewActivity;
import com.bob.gank_client.utils.SnackBarUtil;


/**
 * Created by Lizhaotailang on 2016/9/4.
 */

public class CustomFallback implements CustomTabActivityHelper.CustomTabFallback {

    @Override
    public void openUri(Activity activity, Gank gank) {
            activity.startActivity(new Intent(activity, WebViewActivity.class).putExtra(GankConfig.GANK, gank));
    }

        @Override
        public void openUri(Activity activity, String url) {
                activity.startActivity(new Intent(activity, WebViewActivity.class).putExtra(GankConfig.URL, url));
                Log.d("CustomFallback", "Can  resolve openUri");
        }


}
