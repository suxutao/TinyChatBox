package com.example.tinychatbox.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinychatbox.R;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class LoginActivity extends AppCompatActivity {

    private EditText tiny_name;
    private EditText tiny_password;
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }

    private void initListener() {
        //注册按钮
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhuce();
            }
        });
        //登录按钮
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denglu();
            }
        });
    }

    //登录业务
    private void denglu() {
        //获取校验账号和密码
        String loginName=tiny_name.getText().toString();
        String loginPwd=tiny_password.getText().toString();

        if (TextUtils.isEmpty(loginName)||TextUtils.isEmpty(loginPwd)){
            Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        //具体登录逻辑
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //登录操作
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Model.getInstance().loginSuccess();
                        //保存数据到本地数据库
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提示登录成功
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                                //挑战到主页面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提示登录失败
                                Toast.makeText(LoginActivity.this,"登录失败"+error,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            }
        });
    }

    //注册业务
    private void zhuce() {
        //获取校验账号和密码
        String registerName=tiny_name.getText().toString();
        String registerPwd=tiny_password.getText().toString();

        if (TextUtils.isEmpty(registerName)||TextUtils.isEmpty(registerPwd)){
            Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //去服务器注册账号
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(registerName,registerPwd);
                    //输出成功提示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        tiny_name= findViewById(R.id.tiny_name);
        tiny_password= findViewById(R.id.tiny_password);
        register= findViewById(R.id.register);
        login= findViewById(R.id.login);
    }
}