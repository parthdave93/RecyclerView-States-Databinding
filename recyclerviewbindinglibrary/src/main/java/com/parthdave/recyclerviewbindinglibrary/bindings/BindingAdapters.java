package com.parthdave.recyclerviewbindinglibrary.bindings;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.parthdave.recyclerviewbindinglibrary.ItemDecorators.DividerItemDecoration;
import com.parthdave.recyclerviewbindinglibrary.ItemDecorators.MarginTopRightBottomDecorator;
import com.parthdave.recyclerviewbindinglibrary.R;
import com.parthdave.recyclerviewbindinglibrary.listeners.OnLoadMoreListener;
import com.parthdave.recyclerviewbindinglibrary.states.SwipeRefreshStates;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Parth Dave on 9/5/17.
 * parth.dave93@gmail.com
 */


public class BindingAdapters {
    public static final int recyclerViewMargins = 20;
    
    @BindingAdapter("bind:dividers")
    public static void bindRecyclerView(RecyclerView recyclerView, String dividerType) {
        switch (dividerType) {
            case "margin":
                recyclerView.addItemDecoration(getMarginForRecyclerView());
                break;
            case "bottomDivider":
                recyclerView.addItemDecoration(getDivider(recyclerView.getContext(), false));
                break;
            case "bottomMargin":
                recyclerView.addItemDecoration(getBottomMarginForRecyclerView());
                break;
        }
    }
    
    public static DividerItemDecoration getDivider(Context context, boolean isForSide) {
        return new DividerItemDecoration(context, R.drawable.divider);
    }
    
    public static MarginTopRightBottomDecorator getMarginForRecyclerView() {
        return getMarginForRecyclerView(MarginTopRightBottomDecorator.MarginType.TopLeftRightBottom);
    }
    
    public static MarginTopRightBottomDecorator getBottomMarginForRecyclerView() {
        return getMarginForRecyclerView(MarginTopRightBottomDecorator.MarginType.Bottom);
    }
    
    public static MarginTopRightBottomDecorator getLeftRightMarginForRecyclerView() {
        return getMarginForRecyclerView(MarginTopRightBottomDecorator.MarginType.LeftRight);
    }
    
    public static MarginTopRightBottomDecorator getMarginForRecyclerView(MarginTopRightBottomDecorator.MarginType marginType) {
        return new MarginTopRightBottomDecorator(recyclerViewMargins, marginType);
    }
    
    @BindingAdapter({"bind:refreshingState","bind:onRefreshListener","bind:enabled"})
    public static void swipeRefresh(SwipeRefreshLayout swipeRefreshLayout, ObservableField<SwipeRefreshStates> refreshingState, SwipeRefreshLayout.OnRefreshListener onRefreshListener, boolean enabled){
        swipeRefreshLayout.setRefreshing(refreshingState.get().equals(SwipeRefreshStates.Refreshing));
        swipeRefreshLayout.setEnabled(enabled);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }
    
    @BindingAdapter(value = {"bind:loadMoreEnabled","bind:onLoadMoreListener"},requireAll = false)
    public static void recyclerViewLoadmoreoptions(RecyclerView recyclerView, boolean loadMoreEnabled, final OnLoadMoreListener onLoadMoreListener){
        if(loadMoreEnabled) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1)) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
        
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
        
    }
}
