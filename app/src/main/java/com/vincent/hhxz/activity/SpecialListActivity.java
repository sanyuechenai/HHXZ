package com.vincent.hhxz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.adapter.ListViewAdapter;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.StrategyBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpecialListActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueue;
    private ImageView loading;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_list);
        listView = ((ListView) findViewById(R.id.listview_special));
        initView();
    }

    public void initView(){
        loading = ((ImageView) findViewById(R.id.loadingdata_sla));
        animation=(RotateAnimation) AnimationUtils.loadAnimation(
               this, R.anim.loading);
        loading.startAnimation(animation);
        requestQueue = MyApp.newInstance().getRequestQueue();
        JSONObject jsonObject=new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Urls.SPECIAL_LIST, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                final List<StrategyBean> lists=new ArrayList<>();
                try {
                    JSONArray array=response.getJSONArray("shareList");
                    for (int i = 0; i <array.length() ; i++) {
                        StrategyBean bean=new StrategyBean();
                        JSONObject obj=array.getJSONObject(i);
                        bean.setIcon(obj.optString("icon"));
                        bean.setName(obj.optString("name"));
                        bean.setExternal_id(obj.optString("external_id"));
                        bean.setIs_strategy(obj.getInt("is_strategy"));
                        bean.setShare_id(obj.optString("share_id"));
                        bean.setSource(obj.optString("source"));
                        lists.add(bean);
                    }
                    loading.clearAnimation();
                    loading.setVisibility(View.INVISIBLE);
                    ListViewAdapter adapter=new ListViewAdapter(SpecialListActivity.this,lists);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(SpecialListActivity.this, SpecialActivity.class);
                            intent.putExtra("share_id",lists.get(position).getShare_id());
                            startActivity(intent);
                        }
                    });
                    listView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    //搜索监听
    public void searchClick(View view) {

    }
    //返回按钮监听
    public void click(View view) {
        finish();
    }
}
