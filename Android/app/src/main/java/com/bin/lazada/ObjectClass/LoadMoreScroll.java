package com.bin.lazada.ObjectClass;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class LoadMoreScroll extends RecyclerView.OnScrollListener {

    int itemandautien = 0;
    int tongitem = 0;
    int itemloadtruoc = 5;
    RecyclerView.LayoutManager layoutManager;
    ILoadMore iLoadMore;
    private boolean isLoading;

    public LoadMoreScroll(RecyclerView.LayoutManager layoutManager, ILoadMore iLoadMore) {
        this.layoutManager = layoutManager;
        this.iLoadMore = iLoadMore;
        setLoaded();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        tongitem = layoutManager.getItemCount();

        if(layoutManager instanceof LinearLayoutManager) {
            itemandautien = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if(layoutManager instanceof GridLayoutManager){
            itemandautien = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if(!isLoading && (tongitem <= (itemandautien + itemloadtruoc))) {
            iLoadMore.LoadMore(tongitem);
            isLoading = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public void setLoaded() {
        isLoading = false;
    }
}
