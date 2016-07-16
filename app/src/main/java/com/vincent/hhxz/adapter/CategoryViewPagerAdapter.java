package com.vincent.hhxz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by CJ on 2016/7/4.
 */
public class CategoryViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private String[] titles;

    public CategoryViewPagerAdapter(FragmentManager fm, List<Fragment> data, String[] titles) {
        super(fm);
        mList = data;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    //结合tablayout使用的时候复写getPageTitle方法

    @Override
    public CharSequence getPageTitle(int position) {
        //根据下标获取指定标题的名字
        return titles[position];
    }
}
