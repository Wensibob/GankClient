package com.bob.gank_client.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.view.IBaseView;

/**
 * Created by bob on 17-5-1.
 */

public class AboutPresenter extends BasePresenter<IBaseView> {
        public AboutPresenter(Context context, IBaseView iView) {
                super(context, iView);
        }

        @Override
        public void release() {

        }

        public void starInMarket() {
                Uri uri = Uri.parse(String.format(context.getString(R.string.market), context.getPackageName()));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(context.getPackageManager() )!= null) {
                        context.startActivity(intent);
                }
        }

}
