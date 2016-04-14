package zhaoq.hl.hlphonemallmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText account,password;

    private Button btnLogin,btnLoaddata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = (EditText) findViewById(R.id.edit_account);
        password = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLoaddata = (Button) findViewById(R.id.btn_loaddata);

        btnLogin.setOnClickListener(this);
        btnLoaddata.setOnClickListener(this);
    }


    /**
     * 实现  单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                toMainActiivity();
                break;

            case R.id.btn_loaddata:
                //打开  dialog对话框  获取数据
                InputInfoDialog dialog = new InputInfoDialog(this);
                dialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 开启   主界面
     */
    private void toMainActiivity() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
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
                MyToast.ToastIncenter(this, "再按一次推出程序").show();
                exitTime = System.currentTimeMillis();
            }else{
                finish();
                ApplicationUtils.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
