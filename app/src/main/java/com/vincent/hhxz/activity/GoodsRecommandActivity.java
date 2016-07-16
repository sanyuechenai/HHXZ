package com.vincent.hhxz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.vincent.hhxz.R;

public class GoodsRecommandActivity extends AppCompatActivity {
    private String goodsUrl;
    private String goodBaikeUri;
    private WebView webView;
    private ImageView imageViewCar;
    private String goodName;
    private ImageView loadingData;
    private RotateAnimation animation;
    private int stopAnimation;
    private long duration=5000;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==stopAnimation){
                loadingData.clearAnimation();
                loadingData.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_recommand);
        Intent intent=getIntent();
        goodsUrl = intent.getStringExtra("GoodsURL");
        goodBaikeUri=intent.getStringExtra("baike");
        goodName = intent.getStringExtra("goodname");
        if(goodBaikeUri.isEmpty()){
            Intent intent2=new Intent(this,GoodsShareActivity.class);
            intent2.putExtra("goodWebsite",goodsUrl);
            intent2.putExtra("goodname",goodName);
            startActivity(intent2);
            finish();
            return;
        }
        webView = ((WebView) findViewById(R.id.webView_goodsrecommand));
        webView.getSettings().setJavaScriptEnabled(true);
        loadingData = ((ImageView) findViewById(R.id.loadingdata_gra));
        animation=(RotateAnimation) AnimationUtils.loadAnimation(
                GoodsRecommandActivity.this, R.anim.reflashing);
        animation.setDuration(2000);
        loadingData.startAnimation(animation);
        handler.sendEmptyMessageDelayed(stopAnimation,duration);
        //加载具体的网址
        //设置WebView自己打开相应的网址
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(goodBaikeUri);

        imageViewCar = ((ImageView) findViewById(R.id.addcar));
        imageViewCar.setImageResource(R.mipmap.login_taobao);
        imageViewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GoodsRecommandActivity.this,GoodsShareActivity.class);
                intent.putExtra("goodWebsite",goodsUrl);
                intent.putExtra("goodname",goodName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {  //按下实体的返回键
            if (webView.canGoBack()) {  //说明WebView有上一页
                webView.goBack();  //WebView返回上一页
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void click(View view) {
        finish();
    }
}
