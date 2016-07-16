package com.vincent.hhxz.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.vincent.hhxz.R;
import com.vincent.hhxz.bean.MyEntryTab;
import com.vincent.hhxz.fragment.CategoryFragment;
import com.vincent.hhxz.fragment.FirstFragment;
import com.vincent.hhxz.fragment.MineFragment;
import com.vincent.hhxz.fragment.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] mTitles=new String[]{"首页","分类","发现","我"};
    private int[] mIconUnselectIds=new int[]{R.mipmap.menu_home_first_off,R.mipmap.menu_home_category_off,
    R.mipmap.menu_home_find_off,R.mipmap.menu_home_mine_off};
    private int[] mIconSelectIds=new int[]{R.mipmap.menu_home_first_on,R.mipmap.menu_home_category_on,
            R.mipmap.menu_home_find_on,R.mipmap.menu_home_mine_on};

    public ArrayList<CustomTabEntity> mTabLayouts=new ArrayList<>();
    public CommonTabLayout fTablayout;
    private ArrayList<Fragment> mFragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fTablayout = ((CommonTabLayout) findViewById(R.id.tablayout_main));
        initData();
        fTablayout.setTabData(mTabLayouts,this,R.id.frame_main,mFragments);
        fTablayout.setCurrentTab(0);
    }
    private void initData() {
        //设置tab的标题、选中图标、未选中图标
        for (int i = 0; i < mTitles.length; i++) {
            mTabLayouts.add(new MyEntryTab(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mFragments.add(new FirstFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new SearchFragment());
        mFragments.add(new MineFragment());
    }
}
