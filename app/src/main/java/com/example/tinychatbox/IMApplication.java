package com.example.tinychatbox;

import android.app.Application;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseIM;

public class IMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化
        EMOptions options=new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);
        EaseIM.getInstance().init(this,options);
    }
}
