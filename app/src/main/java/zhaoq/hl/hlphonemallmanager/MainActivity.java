package zhaoq.hl.hlphonemallmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.brand.BrandInfoActivity;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.RequestDataEntity;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.goodsInfo.GoodsInfoActivity;
import zhaoq.hl.hlphonemallmanager.sellquery.SellQueryActivity;
import zhaoq.hl.hlphonemallmanager.tasks.MainDownInfoAsyncTask;
import zhaoq.hl.hlphonemallmanager.tasks.TaskCallBack;
import zhaoq.hl.hlphonemallmanager.tasks.TaskResult;
import zhaoq.hl.hlphonemallmanager.tickets.TicketActivity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToast;
import zhaoq.hl.hlphonemallmanager.utils.StateUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TaskCallBack {

    private ImageView icBack;

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;

    private TextView guizuNo,guizuName,memberNo,memberName;

    private MySqliteHelper helper;

    private SQLiteDatabase db;

    private LoginUserEntitiy user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MySqliteHelper(this,MySqliteHelper.GUIZU_DB);
        db = helper.getWritableDatabase();

        icBack  = (ImageView) findViewById(R.id.ic_back);

        btn1 = (Button) findViewById(R.id.main_btn1);
        btn2 = (Button) findViewById(R.id.main_btn2);
        btn3 = (Button) findViewById(R.id.main_btn3);
        btn4 = (Button) findViewById(R.id.main_btn4);

        btn5 = (Button) findViewById(R.id.down_guizu_info);
        btn6 = (Button) findViewById(R.id.down_guizu_member);
        btn7 = (Button) findViewById(R.id.down_guizu_brand_info);
        btn8 = (Button) findViewById(R.id.down_guizu_goods_info);

        guizuNo = (TextView) findViewById(R.id.main_bottom_guizuno_info);
        guizuName = (TextView) findViewById(R.id.main_botton_guizu_mingcheng);
        memberNo = (TextView) findViewById(R.id.main_bottom_mermberno_info);
        memberName = (TextView) findViewById(R.id.main_bottom_mermber_mingcheng);

        user = (LoginUserEntitiy)getIntent().getSerializableExtra("user");

        guizuNo.setText("柜组编号:"+user.getGuizuno());
        guizuName.setText("柜组名称:"+user.getGuizu());
        memberNo.setText("操作员编号:"+user.getGonghao());
        memberName.setText("操作员姓名:"+user.getMingcheng());
        
        initListener();
    }

    /**
     * 初始化   点击事件
     */
    private void initListener() {
        icBack.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;
            case R.id.main_btn1:
                //开票管理
                toNewActivity(TicketActivity.class);
                break;
            case R.id.main_btn2:
                //品牌信息管理
                toNewActivity(BrandInfoActivity.class);
                break;
            case R.id.main_btn3:
                //商品基本信息
                toNewActivity(GoodsInfoActivity.class);
                break;
            case R.id.main_btn4:
                //柜组销售信息
                toNewActivity(SellQueryActivity.class);
                break;
            case R.id.down_guizu_info:
                //下载   柜组信息
                //拼接  参数信息
                ArrayList<String> list1 = new ArrayList<String>();
                RequestDataEntity entity1 = new RequestDataEntity();
                entity1.setAction("guizu");
                entity1.setGuizuNo(user.getGuizuno());
                entity1.setTelCode(StateUtils.getIMEI(this.getApplicationContext()));  //获取  手机标识
                //转成json：
                String json = new Gson().toJson(entity1);
                list1.add(json);

                new MainDownInfoAsyncTask(this,this,db).execute(list1.toString(),"downGUUZUInfo");

                break;
            case R.id.down_guizu_member:

                new MainDownInfoAsyncTask(this,this,db).execute(null,"downMemberInfo");

                break;
            case R.id.down_guizu_brand_info:
                //下载  品牌信息
                new MainDownInfoAsyncTask(this,this,db).execute(null,"downBrandInfo");

                break;
            case R.id.down_guizu_goods_info:
                //下载  商品信息
                new MainDownInfoAsyncTask(this,this,db).execute(null,"downGoodsInfo");

                break;
            default:
                break;
        }
    }

    /**
     * 创建  方法
     */
    private void toNewActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
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


    /**
     * 异步任务  的  回调方法
     * @param result
     */
    @Override
    public void taskFinished(TaskResult result) {
        switch (result.result_status) {

            case -1:
                MyToast.ToastIncenter(this, "加载失败,请检查服务器").show();
                break;

            case 1:
                MyToast.ToastIncenter(this, "更新成功").show();
                break;

            default:
                break;
        }
    }
}
