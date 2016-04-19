package zhaoq.hl.hlphonemallmanager.client;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import zhaoq.hl.hlphonemallmanager.Configs;
import zhaoq.hl.hlphonemallmanager.utils.HttpTools;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.client
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  16:13
 * 用于请求  数据的API
 */
public final class ClientApi {

    /**
     *  登录界面中  从服务器  初始化  柜组信息  用于加载到本地   用户登录
     * @param data
     * @return
     */
    public static JSONObject getGUIZUinfo(String data){
        JSONObject ret = null;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name",data);
        byte[] bytes = HttpTools.doPost(Configs.LOGIN_GET_GUIZU_INFO_URL,map);
        if(bytes!=null){
            try {
                String result = new String(bytes,"utf-8");
                ret = new JSONObject(result);
                return ret;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 主界面  中初始化    下载数据信息
     * @return
     */
    public static JSONObject getGUIZUinfo(String data,String downP){
        JSONObject ret = null;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name",data);
        byte[] bytes = HttpTools.doPost(Configs.MAIN_DOWN_INFO_URL,map);
        if(bytes!=null){
            try {
                String result = new String(bytes,"utf-8");
                ret = new JSONObject(result);
                return ret;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
