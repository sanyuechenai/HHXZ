package com.vincent.hhxz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vincent.hhxz.R;
import com.vincent.hhxz.bean.CateGoryBean;

import java.util.List;

/**
 * Created by CJ on 2016/6/30.
 */
public class CateGoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<CateGoryBean> data;
    private Context mContext;

    public CateGoryAdapter(Context context,List<CateGoryBean> data){
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
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.categoty_gridview_layout,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= ((ViewHolder) convertView.getTag());
        }
        CateGoryBean bean=data.get(position);
        viewHolder.textView.setText(bean.getName());
        Picasso.with(mContext).load(bean.getLogo()).fit().placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);
        return convertView;
    }
    public class ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View view){
            imageView= ((ImageView) view.findViewById(R.id.image_gridview_category));
            textView= ((TextView) view.findViewById(R.id.text_gridview_category));
        }
    }
}
