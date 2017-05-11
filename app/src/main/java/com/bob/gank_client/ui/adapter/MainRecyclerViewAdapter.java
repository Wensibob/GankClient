package com.bob.gank_client.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bob.gank_client.GankConfig;
import com.bob.gank_client.R;
import com.bob.gank_client.mvp.model.entity.Gank;
import com.bob.gank_client.mvp.presenter.ChromeViewPresenter;
import com.bob.gank_client.ui.activity.WebActivity;
import com.bob.gank_client.ui.activity.WebViewActivity;
import com.bob.gank_client.ui.chromeviews.CustomTabActivityHelper;
import com.bob.gank_client.utils.DateUtil;
import com.bob.gank_client.utils.SnackBarUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bob on 17-5-3.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainMainRecyclerViewHolder> {

        List<Gank> gankList;
        static Context context;
        int lastPosition = 0;
        static ChromeViewPresenter presenter;

        public MainRecyclerViewAdapter(ChromeViewPresenter presenter,List<Gank> gankList, Context context) {
                this.presenter = presenter;
                this.gankList = gankList;
                this.context = context;
        }

        @Override
        public MainMainRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_fragment, parent, false);
                return new MainMainRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainMainRecyclerViewHolder holder, int position) {
                Gank gank = gankList.get(position);
                holder.tv_title.setTag(gank);
                //判断日期是否需要显示
                if (position == 0) {
                        showDateTime(true, holder.tv_date);
                } else {
                        if (gank.publishedAt.equals(gankList.get(position - 1).publishedAt)) {
                                showDateTime(false, holder.tv_date);
                        } else {
                                showDateTime(true,holder.tv_date);
                        }
                }
                holder.tv_date.setText(DateUtil.toDateTimeStr(gank.publishedAt));
                holder.tv_title.setText(gank.desc);
                holder.tv_author.setText(gank.who);
                showItemAnimation(holder,position);
        }

        private void showItemAnimation(MainMainRecyclerViewHolder holder, int position) {
                if (position > lastPosition) {
                        lastPosition = position;
                        ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                                .setDuration(500)
                                .start();
                }
        }

        private void showDateTime(boolean isShow, TextView tvDate) {
                if (isShow) {
                        tvDate.setVisibility(View.VISIBLE);
                } else {
                        tvDate.setVisibility(View.GONE);
                }
        }

        @Override
        public int getItemCount() {
                return gankList.size();
        }

        static class MainMainRecyclerViewHolder extends RecyclerView.ViewHolder {
                @Bind(R.id.tv_main_fragment_item_date)
                TextView tv_date;
                //TODO 如果运行正常，这里可以去掉
                @Bind(R.id.main_fragment_card_ll)
                LinearLayout linearLayout;
                @Bind(R.id.tv_main_frgment_item_title)
                TextView tv_title;
                @Bind(R.id.tv_main_frgment_item_author)
                TextView tv_author;

                @OnClick(R.id.main_fragment_card_view)
                void cardClick() {
                        //TODO 打开webActivity
                        //tv_title.gettag()
//                        SnackBarUtil.showTipWithoutAction(linearLayout,((Gank)tv_title.getTag()).desc);

                        presenter.openWebView((Gank)tv_title.getTag());
//
//
//                        Intent intent = new Intent(context, WebViewActivity.class);
//                        intent.putExtra(GankConfig.GANK,(Gank)tv_title.getTag());
//                        context.startActivity(intent);
                }

                View card;
                public MainMainRecyclerViewHolder(View itemView) {
                        super(itemView);
                        card = itemView;
                        ButterKnife.bind(this,itemView);
                }
        }
}

