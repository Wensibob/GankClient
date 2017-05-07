package com.bob.gank_client.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by bob on 17-5-6.
 */

@SuppressLint("AppCompatCustomView")
public class AutoImageView extends ImageView {
        public AutoImageView(Context context) {
                super(context);
        }

        public AutoImageView(Context context, @Nullable AttributeSet attrs) {
                super(context, attrs);
        }

        public AutoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                int length = MeasureSpec.getSize(widthMeasureSpec);
                if (widthMode == MeasureSpec.AT_MOST) {
                        length = Math.max(100, length);
                }
                setMeasuredDimension(length, length * 4 / 5);
        }
}
