package com.bob.gank_client.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by bob on 17-5-3.
 */

public class SnackBarUtil {

        public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener) {
                Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
        }

        public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener, int duration) {
                Snackbar.make(view, tipText, duration).setAction(actionText, listener).show();
        }

        public static void showTipWithoutAction(View view, String tipText) {
                Snackbar.make(view, tipText,Snackbar.LENGTH_SHORT).show();
        }
}
