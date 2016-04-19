package zhaoq.hl.hlphonemallmanager.tasks;

import android.content.Context;
import android.os.AsyncTask;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:31
 */
public abstract class BaseAsyncTask extends AsyncTask<String,Void,TaskResult>{

    public TaskCallBack callBack;

    public BaseAsyncTask(TaskCallBack callBack){
        this.callBack =  callBack;
    }

    //重写   onpostExecute方法：
    @Override
    protected void onPostExecute(TaskResult result) {
        if(result != null){
            callBack.taskFinished(result);
        }
    }
}
