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
    int itemloadtruoc = 10;
    RecyclerView.LayoutManager layoutManager;
    ILoadMore iLoadMore;
    private boolean isLoading;

    public LoadMoreScroll(RecyclerView.LayoutManager layoutManager, ILoadMore iLoadMore) {
        this.layoutManager = layoutManager;
        this.iLoadMore = iLoadMore;
        this.isLoading = false;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        tongitem = layoutManager.getItemCount();

        if(layoutManager instanceof LinearLayoutManager) {
            itemandautien = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else if(layoutManager instanceof GridLayoutManager){
            itemandautien = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
//        Log.d("testsc", "tongitem:"+ tongitem + "-" + "itemandautien:" + itemandautien + "-" + "itemloadtruoc:" + itemloadtruoc);
        if(tongitem > (itemandautien + itemloadtruoc)) {
            this.isLoading = false;
        }

        if(!isLoading && (tongitem <= (itemandautien + itemloadtruoc))) {
            iLoadMore.LoadMore(tongitem);
            this.isLoading = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }
}
