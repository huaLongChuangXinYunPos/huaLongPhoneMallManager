package zhaoq.hl.hlphonemallmanager.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.client.ClientApi;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/23  14:46
 */
public class SellQueryAsyncTask extends BaseAsyncTask {

    private TaskCallBack callBack;

    public SellQueryAsyncTask(TaskCallBack callBack,Context context) {
        super(callBack,context);
        this.callBack = callBack;
    }

    public static final int authority = 8; //给该  taskResult赋值

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult result = new TaskResult();
        result.task_id = authority;
        if(params != null){
            String data = params[0];
            JSONObject object = ClientApi.getSellGoodsInfoinfo(data);
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
}
