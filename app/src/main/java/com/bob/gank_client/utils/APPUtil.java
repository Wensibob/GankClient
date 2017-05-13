package com.bob.gank_client.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * Created by bob on 17-5-7.
 */

public class APPUtil{

        /**
         * 复制地址到剪贴板
         * @param view
         * @param text
         * @param success
         */
        public static void copyToClipboard(View view, String text, String success)
        {
                ClipData clipData = ClipData.newPlainText("copy", text);
                ClipboardManager manager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(clipData);
                SnackBarUtil.showTipWithoutAction(view, success);
        }

        public static void copyToClipboard(Context context, String text)
        {
                ClipData clipData = ClipData.newPlainText("copy", text);
                ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(clipData);
        }

        /**
         *判断wifi是否连接
         * @param context
         * @return
         */
        public static boolean isWifiConnected(Context context) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                return info != null & info.isConnected();
        }

        /**
         * 判断网络是否可用
         * @param context
         * @return
         */
        public static boolean isNetWorkAvaliable(Context context) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (manager != null) {
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info != null && info.isConnected()) {
                                if (info.getState() == NetworkInfo.State.CONNECTED) {
                                        return true;
                                }
                        }

                }
                return false;
        }
}
