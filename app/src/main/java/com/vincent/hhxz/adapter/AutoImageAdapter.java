package com.vincent.hhxz.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by CJ on 2016/6/30.
 */
public class AutoImageAdapter extends PagerAdapter {

    private List<ImageView> data;

    public AutoImageAdapter(List<ImageView> data){
        this.data=data;
    }
    // 数据源大小
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Integer.MAX_VALUE;
    }

    // 写法固定的,实际的对象View就是最后需要显示的View
    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v == obj;
    }

    /**
     * 视图对象动态绘制 (目的是节省内存的开销,一般情况加载的图片资源是比较耗内存的) 返回的是Object数据源的对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
     /*   int index =  position % data.size();
        View v = data.get(index);
        ((ViewPager)container).addView(v, 0);
        return v;*/
        position %= data.size();
        if (position<0){
            position = data.size()+position;
        }
        ImageView view = data.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }

    /**
     * 视图对象动态删除
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        // super.destroyItem(container, position, object);
//        int index =  position % data.size();
//        View v = data.get(index);
//			container.removeView(v);
     //   ((ViewPager)container).removeView(v);
    }
}
