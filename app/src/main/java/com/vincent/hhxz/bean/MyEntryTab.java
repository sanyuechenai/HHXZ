package com.vincent.hhxz.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by CJ on 2016/6/29.
 */
public class MyEntryTab implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public MyEntryTab(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
