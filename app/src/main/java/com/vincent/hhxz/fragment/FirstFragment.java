package com.vincent.hhxz.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.Utils.CornerImageTransFormation;
import com.vincent.hhxz.Utils.CropCircleTransformation;
import com.vincent.hhxz.Utils.RoundedCornersTransformation;
import com.vincent.hhxz.activity.AutoImageActivity;
import com.vincent.hhxz.activity.GoodsCategoryActivity;
import com.vincent.hhxz.activity.GoodsRecommandActivity;
import com.vincent.hhxz.activity.GoodsShareActivity;
import com.vincent.hhxz.activity.MainActivity;
import com.vincent.hhxz.activity.SearchActivity;
import com.vincent.hhxz.activity.SpecialActivity;
import com.vincent.hhxz.activity.SpecialListActivity;
import com.vincent.hhxz.adapter.AutoImageAdapter;
import com.vincent.hhxz.adapter.CateGoryAdapter;
import com.vincent.hhxz.adapter.GoodRecycleAdapter;
import com.vincent.hhxz.adapter.MiddleCategoryAdapter;
import com.vincent.hhxz.adapter.RVItemClickListener;
import com.vincent.hhxz.adapter.RVToucherListener;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.BannerBean;
import com.vincent.hhxz.bean.CateGoryBean;
import com.vincent.hhxz.bean.MiddleCateGoryBean;
import com.vincent.hhxz.bean.ShareGoodsBean;
import com.vincent.hhxz.bean.StrategyBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_TURN: //开消始轮询息
                    int pager = autoViewPager.getCurrentItem();
                    pager++;
                    autoViewPager.setCurrentItem(pager);
                    handler.sendEmptyMessageDelayed(MSG_TURN, DURATION_TURN);
                    break;
                case MSG_TURN_STOP: //停止轮询消息
                    handler.removeMessages(MSG_TURN); //移除轮询的消息
                    break;

                default:
                    break;
            }
        }
    };

    public static final int  MSG_TURN = 0x11;
    public static final int  MSG_TURN_STOP = 0x12;
    public static final int  MSG_BACK_RESET = 0x99;
    public static final long DURATION_TURN = 3000;

    private ViewPager autoViewPager;
    private GridView categoryGridView;
    private RequestQueue requestQueue;
    private CirclePageIndicator indicator;
    private GridView hotFindGridView;
    private LinearLayout bigImageLayout;
    private RecyclerView goodsRecycle;
    private TextView textSpecial;
    private ImageView imageViewSearch;
    private ImageView imageViewMenu;
   // private PopupWindow popupWindow;
    private View poplayout;
    private PopupWindow popupWindow;
    private LinearLayout titleLayout;
    private boolean isPopupWindowShowing=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(popupWindow==null){
            createPupopWindow();
        }
    }
    //产生popupwindow
    public void createPupopWindow(){
        poplayout =getActivity().getLayoutInflater().inflate(R.layout.pop_layout, null);
        popupWindow = new PopupWindow(poplayout,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.more_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                   isPopupWindowShowing=false;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getDownJsonData();
    }

    public void initView(View view){
        autoViewPager = (ViewPager) view.findViewById(R.id.viewpager_ff);
        categoryGridView = (GridView) view.findViewById(R.id.gridview_ff);
        hotFindGridView = ((GridView) view.findViewById(R.id.gridview_hotfind));
        bigImageLayout = ((LinearLayout) view.findViewById(R.id.bigImagell_ff));
        goodsRecycle = ((RecyclerView) view.findViewById(R.id.goodsrecyclerview_ff));
        textSpecial = ((TextView) view.findViewById(R.id.textSpecial_ff));
        titleLayout = ((LinearLayout) view.findViewById(R.id.title_layout));
        //查看全部专题的监听
        onClick();
        imageViewSearch = ((ImageView) view.findViewById(R.id.search_ff));
        searchClick();
        //3个点的监听
        imageViewMenu = ((ImageView) view.findViewById(R.id.menu_ff));
        menuClick();
    }

    //搜索按钮的监听
    public void searchClick(){
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    //menu的监听产生pupopWindow
    public void menuClick(){
        titleLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             //   Toast.makeText(getActivity(), "2344", Toast.LENGTH_SHORT).show();
                if(isPopupWindowShowing){
                    Log.i("345", "isShowing "+"进入");
                    popupWindow.dismiss();
                }else{
                    Log.i("345", "isShowing "+"未进入");
                    popupWindow.showAsDropDown(titleLayout);
                    isPopupWindowShowing=true;
                }
            }
        });
    }

    public void getDownJsonData(){
        requestQueue = MyApp.newInstance().getRequestQueue();
        JSONObject jsonObject=new JSONObject();
        Log.i("123", "requestQueue: "+requestQueue);
        JsonObjectRequest request=new JsonObjectRequest(Urls.HOME_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("123", "response: "+response);
                //自动轮循
                initViewPager(autoViewPager,response);

                //八大分类
                initCateGridView(categoryGridView,response);
                //热门发现
                initHotFindGridView(hotFindGridView,response);
                //三张大图
                initBigImageLayout(bigImageLayout,response);
                textSpecial.setVisibility(View.VISIBLE);
                //商品
                initGoodsRecyclerView(goodsRecycle,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
    //初始化viewpager
    public void initViewPager(ViewPager viewPager,JSONObject object){
        final List<BannerBean> bannerbeans=new ArrayList();
        List<ImageView> imageList=new ArrayList<>();
        //解析Jason数据的bannaelist对象
        try {
            JSONArray array=object.getJSONArray("bannerList");
            for (int i = 0; i <array.length(); i++) {
                BannerBean bean=new BannerBean();
                JSONObject obj=array.getJSONObject(i);
                bean.setLogo(obj.getString("logo"));
                bean.setLogo2(obj.getString("logo2"));
                bean.setDesc(obj.getString("desc"));
                bean.setName(obj.getString("name"));
                bean.setType(obj.getString("type"));
                bean.setUrl(obj.optString("url"));
                bannerbeans.add(bean);
            }
            //初始化viewpager的数据源
            for (int i = 0; i <bannerbeans.size() ; i++) {
                final int j=i;
                ImageView imageView=new ImageView(getActivity());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(getActivity()).load(bannerbeans.get(i).getLogo2()).fit()
                        .placeholder(R.mipmap.ic_launcher).into(imageView);
                Log.i("111", "url: "+bannerbeans.get(j).getUrl());

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!(bannerbeans.get(j).getUrl().equals(""))){
                            Intent intent =new Intent(getActivity(), AutoImageActivity.class);
                            intent.putExtra("Imgurl",bannerbeans.get(j).getUrl());
                            startActivity(intent);
                            }
                        }
                    });

                imageList.add(imageView);
            }
            AutoImageAdapter adapter=new AutoImageAdapter(imageList);
            viewPager.setAdapter(adapter);
         //   viewPager.setCurrentItem(Integer.MAX_VALUE/2);
            handler.sendEmptyMessageDelayed(MSG_TURN, DURATION_TURN);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onPageScrollStateChanged(int state) { //滑动状态的判断
                    // TODO Auto-generated method stub
                    switch (state) {
                        case ViewPager.SCROLL_STATE_IDLE:
                            //停止滑动状态
                            if(!handler.hasMessages(MSG_TURN)){ //如果没有轮询
                                //重新开始轮询
                                handler.sendEmptyMessageDelayed(MSG_TURN, DURATION_TURN);
                            }
                            break;
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            //手指在屏幕滑动的状态
                            //发送停止轮询的消息(立即发送)
                            handler.sendEmptyMessage(MSG_TURN_STOP);
                            break;
                        case ViewPager.SCROLL_STATE_SETTLING:

                            break;
                        default:
                            break;
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化八大分类cateGridView的布局
    public void initCateGridView(GridView gridView,JSONObject object){
        final List<CateGoryBean> categoryBeans=new ArrayList<>();
        try {
            JSONArray array=object.getJSONArray("categoryList");
            for (int i = 0; i <array.length() ; i++) {
                CateGoryBean bean=new CateGoryBean();
                JSONObject obj=array.getJSONObject(i);
                bean.setType(obj.getString("type"));
                bean.setName(obj.getString("name"));
                bean.setCat_id(obj.getString("cat_id"));
                bean.setIcon(obj.getString("icon"));
                bean.setId(obj.getString("id"));
                bean.setIs_list(obj.optString("is_list"));
                bean.setIs_red(obj.optBoolean("is_red"));
                bean.setLogo(obj.optString("logo"));
                bean.setM_index_id(obj.optString("m_index_id"));
                categoryBeans.add(bean);
            }
            CateGoryAdapter adapter=new CateGoryAdapter(getActivity(),categoryBeans);
            gridView.setAdapter(adapter);
            //每一类的监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(), GoodsCategoryActivity.class);
                    CateGoryBean itemBean = categoryBeans.get(position);
                    intent.putExtra("id",itemBean.getId());
                    intent.putExtra("name",itemBean.getName());
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化hotFindGridView布局
    public void initHotFindGridView(GridView gridView,JSONObject object){
        List<MiddleCateGoryBean> lists=new ArrayList<>();
        try {
            JSONArray array=object.getJSONArray("middleCategoryList");
            for (int i = 0; i <array.length() ; i++) {
                MiddleCateGoryBean bean=new MiddleCateGoryBean();
                JSONObject obj=array.getJSONObject(i);
                bean.setCat_id(obj.getString("cat_id"));
                bean.setDesc(obj.getString("desc"));
                bean.setIcon(obj.optString("icon"));
                bean.setName(obj.optString("name"));
                lists.add(bean);
            }
            MiddleCategoryAdapter adapter=new MiddleCategoryAdapter(getActivity(),lists);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position==0){
                            //跳转到发现的fragment;
                            ((MainActivity) getActivity()).fTablayout.setCurrentTab(2);
                        }else if(position==1){
                            //跳转到分类的fragment;
                            ((MainActivity) getActivity()).fTablayout.setCurrentTab(1);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化三张大图布局
    public void initBigImageLayout(LinearLayout ll,JSONObject object){
            final List<StrategyBean> lists=new ArrayList<>();
        try {
            JSONArray array = object.getJSONArray("strategies_at_index");
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
            for (int i = 0; i <lists.size() ; i++) {
                final int j=i;
                View view=getActivity().getLayoutInflater().inflate(R.layout.bigimage_item,null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                ImageView iv = (ImageView) view.findViewById(R.id.bigImage);
                TextView tvWord = (TextView) view.findViewById(R.id.textword_bigImage);
                tvWord.setText(lists.get(i).getName());
                Picasso.with(getActivity()).load(lists.get(i).getIcon()).transform(new CornerImageTransFormation(10))
                        .resize(1080,400).placeholder(R.mipmap.ic_launcher).into(iv);
                //http://mt.sohu.com/20160424/n445851123.shtml
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), SpecialActivity.class);
                        intent.putExtra("share_id",lists.get(j).getShare_id());
                        startActivity(intent);
                    }
                });
                ll.addView(view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //查看更多的监听
    public void onClick(){
        textSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SpecialListActivity.class);
                startActivity(intent);
            }
        });
    }

    //商品展示布局
    public void initGoodsRecyclerView(RecyclerView recyclerView,JSONObject object){
        final List<ShareGoodsBean> list=new ArrayList<>();
        try {
            JSONArray array=object.getJSONArray("shareList");
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
            GoodRecycleAdapter adapter=new GoodRecycleAdapter(getActivity(),list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            recyclerView.addOnItemTouchListener(new RVToucherListener(getActivity(), recyclerView, new RVItemClickListener() {
                @Override
                public void onItemClicked(View itemView, int position) {
                    Intent intent=new Intent(getActivity(), GoodsRecommandActivity.class);
                    intent.putExtra("GoodsURL",list.get(position).getGoods_url());
                    intent.putExtra("baike",list.get(position).getBaike_url());
                    intent.putExtra("goodname",list.get(position).getName());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClicked(View itemView, int position) {

                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}