package com.parthdave.databindingandrecyclerview.dummymodelsprovider;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.parthdave.databindingandrecyclerview.Commons;
import com.parthdave.databindingandrecyclerview.models.User;

/**
 * Created by Parth Dave on 9/5/17.
 * Spaceo Technologies Pvt Ltd.
 * parthd.spaceo@gmail.com
 */

public class UserModelsProvider {
    
    public static ObservableList<User> getUserModels(){
        ObservableList<User> users = new ObservableArrayList<>();
        for (int index = 0; index < 10; index++) {
            User user = new User();
            user.setUserImage(Commons.userDummyImage);
            user.setUserAge("1"+index);
            user.setUserName("Dummy "+index);
            user.setUserStatus("Single");
            users.add(user);
        }
        return users;
    }
}
