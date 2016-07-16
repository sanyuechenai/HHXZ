package com.vincent.hhxz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.vincent.hhxz.R;
import com.vincent.hhxz.app.MyApp;
import com.vincent.hhxz.bean.SearchKeys;
import com.vincent.hhxz.bean.UserAccount;
import com.vincent.hhxz.fragment.FragmentEvent;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser;
    private ImageView clearUser;
    private ImageView clearPassWord;
    private EditText editTextPw;
    private ImageView pw_show;
    private ImageView pw_miss;
    private Button login;
    private SharedPreferences spflogin;
    SharedPreferences spfUser;
    private TextView registerText;
    private String initUseName="";
    private String initPassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         spflogin=getSharedPreferences(MyApp.shref_filename,MODE_PRIVATE);
         spfUser=getSharedPreferences(MyApp.shref_Username,MODE_PRIVATE);
       //注册界面传值过来
        Intent intent=getIntent();
        initUseName=intent.getStringExtra("nichen");
        initPassword=intent.getStringExtra("password");
        initView(initUseName,initPassword);

    }



    public void initView(String s1,String s2){
        //账户
        editTextUser = ((EditText) findViewById(R.id.useredit_login));
        editTextUser.setText(s1);
        //密码
        editTextPw = ((EditText) findViewById(R.id.pwEdit_login));
        editTextPw.setText(s2);
        //清除按钮
        clearUser = ((ImageView) findViewById(R.id.clear_user));
        clearPassWord = ((ImageView) findViewById(R.id.clear_pw));
        //清除按钮的监听
        clearUser.setOnClickListener(new ClearListener());
        clearPassWord.setOnClickListener(new ClearListener());
        //账户editText的监听
        editTextUser.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp=s;
                clearUser.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length()==0){
                    clearUser.setVisibility(View.INVISIBLE);
                }
            }
        });
        //密码editText的监听
        editTextPw.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   temp=s;
                clearPassWord.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length()==0){
                    clearPassWord.setVisibility(View.INVISIBLE);
                }
            }
        });

        pw_show = ((ImageView) findViewById(R.id.pwshow_login));
        pw_miss = ((ImageView) findViewById(R.id.pwmiss_login));
        pw_show.setOnClickListener(new Listener());
        pw_miss.setOnClickListener(new Listener());

        login = ((Button) findViewById(R.id.login));
        login.setOnClickListener(new LoginListener());
        //注册按钮的监听
        registerText = ((TextView) findViewById(R.id.register_login));
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //密码是否可见的监听实现类
    public class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pwshow_login://密码隐藏
                    editTextPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pw_show.setVisibility(View.GONE);
                    pw_miss.setVisibility(View.VISIBLE);
                    break;
                case R.id.pwmiss_login://密码显示
                    editTextPw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pw_show.setVisibility(View.VISIBLE);
                    pw_miss.setVisibility(View.GONE);
                    break;
            }
        }
    }
        //清除按钮的监听实现类
    public class ClearListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.clear_user:
                    editTextUser.setText("");
                    break;
                case R.id.clear_pw:
                    editTextPw.setText("");
                    break;
            }
        }
    }


    public class LoginListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String sName=editTextUser.getText().toString().trim();
            String sPassword = editTextPw.getText().toString().trim();
            if(sName.equals("")||sPassword.equals("")){
                Toast.makeText(LoginActivity.this, "请输入账户名或者密码", Toast.LENGTH_SHORT).show();
                return;
            }
            //查询数据库是否有该账户信息
            Select select=new Select();
            List<UserAccount> list = select.from(UserAccount.class).where("user_name=?",sName).execute();
            if(list.size()!=0){//有该账户
                UserAccount bean=list.get(0);
                String userName = bean.getUserName();
                String password = bean.getPassword();
                if(sName.equals(userName)&&sPassword.equals(password)){
                    spflogin.edit().putBoolean(MyApp.IS_LOGIN,true).commit();
                    spfUser.edit().putString(MyApp.IS_LOGIN_NAME,sName).commit();
                    String update="更新UI";
                    EventBus.getDefault().post(update);
                    finish();
                }else{
                    editTextPw.setText("");
                    Toast.makeText(LoginActivity.this, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }else{//没有该账户
                Toast.makeText(LoginActivity.this, "您输入的账户不存在！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void backClick(View view) {
        finish();
    }
}
