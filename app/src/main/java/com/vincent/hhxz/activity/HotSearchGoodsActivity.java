package com.vincent.hhxz.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.adapter.GoodsGridViewAdapter;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.ShareGoodsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HotSearchGoodsActivity extends AppCompatActivity {

    private String hotkey;
    private String pricefilter="0";
    private TextView searchGoodName;
    private GridView searchGoodGridView;
    private RequestQueue requestQueue;
    private List<ShareGoodsBean> list=new ArrayList<>();
   // http://www.hehe168.com/mapi.php?act=getSearch&c=2&count=8&key=%E5%A4%A7%E6%9E%A3&price_filter=0&start=0
    private String aaa = "http://www.hehe168.com/mapi.php?act=getSearch&c=2&count=8&key=新西兰&price_filter=0&start=0";
    private String urk="http://www.hehe168.com/mapi.php?act=getSearch&c=2&count=8&key=%d&price_filter=0&start=0";
    private ImageView imageViewSearching;
    private AnimationDrawable drawable;
    private TextView searchResult;
    private OkHttpClient client;
    private GoodsGridViewAdapter adapter;
    private ImageView searchNoResult;
    private PopupWindow popupWindow;
    private View pricefilterlayout;
    private Animator totateAnimator;
    private  RotateAnimation animation;
    public static final int NO_GOODS=0;
    public static final int HAS_GOODS=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NO_GOODS:
                   // drawable.stop();
                    imageViewSearching.clearAnimation();
                    imageViewSearching.setVisibility(View.INVISIBLE);
                 //   totateAnimator.cancel();
                    searchNoResult.setVisibility(View.VISIBLE);
                //    searchGoodGridView.setVisibility(View.INVISIBLE);
                    reflashLayout.setVisibility(View.GONE);
                    searchResult.setVisibility(View.VISIBLE);
                    break;
                case HAS_GOODS:
                  /*  totateAnimator.cancel();*/
                    reflashLayout.setVisibility(View.GONE);
                    showGoodsGridView();
                    break;
            }
        }
    };
    private ImageView imageViewPriceFilter;
    private LinearLayout reflashLayout;
    private ImageView imageViewReflashing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_search_goods);
        Intent intent=getIntent();
        hotkey = intent.getStringExtra("hotkey");
         client=new OkHttpClient();
        searchGoodName = ((TextView) findViewById(R.id.searchGoodName));
        imageViewSearching = ((ImageView) findViewById(R.id.searching));
        reflashLayout = ((LinearLayout) findViewById(R.id.reflashlayout));
        imageViewReflashing = ((ImageView) findViewById(R.id.reflashing));
        //开始动画
       /* drawable = ((AnimationDrawable) imageViewSearching.getBackground());
        drawable.start();*/
       /* totateAnimator= AnimatorInflater.loadAnimator(this,R.animator.rotate);
        totateAnimator.setTarget(imageViewSearching);
        totateAnimator.start();*/
        animation=(RotateAnimation) AnimationUtils.loadAnimation(
                HotSearchGoodsActivity.this, R.anim.reflashing);
        animation.setDuration(2000);
        imageViewSearching.startAnimation(animation);

        searchResult = ((TextView) findViewById(R.id.searchresult));
        searchNoResult = ((ImageView) findViewById(R.id.search_no_result));
        searchGoodName.setText(hotkey);
        searchGoodGridView = ((GridView) findViewById(R.id.goodsdetialgridview_goodssearch));
        requestQueue = MyApp.newInstance().getRequestQueue();
        initGridView(hotkey,pricefilter);
        if(popupWindow==null){
            createPupopWindow();
        }
        imageViewPriceFilter = ((ImageView) findViewById(R.id.pricefilter));
        imageViewPriceFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else{
                    popupWindow.showAsDropDown(imageViewPriceFilter,0,5);
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
                               HotSearchGoodsActivity.this, R.anim.reflashing);
                        Log.i("789", "reflashing: "+44444);
                       // rarate.cancel();
                        rotateAnimation.setDuration(3000);
                        imageViewReflashing.startAnimation(rotateAnimation);
                       // imageViewReflashing.clearAnimation();
                        initGridView(hotkey,i/2+"");
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
//网络请求下载数据
   public void initGridView(String keyWord,String price){
        String url = Urls.SEARH_GOODS+keyWord+
                "&price_filter="+price+"&start=0";
        Request request=new Request.Builder().url(url).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {

           }

           @Override
           public void onResponse(Call call, okhttp3.Response response) throws IOException {
               String jsonData = response.body().string();
               Log.i("124", "onResponse: "+"1111111");
               Log.i("124", "jsonData: "+jsonData);
               // GoodsBean bean= JSON.parseObject(jsonData,Goo)
               // GoodsShareBean bean=JSON.parseObject(jsonData,GoodsShareBean.class);
               // Log.i("123", "bean>>> "+bean);
               try {
                   JSONObject object=new JSONObject(jsonData);
                   int goodsNum=object.getInt("total");
                   if(goodsNum==0){

                       handler.sendEmptyMessage(NO_GOODS);
                   }else{
                       JSONArray array=object.getJSONArray("shares");
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
                           Log.i("123", "bean: "+bean);
                       }

                       handler.sendEmptyMessage(HAS_GOODS);
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
    }
    //显示商品
        public void showGoodsGridView(){
          //  drawable.stop();
            imageViewSearching.clearAnimation();
            imageViewSearching.setVisibility(View.INVISIBLE);
            searchGoodGridView.setVisibility(View.VISIBLE);
            adapter=new GoodsGridViewAdapter(HotSearchGoodsActivity.this,list);
            searchGoodGridView.setAdapter(adapter);
            searchGoodGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(HotSearchGoodsActivity.this, GoodsRecommandActivity.class);
                    intent.putExtra("GoodsURL",list.get(position).getGoods_url());
                    intent.putExtra("baike",list.get(position).getBaike_url());
                    intent.putExtra("goodname",list.get(position).getName());
                    startActivity(intent);
                }
            });
        }
    public void backClickSearch(View view) {
        finish();
    }

}
