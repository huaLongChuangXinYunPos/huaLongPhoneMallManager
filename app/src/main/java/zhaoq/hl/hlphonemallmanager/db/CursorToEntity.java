package zhaoq.hl.hlphonemallmanager.db;

import android.database.Cursor;

import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.db
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  11:45
 */
public final class CursorToEntity {

    /**
     * 品牌信息转换为实体
     */
    public static DownBrandEntity getBrand(Cursor cursor){
        DownBrandEntity downBrandEntity = new DownBrandEntity();
        downBrandEntity.setGuizu(cursor.getString(cursor.getColumnIndex("guizu")));
        downBrandEntity.setGuizuNo(cursor.getString(cursor.getColumnIndex("guizuno")));
        downBrandEntity.setPinpai(cursor.getString(cursor.getColumnIndex("pinpai")));
        downBrandEntity.setPinpaino(cursor.getString(cursor.getColumnIndex("pinpaino")));
        return downBrandEntity;

    }


    /**
     * 对商品  信息  进行处理
     * @param cursor
     * @return
     */
    public static DownGUIGUGoodsEntiity getGoodsEntity(Cursor cursor){

        DownGUIGUGoodsEntiity goodsEntity = new DownGUIGUGoodsEntiity();

        goodsEntity.setGuizu(cursor.getString(cursor.getColumnIndex("guizu")));
        goodsEntity.setGuizuno(cursor.getString(cursor.getColumnIndex("guizuno")));
        goodsEntity.setPinpai(cursor.getString(cursor.getColumnIndex("pinpai")));
        goodsEntity.setPinpaino(cursor.getString(cursor.getColumnIndex("pinpaino")));
        goodsEntity.setBzjj(cursor.getDouble(cursor.getColumnIndex("Bzjjmoney")));
        goodsEntity.setDw1(cursor.getString(cursor.getColumnIndex("Dw1")));
        goodsEntity.setSpNo(cursor.getString(cursor.getColumnIndex("SpNo")));
        goodsEntity.setMingcheng(cursor.getString(cursor.getColumnIndex("Mingcheng")));
        goodsEntity.setBzlsj(cursor.getDouble(cursor.getColumnIndex("Bzlsjmoney")));
        goodsEntity.setDanwei(cursor.getString(cursor.getColumnIndex("Danwei")));
        goodsEntity.setGuige(cursor.getString(cursor.getColumnIndex("guige")));

        return goodsEntity;
    }


}
