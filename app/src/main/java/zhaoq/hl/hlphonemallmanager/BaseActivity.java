package zhaoq.hl.hlphonemallmanager;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/20  13:59
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    //定义 全局通用的   用户信息：
    private static LoginUserEntitiy user;

    private TextView guizuNo,guizuName,memberNo,memberName;

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        guizuNo = (TextView) findViewById(R.id.main_bottom_guizuno_info);
        guizuName = (TextView) findViewById(R.id.main_botton_guizu_mingcheng);
        memberNo = (TextView) findViewById(R.id.main_bottom_mermberno_info);
        memberName = (TextView) findViewById(R.id.main_bottom_mermber_mingcheng);

        ApplicationUtils.getInstance().addActivity(this);
        user = ApplicationUtils.getInstance().getUser();
        db = ApplicationUtils.getInstance().getHelper(this).getWritableDatabase();

        guizuNo.setText("柜组编号:"+user.getGuizuno());
        guizuNo.setTextColor(getResources().getColor(R.color.list_view_pressed));
        guizuNo.setTextSize(13);
        guizuName.setText("柜组名称:" + user.getGuizu());
        guizuName.setTextColor(getResources().getColor(R.color.list_view_pressed));
        guizuName.setTextSize(13);
        memberNo.setText("操作员编号:" + user.getGonghao());
        memberNo.setTextColor(getResources().getColor(R.color.list_view_pressed));
        memberNo.setTextSize(13);
        memberName.setText("操作员姓名:" + user.getMingcheng());
        memberName.setTextColor(getResources().getColor(R.color.list_view_pressed));
        memberName.setTextSize(13);
    }

    protected abstract void findView();

    @Override
    public void onClick(View v) {
        myOnclick(v);
    }

    protected abstract void myOnclick(View v);

    //再按一次   退出程序
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis() - exitTime)>2000){
                MyToastUtils.toastInBottom(this, "再按一次退出程序").show();
                exitTime = System.currentTimeMillis();
            }else{
                finish();
                ApplicationUtils.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if(db!=null && db.isOpen()){
            db.close();
        }
        super.onDestroy();
    }
}
