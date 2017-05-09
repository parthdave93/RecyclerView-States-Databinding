package com.parthdave.databindingandrecyclerview.bindings;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.parthdave.databindingandrecyclerview.BR;
import com.parthdave.databindingandrecyclerview.R;
import com.parthdave.databindingandrecyclerview.models.User;
import com.parthdave.recyclerviewbindinglibrary.bindings.ListBindingProviders;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by Parth Dave on 9/5/17.
 * Spaceo Technologies Pvt Ltd.
 * parthd.spaceo@gmail.com
 */

public class ListBindings {
    public static final class UserListingBinding extends ListBindingProviders<User> {
        private final ObservableList<User> items = new ObservableArrayList<>();
        private final ItemBinding<User> itemBinding = ItemBinding.of(BR.user, R.layout.item_user_list);
        
        @Override
        public ObservableList<User> getItems() {
            Log.d("getItems","getItems");
            return items;
        }
        
        @Override
        public ItemBinding<User> getItemBinding() {
            Log.d("getItemBinding","getItemBinding");
            return itemBinding;
        }
    }
}
