package zhaoq.hl.hlphonemallmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zhaoq.hl.hlphonemallmanager.Configs;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.db
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/19  11:21
 */
public class MySqliteHelper extends SQLiteOpenHelper {


    //设置  数据库名称：保存  用户登录信息
    public static final String GUIZU_DB = "guizuDb";

    public static final String TABLE_USER_NAME = "t_guizu_yuangong";
    public static final String TABLE_GUIZU_NAME = "t_guizu";
    public static final String TABLE_BRAND_NAME = "t_pinpai";
    public static final String TABLE_GOODS_NAME = "t_spxx";
    public static final String TABLE_TICKETS_NAME = "t_tmp_lsdsp";


    private static String name;
    private Context context;

    //创建用户表：
    public static String CREATE_TABLE_USER = "create table if not exists "+TABLE_USER_NAME+"(" +
            "id integer primary key autoincrement,gonghao text,guizu text,guizuno text,mingcheng text," +
            "Pass text) ";
    //插入数据到  用户表中
    public static String INSERT_USE_INFO = "insert into " +TABLE_USER_NAME+"(gonghao,guizu,guizuno,mingcheng,Pass) values(" +
            "'%s', '%s','%s','%s','%s')";

    //创建  柜组表：
    public static String CREATE_TABLE_GUIZU = "create table if not exists " +TABLE_GUIZU_NAME+"(" +
            "id integer primary key autoincrement,guizu text,guizuno text,quno text,qu text," +
            "leixing text,TelCode text)";
    public static String INSERT_GUIZU_INFO = "insert into " + TABLE_GUIZU_NAME+"(guizu,guizuno,quno,qu,leixing,TelCode) values(" +
            "'%s', '%s','%s','%s','%s','%s')";

    //创建  品牌表：
    public static String CREATE_TABLE_BRAND = "create table if not exists " +TABLE_BRAND_NAME+"(" +
            "id integer primary key autoincrement,pinpai text,pinpaino text,guizu text,guizuno text," +
            "bNew text)";
    public static String INSERT_BRAND_INFO = "insert into " + TABLE_BRAND_NAME+"(pinpai,pinpaino,guizu,guizuno,bNew) values(" +
            "'%s', '%s','%s','%s','%s')";

    //创建  商品表：
    public static String CREATE_TABLE_GOODS = "create table if not exists " + TABLE_GOODS_NAME +"(" +
            "id integer primary key autoincrement, pinpai text,pinpaino text,guizu text,guizuno text," +
            "Dw1 text,SpNo text,Mingcheng text,Bzjjmoney text,Bzlsjmoney text,Danwei text,guige text,bNew text)";
    public static String INSERT_GOODS_INFO = "insert into " + TABLE_GOODS_NAME+"(pinpai,pinpaino,guizu,guizuno,Dw1,SpNo," +
            "Mingcheng,Bzjjmoney,Bzlsjmoney,Danwei,guige,bNew) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')";


    //创建  商品表：
    public static String CREATE_TABLE_TICKETS = "create table if not exists " +TABLE_TICKETS_NAME+"(" +
            "id integer primary key autoincrement, lsdno text,spno text,shuliang text,danjia text," +
            "jine text,lsriqi text,gonghao text,mingcheng text,lsdDown text)";
    public static String INSERT_TICKETS_INFO = "insert into " + TABLE_TICKETS_NAME+"(lsdno,spno,shuliang,danjia," +
            "jine,lsriqi,gonghao,Mingcheng,lsdDown) values('%s','%s','%s','%s','%s','%s','%s','%s','%s')";


    public MySqliteHelper(Context context,String name) {
        super(context, name, null, 1);
        this.name = name;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(name.equals(GUIZU_DB)){
            db.execSQL(CREATE_TABLE_USER);//创建  用户信息表
            db.execSQL(CREATE_TABLE_GUIZU);//创建  柜组表
            db.execSQL(CREATE_TABLE_BRAND); //创建 品牌表
            db.execSQL(CREATE_TABLE_GOODS);//创建  商品信息表
            db.execSQL(CREATE_TABLE_TICKETS); //创建    开票信息表
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
