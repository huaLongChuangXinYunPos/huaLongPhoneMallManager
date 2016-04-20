package zhaoq.hl.hlphonemallmanager.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIZUInfo;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/19  11:44
 */
public final class JSONUtils {

    //解析   数据的  工具类 将  数据JSONArray解析为  list
    public static ArrayList<LoginUserEntitiy> parseMemberToList(JSONArray array) {
        ArrayList<LoginUserEntitiy> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(array.toString(), new TypeToken<List<LoginUserEntitiy>>(){}.getType());
        return list;
    }

    /**
     *解析  柜组  实体数据
     */
    public static ArrayList<DownGUIZUInfo> parseGUIZUToList(JSONArray array) {
        ArrayList<DownGUIZUInfo> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(array.toString(), new TypeToken<List<DownGUIZUInfo>>(){}.getType());
        return list;
    }

    /**
     * 解析   品牌信息实体
     * @param array
     * @return
     */
    public static ArrayList<DownBrandEntity> parseBrandToList(JSONArray array) {
        ArrayList<DownBrandEntity> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(array.toString(), new TypeToken<List<DownBrandEntity>>(){}.getType());
        return list;
    }

    /**
     * 下载  柜组 商品信息
     * @param array
     * @return
     */
    public static ArrayList<DownGUIGUGoodsEntiity> parseGoodsToList(JSONArray array) {
        ArrayList<DownGUIGUGoodsEntiity> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(array.toString(), new TypeToken<List<DownGUIGUGoodsEntiity>>(){}.getType());
        return list;
    }
}
