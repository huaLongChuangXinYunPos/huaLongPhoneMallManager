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
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.utils.JSONUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:30
 */
public class InitLoginUserinfoAsyncTask extends BaseAsyncTask {

    private SQLiteDatabase db;

    private Context context;

    public InitLoginUserinfoAsyncTask(TaskCallBack callBack, Context context, SQLiteDatabase db) {
        super(callBack,context);
        this.db = db;
        this.context = context;
    }

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult  result = new TaskResult();
        result.task_id = Configs.GET_GUIZU_INFO_AUTHORITY;

        if(params!=null){
            String data = params[0];
            JSONObject object = ClientApi.getGUIZUinfo(data,context);
            if (object!=null){
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

    @Override
    protected void onPostExecute(TaskResult result) {
        try {
            if(result.data!=null){
                JSONObject object = result.data;
                JSONArray data = object.getJSONArray("data");
                //解析  数据
                ArrayList<LoginUserEntitiy> lists = JSONUtils.parseMemberToList(data);
//                操作数据库
                db.beginTransaction();
                if(lists!=null){
                    db.execSQL("delete from "+ MySqliteHelper.TABLE_USER_NAME);
                    for(int i = 0;i < lists.size();i++){
//                        t_users(gonghao,guizu,guizuno,mingcheng,Pass)
                        db.execSQL(String.format(MySqliteHelper.INSERT_USE_INFO,lists.get(i).getGonghao(),lists.get(i).getGuizu(),
                                lists.get(i).getGuizuno(),lists.get(i).getMingcheng(),lists.get(i).getPass()));
                    }
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
