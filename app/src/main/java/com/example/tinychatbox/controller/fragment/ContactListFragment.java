package com.example.tinychatbox.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.tinychatbox.R;
import com.example.tinychatbox.controller.activity.AddContactActivity;
import com.google.android.material.search.SearchBar;
import com.hyphenate.easeui.modules.contact.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

//联系人列表页面
public class ContactListFragment extends EaseContactListFragment {
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        View searchView = LayoutInflater.from(mContext).inflate(R.layout.titlebar_main,null);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_fragment_contact,null);
        llRoot.addView(headerView,0);
        llRoot.addView(searchView,0);
        SearchBar search = searchView.findViewById(R.id.search_bar);


        EaseTitleBar titleBar=findViewById(R.id.main_titleBar);
        titleBar.setTitle("通讯录");
        titleBar.setRightImageResource(com.hyphenate.easeui.R.drawable.ease_chat_item_menu_copy);
        titleBar.setOnRightClickListener(new EaseTitleBar.OnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                Intent intent=new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });
    }
}
