package com.vincent.hhxz.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.vincent.hhxz.R;
import com.vincent.hhxz.Utils.MyGridView;
import com.vincent.hhxz.activity.GoodsCategoryActivity;
import com.vincent.hhxz.adapter.FindFragmentAdapter;
import com.vincent.hhxz.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private String[] gongyongImgUrls=new String[]{
            "http://www.hehe168.com/public/upload/images/201512/13/566d0a827d9ce.png",    //补血养颜
            "http://www.hehe168.com/public/upload/images/201512/11/566a92750ab10.png",//润喉润肺
            "http://www.hehe168.com/public/upload/images/201512/11/566a92823fea3.png",//助神安眠
            "http://www.hehe168.com/public/upload/images/201512/11/566a9293878f2.png",//去火清热
            "http://www.hehe168.com/public/upload/images/201512/11/566a929f89bca.png",//乌黑益肾
            "http://www.hehe168.com/public/upload/images/201512/11/566a92aab5345.png",//护肝明目
            "http://www.hehe168.com/public/upload/images/201512/11/566a92be1a58d.png",    //滋养脾胃
            "http://www.hehe168.com/public/upload/images/201512/11/566a92cbedc37.png",    //预防感冒
            "http://www.hehe168.com/public/upload/images/201512/11/566a92d5d1e62.png"	//降三高
    };
    private String[] kouganImgUrls=new String[]{
            "http://www.hehe168.com/public/upload/images/201512/14/566e38eff1c14.png",    //酸酸
            "http://www.hehe168.com/public/upload/images/201512/14/566e3d2256890.png",    //甜甜
            "http://www.hehe168.com/public/upload/images/201512/14/566e6882419e5.png",    //咸咸
            "http://www.hehe168.com/public/upload/images/201512/14/566e7220c8014.png",    //鲜鲜
            "http://www.hehe168.com/public/upload/images/201512/14/566e38d2e9916.png",    //苦苦
            "http://www.hehe168.com/public/upload/images/201512/14/566e38dfd7771.png"    //麻辣
    };
    private String[] jieriImgUrls=new String[]{
            "http://www.hehe168.com/public/upload/images/201512/10/5669505006cee.png",    //圣诞节
            "http://www.hehe168.com/public/upload/images/201512/10/5669232b5e02a.png",    //新年
            "http://www.hehe168.com/public/upload/images/201512/10/56692fe94ec14.png", //父亲节
            "http://www.hehe168.com/public/upload/images/201512/10/566936680dda6.png",    //母亲节
            "http://www.hehe168.com/public/upload/images/201512/10/566940763bda0.png", //生日
            "http://www.hehe168.com/public/upload/images/201512/10/56693cf466946.png", //七夕节
            "http://www.hehe168.com/public/upload/images/201512/10/56694c271fdca.png", //中秋
            "http://www.hehe168.com/public/upload/images/201512/10/56694d33966f1.png"    //端午
    };
    private String[] changheImgUrls=new String[]{
            "http://www.hehe168.com/public/upload/images/201512/13/566d2c044f833.png",    //早餐
            "http://www.hehe168.com/public/upload/images/201512/13/566d2c462f84a.png",    //烘培
            "http://www.hehe168.com/public/upload/images/201512/13/566d1fe7d1c3d.png",    //办公室
            "http://www.hehe168.com/public/upload/images/201512/13/566d2c17d99cc.png",    //Party
            "http://www.hehe168.com/public/upload/images/201512/13/566d20049cb77.png",    //户外
            "http://www.hehe168.com/public/upload/images/201512/13/566d2c2753e58.png",    //结婚
            "http://www.hehe168.com/public/upload/images/201512/13/566d2c36af54a.png",    //感谢
            "http://www.hehe168.com/public/upload/images/201512/13/566d202e3d2ea.png", //火锅
            "http://www.hehe168.com/public/upload/images/201512/18/5673d9f778f30.png"//夜宵
    };
    private String[] renqunImgUrls=new String[]{
            "http://www.hehe168.com/public/upload/images/201512/10/566939a5926a5.png",    //自己
            "http://www.hehe168.com/public/upload/images/201512/13/566d369764e33.png",//她他
            "http://www.hehe168.com/public/upload/images/201512/13/566d3c4064401.png",    //爸妈
            "http://www.hehe168.com/public/upload/images/201512/14/566e25eb86735.png", //宝宝
            "http://www.hehe168.com/public/upload/images/201512/13/566d36b180d79.png",    //熊孩子
            "http://www.hehe168.com/public/upload/images/201512/13/566d3f403f309.png", //孕妈
            "http://www.hehe168.com/public/upload/images/201512/17/5672294222c06.png",    //BOSS
            "http://www.hehe168.com/public/upload/images/201512/18/5673ceea2067c.png",    //丈母娘
            "http://www.hehe168.com/public/upload/images/201512/13/566d36852c78a.png" //闺蜜们
    };
    private String[] gongyongName=new String[]{
            "补血养颜","润喉润肺","助神安眠","去火清热","乌黑益肾",
            "护肝明目","滋养脾胃","预防感冒","降三高"
    };
    private String[] gonggongGoodsId=new String[]{
            "313","314","415","316","317","318","405","406","407"
    };

    private String[] kouganName=new String[]{
            "酸酸", "甜甜", "咸咸", "鲜鲜", "苦苦", "麻辣"
    };
    private String[] kouganGoodsId=new String[]{
            "405","406","407","408","409","410"
    };
    private String[] jieriName=new String[]{
            "圣诞节","新年","父亲节","母亲节","生日","七夕节","中秋","端午"
    };
    private String[] jieriGoodsId=new String[]{
            "324","323","350","343","319","320","339","338"
    };
    private String[] changheName=new String[]{
            "早餐","烘培","办公室","Party","户外","结婚","感谢","火锅","夜宵"
    };
    private String[] changheGoodsId=new String[]{
            "349","333","334","340","341","335","336","357","321"
    };
    private String[] renqunName=new String[]{
            "自己","她他","爸妈","宝宝","熊孩子","孕妈","BOSS","丈母娘","闺蜜们"
    };
    private String[] renqunGoodsId=new String[]{
            "305","306","307","348","308","309","310","311","312"
    };

    private List<GoodsBean> gongyongList=new ArrayList<>();
    private List<GoodsBean> kouganList=new ArrayList<>();
    private List<GoodsBean> jieriList=new ArrayList<>();
    private List<GoodsBean> changheList=new ArrayList<>();
    private List<GoodsBean> renqunList=new ArrayList<>();
    private MyGridView gongyongGridView;
    private MyGridView kouganGridView;
    private MyGridView jieriGridView;
    private MyGridView changheGridView;
    private MyGridView renqunGridView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        gongyongGridView = ((MyGridView) view.findViewById(R.id.gongyong_searchfragment));
        kouganGridView = ((MyGridView) view.findViewById(R.id.kougan_searchfragment));
        jieriGridView = ((MyGridView) view.findViewById(R.id.jieri_searchfragment));
        changheGridView = ((MyGridView) view.findViewById(R.id.changhe_searchfragment));
        renqunGridView = ((MyGridView) view.findViewById(R.id.renquan_searchfragment));
        FindFragmentAdapter adapter0=new FindFragmentAdapter(getActivity(),gongyongList);
        FindFragmentAdapter adapter1=new FindFragmentAdapter(getActivity(),kouganList);
        FindFragmentAdapter adapter2=new FindFragmentAdapter(getActivity(),jieriList);
        FindFragmentAdapter adapter3=new FindFragmentAdapter(getActivity(),changheList);
        FindFragmentAdapter adapter4=new FindFragmentAdapter(getActivity(),renqunList);

       //每个GridView的绑定数据源和监听
        gongyongGridView.setAdapter(adapter0);
        gongyongGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnActivity(gongyongList,position);
            }
        });
        kouganGridView.setAdapter(adapter1);
        kouganGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnActivity(kouganList,position);
            }
        });
        jieriGridView.setAdapter(adapter2);
        jieriGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnActivity(jieriList,position);
            }
        });
        changheGridView.setAdapter(adapter3);
        changheGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnActivity(changheList,position);
            }
        });
        renqunGridView.setAdapter(adapter4);
        renqunGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnActivity(renqunList,position);
            }
        });
    }

            //跳转界面方法
    public void turnActivity(List<GoodsBean> list,int position){
        Intent intent=new Intent(getActivity(), GoodsCategoryActivity.class);
        intent.putExtra("id",list.get(position).getImgId());
        intent.putExtra("name",list.get(position).getImgName());
        startActivity(intent);

    }
            //初始化数据源
    public void initData(){
        for (int i = 0; i <gongyongImgUrls.length ; i++) {
            GoodsBean bean=new GoodsBean();
            bean.setImgUrl(gongyongImgUrls[i]);
            bean.setImgName(gongyongName[i]);
            bean.setImgId(gonggongGoodsId[i]);
            gongyongList.add(bean);
        }
        for (int i = 0; i <kouganImgUrls.length ; i++) {
            GoodsBean bean=new GoodsBean();
            bean.setImgUrl(kouganImgUrls[i]);
            bean.setImgName(kouganName[i]);
            bean.setImgId(kouganGoodsId[i]);
            kouganList.add(bean);
        }
        for (int i = 0; i <jieriImgUrls.length ; i++) {
            GoodsBean bean=new GoodsBean();
            bean.setImgUrl(jieriImgUrls[i]);
            bean.setImgName(jieriName[i]);
            bean.setImgId(jieriGoodsId[i]);
            jieriList.add(bean);
        }
        for (int i = 0; i <changheImgUrls.length ; i++) {
            GoodsBean bean=new GoodsBean();
            bean.setImgUrl(changheImgUrls[i]);
            bean.setImgName(changheName[i]);
            bean.setImgId(changheGoodsId[i]);
            changheList.add(bean);
        }
        for (int i = 0; i <renqunImgUrls.length ; i++) {
            GoodsBean bean=new GoodsBean();
            bean.setImgUrl(renqunImgUrls[i]);
            bean.setImgName(renqunName[i]);
            bean.setImgId(renqunGoodsId[i]);
            renqunList.add(bean);
        }
    }
}
