package zhaoq.hl.hlphonemallmanager.tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import zhaoq.hl.hlphonemallmanager.R;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:31
 */
public abstract class BaseAsyncTask extends AsyncTask<String,Void,TaskResult> implements DialogInterface.OnCancelListener {

    public TaskCallBack callBack;

    public Context context;

    public BaseAsyncTask(TaskCallBack callBack,Context context){
        this.callBack =  callBack;
        this.context = context;
    }

    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context, R.style.ProDialogStyle);
        dialog.setMessage("加载中...");
        dialog.setCancelable(true);
        dialog.setOnCancelListener(this);
        dialog.show();
        super.onPreExecute();
    }

    //重写   onpostExecute方法：
    @Override
    protected void onPostExecute(TaskResult result) {
        dialog.dismiss();
        if(result != null){
            callBack.taskFinished(result);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.cancel(true);
    }
}
