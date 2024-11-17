package com.example.tinychatbox.controller.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tinychatbox.R;
import com.example.tinychatbox.controller.adapter.InviteAdapter;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.model.bean.InvationInfo;

import java.util.List;

//邀请信息列表界面
public class InviteActivity extends AppCompatActivity {

    private ListView lv_invite;
    private InviteAdapter.OnInviteListener mOninviteListener=new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(InvationInfo invationInfo) {

        }

        @Override
        public void onReject(InvationInfo invationInfo) {

        }
    };
    private InviteAdapter inviteAdapter;

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
    }

    private void refresh() {
        List<InvationInfo> invations = Model.getInstance().getDbManager().getInviteTableDao().getInvations();
        inviteAdapter.refresh(invations);
    }

    private void initView() {
        lv_invite=findViewById(R.id.lv_invite);
    }
}