package com.vincent.hhxz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.vincent.hhxz.R;
import com.vincent.hhxz.Url.Urls;

public class GoodsShareActivity extends AppCompatActivity {

    private String goodsUrl;
    private WebView webView;
    private String goodName;
    private TextView goodTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_share);
        Intent intent=getIntent();
        goodsUrl = intent.getStringExtra("goodWebsite");
        goodName = intent.getStringExtra("goodname");
        goodTitle = ((TextView) findViewById(R.id.name_goodsshare));
        goodTitle.setText(goodName);
        webView = ((WebView) findViewById(R.id.webView_goodsshare));
        webView.getSettings().setJavaScriptEnabled(true);
        //加载具体的网址
        //设置WebView自己打开相应的网址
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(goodsUrl);
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
