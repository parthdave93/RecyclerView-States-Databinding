package com.parthdave.databindingandrecyclerview.ui;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.parthdave.databindingandrecyclerview.R;
import com.parthdave.databindingandrecyclerview.bindings.ListBindings;
import com.parthdave.databindingandrecyclerview.databinding.ActivityMainBinding;
import com.parthdave.databindingandrecyclerview.dummymodelsprovider.UserModelsProvider;
import com.parthdave.recyclerviewbindinglibrary.listeners.NetworkTryAgainListener;
import com.parthdave.recyclerviewbindinglibrary.listeners.OnLoadMoreListener;
import com.parthdave.recyclerviewbindinglibrary.states.RecyclerviewErrorStateType;
import com.parthdave.recyclerviewbindinglibrary.states.SwipeRefreshStates;

/**
 * Created by Parth Dave on 9/5/17.
 * parth.dave93@gmail.com
 */

public class MainActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener,NetworkTryAgainListener,OnLoadMoreListener {
    ListBindings.UserListingBinding userListingBinding;
    ActivityMainBinding activityMainBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setNetworkListener(this);
        activityMainBinding.setOnRefreshListener(this);
        activityMainBinding.setOnLoadMoreListener(this);
    
    
        userListingBinding = new ListBindings.UserListingBinding();
        userListingBinding.setRecyclerViewState(RecyclerviewErrorStateType.NetworkError);
        userListingBinding.setLoadMoreEnabled(true);
        activityMainBinding.setListingProvider(userListingBinding);
    }
    
    @Override
    public void onRefresh() {
        Toast.makeText(this,"OnRefresh",Toast.LENGTH_SHORT).show();
        loadData();
        userListingBinding.setSwipeRefreshStates(SwipeRefreshStates.Refreshing);
    }
    
    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"data loaded",Toast.LENGTH_SHORT).show();
                userListingBinding.setRecyclerViewState(RecyclerviewErrorStateType.Loaded);
                userListingBinding.setSwipeRefreshStates(SwipeRefreshStates.NotRefreshing);
                userListingBinding.getItems().addAll(UserModelsProvider.getUserModels());
            }
        },2000);
    }
    
    @Override
    public void onLoadMore() {
        Toast.makeText(this,"onLoadMore",Toast.LENGTH_SHORT).show();
        loadData();
        userListingBinding.setRecyclerViewState(RecyclerviewErrorStateType.LoadMore);
    }
    
    @Override
    public void onTryAgainClick() {
        Toast.makeText(this,"onTryAgainClick",Toast.LENGTH_SHORT).show();
        loadData();
        userListingBinding.setRecyclerViewState(RecyclerviewErrorStateType.LoadingStarted);
    }
}
