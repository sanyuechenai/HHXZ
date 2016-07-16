package com.vincent.hhxz.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vincent.hhxz.R;

public class SplashActivity extends AppCompatActivity {

    private int count=3;
    private static final int MSG=0x00;
    private static final int DERATION=1000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    count--;
                    if (count <= 0) {
                        if (handler.hasMessages(MSG))
                            handler.removeMessages(MSG);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        handler.sendEmptyMessageDelayed(MSG, DERATION);
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(MSG,DERATION);
    }
}
