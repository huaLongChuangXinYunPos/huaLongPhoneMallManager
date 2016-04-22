package zhaoq.hl.hlphonemallmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.RequestDataEntity;
import zhaoq.hl.hlphonemallmanager.tasks.InitLoginUserinfoAsyncTask;
import zhaoq.hl.hlphonemallmanager.tasks.TaskCallBack;
import zhaoq.hl.hlphonemallmanager.tasks.TaskResult;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;
import zhaoq.hl.hlphonemallmanager.utils.StateUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/14  16:26
 */
public class InputInfoDialog extends Dialog implements View.OnClickListener,TaskCallBack {

    private Context context;
    private SQLiteDatabase db;

    public InputInfoDialog(Context context, int themeResId,SQLiteDatabase db) {
        super(context, themeResId);
        this.context = context;
        this.db = db;
    }

    private View view;

    private EditText editAccount;
    private EditText editPassword;

    private Button btnSure,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view,null);

        editAccount = (EditText) view.findViewById(R.id.edit_account);
        editPassword = (EditText) view.findViewById(R.id.edit_password);
        btnExit = (Button) view.findViewById(R.id.dialog_input_exit);
        btnSure = (Button) view.findViewById(R.id.dialog_input_sure);

        initListener();
        this.setContentView(view);
    }
    //初始化单击事件方法
    private void initListener() {
        btnExit.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.dialog_input_exit:
                this.dismiss();
                break;
            case R.id.dialog_input_sure:
                //check  检查   输入数据信息
                //检查  输入数据信息
                String account = editAccount.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if(checkInput(account,password)){
                    //添加  数据信息
                    ArrayList<String> list = new ArrayList<String>();

                    RequestDataEntity entity = new RequestDataEntity();
                    entity.setAction("telCode");
                    entity.setGuizuPwd(password);
                    entity.setGuizuNo(account);
                    entity.setTelCode(StateUtils.getIMEI(context));  //获取  手机标识

                    //转成json：
                    String json = new Gson().toJson(entity);
                    list.add(json);

                    //开始  查询  数据信息
                    new InitLoginUserinfoAsyncTask(this,context,db).execute(list.toString());  //并将数据信息  保存到本地

                    this.dismiss();
                }
                break;

            default:
                break;
        }
    }

    //检查  数据
    private boolean checkInput(String account,String password) {
        if((!TextUtils.isEmpty(account))&&(!TextUtils.isEmpty(password))){
            return  true;
        }else{
            MyToastUtils.toastInCenter(context.getApplicationContext(), "输入数据不准为空").show();
        }
        return false;
    }

    @Override
    public void taskFinished(TaskResult result) {
        switch (result.result_status){
            case -1:
                MyToastUtils.toastInCenter(context, "加载失败,请检查服务器").show();
                break;
            case 1:
                MyToastUtils.toastInCenter(context, "加载成功,可以登录").show();
                break;
            case 2:
                MyToastUtils.toastInCenter(context, "加载失败,柜组密码错误").show();
                break;
            case 3:
                MyToastUtils.toastInCenter(context, "加载失败,当前柜组已被绑定其他手机").show();
                break;
            case 4:
                MyToastUtils.toastInCenter(context, "加载失败,当前柜组信息不属于该手机").show();
                break;
            default:
                break;
        }
    }
}
