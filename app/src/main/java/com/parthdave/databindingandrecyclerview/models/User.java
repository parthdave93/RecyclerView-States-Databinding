package com.parthdave.databindingandrecyclerview.models;

/**
 * Created by Parth Dave on 9/5/17.
 * Spaceo Technologies Pvt Ltd.
 * parthd.spaceo@gmail.com
 */

public class User {
    private String userImage;
    private String userName;
    private String userAge;
    private String userStatus;
    
    public String getUserImage() {
        return userImage;
    }
    
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserAge() {
        return userAge;
    }
    
    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
    
    public String getUserStatus() {
        return userStatus;
    }
    
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
