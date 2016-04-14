package zhaoq.hl.hlphonemallmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.brand.BrandInfoActivity;
import zhaoq.hl.hlphonemallmanager.goodsInfo.GoodsInfoActivity;
import zhaoq.hl.hlphonemallmanager.sellquery.SellQueryActivity;
import zhaoq.hl.hlphonemallmanager.tickets.TicketActivity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView icBack;

    private Button btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icBack  = (ImageView) findViewById(R.id.ic_back);

        btn1 = (Button) findViewById(R.id.main_btn1);
        btn2 = (Button) findViewById(R.id.main_btn2);
        btn3 = (Button) findViewById(R.id.main_btn3);
        btn4 = (Button) findViewById(R.id.main_btn4);
        
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
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;
            case R.id.main_btn1:
                toNewActivity(TicketActivity.class);
                break;
            case R.id.main_btn2:
                toNewActivity(BrandInfoActivity.class);
                break;
            case R.id.main_btn3:
                toNewActivity(GoodsInfoActivity.class);
                break;
            case R.id.main_btn4:
                toNewActivity(SellQueryActivity.class);
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
}
