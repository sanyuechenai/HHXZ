package com.vincent.hhxz.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vincent.hhxz.R;
import com.vincent.hhxz.activity.LoginActivity;
import com.vincent.hhxz.activity.RegisterActivity;
import com.vincent.hhxz.activity.SettingActivity;
import com.vincent.hhxz.app.MyApp;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    private RelativeLayout noLogin;
    private RelativeLayout hasLogin;
    private TextView testUserName;
    private LinearLayout settingLayout;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subcriber
    public void onMainEventThread(String update){
        if(MyApp.isLogin()){
            noLogin.setVisibility(View.GONE);
            String name = MyApp.getUserName().toString();
            String firstWord=name.substring(0,1);
            String endWord=name.substring(name.length()-1);
            String userName=firstWord+"**"+endWord;
            testUserName.setText(userName);
            hasLogin.setVisibility(View.VISIBLE);
        }else if(!MyApp.isLogin()){
            noLogin.setVisibility(View.VISIBLE);
            hasLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //头像设置
        noLogin = ((RelativeLayout) view.findViewById(R.id.nologing_mine));
        hasLogin = ((RelativeLayout) view.findViewById(R.id.hasloging_mine));
        testUserName = ((TextView) view.findViewById(R.id.usename_mine));
        if(MyApp.isLogin()){
            noLogin.setVisibility(View.GONE);
            String name = MyApp.getUserName().toString();
            String firstWord=name.substring(0,1);
            String endWord=name.substring(name.length()-1);
            String userName=firstWord+"**"+endWord;
            testUserName.setText(userName);
            hasLogin.setVisibility(View.VISIBLE);
        }else if(!MyApp.isLogin()){
            noLogin.setVisibility(View.VISIBLE);
            hasLogin.setVisibility(View.GONE);
        }
        TextView loginText = (TextView) view.findViewById(R.id.loginbtn_mine);
        loginText.setOnClickListener(new Listener());
        TextView registerText = (TextView) view.findViewById(R.id.regiestertn_mine);
        registerText.setOnClickListener(new Listener());

        settingLayout = ((LinearLayout) view.findViewById(R.id.setting_mine));
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }



    public class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginbtn_mine:
                    //Toast.makeText(getActivity(), "1233", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.regiestertn_mine:
                    Intent intent2=new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }

}
