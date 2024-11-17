package com.example.tinychatbox.model.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tinychatbox.model.bean.GroupInfo;
import com.example.tinychatbox.model.bean.InvationInfo;
import com.example.tinychatbox.model.bean.UserInfo;
import com.example.tinychatbox.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class InviteTableDao {
    private DBHelper mHelper;

    public InviteTableDao(DBHelper helper) {
        mHelper=helper;
    }

    public void addInvitation(InvationInfo invationInfo){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put(InviteTable.COL_REASON,invationInfo.getReason());
        values.put(InviteTable.COL_STATUS,invationInfo.getStatus().ordinal());

        UserInfo user = invationInfo.getUser();

        if (user!=null){
            values.put(InviteTable.COL_USER_HXID,invationInfo.getUser().getHxid());
            values.put(InviteTable.COL_USER_NAME,invationInfo.getUser().getName());
        }else{
            values.put(InviteTable.COL_GROUP_HXID,invationInfo.getGroup().getGroupId());
            values.put(InviteTable.COL_GROUP_NAME,invationInfo.getGroup().getGroupName());
            values.put(InviteTable.COL_USER_HXID,invationInfo.getGroup().getInvatePerson());
        }

        db.replace(InviteTable.TAB_NAME,null,values);
    }

    @SuppressLint("Range")
    public List<InvationInfo> getInvations(){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "select * from "+InviteTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<InvationInfo>invationInfos=new ArrayList<>();

        while (cursor.moveToNext()){
            InvationInfo invationInfo = new InvationInfo();

            invationInfo.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
            invationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));

            String groupId=cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID));
            if (groupId==null){
                UserInfo userInfo = new UserInfo();

                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                userInfo.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                userInfo.setNick(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
            }else{
                GroupInfo groupInfo = new GroupInfo();

                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                groupInfo.setInvatePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
            }
            invationInfos.add(invationInfo);
        }

        cursor.close();
        return invationInfos;
    }

    private InvationInfo.InvationStatus int2InviteStatus(int intStatus){
        if (intStatus==InvationInfo.InvationStatus.NEW_INVITE.ordinal())
            return InvationInfo.InvationStatus.NEW_INVITE;
        if (intStatus==InvationInfo.InvationStatus.INVITE_ACCEPT.ordinal())
            return InvationInfo.InvationStatus.INVITE_ACCEPT;
        if (intStatus==InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER.ordinal())
            return InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER;
        if (intStatus==InvationInfo.InvationStatus.GROUP_INVITE_ACCEPTED.ordinal())
            return InvationInfo.InvationStatus.GROUP_INVITE_ACCEPTED;
        if (intStatus==InvationInfo.InvationStatus.GROUP_APPLICATION_ACCEPTED.ordinal())
            return InvationInfo.InvationStatus.GROUP_APPLICATION_ACCEPTED;
        if (intStatus==InvationInfo.InvationStatus.NEW_GROUP_INVITE.ordinal())
            return InvationInfo.InvationStatus.NEW_GROUP_INVITE;
        if (intStatus==InvationInfo.InvationStatus.NEW_GROUP_APPLICATION.ordinal())
            return InvationInfo.InvationStatus.NEW_GROUP_APPLICATION;
        if (intStatus==InvationInfo.InvationStatus.GROUP_INVITE_DECLINED.ordinal())
            return InvationInfo.InvationStatus.GROUP_INVITE_DECLINED;
        if (intStatus==InvationInfo.InvationStatus.GROUP_APPLICATION_DECLINED.ordinal())
            return InvationInfo.InvationStatus.GROUP_APPLICATION_DECLINED;
        if (intStatus==InvationInfo.InvationStatus.GROUP_ACCEPT_INVITE.ordinal())
            return InvationInfo.InvationStatus.GROUP_ACCEPT_INVITE;
        if (intStatus==InvationInfo.InvationStatus.GROUP_ACCEPT_APPLICATION.ordinal())
            return InvationInfo.InvationStatus.GROUP_ACCEPT_APPLICATION;
        if (intStatus==InvationInfo.InvationStatus.GROUP_REJECT_APPLICATION.ordinal())
            return InvationInfo.InvationStatus.GROUP_REJECT_APPLICATION;
        if (intStatus==InvationInfo.InvationStatus.GROUP_REJECT_INVITE.ordinal())
            return InvationInfo.InvationStatus.GROUP_REJECT_INVITE;
        return null;
    }

    public void removeInvation(String hxid){
        if (hxid==null)
            return;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.delete(InviteTable.TAB_NAME,InviteTable.COL_USER_HXID+"=?",new String[]{hxid});
    }

    public void updateInvationsStatus(InvationInfo.InvationStatus invitaionStatus,String hxid){
        if (hxid==null)
            return;
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put(InviteTable.COL_STATUS,invitaionStatus.ordinal());

        db.update(InviteTable.TAB_NAME,values,InviteTable.COL_USER_HXID+"=?",new String[]{hxid});
    }
}
