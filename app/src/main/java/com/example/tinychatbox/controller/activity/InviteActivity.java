package com.example.tinychatbox.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.tinychatbox.R;
import com.example.tinychatbox.controller.adapter.InviteAdapter;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.model.bean.InvationInfo;
import com.example.tinychatbox.utils.Constant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

//邀请信息列表界面
public class InviteActivity extends AppCompatActivity {

    private ListView lv_invite;
    private InviteAdapter.OnInviteListener mOninviteListener=new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(InvationInfo invationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invationInfo.getUser().getHxid());

                        Model.getInstance().getDbManager().getInviteTableDao().updateInvationsStatus(InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER,invationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"您接受了邀请",Toast.LENGTH_SHORT).show();
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"接受邀请失败",Toast.LENGTH_SHORT).show();
                                refresh();
                            }
                        });
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        @Override
        public void onReject(InvationInfo invationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(invationInfo.getUser().getHxid());

                        Model.getInstance().getDbManager().getInviteTableDao().removeInvation(invationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"您拒绝了邀请",Toast.LENGTH_SHORT).show();
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝邀请失败",Toast.LENGTH_SHORT).show();
                                refresh();
                            }
                        });
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    };
    private InviteAdapter inviteAdapter;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangedReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invite);

        initView();

        initData();
    }

    private void initData() {
        inviteAdapter = new InviteAdapter(this,mOninviteListener);

        lv_invite.setAdapter(inviteAdapter);

        //刷新方法
        refresh();

        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(ContactInviteChangedReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
    }

    private void refresh() {
        List<InvationInfo> invations = Model.getInstance().getDbManager().getInviteTableDao().getInvations();
        inviteAdapter.refresh(invations);
    }

    private void initView() {
        lv_invite=findViewById(R.id.lv_invite);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(ContactInviteChangedReceiver);
    }
}