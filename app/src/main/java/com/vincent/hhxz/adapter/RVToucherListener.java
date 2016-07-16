package com.vincent.hhxz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CJ on 2016/7/1.
 */
public class RVToucherListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private RVItemClickListener rvItemClickListener;
    private RecyclerView mRecyclerView;

    public RVToucherListener(Context context, RecyclerView recyclerView, final RVItemClickListener rvItemClickListener) {
        mRecyclerView = recyclerView;
        this.rvItemClickListener = rvItemClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                //获取X/Y坐标所对应的itemView
                View itemView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                //获取itemView所对应的位置position
                int position = mRecyclerView.getChildAdapterPosition(itemView);
                if (rvItemClickListener != null) {
                    rvItemClickListener.onItemLongClicked(itemView, position);
                }
            }
        });
    }

    /**
     * 事件拦截
     * 如果返回true，则触屏事件不会向子控件继续传递
     * 如果返回false，则触屏事件会继续向子控件传递
     * MOTION_EVENT:ACTION_DOWN ACTION_MOVE ACTION_UP
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (gestureDetector.onTouchEvent(e) && rvItemClickListener != null) {  //监听到单击事件并且rvItemCli！=null
            //获取X/Y坐标所对应的itemView
            View itemView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            //获取itemView所对应的位置position
            int position = mRecyclerView.getChildAdapterPosition(itemView);
            rvItemClickListener.onItemClicked(itemView, position);
        }
        return false;
    }

    /**
     * 事件处理(消费)，需要进一步的逻辑实现View的点击、长按等等事件
     * @param rv
     * @param e
     */
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
