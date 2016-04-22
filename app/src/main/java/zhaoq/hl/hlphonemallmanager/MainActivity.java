package zhaoq.hl.hlphonemallmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.activity.BrandManageActivity;
import zhaoq.hl.hlphonemallmanager.entity.RequestDataEntity;
import zhaoq.hl.hlphonemallmanager.activity.GoodsInfoActivity;
import zhaoq.hl.hlphonemallmanager.activity.SellQueryActivity;
import zhaoq.hl.hlphonemallmanager.tasks.MainDownInfoAsyncTask;
import zhaoq.hl.hlphonemallmanager.tasks.TaskCallBack;
import zhaoq.hl.hlphonemallmanager.tasks.TaskResult;
import zhaoq.hl.hlphonemallmanager.activity.TicketManageActivity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;
import zhaoq.hl.hlphonemallmanager.utils.StateUtils;

public class MainActivity extends BaseActivity implements TaskCallBack {

    private ImageView icBack;

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;

    private SQLiteDatabase db;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_main);
        db = ApplicationUtils.getInstance().getHelper(this).getWritableDatabase();

        icBack  = (ImageView) findViewById(R.id.ic_back);

        btn1 = (Button) findViewById(R.id.main_btn1);
        btn2 = (Button) findViewById(R.id.main_btn2);
        btn3 = (Button) findViewById(R.id.main_btn3);
        btn4 = (Button) findViewById(R.id.main_btn4);

        btn5 = (Button) findViewById(R.id.down_guizu_info);
        btn6 = (Button) findViewById(R.id.down_guizu_member);
        btn7 = (Button) findViewById(R.id.down_guizu_brand_info);
        btn8 = (Button) findViewById(R.id.down_guizu_goods_info);

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
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;
            case R.id.main_btn1:
                //开票管理
                toNewActivity(TicketManageActivity.class);
                break;
            case R.id.main_btn2:
                //品牌信息管理
                toNewActivity(BrandManageActivity.class);
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
                //获取 柜组信息
                requestData("guizu","downGUUZUInfo");
                break;
            case R.id.down_guizu_member:
                //获取 组员信息
                requestData("yuangong", "downMemberInfo");
                break;
            case R.id.down_guizu_brand_info:
                //下载  品牌信息
                requestData("pinpai", "downBrandInfo");
                break;
            case R.id.down_guizu_goods_info:
                //下载  商品信息
                requestData("xinxi","downGoodsInfo");
                break;
            default:
                break;
        }
    }

    //创建  异步任务  请求数据
    private void requestData(String action,String authority) {
        //下载   柜组信息
        //拼接  参数信息
        ArrayList<String> list1 = new ArrayList<String>();
        RequestDataEntity entity1 = new RequestDataEntity();
        entity1.setAction(action);
        entity1.setGuizuNo(ApplicationUtils.getInstance().getUser().getGuizuno());
        entity1.setTelCode(StateUtils.getIMEI(this.getApplicationContext()));  //获取  手机标识
        //转成json：
        String json = new Gson().toJson(entity1);
        list1.add(json);
        new MainDownInfoAsyncTask(this,this,db).execute(list1.toString(),authority);
    }

    /**
     * 创建  方法
     */
    private void toNewActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
    }

    /**
     * 异步任务  的  回调方法
     * @param result
     */
    @Override
    public void taskFinished(TaskResult result) {
        switch (result.result_status) {

            case -1:
                MyToastUtils.toastInCenter(this, "加载失败,请检查服务器").show();
                break;

            case 1:
                MyToastUtils.toastInCenter(this, "更新成功").show();
                break;

            default:
                break;
        }
    }
}
