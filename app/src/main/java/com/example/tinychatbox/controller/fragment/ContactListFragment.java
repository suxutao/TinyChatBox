package com.example.tinychatbox.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.tinychatbox.R;
import com.example.tinychatbox.controller.activity.AddContactActivity;
import com.example.tinychatbox.controller.activity.InviteActivity;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.utils.Constant;
import com.example.tinychatbox.utils.SpUtils;
import com.google.android.material.search.SearchBar;
import com.hyphenate.easeui.modules.contact.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

//联系人列表页面
public class ContactListFragment extends EaseContactListFragment {

    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
        }
    };
    private ImageView iv_contact_red;
    private EaseTitleBar titleBar;
    private LinearLayout ll_invite;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        View searchView = LayoutInflater.from(mContext).inflate(R.layout.titlebar_main,null);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_fragment_contact,null);
        llRoot.addView(headerView,0);
        llRoot.addView(searchView,0);
        SearchBar search = searchView.findViewById(R.id.search_bar);


        titleBar = findViewById(R.id.main_titleBar);
        titleBar.setTitle("通讯录");
        titleBar.setRightImageResource(com.hyphenate.easeui.R.drawable.ease_chat_item_menu_copy);
        titleBar.setOnRightClickListener(new EaseTitleBar.OnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                Intent intent=new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        //红点对象
        iv_contact_red = headerView.findViewById(R.id.iv_contact_red);

        boolean isNewInvite= SpUtils.getInstance().getBoolean(SpUtils.IS_NEW_INVITE,true);
        iv_contact_red.setVisibility(isNewInvite?View.VISIBLE:View.GONE);

        //处理广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactInviteChangeReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));


        ll_invite = headerView.findViewById(R.id.ll_invite);
        ll_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_contact_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,false);

                Intent intent = new Intent(getActivity(), InviteActivity.class);

                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭广播
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
    }
}
