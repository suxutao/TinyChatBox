package com.example.tinychatbox.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tinychatbox.R;
import com.example.tinychatbox.controller.activity.AddContactActivity;
import com.example.tinychatbox.controller.activity.ChatActivity;
import com.example.tinychatbox.controller.activity.InviteActivity;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.utils.Constant;
import com.example.tinychatbox.utils.SpUtils;
import com.google.android.material.search.SearchBar;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContact;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.contact.EaseContactListFragment;
import com.hyphenate.easeui.modules.contact.model.EaseContactCustomBean;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

//联系人列表页面
public class ContactListFragment extends EaseContactListFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
        }
    };
    private BroadcastReceiver ContactChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新页面
        }
    };

    private ImageView iv_contact_red;
    private EaseTitleBar titleBar;
    private LinearLayout ll_invite;
    private View searchView;
    private View headerView;
    private SearchBar search;


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        searchView = LayoutInflater.from(mContext).inflate(R.layout.titlebar_main,null);
        headerView = LayoutInflater.from(mContext).inflate(R.layout.header_fragment_contact,null);
        llRoot.addView(headerView,0);
        llRoot.addView(searchView,0);
        search = searchView.findViewById(R.id.search_bar);

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
        mLBM.registerReceiver(ContactChangeReceiver,new IntentFilter(Constant.CONTACT_CHANGED));

        EMClient.getInstance().contactManager().asyncFetchAllContactsFromLocal(new EMValueCallBack<List<EMContact>>() {
            @Override
            public void onSuccess(List<EMContact> value) {
                // 更新UI显示好友列表
                for(EMContact v:value){
                    contactLayout.getContactList().addCustomItem(R.id.ll_invite, com.hyphenate.easeui.R.drawable.ease_chat_room_icon, v.getUsername());
                }
            }
            @Override
            public void onError(int error, String errorMsg) {
                // 处理错误情况
            }
        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (contactLayout != null) {
            contactLayout.onRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initListener() {
        super.initListener();
        contactLayout.getSwipeRefreshLayout().setOnRefreshListener(this);
        search.setOnClickListener(this);
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
        contactLayout.getContactList().setOnCustomItemClickListener(new OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(View view, int position) {
                EaseContactCustomBean item = contactLayout.getContactList().getCustomAdapter().getItem(position);
                String name = item.getName();

                Intent intent = new Intent(getActivity(), ChatActivity.class);

                //传递参数
                intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, name);

                startActivity(intent);
            }
        });
        contactLayout.onRefresh();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭广播
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
        mLBM.unregisterReceiver(ContactChangeReceiver);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        EaseUser item = contactLayout.getContactList().getItem(position);

        Intent intent = new Intent(getActivity(), ChatActivity.class);

        //传递参数
        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, item.getUsername());

        startActivity(intent);
    }

    @Override
    public void onMenuPreShow(EasePopupMenuHelper menuHelper, int position) {
        super.onMenuPreShow(menuHelper, position);
        menuHelper.addItemMenu(1, R.id.delete, 1, "删除联系人");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item, int position) {
        EaseUser user = contactLayout.getContactList().getItem(position);
        switch (item.getItemId()) {
            //执行删除选中的联系人操作
            case R.id.delete:
                deleteContact(user);
                return true;
        }
        return super.onMenuItemClick(item, position);
    }

    //执行删除选中的联系人操作
    private void deleteContact(EaseUser user) {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(user.getUsername());

                    //本地数据库的更新
                    Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(user.getUsername());

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除" + user.getUsername() + "成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除" + user.getUsername() + "失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }
}
