package com.vincent.hhxz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vincent.hhxz.R;
import com.vincent.hhxz.bean.SubListBean;

import java.util.List;

/**
 * Created by CJ on 2016/7/4.
 */
public class CategoryGridViewAdapter extends BaseAdapter {
    private List<SubListBean> data;
    private Context mContext;
    private LayoutInflater mInflater;

    public CategoryGridViewAdapter(Context context,List<SubListBean> data){
        mContext=context;
        this.data=data;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data==null? 0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView viewHolder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.gridview_rightfragment_layout,null);
            viewHolder=new HolderView(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= ((HolderView) convertView.getTag());
        }
        SubListBean bean=data.get(position);
        viewHolder.goodsNameRightFragment.setText(bean.getName());
        Picasso.with(mContext).load(bean.getLogo()).resize(356,356)
                .placeholder(R.mipmap.ic_launcher).into(viewHolder.goodsImageRightFragment);
        return convertView;
    }

    public class HolderView{
        public  TextView goodsNameRightFragment;
        public ImageView goodsImageRightFragment;

        public HolderView(View view){
            goodsNameRightFragment= ((TextView) view.findViewById(R.id.textgoodsname_rightfragment));
            goodsImageRightFragment= ((ImageView) view.findViewById(R.id.imagegoods_rightfragment));
        }
    }
}
