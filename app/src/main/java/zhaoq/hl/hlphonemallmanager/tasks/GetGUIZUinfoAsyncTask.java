package zhaoq.hl.hlphonemallmanager.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import zhaoq.hl.hlphonemallmanager.Configs;
import zhaoq.hl.hlphonemallmanager.client.ClientApi;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:30
 */
public class GetGUIZUinfoAsyncTask extends BaseAsyncTask {

    public GetGUIZUinfoAsyncTask(TaskCallBack callBack) {
        super(callBack);
    }

    //添加  进度条



    @Override
    protected TaskResult doInBackground(String... params) {

        TaskResult  result = new TaskResult();
        result.task_id = Configs.GET_GUIZU_INFO_AUTHORITY;

        if(params!=null){
            String data = params[0];
            JSONObject object = ClientApi.getGUIZUinfo(data);
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

}
