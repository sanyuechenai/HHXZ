package com.vincent.hhxz.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vincent.hhxz.R;
import com.vincent.hhxz.activity.SearchActivity;
import com.vincent.hhxz.adapter.CategoryViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    private TabLayout tabLayout;
    private String[] titles=new String[]{"分类","品牌"};
    private List<Fragment> listFragments=new ArrayList<>();
    private ViewPager viewPager;
    private ImageView searchImage;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // ConnectivityManager connectivityManager = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = ((TabLayout) view.findViewById(R.id.tablayout_cf));
        for (int i = 0; i <titles.length ; i++) {
            TabLayout.Tab tab=tabLayout.newTab().setText(titles[i]);
        }
        viewPager = ((ViewPager) view.findViewById(R.id.viewpager_cf));
        tabLayout.setTabTextColors(Color.GRAY,Color.WHITE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        listFragments.add(new LeibieFragment());
        listFragments.add(new BandFragment());
        CategoryViewPagerAdapter adapter=new CategoryViewPagerAdapter(getActivity().getSupportFragmentManager(),listFragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        searchImage = ((ImageView) view.findViewById(R.id.search_cf));
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
