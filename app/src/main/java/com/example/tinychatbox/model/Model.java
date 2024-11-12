package com.example.tinychatbox.model;

import android.content.Context;

import com.example.tinychatbox.model.dao.UserAccountDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//数据模型层全局类
public class Model {
    private Context mContext;
    private static Model model=new Model();
    private ExecutorService executors= Executors.newCachedThreadPool();//全局线程池
    private UserAccountDao userAccountDao;

    private Model(){}

    public static Model getInstance() {
        return model;
    }

    public void init(Context context){
        mContext=context;
        userAccountDao = new UserAccountDao(mContext);
    }

    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executors;
    }

    //用户登录成功
    public void loginSuccess() {

    }
    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
}
