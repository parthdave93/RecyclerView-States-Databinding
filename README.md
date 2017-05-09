# RecyclerView-States-Databinding
RecyclerView States &amp; Databinding

<img src="/captures/functionality.gif"/>


Databinding provides an easier way to manage application with less code. let's start by network states and recyclerview items.

Test cases for an application item listing:
1. Network not available
2. Network Available but not found in result
3. Item found and shown in listings
4. Load More functionality
5. Pull to refresh functionality
6. recyclerview dividers

All above this can be managed easily with databinding.
First I'm using evant RecyclerView databinding library here.

````
    compile 'me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:2.0.1'
    compile 'me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:2.0.1'
````

Basics:
Things we need 
1. Common Recyclerview layout which will have no internet image, one message to show messages, recyclerview itself
2. Common bindings of listeners
3. List Binder with listeners
4. Connect All things
5. Implement 

<br/>

# Step 1:

<br/>
Common Recyclerview layout which will have no internet image, one message to show messages, recyclerview itself

So 
1. Framelayout as parent
2. Add no internet and all messages in one linearlayout
3. Progress bars for loadmore and loding
4. Recyclerview

```

<FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:background="your background">


        <LinearLayout
            android:id="@+id/llNoInternetConnection"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:orientation="vertical">
            
            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@drawable/no_internet_connection"/>
                
            <TextView
                style="@style/Button.selector_btn_green"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/try_again"
                android:clickable="true"
                android:layout_margin="@dimen/_15sdp"
                android:layout_gravity="center_horizontal"
                android:onClick="@{(v)->networkListener.onTryAgainClick()}"/>
                
            <TextView
                android:id="@+id/tvMessageText"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="@{provider.message}"/>
        </LinearLayout>
        
        <!-- this will be shown at loading of recyclerview data no need to block ui -->
        
        <ProgressBar
            android:id="@+id/progressbar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            />
            
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            <android.support.v4.widget.SwipeRefreshLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent">
                
                <android.support.v7.widget.RecyclerView
                    android:id="@+id/rvList"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:clipToPadding="false"
                    android:paddingBottom="@dimen/_60sdp"
                    android:layout_marginTop="@dimen/_5sdp"/>
                    
            </android.support.v4.widget.SwipeRefreshLayout>
            
        </LinearLayout>
        
        <ProgressBar
            android:id="@+id/loadMoreProgress"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|center_horizontal"/>
            
    </FrameLayout>
    
```
<br/>

#Step 2:

<br/>
Common bindings of listeners

```
public interface NetworkTryAgainListener {
    public void onTryAgainClick();
}
```

```
public interface OnLoadMoreListener {
  void onLoadMore();
}
```

<br/>

#Step 3:

<br/>
Binding providers and states

There will be below possibility of recyclerview 
1. network error
2. No items found for which we will be showing message
3. Loading state
4. Loadmore state
5. Loaded state

```
public enum RecyclerviewErrorStateType {
    NetworkError,NoItemsFoundError,LoadingStarted,Loaded,LoadMore
}
```

While for swipe to refresh view we will be having 2 states
1. Refreshing
2. Refreshed

```
public enum SwipeRefreshStates {
    Refreshing,NotRefreshing
}
```

Needing one abstract class in which we manage states of recyclerview and swipe to refresh view 


```
public abstract class ListBindingProviders<T> {
    //for recyclerview and progress bar states
    private final ObservableField<RecyclerviewErrorStateType> recyclerViewState = new ObservableField<>(RecyclerviewErrorStateType.LoadingStarted);
    //swipe layout states
    private final ObservableField<SwipeRefreshStates> swipeRefreshStates = new ObservableField<>(SwipeRefreshStates.NotRefreshing);
    
    //check if swipe refresh layout needed or not
    public boolean isSwipeRefreshEnabled = true;
    //check if loadmore needed or not 
    public boolean isLoadMoreEnabled = false;
    //show error messages
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
```
<br/>

#Step 4:

<br/>
Connect All things with xml


````
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:bind="http://schemas.android.com/tools">

    <data>
        <import type="android.view.View"/>
        <import type="com.parthdave.recyclerviewbindinglibrary.bindings.ListBindingProviders"/>

        <import type="android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener"/>

        <import type="me.tatarka.bindingcollectionadapter2.LayoutManagers.LayoutManagerFactory"/>

        <import type="com.parthdave.recyclerviewbindinglibrary.states.RecyclerviewErrorStateType"/>
        <import type="com.parthdave.recyclerviewbindinglibrary.listeners.NetworkTryAgainListener"/>
        <import type="com.parthdave.recyclerviewbindinglibrary.listeners.OnLoadMoreListener"/>


        <variable
            name="provider"
            type="ListBindingProviders"/>

        <variable
            name="networkListener"
            type="NetworkTryAgainListener"/>

        <variable
            name="bindingDivider"
            type="String"/>

        <variable
            name="layoutManager"
            type="LayoutManagerFactory"/>

        <variable
            name="onRefreshListener"
            type="OnRefreshListener"/>


        <variable
            name="onLoadMoreListener"
            type="OnLoadMoreListener"/>
    </data>

    <FrameLayout
       ...
       >


        <LinearLayout
            android:id="@+id/llNoInternetConnection"
            ...
            android:visibility="@{provider.recyclerViewState==RecyclerviewErrorStateType.NetworkError?View.VISIBLE:View.GONE}">

            <ImageView
                ...
                android:src="@drawable/no_internet_connection"/>

            <TextView
                ...
                android:onClick="@{(v)->networkListener.onTryAgainClick()}"/>

            <!-- To show error message -->
            <TextView
               ...
                android:text="@{provider.message}"/>
        </LinearLayout>

        <ProgressBar
            android:id="@+id/progressbar"
            ...
            android:visibility="@{provider.recyclerViewState==RecyclerviewErrorStateType.LoadingStarted?View.VISIBLE:View.GONE}"/>

        <LinearLayout
            ...
            android:visibility="@{(provider.recyclerViewState==RecyclerviewErrorStateType.Loaded || provider.recyclerViewState==RecyclerviewErrorStateType.LoadMore)?View.VISIBLE:View.GONE}">

            <android.support.v4.widget.SwipeRefreshLayout
                ...
                bind:enabled="@{provider.isSwipeRefreshEnabled}"
                bind:onRefreshListener="@{onRefreshListener}"
                bind:refreshingState="@{provider.swipeRefreshStates}">

                <android.support.v7.widget.RecyclerView
                   ...
                    app:itemBinding="@{provider.itemBinding}"
                    app:items="@{provider.items}"
                    app:layoutManager="@{layoutManager}"
                    bind:dividers="@{bindingDivider}"
                    bind:loadMoreEnabled="@{provider.loadMoreEnabled}"
                    bind:onLoadMoreListener="@{onLoadMoreListener}">

                </android.support.v7.widget.RecyclerView>

            </android.support.v4.widget.SwipeRefreshLayout>
        </LinearLayout>

        <ProgressBar
            android:id="@+id/loadMoreProgress"
            ...
            android:visibility="@{provider.recyclerViewState==RecyclerviewErrorStateType.LoadMore?View.VISIBLE:View.GONE}"/>

    </FrameLayout>
</layout>
````

<br/>

#Step 5:

<br/>
Implementation
now we just need this:
1. binding dividers is type of divider you want
2. layoutmanager is the manager for listing like linear grid and all
3. listners -> onRefreshListener,networkListener,onLoadMoreListener
5. Our item binding

```
<include
            layout="@layout/common_recyclerview_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:provider="@{listingProvider}"
            app:bindingDivider="@{@string/rv_bottomDivider}"
            app:layoutManager="@{LayoutManagers.linear()}"
            app:onRefreshListener="@{onRefreshListener}"
            app:networkListener="@{networkListener}"
            app:onLoadMoreListener="@{onLoadMoreListener}"/>

```


To test this I have created different states:

````
        userListingBinding = new ListBindings.UserListingBinding();
        userListingBinding.setRecyclerViewState(RecyclerviewErrorStateType.NetworkError);
        userListingBinding.setLoadMoreEnabled(true);
        activityMainBinding.setListingProvider(userListingBinding);
````

````
 @Override
    public void onTryAgainClick() {
        Toast.makeText(this,"onTryAgainClick",Toast.LENGTH_SHORT).show();
        //dummy loadings
        loadData();
        userListingBinding.setRecyclerViewState(RecyclerviewErrorStateType.LoadingStarted);
    }
````