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
import com.vincent.hhxz.bean.ShareGoodsBean;

import java.util.List;

/**
 * Created by CJ on 2016/7/1.
 */
public class GoodsGridViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<ShareGoodsBean> data;

    public GoodsGridViewAdapter(Context context,List<ShareGoodsBean> data){
        this.context=context;
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
        GoodViewHolder viewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.recyclerview_otheritem_layout,null);
            viewHolder=new GoodViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= ((GoodViewHolder) convertView.getTag());
        }
        ShareGoodsBean bean = data.get(position);
        viewHolder.goodsName.setText(bean.getName());
        viewHolder.goodsPrice.setText(bean.getPrice());
        viewHolder.heartNum.setText(bean.getLike());
        Picasso.with(context).load(bean.getIcon()).resize(450,450)
                .placeholder(R.mipmap.ic_launcher).into(viewHolder.goodsImageView);

        return convertView;
    }


    public class GoodViewHolder{
        public ImageView goodsImageView;
        public TextView goodsName;
        public TextView goodsPrice;
        public TextView heartNum;

        public GoodViewHolder(View itemView) {
            goodsImageView= ((ImageView) itemView.findViewById(R.id.imageitem_recycleview));
            goodsName= ((TextView) itemView.findViewById(R.id.goodsname_item));
            goodsPrice= ((TextView) itemView.findViewById(R.id.goodprice_item));
            heartNum= ((TextView) itemView.findViewById(R.id.heartnum_item));
        }

    }
}
