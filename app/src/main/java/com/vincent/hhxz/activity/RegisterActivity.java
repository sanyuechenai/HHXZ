package com.vincent.hhxz.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.vincent.hhxz.R;
import com.vincent.hhxz.bean.UserAccount;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNiChen;
    private EditText editTextEmail;
    private EditText editTextPw;
    private ImageView clearNiChen;
    private ImageView clearEmail;
    private ImageView clearPw;
    private ImageButton imageOK;
    private boolean isNiChenOK=false;
    private boolean isEmailOK=false;
    private boolean isPassWordOK=false;
    private ImageView pw_show;
    private ImageView pw_miss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    public void initView(){
        imageOK = ((ImageButton) findViewById(R.id.ok_register));
        imageOK.setEnabled(false);
        editTextNiChen = ((EditText) findViewById(R.id.nicheng_register));
        editTextEmail = ((EditText) findViewById(R.id.email_register));
        editTextPw = ((EditText) findViewById(R.id.pwEdit_register));
        clearNiChen = ((ImageView) findViewById(R.id.clear_nicheng));
        clearNiChen.setOnClickListener(new clearListener());
        clearEmail = ((ImageView) findViewById(R.id.clear_email));
        clearEmail.setOnClickListener(new clearListener());
        clearPw = ((ImageView) findViewById(R.id.clearpw_register));
        clearPw.setOnClickListener(new clearListener());
        //昵称的监听
        editTextNiChen.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    temp=s;
                clearNiChen.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length()==0){
                    clearNiChen.setVisibility(View.INVISIBLE);
                    isNiChenOK=false;
                    imageOK.setEnabled(false);
                }else{
                    isNiChenOK=true;
                }
                if(isNiChenOK&&isEmailOK&&isPassWordOK){
                    imageOK.setEnabled(true);
                }
            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp=s;
                clearEmail.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length()==0){
                    clearEmail.setVisibility(View.INVISIBLE);
                    isEmailOK=false;
                    imageOK.setEnabled(false);
                }else{
                    isEmailOK=true;
                }
                if(isNiChenOK&&isEmailOK&&isPassWordOK){
                    imageOK.setEnabled(true);
                }
            }
        });

        editTextPw.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp=s;
                clearPw.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(temp.length()==0){
                    clearPw.setVisibility(View.INVISIBLE);
                    isPassWordOK=false;//标志设为否（表示该edittext为空）
                    imageOK.setEnabled(false);
                }else{
                    isPassWordOK=true;
                }
                //判断3个editText是否都不为空
                if(isNiChenOK&&isEmailOK&&isPassWordOK){
                    imageOK.setEnabled(true);
                }
            }
        });
        //密码是否可见的监听
        pw_show = ((ImageView) findViewById(R.id.pwshow_regieter));
        pw_miss = ((ImageView) findViewById(R.id.pwmiss_register));
        pw_show.setOnClickListener(new Listener());
        pw_miss.setOnClickListener(new Listener());
        imageOK.setOnClickListener(new OkListener());
    }
    //查询数据库是否存在该昵称
    public List<UserAccount> QueryData(String nichen){

        Select select=new Select();
        List<UserAccount> list = select.from(UserAccount.class).where("user_name=?", nichen).execute();
        return list;
    }
    //将注册好的信息写入到数据库中

    public void addData(String niChen,String passWord){
        UserAccount user=new UserAccount(niChen,passWord);
        user.save();
    }

    public class OkListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String niChen = editTextNiChen.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPw.getText().toString().trim();
            if(niChen.equals("")||email.equals("")||password.equals("")){
                Toast.makeText(RegisterActivity.this, "昵称或者邮箱或者密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if(QueryData(niChen).size()!=0){
                Toast.makeText(RegisterActivity.this, "该昵称已存在，请更换", Toast.LENGTH_SHORT).show();

            }else{
                if(isEmailLegal(email)){
                    //将注册昵称和密码写入数据库
                    addData(niChen,password);
                    //弹出对话框，表示注册成功，同时提醒你是否立即登录
                    createDialog(niChen,password);
                }else{
                    Toast.makeText(RegisterActivity.this, "邮箱不合法", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void createDialog(final String niChen, final String password){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("恭喜你注册成功");
        builder.setMessage("是否立即登录");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("nichen",niChen);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // finish();
            }
        });
        builder.create().show();
    }
    //判断邮箱是否合法
    public boolean isEmailLegal(String email){
        boolean isExit=email.contains("@");
        int firstPosition=email.indexOf("@");
        int lastPosition=email.lastIndexOf("@");
        boolean isOnly=((firstPosition==lastPosition)? true:false);
        boolean isExit2=email.contains(".");
        int firstPosition2=email.indexOf(".");
        int lastPosition2=email.lastIndexOf(".");
        boolean isOnly2=((firstPosition2==lastPosition2)? true:false);
        if(isExit&&isExit2&&isOnly&&isOnly2&&firstPosition!=0&&firstPosition2!=0&&
                firstPosition+1<firstPosition2&&(firstPosition2!=email.length()-1)){
            return true;
        }else{
            return false;
        }

    }
    //清除内容的监听类
    public class clearListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.clear_nicheng:
                    editTextNiChen.setText("");
                    break;
                case R.id.clear_email:
                    editTextEmail.setText("");
                    break;
                case R.id.clearpw_register:
                    editTextPw.setText("");
                    break;

            }
        }
    }

    //密码是否可见的监听类
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


    public void backClick(View view) {
        finish();
    }
}
