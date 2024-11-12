package com.example.tinychatbox.model.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tinychatbox.model.bean.UserInfo;
import com.example.tinychatbox.model.db.UserAccountDB;

//用户数据操作类
public class UserAccountDao {

    private final UserAccountDB mHelper;

    public UserAccountDao(Context context) {
        mHelper = new UserAccountDB(context);
    }

    public void addAccount(UserInfo user){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put(UserAccountTable.COL_HXID,user.getHxid());
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_NICK,user.getNick());
        values.put(UserAccountTable.COL_PHOTO,user.getPhoto());

        db.replace(UserAccountTable.TAB_NAME,null,values);
    }

    @SuppressLint("Range")
    public UserInfo getAccountByHXID(String hxid){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql="select * from "+UserAccountTable.TAB_NAME+" where "+UserAccountTable.COL_HXID+"=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});

        UserInfo userInfo=null;
        if (cursor.moveToNext()){
            userInfo=new UserInfo();

            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }
        cursor.close();

        return userInfo;
    }
}
