package zhaoq.hl.hlphonemallmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.dialog.InputInfoDialog;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edaccount,edpassword;

    private Button btnLogin,btnLoaddata;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edaccount = (EditText) findViewById(R.id.edit_account);
        edpassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLoaddata = (Button) findViewById(R.id.btn_loaddata);

        btnLogin.setOnClickListener(this);
        btnLoaddata.setOnClickListener(this);

        //将  数据保存到本地数据库：  创建  数据库
        db = ApplicationUtils.getInstance().getHelper(this).getWritableDatabase();

        ApplicationUtils.getInstance().addActivity(this);
    }


    /**
     * 实现  单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                String account = edaccount.getText().toString().trim();
                String password = edpassword.getText().toString().trim();
                if(checkInput(account, password)){
                    //从数据库中  查询数据
                    db = ApplicationUtils.getInstance().getHelper(this).getReadableDatabase();
                    //查询数据
                    Cursor cursor = db.query(MySqliteHelper.TABLE_USER_NAME,new String[]{"gonghao","Pass","guizu","guizuno","mingcheng"},
                            "gonghao = '"+ account +"'",null,null,null,null);

                    //判断  数据：
                    switch(cursor.getCount()){
                        case 0:
                            MyToastUtils.toastInCenter(this, "登录失败,未查询到该用户").show();
                            break;
                        case 1:
                            while(cursor.moveToNext()){
                                if(cursor.getString(cursor.getColumnIndex("Pass")).equals(password)){

                                    LoginUserEntitiy entity = new LoginUserEntitiy();
                                    entity.setGonghao(cursor.getString(cursor.getColumnIndex("gonghao")));
                                    entity.setGuizu(cursor.getString(cursor.getColumnIndex("guizu")));
                                    entity.setGuizuno(cursor.getString(cursor.getColumnIndex("guizuno")));
                                    entity.setPass(cursor.getString(cursor.getColumnIndex("Pass")));
                                    entity.setMingcheng(cursor.getString(cursor.getColumnIndex("mingcheng")));
                                    //  全局用户信息
                                    ApplicationUtils.getInstance().setUser(entity);

                                    toMainActivity();
                                }else{
                                    MyToastUtils.toastInCenter(this, "登录失败,请验证输入密码").show();
                                }
                            }
                            break;
                        default:
                            MyToastUtils.toastInCenter(this, "登录失败,请验证账号或密码").show();
                            break;
                    }
                }
                break;

            case R.id.btn_loaddata:
                //打开  dialog对话框  获取数据
                InputInfoDialog dialog = new InputInfoDialog(this,R.style.LoginAlertDialogStyle,db);
                dialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 开启   主界面
     */
    private void toMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //再按一次   退出程序
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis() - exitTime)>2000){
                MyToastUtils.toastInCenter(this, "再按一次推出程序").show();
                exitTime = System.currentTimeMillis();
            }else{
                finish();
                ApplicationUtils.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //检查  数据
    private boolean checkInput(String account,String password) {
        if((!TextUtils.isEmpty(account))&&(!TextUtils.isEmpty(password))){
            return  true;
        }else{
            MyToastUtils.toastInCenter(getApplicationContext(), "输入数据不准为空").show();
        }
        return false;
    }
}
