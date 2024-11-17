package com.example.tinychatbox.model;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.tinychatbox.model.bean.InvationInfo;
import com.example.tinychatbox.model.bean.UserInfo;
import com.example.tinychatbox.utils.Constant;
import com.example.tinychatbox.utils.SpUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

public class EventListener {

    private final LocalBroadcastManager mLBM;
    private Context mContext;

    public EventListener(Context context){
        mContext=context;

        //发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mContext);
        //监听联系人变化
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }

    private final EMContactListener emContactListener=new EMContactListener() {

        //添加好友
        @Override
        public void onContactAdded(String hxid) {
            Model.getInstance().getDbManager().getContactTableDao().saveContact(new UserInfo(hxid),true);
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        //删除好友
        @Override
        public void onContactDeleted(String hxid) {
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(hxid);
            Model.getInstance().getDbManager().getInviteTableDao().removeInvation(hxid);

            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        //接收到别人的新邀请
        @Override
        public void onContactInvited(String hxid, String reason) {
            InvationInfo invationInfo=new InvationInfo();
            invationInfo.setUser(new UserInfo(hxid));
            invationInfo.setReason(reason);
            invationInfo.setStatus(InvationInfo.InvationStatus.NEW_INVITE);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);

            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        //别人同意好友邀请
        @Override
        public void onFriendRequestAccepted(String hxid) {
            InvationInfo invationInfo=new InvationInfo();
            invationInfo.setUser(new UserInfo(hxid));
            invationInfo.setStatus(InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invationInfo);

            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        //别人拒绝好友邀请
        @Override
        public void onFriendRequestDeclined(String hxid) {

            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);

            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
}
