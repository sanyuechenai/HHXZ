package com.vincent.hhxz.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.activity.GoodsCategoryActivity;
import com.vincent.hhxz.adapter.CategoryGridViewAdapter;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.CateGoryBean;
import com.vincent.hhxz.bean.SubListBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {


    private GridView gridViewRight;
    private List<SubListBean> subListBeans=new ArrayList<>();
    private int position=0;
    private RotateAnimation animation;
    private ImageView loadingData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_right, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridViewRight = ((GridView) view.findViewById(R.id.gridview_rightfragment));
        loadingData = ((ImageView) view.findViewById(R.id.loadingdata_rightfragment));
        animation=(RotateAnimation) AnimationUtils.loadAnimation(
                getActivity(), R.anim.reflashing);
        animation.setDuration(2000);
        loadingData.startAnimation(animation);
        downJsonData(position);
    }

    @Subcriber
    public void onMainEventThread(FragmentEvent fragmentEvent){
        Log.i("246", "onMainEventThread: ");
        gridViewRight.setVisibility(View.INVISIBLE);
        loadingData.setVisibility(View.VISIBLE);
        loadingData.startAnimation(animation);
        position=fragmentEvent.getPosition();
        Log.i("246", "position is>>> "+position);
        downJsonData(position);
    }

    public void downJsonData(final int position){
        RequestQueue requestQueue = MyApp.newInstance().getRequestQueue();
        JSONObject jsonObject=new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Urls.CATEGORY_URL, jsonObject, new Response.Listener<JSONObject>() {
            private CategoryGridViewAdapter adapter1;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("catList");
                    JSONObject object=array.getJSONObject(position);
                    JSONArray array1=null;
                    subListBeans.clear();
                    Log.i("246", "是否进入 ");
                    if(position==3){
                        array1=object.getJSONArray("subTags");
                    }else{
                        array1=object.getJSONArray("subList");
                    }
                    for (int i = 0; i <array1.length() ; i++) {
                        SubListBean bean=new SubListBean();
                        JSONObject obj=array1.getJSONObject(i);
                        bean.setCat_id(obj.optString("cat_id"));
                        bean.setCover_logo(obj.optString("cover_logo"));
                        bean.setLogo(obj.optString("logo"));
                        bean.setName(obj.optString("name"));
                        subListBeans.add(bean);
                    }
                    loadingData.clearAnimation();
                    loadingData.setVisibility(View.INVISIBLE);
                    gridViewRight.setVisibility(View.VISIBLE);
                    CategoryGridViewAdapter adapter=new CategoryGridViewAdapter(getActivity(),subListBeans);
                    gridViewRight.setAdapter(adapter);
                    gridViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(getActivity(), GoodsCategoryActivity.class);
                            SubListBean itemBean = subListBeans.get(position);
                            intent.putExtra("id",itemBean.getCat_id());
                            intent.putExtra("name",itemBean.getName());
                            startActivity(intent);
                        }
                    });
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

}
