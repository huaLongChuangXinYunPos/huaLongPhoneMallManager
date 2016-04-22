package zhaoq.hl.hlphonemallmanager.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:19
 */
public class MyToastUtils {

    //自定义 toast工具类
    public static Toast toastInCenter(Context context,String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        return toast;
    }

    //自定义 toast工具类
    public static Toast toastInBottom(Context context,String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        return toast;
    }


}
