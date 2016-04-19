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
import zhaoq.hl.hlphonemallmanager.entity.GUOZUEntitiy;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.utils.JSONUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:30
 */
public class MainDownInfoAsyncTask extends BaseAsyncTask {

    private Context context;
    private SQLiteDatabase db;

    public MainDownInfoAsyncTask(TaskCallBack callBack, Context context, SQLiteDatabase db) {
        super(callBack);
        this.context = context;
        this.db = db;
    }

    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context, R.style.ProDialogStyle);
        dialog.setMessage("加载中...");
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult  result = new TaskResult();
        result.task_id = Configs.MAIN_DOWN_INFO_AUTHORITY;
        requestTheme = params[1];

        if(params!=null){
            String data = params[0];
            JSONObject object = ClientApi.getGUIZUinfo(data,params[1]);
            try {
                result.result_status = object.getInt("resultStatus");
                result.data = object;
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("info","解析异常");
            }
        }
        return null;
    }


    private static String requestTheme;//请求   的 类型

    @Override
    protected void onPostExecute(TaskResult result) {
        try {
            if(result.data!=null){
                JSONObject object = result.data;
                JSONArray data = object.getJSONArray("data");
                //解析  数据
                switch(requestTheme){
                    case "downGUUZUInfo":  //下载  柜组信息



                        break;
                    case "downMemberInfo": //下载 员工信息
                        ArrayList<LoginUserEntitiy> lists = JSONUtils.parseToList(data);
//                操作数据库
                        db.beginTransaction();
                        if(lists!=null){
                            db.execSQL("delete from t_users");
                            for(int i = 0;i < lists.size();i++){
//                        t_users(gonghao,guizu,guizuno,mingcheng,Pass)
                                db.execSQL(String.format(MySqliteHelper.INSERT_USE_INFO,lists.get(i).getGonghao(),lists.get(i).getGuizu(),
                                        lists.get(i).getGuizuno(),lists.get(i).getMingcheng(),lists.get(i).getPass()));
                            }
                        }
                        db.setTransactionSuccessful();
                        db.endTransaction();
                        break;
                    case "downBrandInfo"://下载  品牌信息



                        break;

                    case "downGoodsInfo"://下载  商品信息



                        break;
                    default:
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
        super.onPostExecute(result);
    }
}
