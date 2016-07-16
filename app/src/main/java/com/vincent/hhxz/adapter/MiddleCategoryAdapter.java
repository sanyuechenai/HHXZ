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
import com.vincent.hhxz.bean.CateGoryBean;
import com.vincent.hhxz.bean.MiddleCateGoryBean;

import java.util.List;

/**
 * Created by CJ on 2016/6/30.
 */
public class MiddleCategoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MiddleCateGoryBean> data;
    private Context mContext;

    public MiddleCategoryAdapter(Context context,List<MiddleCateGoryBean> data){
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
            convertView=mInflater.inflate(R.layout.middlecategory_gridview_layout,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= ((ViewHolder) convertView.getTag());
        }
        MiddleCateGoryBean bean=data.get(position);
        viewHolder.textViewUp.setText(bean.getName());
        viewHolder.textViewDown.setText(bean.getDesc());
        Picasso.with(mContext).load(bean.getIcon()).fit().placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);
        return convertView;
    }
    public class ViewHolder{
        public ImageView imageView;
        public TextView textViewUp;
        public TextView textViewDown;
        public ViewHolder(View view){
            imageView= ((ImageView) view.findViewById(R.id.image_middle_gridview));
            textViewUp= ((TextView) view.findViewById(R.id.textup_middle));
            textViewDown= ((TextView) view.findViewById(R.id.textdown_middle));
        }
    }
}
