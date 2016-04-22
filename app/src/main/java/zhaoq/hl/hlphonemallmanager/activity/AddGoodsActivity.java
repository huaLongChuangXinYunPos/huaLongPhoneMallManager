package zhaoq.hl.hlphonemallmanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

public class AddGoodsActivity extends BaseActivity {

    public static final int RESULT_CODE = 1; //返回码

    private ImageView icBack;

    private EditText brandno,brandname,goodsSp,goodsname,goodsdw1,goodsUnit,goodsOut,goodsIn;

    private EditText businessNo,businessName;

    private ImageView queryIc;

    private Button btnSure;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_add_goods);
        initView();
        initListener();
    }

    private void initListener() {
        queryIc.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        icBack.setOnClickListener(this);
    }

    private void initView() {

        icBack  = (ImageView) findViewById(R.id.ic_back);
        goodsSp = (EditText) findViewById(R.id.goods_spno);
        goodsname = (EditText) findViewById(R.id.goods_mingcheng);
        goodsdw1 = (EditText) findViewById(R.id.goods_dw1);
        goodsUnit = (EditText) findViewById(R.id.goods_unit);
        goodsOut = (EditText) findViewById(R.id.goods_out_price);
        goodsIn = (EditText) findViewById(R.id.goods_in_price);
        brandno = (EditText) findViewById(R.id.goods_brandno);
        brandname = (EditText) findViewById(R.id.goods_input_no);

        businessNo = (EditText) findViewById(R.id.goods_businessNo);
        businessName = (EditText) findViewById(R.id.goods_businessName);

        btnSure = (Button) findViewById(R.id.goods_add_btn);
        queryIc = (ImageView) findViewById(R.id.goods_add_query);

    }

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.goods_add_btn:
                //-----
                if(checkInput()){
                    DownGUIGUGoodsEntiity dao = new DownGUIGUGoodsEntiity();


                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dao",dao);
                    intent.putExtras(bundle);
                    this.setResult(RESULT_CODE, intent);
                    finish();
                }
                break;

            case R.id.goods_add_query:



                break;

            default:
                break;
        }
    }

//    pinpai  varchar(32), -- 品牌名称
//    pinpaino varchar(32), -- 品牌编号
//    guizu varchar(32), --柜组名称
//    guizuno varchar(32), -- 柜组编号
//    Dw1 varchar(32), -- 货号
//    SpNo varchar(32), --商品编号
//    Mingcheng varchar(64), --- 商品名称
//    Bzjj money, -- 进价
//    Bzlsj money,-- 零售价
//    Danwei varchar(8), --单位
//    guige varchar(50), -- 规格
//    bNew varchar(2)  --- 状态：1表示新添加、上传成功后改成0

    //检查   数据
    private boolean checkInput() {
    //                brandno,brandname,goodsSp,goodsname,goodsdw1,goodsUnit,goodsOut,goodsIn;
    //               EditText businessNo,businessName;
        String bNo = brandno.getText().toString().trim();  //品牌号
        String bName = brandname.getText().toString(); //品牌名
        String gSp = goodsSp.getText().toString().trim();//商品  编号
        String gNo = goodsdw1.getText().toString(); //商品号
        String gName = goodsname.getText().toString().trim(); //商品名
        String gOutP = goodsOut.getText().toString(); //售价
        String gInP = goodsIn.getText().toString(); //进价
        String busNo = businessNo.getText().toString(); //厂家号
        String busName = businessName.getText().toString();//厂家名
//
        if(!bNo.equals("") && bNo!=null){
            MyToastUtils.toastInCenter(this,"品牌号不准为空").show();
            return false;
        }
        if(!bName.equals("") && bNo!=null){
            MyToastUtils.toastInCenter(this,"品牌名称不准为空").show();
            return false;
        }
        if(!gNo.equals("") && gNo!=null){
            MyToastUtils.toastInCenter(this,"货号不准为空").show();
            return false;
        }
        if(!gName.equals("") && gName!=null){
            MyToastUtils.toastInCenter(this,"商品名不准为空").show();
            return false;
        }
        if(!gOutP.equals("") && gOutP!=null){
            MyToastUtils.toastInCenter(this,"售价不准为空").show();
            return false;
        }
        if(!gInP.equals("") && gInP!=null){
            MyToastUtils.toastInCenter(this,"进价不准为空").show();
            return false;
        }
        if(!busNo.equals("") && busNo!=null){
            MyToastUtils.toastInCenter(this,"商家号不准为空").show();
            return false;
        }
        if(!busName.equals("") && busName!=null){
            MyToastUtils.toastInCenter(this,"商家名不准为空").show();
            return false;
        }
        if(!gSp.equals("") && gSp!=null){
            MyToastUtils.toastInCenter(this,"商品编号不准为空").show();
            return false;
        }
        return true;
    }
}
