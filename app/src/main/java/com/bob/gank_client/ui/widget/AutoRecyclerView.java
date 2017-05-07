package com.bob.gank_client.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by bob on 17-5-3.
 */

public class AutoRecyclerView extends RecyclerView {
        private boolean isScrollingToBottom = true;
        private FloatingActionButton floatingActionButton;
        private LoadMoreListener loadMoreListener;


        public AutoRecyclerView(Context context) {
                super(context);
        }

        public AutoRecyclerView(Context context, @Nullable AttributeSet attrs) {
                super(context, attrs);
        }

        public AutoRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
        }

        public void applyFloatingActionButton(FloatingActionButton floatingActionButton) {
                this.floatingActionButton = floatingActionButton;
        }

        public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
                this.loadMoreListener = loadMoreListener;
        }

        @Override
        public void onScrolled(int dx, int dy) {
                isScrollingToBottom = dy>0;
                if (floatingActionButton != null) {
                        if (isScrollingToBottom) {
                                if (floatingActionButton.isShown()) {
                                        floatingActionButton.hide();
                               }
                        } else {
                                if (!floatingActionButton.isShown()) {
                                        floatingActionButton.show();
                                }
                        }
                }
        }

        @Override
        public void onScrollStateChanged(int state) {
                LinearLayoutManager linearLayoutManager= (LinearLayoutManager) getLayoutManager();
                if (state == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastVisbleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                        int totalItemCount = linearLayoutManager.getItemCount();
                        if (lastVisbleItem == (totalItemCount - 1) && isScrollingToBottom) {
                                if (loadMoreListener != null) {
                                        loadMoreListener.loadMore();
                                }
                        }
                }
        }

        public interface LoadMoreListener{
                void loadMore();
        }
}


