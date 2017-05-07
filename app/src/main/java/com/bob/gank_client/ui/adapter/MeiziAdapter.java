package com.bob.gank_client.ui.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.R;
import com.bob.gank_client.ShareElement;
import com.bob.gank_client.mvp.model.entity.Meizi;
import com.bob.gank_client.ui.activity.GankActivity;
import com.bob.gank_client.ui.activity.MeiZiActivity;
import com.bob.gank_client.utils.DateUtil;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bob on 17-5-6.
 */

public class MeiziAdapter extends RecyclerView.Adapter <MeiziAdapter.MeiziHolder>{
        List<Meizi> list;
        static Context context;
        int lastPosition = 0;

        public MeiziAdapter(List<Meizi> list, Context context) {
                this.list = list;
                this.context = context;
        }

        @Override
        public MeiziHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
                return new MeiziHolder(view);
        }

        @Override
        public void onBindViewHolder(MeiziHolder holder, int position) {
                final Meizi meizi = list.get(position);
                holder.card.setTag(meizi);
                Glide.with(context)
                        .load(meizi.url)
                        .crossFade()
                        .into(holder.ivMeizi);
                holder.tv_time.setText(DateUtil.toDateTimeStr(meizi.publishedAt));
                holder.tv_desc.setText(meizi.desc);
                showItemAnimation(holder, position);
        }

        @Override
        public int getItemCount() {
                return list.size();
        }

        private void showItemAnimation(MeiziHolder holder, int position) {
                if (position > lastPosition) {
                        lastPosition = position;
                        ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                                .setDuration(500)
                                .start();
                }
        }
        static class MeiziHolder extends RecyclerView.ViewHolder {

                @Bind(R.id.iv_meizi)
                ImageView ivMeizi;
                @Bind(R.id.tv_time)
                TextView tv_time;
                @Bind(R.id.tv_desc)
                TextView tv_desc;

                @OnClick(R.id.iv_meizi)
                void meiziClick() {
                        ShareElement.shareDrawable = ivMeizi.getDrawable();
                        Intent intent = new Intent(context, MeiZiActivity.class);
                        intent.putExtra(GankConfig.MEIZI, (Serializable) card.getTag());
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, ivMeizi, GankConfig.TRANSLATE_GIRL_VIEW);
                        ActivityCompat.startActivity((Activity)context, intent, optionsCompat.toBundle());
                }

                @OnClick(R.id.rl_gank)
                void gankClick() {
                        ShareElement.shareDrawable = ivMeizi.getDrawable();
                        Intent intent = new Intent(context, GankActivity.class);
                        intent.putExtra(GankConfig.MEIZI, (Serializable) card.getTag());
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, ivMeizi, GankConfig.TRANSLATE_GIRL_VIEW);
                        ActivityCompat.startActivity((Activity)context, intent, optionsCompat.toBundle());
                }

                View card;
                public MeiziHolder(View itemView) {
                        super(itemView);
                        card = itemView;
                        ButterKnife.bind(this, itemView);
                }
        }
}

