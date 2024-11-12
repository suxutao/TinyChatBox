package com.example.tinychatbox.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tinychatbox.model.dao.UserAccountTable;

public class UserAccountDB extends SQLiteOpenHelper {
    public UserAccountDB(@Nullable Context context) {
        super(context, "account.db", null, 1);
    }

    //数据库创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserAccountTable.CREATE_TAB);
    }

    //数据库更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
