package com.vincent.hhxz.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.app.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {


    private ListView listViewLeft;
    private List<String> categoryName=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "111111");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewLeft = ((ListView) view.findViewById(R.id.listview_leftfragment));
        DownJsonData();

    }
    //网络下载Json数据
    public void DownJsonData(){
        RequestQueue requestQueue = MyApp.newInstance().getRequestQueue();
        Log.i("12345", "是否进入 ");
        final JSONObject jsonObject=new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Urls.CATEGORY_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("catList");
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject object=array.getJSONObject(i);
                        categoryName.add(object.optString("name"));
                    }
                    Log.i("12345", "categoryName的大小 "+categoryName);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categoryName);
                    listViewLeft.setAdapter(adapter);
                    //setAdapter() 其实是异步的 ，调用了这个方法，
                    // ListView 的 item 并没有立马创建，而是在下一轮消息处理时才创建,所以用post提交一个 Runnable() 对象，在 Runnable() 内部来做默认选中这种初始化动作
                    listViewLeft.post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) listViewLeft.getChildAt(0).findViewById(android.R.id.text1)).setTextColor(Color.GREEN);
                            listViewLeft.getChildAt(0).setBackgroundColor(Color.WHITE);
                        }
                    });

                    listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView itemText = (TextView) view.findViewById(android.R.id.text1);
                            for (int i = 0; i <listViewLeft.getChildCount() ; i++) {
                                if(i!=position){
                                    ((TextView) listViewLeft.getChildAt(i).findViewById(android.R.id.text1)).setTextColor(Color.GRAY);
                                    listViewLeft.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                                }else{
                                    ((TextView) listViewLeft.getChildAt(i).findViewById(android.R.id.text1)).setTextColor(Color.GREEN);
                                    listViewLeft.getChildAt(i).setBackgroundColor(Color.WHITE);
                                }
                            }

                            EventBus.getDefault().post(new FragmentEvent(position));
                            Log.i("246", "onItemClick: "+position);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("12345", "没有数据 ");
            }
        });
        requestQueue.add(request);
    }
}
