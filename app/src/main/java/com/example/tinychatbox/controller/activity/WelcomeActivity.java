package com.example.tinychatbox.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tinychatbox.R;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;

public class WelcomeActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(@NonNull Message msg){
            if (isFinishing()){
                return;
            }
            //判断进入主页面还是登录页面
            toMainOrLogin();
        }

        private void toMainOrLogin() {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (EMClient.getInstance().isLoggedInBefore()){
                        UserInfo user = Model.getInstance().getUserAccountDao().getAccountByHXID(EMClient.getInstance().getCurrentUser());
                        if (user == null){
                            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Model.getInstance().loginSuccess(user);
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        //两秒钟延时
        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}