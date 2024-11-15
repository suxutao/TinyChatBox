package com.example.tinychatbox.model.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tinychatbox.model.bean.UserInfo;
import com.example.tinychatbox.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

//联系人表的操作类
public class ContactTableDao {

    private DBHelper mHelper;

    public ContactTableDao(DBHelper dbHelper) {
        mHelper=dbHelper;
    }

    @SuppressLint("Range")
    public List<UserInfo> getContacts(){
        SQLiteDatabase db=mHelper.getReadableDatabase();

        String sql="select * from "+ContactTable.TAB_NAME+" where "+ContactTable.COL_IS_CONTACT+"=1";
        Cursor cursor = db.rawQuery(sql, null);

        List<UserInfo> users=new ArrayList<>();

        while (cursor.moveToNext()){
            UserInfo userInfo = new UserInfo();

            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));

            users.add(userInfo);
        }

        cursor.close();
        return users;
    }

    @SuppressLint("Range")
    public UserInfo getContactByHx(String hxId){
        if (hxId==null)
            return null;
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql ="select * from "+ContactTable.TAB_NAME+" where "+ContactTable.COL_HXID+"=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});

        UserInfo userInfo = null;
        if (cursor.moveToNext()){
            userInfo=new UserInfo();

            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
        }

        cursor.close();;
        return userInfo;
    }

    public List<UserInfo> getContactsByHx(List<String>hxIds){
        if (hxIds==null||hxIds.size()<=0){
            return null;
        }
        List<UserInfo>contacts=new ArrayList<>();
        for (String hxid:hxIds) {
            UserInfo contact = getContactByHx(hxid);
            contacts.add(contact);
        }
        return contacts;
    }

    public void saveContact(UserInfo user,boolean isMyContact){
        if (user==null)
            return;
        SQLiteDatabase db = mHelper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put(ContactTable.COL_HXID,user.getHxid());
        values.put(ContactTable.COL_NAME,user.getName());
        values.put(ContactTable.COL_NICK,user.getNick());
        values.put(ContactTable.COL_PHOTO,user.getPhoto());
        values.put(ContactTable.COL_IS_CONTACT,isMyContact?1:0);

        db.replace(ContactTable.TAB_NAME,null,values);
    }

    public void saveContacts(List<UserInfo> contacts,boolean isMyContact){
        if (contacts==null||contacts.size()<=0)
            return;
        for (UserInfo contact :
                contacts) {
            saveContact(contact, isMyContact);
        }
    }

    public void deleteContactByHxId(String hxid){
        if (hxid==null)
            return;

        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.delete(ContactTable.TAB_NAME,ContactTable.COL_HXID+"=?",new String[]{hxid});
    }
}
