package com.vincent.hhxz.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by CJ on 2016/7/6.
 */

@Table(name="useraccount",id="_id")
public class UserAccount extends Model {


    @Column(name="user_name")
    private String userName;

    @Column(name="passvord")
    private String Password;

    public UserAccount(){

    }

    public UserAccount(String userName, String password) {
        this.userName = userName;
        Password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
