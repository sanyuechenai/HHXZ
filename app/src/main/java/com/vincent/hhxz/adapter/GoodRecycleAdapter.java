package com.vincent.hhxz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vincent.hhxz.R;
import com.vincent.hhxz.bean.ShareGoodsBean;

import java.util.List;

/**
 * Created by CJ on 2016/6/30.
 */
public class GoodRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShareGoodsBean> data;
    private Context mContext;
    private LayoutInflater mInflater;
    private int firstItem=0;
    private int otherItem=1;

    public GoodRecycleAdapter(Context context,List<ShareGoodsBean> data){
        mContext=context;
        this.data=data;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==firstItem){
           View itemView=mInflater.inflate(R.layout.recycleview_firstitem_layoutr,null);
           return new MyViewHolder(itemView);

       }else if(viewType==otherItem){
           View itemView=mInflater.inflate(R.layout.recyclerview_otheritem_layout,null);
           return new MyViewHolder(itemView);
       }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder=((MyViewHolder) holder);
        mHolder.goodsName.setText(data.get(position).getName());
        mHolder.goodsPrice.setText("￥"+data.get(position).getPrice());
        mHolder.heartNum.setText(data.get(position).getLike());
        Picasso.with(mContext).load(data.get(position).getIcon()).resize(450,450)
                .placeholder(R.mipmap.ic_launcher).into(mHolder.goodsImageView);
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return firstItem;
        }else{
            return otherItem;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView goodsImageView;
        public TextView goodsName;
        public TextView goodsPrice;
        public TextView heartNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodsImageView= ((ImageView) itemView.findViewById(R.id.imageitem_recycleview));
            goodsName= ((TextView) itemView.findViewById(R.id.goodsname_item));
            goodsPrice= ((TextView) itemView.findViewById(R.id.goodprice_item));
            heartNum= ((TextView) itemView.findViewById(R.id.heartnum_item));
        }
    }
}
