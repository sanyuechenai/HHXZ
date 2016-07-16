package com.vincent.hhxz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;
import com.vincent.hhxz.adapter.SearchKeyAdapter;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.SearchKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private TextView searchText;
    private ImageView imageViewClear;
    private RequestQueue requestQueue;
    private List<String> hotList=new ArrayList<>();
    private List<String> searchKeyList=new ArrayList<>();
    private TextView search0;
    private TextView search1;
    private TextView search2;
    private TextView search3;
    private TextView search4;
    private TextView search5;
    private TextView search6;
    private TextView search7;
    private TextView search8;
    private TextView search9;
    private TextView search10;
    private TextView search11;
    private ListView listViewOldSearch;
    private LinearLayout oldList;
    private SearchKeyAdapter adapter;
    private TextView deleteAll;
    private TextView searchBtn;
    private LinearLayout hotkeyLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        queryData();
        initView();
    }
    //查询数据库方法
    public void queryData(){
        Select select = new Select();
        List<SearchKeys> list = select.from(SearchKeys.class).execute();
        if(list.size()!=0){
            for (int i=0;i<list.size();i++) {
                searchKeyList.add(list.get(list.size()-1-i).getKeyWord());
            }
        }
    }
    //向数据库添加数据
    public void addData(String key){
        SearchKeys sk=new SearchKeys(key);
        sk.save();
    }
    //删除数据库内容
    public void deleteData(){
        Delete delete=new Delete();
        delete.from(SearchKeys.class).execute();
    }
    private void initView() {
        searchEditText = ((EditText) findViewById(R.id.et_SA));
        hotkeyLayout = ((LinearLayout) findViewById(R.id.hotkeyviewlayout_sa));
        searchText = ((TextView) findViewById(R.id.search_SA));
        imageViewClear = ((ImageView) findViewById(R.id.clear_SA));
        //edittext的监听。有字符时。X号显示
        searchEditText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp=s;
                imageViewClear.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length()==0){
                    imageViewClear.setVisibility(View.INVISIBLE);
                }
            }
        });
        //清除按钮的监听
        imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                imageViewClear.setVisibility(View.INVISIBLE);
            }
        });
        hotSearchView();
        //历史搜索列表
        listViewOldSearch = ((ListView) findViewById(R.id.oldsearch_item_listview));
        adapter=new SearchKeyAdapter(this,searchKeyList);
        listViewOldSearch.setAdapter(adapter);
        listViewOldSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                turnActivity(searchKeyList.get(position));
            }
        });
        //是否隐藏
        oldList = ((LinearLayout) findViewById(R.id.oldsearch_layout_SA));
        if(searchKeyList.size()!=0){
            oldList.setVisibility(View.VISIBLE);
        }
        //搜索的监听
        searchBtn = ((TextView) findViewById(R.id.search_SA));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=searchEditText.getText().toString().trim();
                if(!s.isEmpty()){
                    turnActivity(s);
                }else{
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //清除历史记录的监听
        deleteAll= ((TextView) findViewById(R.id.delete_btn_SA));
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                oldList.setVisibility(View.INVISIBLE);
            }
        });
    }
    //初始化热门搜索布局
    public void hotSearchView(){
         search0 = (TextView)findViewById(R.id.search0);
         search1 = (TextView)findViewById(R.id.search1);
         search2 = (TextView)findViewById(R.id.search2);
         search3 = (TextView)findViewById(R.id.search3);
         search4 = (TextView)findViewById(R.id.search4);
         search5 = (TextView)findViewById(R.id.search5);
         search6 = (TextView)findViewById(R.id.search6);
         search7 = (TextView)findViewById(R.id.search7);
         search8 = (TextView)findViewById(R.id.search8);
         search9 = (TextView)findViewById(R.id.search9);
         search10 = (TextView)findViewById(R.id.search10);
         search11 = (TextView)findViewById(R.id.search11);
        requestQueue = MyApp.newInstance().getRequestQueue();
        JSONObject jsonObject=new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Urls.HOT_SEARCH, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array=response.getJSONArray("hots");
                    for (int i = 0; i <12 ; i++) {
                        hotList.add(array.getString(i));
                    }
                    initTextView(hotList);
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
    //12个热门搜索词的搜索
    public void initTextView(List<String> list){
        hotkeyLayout.setVisibility(View.VISIBLE);
        search0.setText(list.get(0));
        search0.setOnClickListener(new Listener());
        search1.setText(list.get(1));
        search1.setOnClickListener(new Listener());
        search2.setText(list.get(2));
        search2.setOnClickListener(new Listener());
        search3.setText(list.get(3));
        search3.setOnClickListener(new Listener());
        search4.setText(list.get(4));
        search4.setOnClickListener(new Listener());
        search5.setText(list.get(5));
        search5.setOnClickListener(new Listener());
        search6.setText(list.get(6));
        search6.setOnClickListener(new Listener());
        search7.setText(list.get(7));
        search7.setOnClickListener(new Listener());
        search8.setText(list.get(8));
        search8.setOnClickListener(new Listener());
        search9.setText(list.get(9));
        search9.setOnClickListener(new Listener());
        search10.setText(list.get(10));
        search10.setOnClickListener(new Listener());
        search11.setText(list.get(11));
        search11.setOnClickListener(new Listener());
    }
    //跳转界面的方法
    public void turnActivity(String searchKey){
        boolean isHasSearchkey=false;
        Intent intent=new Intent(SearchActivity.this,HotSearchGoodsActivity.class);
        intent.putExtra("hotkey",searchKey);
        startActivity(intent);
        //将搜索栏里的字加入到集合中
        if(searchKeyList.size()==0){
          //  searchKeyList.add(0,searchKey);
            addData(searchKey);
            queryData();
            adapter.notifyDataSetChanged();
            oldList.setVisibility(View.VISIBLE);
            //刷新适配器
        }else{
            for (int i = 0; i <searchKeyList.size() ; i++) {
                if(searchKey.equals(searchKeyList.get(i))){
                    isHasSearchkey=true;
                    return ;
                }
            }
            if(!isHasSearchkey){
                searchKeyList.clear();
                addData(searchKey);
                queryData();
                //刷新适配器
                adapter.notifyDataSetChanged();
                oldList.setVisibility(View.VISIBLE);
            }
        }

    }

    //返回监听
    public void btnClickSearch(View view) {

        finish();
    }
    //每一个热门搜索的监听
    public class Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search0:
                    searchEditText.setText(search0.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search1:
                    searchEditText.setText(search1.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search2:
                    searchEditText.setText(search2.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search3:
                    searchEditText.setText(search3.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search4:
                    searchEditText.setText(search4.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search5:
                    searchEditText.setText(search5.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search6:
                    searchEditText.setText(search6.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search7:
                    searchEditText.setText(search7.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search8:
                    searchEditText.setText(search8.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search9:
                    searchEditText.setText(search9.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search10:
                    searchEditText.setText(search10.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
                case R.id.search11:
                    searchEditText.setText(search11.getText().toString());
                    turnActivity(searchEditText.getText().toString().trim());
                    break;
            }
        }
    }
}
