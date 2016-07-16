package com.vincent.hhxz.adapter;

import android.view.View;

/**
 * Created by CJ on 2016/7/1.
 */
public interface RVItemClickListener {

    /**
     * RecyclerView的item单击事件
     * @param itemView 所点击的item视图
     * @param position 点击item的position
     */
    public void onItemClicked(View itemView, int position);

    /**
     * RecyclerView的item长按事件
     * @param itemView 所长按的item视图
     * @param position 长按item的position
     */
    public void onItemLongClicked(View itemView, int position);
}
