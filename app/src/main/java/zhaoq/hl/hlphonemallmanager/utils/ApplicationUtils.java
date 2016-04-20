package zhaoq.hl.hlphonemallmanager.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;

/**
 * PACKAGE_NAME:zhaoq.hl.fastdelivery.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/13  13:52
 */
public class ApplicationUtils extends Application {

    //存放Activity
    private List<Activity> mList = new LinkedList<Activity>();

    private static ApplicationUtils instance;//创建实例

    //所有的静态同步方法用的也是同一把锁——类对象本身
    public synchronized static ApplicationUtils getInstance(){
        if(null == instance){
            instance = new ApplicationUtils();
        }
        return instance;
    }

    //Activity创建时  添加到链表中
    public void addActivity(Activity activity){
        mList.add(activity);
    }

    /**
     * 销毁activity
     */
    public void exit(){
        try{
            for(Activity activity : mList){
                if(activity !=null){
                    activity.finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.exit(0);
        }
    }

    //内存  占用较高时    调用垃圾回收机制
    public void onLowMomory(){
        super.onLowMemory();
        System.gc();
    }

    private static LoginUserEntitiy user;

    private static MySqliteHelper helper;

    public MySqliteHelper getHelper(Context context){
        return new MySqliteHelper(context,MySqliteHelper.GUIZU_DB);
    }

    public void setUser(LoginUserEntitiy user){
        this.user = user;
    }

    //获取  user用户对象
    public LoginUserEntitiy getUser(){
        return user;
    }

}
