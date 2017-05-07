package com.bob.gank_client.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by bob on 17-5-1.
 */

public class ShareUtil {
        public static void shareImage(Context context, Uri uri, String title) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/jpeg");
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(shareIntent, title));
        }

        //TODO 分享图片如上，分享链接的时候需要做到如简书的效果

        //TODO 分享APP的时候可以适当花些功夫


}
