package zhaoq.hl.hlphonemallmanager.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zhaoq.hl.hlphonemallmanager.entity.GUOZUEntitiy;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/19  11:44
 */
public final class JSONUtils {

    //解析   数据的  工具类 将  数据JSONArray解析为  list
    public static ArrayList<LoginUserEntitiy> parseToList(JSONArray array) {
        ArrayList<LoginUserEntitiy> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(array.toString(), new TypeToken<List<LoginUserEntitiy>>(){}.getType());
        return list;
    }

    /**
     *解析  柜组  实体数据
     */
    public static ArrayList<GUOZUEntitiy> parseGUIZUToList(JSONArray array) {
        ArrayList<GUOZUEntitiy> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(array.toString(), new TypeToken<List<GUOZUEntitiy>>(){}.getType());
        return list;
    }
}
