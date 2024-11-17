package com.example.tinychatbox.model.db;

import android.content.Context;

import com.example.tinychatbox.model.dao.ContactTableDao;
import com.example.tinychatbox.model.dao.InviteTableDao;

//联系人和邀请信息表的操作类的管理类
public class DBManager {

    private final DBHelper dbHelper;
    private final ContactTableDao contactTableDao;
    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name){
        dbHelper = new DBHelper(context, name);

        //两张表的操作类
        contactTableDao = new ContactTableDao(dbHelper);
        inviteTableDao = new InviteTableDao(dbHelper);
    }
    public ContactTableDao getContactTableDao(){
        return contactTableDao;
    }
    public InviteTableDao getInviteTableDao(){
        return inviteTableDao;
    }

    public void close() {
        dbHelper.close();
    }
}
