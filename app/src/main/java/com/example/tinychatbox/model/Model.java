package com.example.tinychatbox.model;

import android.content.Context;

import com.example.tinychatbox.model.bean.UserInfo;
import com.example.tinychatbox.model.dao.UserAccountDao;
import com.example.tinychatbox.model.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//数据模型层全局类
public class Model {
    private Context mContext;
    private static Model model=new Model();
    private ExecutorService executors= Executors.newCachedThreadPool();//全局线程池
    private UserAccountDao userAccountDao;
    private DBManager dbManager;
    private EventListener eventListener;

    private Model(){}

    public static Model getInstance() {
        return model;
    }

    public void init(Context context){
        mContext=context;
        userAccountDao = new UserAccountDao(mContext);
        eventListener = new EventListener(mContext);
    }

    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executors;
    }

    //用户登录成功
    public void loginSuccess(UserInfo account) {
        if (account==null)
            return;
        if (dbManager!=null)
            dbManager.close();
        dbManager = new DBManager(mContext, account.getName());
    }
    public DBManager getDbManager(){
        return dbManager;
    }


    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
}
