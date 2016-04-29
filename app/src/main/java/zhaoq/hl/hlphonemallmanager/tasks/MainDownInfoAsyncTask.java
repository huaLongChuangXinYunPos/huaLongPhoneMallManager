package zhaoq.hl.hlphonemallmanager.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.Configs;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.client.ClientApi;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIZUInfo;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.utils.JSONUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:30
 */
public class MainDownInfoAsyncTask extends BaseAsyncTask {

    private SQLiteDatabase db;

    private Context context;

    public MainDownInfoAsyncTask(TaskCallBack callBack, Context context, SQLiteDatabase db) {
        super(callBack,context);
        this.db = db;
        this.context = context;
    }

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult  result = new TaskResult();
        result.task_id = Configs.MAIN_DOWN_INFO_AUTHORITY;
        action = params[1];

        if(params!=null){
            String data = params[0];
            JSONObject object = ClientApi.getGUIZUinfo(data,params[1],context);
            if(object!=null){
                try {
                    result.result_status = object.getInt("resultStatus");
                    result.data = object;
                    return result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static String action;//请求   的 类型

    @Override
    protected void onPostExecute(TaskResult result) {
        try {
            if(result.data!=null){
                JSONObject object = result.data;
                JSONArray data = object.getJSONArray("data");
                db.beginTransaction();
                //解析  数据
                    switch(action){
                        case "downGUUZUInfo":  //下载  柜组信息
                            ArrayList<DownGUIZUInfo> list1 = JSONUtils.parseGUIZUToList(data);
                            if(list1!=null){
                                db.execSQL("delete from "+ MySqliteHelper.TABLE_GUIZU_NAME);
                                for(int i = 0;i < list1.size();i++){
                                    // "t_guizu(guizu,guizuno,quno,qu,leixing,TelCode)
                                    db.execSQL(String.format(MySqliteHelper.INSERT_GUIZU_INFO,list1.get(i).getGuizu(),list1.get(i).getGuizuno(),
                                            list1.get(i).getQuno(),list1.get(i).getQu(),list1.get(i).getLeixing(),list1.get(i).getTelCode()));
                                }
                            }
                            break;
                        case "downMemberInfo": //下载 员工信息
                            ArrayList<LoginUserEntitiy> list2 = JSONUtils.parseMemberToList(data);
                            if(list2!=null){
                                db.execSQL("delete from " + MySqliteHelper.TABLE_USER_NAME);
                                for(int i = 0;i < list2.size();i++){
                                // t_users(gonghao,guizu,guizuno,mingcheng,Pass)
                                    db.execSQL(String.format(MySqliteHelper.INSERT_USE_INFO,
                                            list2.get(i).getGonghao(),list2.get(i).getGuizu(),
                                            list2.get(i).getGuizuno(),list2.get(i).getMingcheng(),list2.get(i).getPass()));
                                }
                            }
                            Log.i("info",result.data.toString()+"---downMemberInfo--");
                            break;
                        case "downBrandInfo"://下载  品牌信息

                            ArrayList<DownBrandEntity> list3 = JSONUtils.parseBrandToList(data);
                            if(list3!=null){
                                db.execSQL("delete from " + MySqliteHelper.TABLE_BRAND_NAME);
                                for(int i = 0;i < list3.size();i++){
                                    // t_brand(pinpai,pinpaino,guizu,guizuno,bNew)
                                    db.execSQL(String.format(MySqliteHelper.INSERT_BRAND_INFO,list3.get(i).getPinpai(),list3.get(i).getPinpaino(),
                                            list3.get(i).getGuizu(),list3.get(i).getGuizuNo(),"0"));
                                }
                            }
                            break;

                        case "downGoodsInfo"://下载  商品信息

                            ArrayList<DownGUIGUGoodsEntiity> list4 = JSONUtils.parseGoodsToList(data);
                            if(list4!=null){
                                db.execSQL("delete from " + MySqliteHelper.TABLE_GOODS_NAME);
                                for(int i = 0;i < list4.size();i++){
    //(pinpai,pinpaino,guizu,guizuno,Dw1,SpNo,Mingcheng,Bzjjmoney,Bzlsjmoney,Danwei,guige,bNew)
                                    db.execSQL(String.format(MySqliteHelper.INSERT_GOODS_INFO,
                                            list4.get(i).getPinpai(),list4.get(i).getPinpaino(),list4.get(i).getGuizu(),list4.get(i).getGuizuno(),
                                            list4.get(i).getDw1(),list4.get(i).getSpNo(),list4.get(i).getMingcheng(),list4.get(i).getBzjj(),
                                            list4.get(i).getBzlsj(),list4.get(i).getDanwei(),list4.get(i).getGuige(),"0"));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
    }
}
