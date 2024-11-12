package com.example.tinychatbox;

import android.app.Application;

import com.example.tinychatbox.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseIM;

public class IMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化UI
        EMOptions options=new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);
        EaseIM.getInstance().init(this,options);

        //初始化全局数据模型
        Model.getInstance().init(this);
    }
}
