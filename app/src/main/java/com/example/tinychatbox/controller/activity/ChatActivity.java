package com.example.tinychatbox.controller.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.tinychatbox.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.chat.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

public class ChatActivity extends FragmentActivity {

    private String mHxid;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        initData();

        initView();
    }

    private void initView() {
        EaseTitleBar titleBar=findViewById(R.id.chat_title_bar);
        //设置标题为当前点击的用户
        Intent intent=getIntent();
        titleBar.setTitle(intent.getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID));
        //设置右侧图标
        titleBar.setRightImageResource(com.hyphenate.easeui.R.drawable.ease_chat_item_file);
        //设置左侧图标
        titleBar.setLeftImageResource(com.hyphenate.easeui.R.drawable.em_system_nofinication);
    }

    private void initData() {
        chatFragment = new EaseChatFragment();

        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_CONVERSATION_ID);

        chatFragment.setArguments(getIntent().getExtras());

        //替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, chatFragment).commit();

    }
}