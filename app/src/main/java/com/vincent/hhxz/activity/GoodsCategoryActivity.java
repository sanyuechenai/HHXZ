package com.vincent.hhxz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.adapter.GoodRecycleAdapter;
import com.vincent.hhxz.adapter.GoodsGridViewAdapter;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.ShareGoodsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoodsCategoryActivity extends AppCompatActivity {

    private String goodsId;
    private String categoryName;
    private GridView gridView;
    private RequestQueue requestQueue;
    private TextView titleName;
    public static final int HAS_GOODS=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HAS_GOODS:
                  /*  totateAnimator.cancel();*/
                    reflashLayout.setVisibility(View.GONE);
                    showGoodsGridView();
                    break;
            }
        }
    };
    private LinearLayout reflashLayout;
    private ImageView imageViewSearching;
    private  RotateAnimation animation;
    private GoodsGridViewAdapter adapter;
    private List<ShareGoodsBean> list=new ArrayList<>();
    private ImageButton priceButton;
    private View pricefilterlayout;
    private PopupWindow popupWindow;
    private ImageView reflashing;
    private String price="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_category);
        Intent intent=getIntent();
        goodsId = intent.getStringExtra("id");
        categoryName = intent.getStringExtra("name");
        titleName = ((TextView) findViewById(R.id.titleName));
        reflashing = ((ImageView) findViewById(R.id.reflashing_goodscategory));
        priceButton = ((ImageButton) findViewById(R.id.pricefilter_goodscategory));
        imageViewSearching = ((ImageView) findViewById(R.id.searching_goodscategory));
        animation=(RotateAnimation) AnimationUtils.loadAnimation(
                GoodsCategoryActivity.this, R.anim.reflashing);
        animation.setDuration(2000);
        imageViewSearching.startAnimation(animation);
        reflashLayout = ((LinearLayout) findViewById(R.id.reflashlayout_goodscategory));
        titleName.setText(categoryName);
        gridView = ((GridView) findViewById(R.id.goodsdetialgridview_goodscategory));
        initGridView(Urls.GOODS_CATEGORY_URL,goodsId,price);
        if(popupWindow==null){
            createPupopWindow();
        }
        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else{
                    popupWindow.showAsDropDown(priceButton,0,5);
                }
            }
        });
    }

    //产生popupwindow
    public void createPupopWindow(){
        pricefilterlayout =getLayoutInflater().inflate(R.layout.chosegoodsbyprice_layout, null);
        RadioGroup priceRadioGrouop = (RadioGroup) pricefilterlayout.findViewById(R.id.priceradiogroup);
        priceRadioGrouop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i <group.getChildCount() ; i++) {
                    if(checkedId==group.getChildAt(i).getId()){
                        popupWindow.dismiss();
                        reflashLayout.setVisibility(View.VISIBLE);
                        Log.i("789", "reflashing: "+34444);
                        RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                                GoodsCategoryActivity.this, R.anim.reflashing);
                        Log.i("789", "reflashing: "+44444);
                        // rarate.cancel();
                        rotateAnimation.setDuration(3000);
                        reflashing.startAnimation(rotateAnimation);
                        // imageViewReflashing.clearAnimation();
                        initGridView(Urls.GOODS_CATEGORY_URL,goodsId,i/2+"");
                    }
                }
            }
        });

        popupWindow = new PopupWindow(pricefilterlayout,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.more_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }

//&count=8&price_filter=7&start=0&tag_id=-1
    public void initGridView(String website,String id,String price){
        String Url=website+id+"&count=16&price_filter="+price+"&start=0&tag_id=-1";
        requestQueue = MyApp.newInstance().getRequestQueue();
        JSONObject jsonObject=new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //final List<ShareGoodsBean> list=new ArrayList<>();
                try {
                    JSONArray array=response.getJSONArray("shareList");
                    list.clear();
                    for (int i = 0; i <array.length() ; i++) {
                        ShareGoodsBean bean=new ShareGoodsBean();
                        JSONObject obj=array.getJSONObject(i);
                        bean.setIcon(obj.optString("icon"));
                        bean.setShare_id(obj.optString("share_id"));
                        bean.setName(obj.optString("name"));
                        bean.setBaike_url(obj.optString("baike_url"));
                        bean.setExternal_id(obj.optString("external_id"));
                        bean.setGoods_url(obj.optString("goods_url"));
                        bean.setLike(obj.optString("like"));
                        bean.setPrice(obj.optString("price"));
                        bean.setUrl(obj.optString("url"));
                        list.add(bean);
                    }
//                   ( GoodRecycleAdapter adapter=new GoodRecycleAdapter(getActivity(),list);
//                    recyclerView.setAdapteradapter);
//                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            /*        GoodsGridViewAdapter adapter=new GoodsGridViewAdapter(GoodsCategoryActivity.this,list);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(GoodsCategoryActivity.this, GoodsRecommandActivity.class);
                            intent.putExtra("GoodsURL",list.get(position).getGoods_url());
                            intent.putExtra("baike",list.get(position).getBaike_url());
                            intent.putExtra("goodname",list.get(position).getName());
                            startActivity(intent);
                        }
                    });*/
                    handler.sendEmptyMessage(HAS_GOODS);
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
    public void showGoodsGridView(){
        //  drawable.stop();
        imageViewSearching.clearAnimation();
        imageViewSearching.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);
        adapter=new GoodsGridViewAdapter(GoodsCategoryActivity.this,list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(GoodsCategoryActivity.this, GoodsRecommandActivity.class);
                intent.putExtra("GoodsURL",list.get(position).getGoods_url());
                intent.putExtra("baike",list.get(position).getBaike_url());
                intent.putExtra("goodname",list.get(position).getName());
                startActivity(intent);
            }
        });
    }

    public void backClick(View view) {
        finish();
    }
}
