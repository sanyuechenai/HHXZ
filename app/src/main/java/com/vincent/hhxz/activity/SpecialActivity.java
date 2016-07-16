package com.vincent.hhxz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;

public class SpecialActivity extends AppCompatActivity {

    private WebView webView;
    private String imgUrl;
       //     ="http://www.hehe168.com/note/3g/";
       private ImageView loading;
    private Animation animation;
    private int duration=5000;
    private int show=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==show){
                webView.setVisibility(View.VISIBLE);
                loading.clearAnimation();
                loading.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        Intent intent=getIntent();
        imgUrl = intent.getStringExtra("share_id");
        Log.i("123", "网址: "+Urls.SPECIAL_DETIALS+imgUrl);
        loading = ((ImageView) findViewById(R.id.loadingdata_sa));
        animation=(RotateAnimation) AnimationUtils.loadAnimation(
                SpecialActivity.this, R.anim.loading);
        animation.setDuration(2000);
        loading.startAnimation(animation);
        webView = ((WebView) findViewById(R.id.webView_Spcial));
        webView.getSettings().setJavaScriptEnabled(true);
        //加载具体的网址
        //设置WebView自己打开相应的网址
        handler.sendEmptyMessageDelayed(show,duration);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Urls.SPECIAL_DETIALS+imgUrl);

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

    public void shareClick(View view) {

    }

    public void backClick(View view) {
       finish();
    }
}
