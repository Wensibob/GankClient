package com.bob.gank_client.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bob on 17-5-7.
 */

public class GankAdapter extends  RecyclerView.Adapter<GankAdapter.GankHolder>{
        private  List<Gank> gankList;
        private Context context;

        public GankAdapter(List<Gank> gankList, Context context) {
                this.gankList = gankList;
                this.context = context;
        }

        @Override
        public GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
                return new GankHolder(view);
        }

        @Override
        public void onBindViewHolder(GankHolder holder, int position) {
                Gank gank = gankList.get(position);
                holder.cardView.setTag(gank);
                if (position == 0) {
                        showCategeory(true, holder.tvCategeory);
                } else {
                        if (gankList.get(position).type.equals(gankList.get(position - 1).type)) {
                                showCategeory(false, holder.tvCategeory);
                        } else {
                                showCategeory(true,holder.tvCategeory);
                        }
                }
                holder.tvCategeory.setText(gank.type);
                holder.tv_title.setText(gank.desc);
                holder.tv_author.setText(gank.who);
        }

        private void showCategeory(boolean isShow, TextView tvCategeory) {
                if (isShow) {
                        tvCategeory.setVisibility(View.VISIBLE);
                } else {
                        tvCategeory.setVisibility(View.GONE);
                }

        }

        @Override
        public int getItemCount() {
                return gankList.size();
        }

        static class GankHolder extends RecyclerView.ViewHolder {

                @Bind(R.id.tv_gank_item_categeory)
                TextView tvCategeory;
                @Bind(R.id.tv_gank_item_title)
                TextView tv_title;
                @Bind(R.id.tv_gank_item_author)
                TextView tv_author;
                @Bind(R.id.gank_item_card_view)
                CardView cardView;

                @OnClick(R.id.gank_item_card_ll)
                void gankClick() {
                        //TODO 出发点击事件启动WebActivity
                }

                public GankHolder(View itemView) {
                        super(itemView);
                        ButterKnife.bind(this, itemView);
                }
        }


}
