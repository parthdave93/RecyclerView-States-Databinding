package com.parthdave.recyclerviewbindinglibrary.bindings;

import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.parthdave.recyclerviewbindinglibrary.states.RecyclerviewErrorStateType;
import com.parthdave.recyclerviewbindinglibrary.states.SwipeRefreshStates;

import me.tatarka.bindingcollectionadapter2.ItemBinding;


/**
 * Created by Parth Dave on 9/5/17.
 * parth.dave93@gmail.com
 */

public abstract class ListBindingProviders<T> {
    private final ObservableField<RecyclerviewErrorStateType> recyclerViewState = new ObservableField<>(RecyclerviewErrorStateType.LoadingStarted);
    private final ObservableField<SwipeRefreshStates> swipeRefreshStates = new ObservableField<>(SwipeRefreshStates.NotRefreshing);
    
    public boolean isSwipeRefreshEnabled = true;
    public boolean isLoadMoreEnabled = false;
    private String message;
    
    public abstract ObservableList<T> getItems();
    
    public abstract ItemBinding<T> getItemBinding();
    
    public void setSwipeRefreshEnabled(boolean swipeRefreshEnabled) {
        isSwipeRefreshEnabled = swipeRefreshEnabled;
    }
    
    public boolean isSwipeRefreshEnabled() {
        return isSwipeRefreshEnabled;
    }
    
    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
    }
    
    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public ObservableField<RecyclerviewErrorStateType> getRecyclerViewState() {
        return recyclerViewState;
    }
    
    
    public void setRecyclerViewState(RecyclerviewErrorStateType recyclerviewErrorStateType) {
        recyclerViewState.set(recyclerviewErrorStateType);
    }
    
    public ObservableField<SwipeRefreshStates> getSwipeRefreshStates() {
        return swipeRefreshStates;
    }
    
    public void setSwipeRefreshStates(SwipeRefreshStates state) {
        swipeRefreshStates.set(state);
    }
    
    
}