package com.vincent.hhxz.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vincent.hhxz.R;
import com.vincent.hhxz.app.MyApp;

import org.simple.eventbus.EventBus;

public class SettingActivity extends AppCompatActivity {


    private Button logout;
    private SharedPreferences spflogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spflogin=getSharedPreferences(MyApp.shref_filename,MODE_PRIVATE);
        initView();
    }


    public void initView(){
        logout = ((Button) findViewById(R.id.logout));
        if(MyApp.isLogin()){
            logout.setVisibility(View.VISIBLE);
        }else {
            logout.setVisibility(View.INVISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }

    public void createDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("您确定要退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spflogin.edit().putBoolean(MyApp.IS_LOGIN,false).commit();
                String update="更新UI";
                EventBus.getDefault().post(update);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public void backClick(View view) {
        finish();
    }
}
