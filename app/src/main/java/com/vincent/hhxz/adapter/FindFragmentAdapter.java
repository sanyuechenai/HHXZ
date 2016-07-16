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
import com.vincent.hhxz.bean.GoodsBean;

import java.util.List;

/**
 * Created by CJ on 2016/7/5.
 */
public class FindFragmentAdapter extends BaseAdapter {

    private List<GoodsBean> data;
    private Context mContext;
    private LayoutInflater mInflater;

    public FindFragmentAdapter(Context context,List<GoodsBean> data){
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
        MyHolderView viewHolder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.gridview_rightfragment_layout,null);
            viewHolder=new MyHolderView(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= ((MyHolderView) convertView.getTag());
        }
        GoodsBean bean=data.get(position);
        viewHolder.goodsNameRightFragment.setText(bean.getImgName());
        Picasso.with(mContext).load(bean.getImgUrl()).resize(280,280)
                .placeholder(R.mipmap.ic_launcher).into(viewHolder.goodsImageRightFragment);
        return convertView;
    }

    public class MyHolderView{
        public TextView goodsNameRightFragment;
        public ImageView goodsImageRightFragment;

        public MyHolderView(View view){
            goodsNameRightFragment= ((TextView) view.findViewById(R.id.textgoodsname_rightfragment));
            goodsImageRightFragment= ((ImageView) view.findViewById(R.id.imagegoods_rightfragment));
        }
    }
}
