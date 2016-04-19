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

    private static String name;

    private Context context;

    //设置  数据库名称：保存  用户登录信息
    public static final String GUIZU_DB = "guizuDb";
    //创建表：
    public static String CREATE_TABLE_USER = "create table if not exists t_users(" +
            "id integer primary key autoincrement,gonghao text,guizu text,guizuno text,mingcheng text," +
            "Pass text) ";
    //插入数据到   表中
    public static String INSERT_USE_INFO = "insert into t_users(gonghao,guizu,guizuno,mingcheng,Pass) values(" +
            "'%s', '%s','%s','%s','%s')";


    public MySqliteHelper(Context context,String name) {
        super(context, name, null, 1);
        this.name = name;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(name.equals(GUIZU_DB)){
            db.execSQL(CREATE_TABLE_USER);//创建  用户信息表
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
