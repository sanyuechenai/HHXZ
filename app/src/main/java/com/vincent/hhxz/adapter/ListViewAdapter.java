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
import com.vincent.hhxz.Utils.CornerImageTransFormation;
import com.vincent.hhxz.bean.ShareGoodsBean;
import com.vincent.hhxz.bean.StrategyBean;

import java.util.List;

/**
 * Created by CJ on 2016/7/1.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<StrategyBean> data;

    public ListViewAdapter(Context context,List<StrategyBean> data){
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
            convertView=mInflater.inflate(R.layout.bigimage_item,null);
            viewHolder=new GoodViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= ((GoodViewHolder) convertView.getTag());
        }
        StrategyBean bean = data.get(position);
        viewHolder.textWord.setText(bean.getName());
        Picasso.with(context).load(bean.getIcon()).resize(1080,400)
                .placeholder(R.mipmap.ic_launcher).transform(new CornerImageTransFormation(10))
                .into(viewHolder.bigImageView);
        return convertView;
    }
    public class GoodViewHolder{
        public ImageView bigImageView;
        public TextView textWord;

        public GoodViewHolder(View itemView) {
            bigImageView= ((ImageView) itemView.findViewById(R.id.bigImage));
            textWord= ((TextView) itemView.findViewById(R.id.textword_bigImage));
        }

    }
}
