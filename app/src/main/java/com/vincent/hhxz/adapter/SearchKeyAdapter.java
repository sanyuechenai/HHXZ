package com.vincent.hhxz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vincent.hhxz.R;

import java.util.List;

/**
 * Created by CJ on 2016/7/2.
 */
public class SearchKeyAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> dataKeys;
    private LayoutInflater mInflater;

    public SearchKeyAdapter(Context context,List<String> data){
        mContext=context;
        dataKeys=data;
        mInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return dataKeys==null? 0:dataKeys.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyOldSearchViewHolder holder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.oldsearch_layout,null);
            holder=new MyOldSearchViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= ((MyOldSearchViewHolder) convertView.getTag());
        }
        holder.oldSearchKey.setText(dataKeys.get(position));
        return convertView;
    }

    public class MyOldSearchViewHolder{
         public TextView oldSearchKey;

        public MyOldSearchViewHolder(View view){
            oldSearchKey= ((TextView) view.findViewById(R.id.oldserch_text));
        }
    }

}
