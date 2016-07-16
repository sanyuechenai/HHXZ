package com.vincent.hhxz.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by CJ on 2016/6/30.
 */
public class MyApp extends Application {

    public static MyApp myApp;
    private RequestQueue requestQueue;
    private boolean isLogin=false;
    public static final String shref_filename = "myloging";

    public static final String shref_Username = "usename";
    //ctrl+shift+u
    public static final String IS_LOGIN = "login";

    public static final String IS_LOGIN_NAME = "user";
    public static Context mContext;


    public static MyApp newInstance(){
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
        ActiveAndroid.initialize(this);
        mContext=this;

    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(this);
        }
        return requestQueue;
    }

    public static boolean isLogin(){
        SharedPreferences shref = mContext.getSharedPreferences(shref_filename, Context.MODE_PRIVATE);
        boolean isFirst = shref.getBoolean(IS_LOGIN , false);
        return isFirst;
    }
    public static String getUserName(){
        SharedPreferences shref = mContext.getSharedPreferences(shref_Username, Context.MODE_PRIVATE);
        String userName=shref.getString(IS_LOGIN_NAME,"");
        return userName;
    }


}
