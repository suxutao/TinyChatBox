package com.example.tinychatbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tinychatbox.IMApplication;

public class SpUtils {
    public static final String IS_NEW_INVITE = "is_new_invite";
    private static SpUtils instance=new SpUtils();
    private static SharedPreferences msp;

    private SpUtils(){}

    //单例
    public static SpUtils getInstance(){
        if (msp==null){
            msp = IMApplication.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }
        return instance;
    }

    //保存方法
    public void save(String key,Object value) {
        if (value instanceof String){
            msp.edit().putString(key,(String) value).commit();
        } else if (value instanceof Boolean) {
            msp.edit().putBoolean(key,(Boolean)value).commit();
        } else if (value instanceof Integer) {
            msp.edit().putInt(key,(Integer)value).commit();
        }
    }

    //获取数据的方法
    public String getString(String key,String defValue){
        return msp.getString(key,defValue);
    }
    public boolean getBoolean(String key,boolean defValue){
        return msp.getBoolean(key, defValue);
    }
    public int getInt(String key,int defValue){
        return msp.getInt(key, defValue);
    }

}
