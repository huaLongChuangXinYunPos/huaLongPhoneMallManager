package zhaoq.hl.hlphonemallmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;

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

    public static String authority;

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

        authority = getIntent().getStringExtra("authority");

        if(authority.equals(GoodsInfoActivity.updateAuthority)){
            //设置 数据  直接显示  获取 bundle
            Bundle bundle = getIntent().getBundleExtra("bundle");
            DownGUIGUGoodsEntiity dao = (DownGUIGUGoodsEntiity) bundle.getSerializable("dao");

            brandno.setText(( dao.getPinpaino() == null||dao.getPinpaino().equals(""))? "无":dao.getPinpaino());
            brandname.setText(dao.getPinpai());
            goodsSp.setText(dao.getSpNo());
            goodsname.setText(dao.getMingcheng());
            goodsdw1.setText(dao.getDw1());
            goodsUnit.setText(( dao.getDanwei() == null||dao.getDanwei().equals(""))? "无":dao.getDanwei());
            goodsOut.setText(NumUtils.getFormatedNum(dao.getBzlsj()));
            goodsIn.setText(NumUtils.getFormatedNum(dao.getBzjj()));

            businessName.setText("无");
            businessNo.setText("无");

            goodsSp.setEnabled(false);
        }
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
                    SQLiteDatabase db = ApplicationUtils.getInstance().getHelper(this).getWritableDatabase();

                    DownGUIGUGoodsEntiity dao = new DownGUIGUGoodsEntiity();
                    //设置数据
                    dao.setSpNo(goodsSp.getText().toString().trim());
                    dao.setMingcheng(goodsname.getText().toString().trim());
                    dao.setDw1(goodsdw1.getText().toString().trim());
                    dao.setDanwei(goodsUnit.getText().toString().trim());
                    dao.setBzlsj(Double.parseDouble(NumUtils.getFormatFloat(goodsOut.getText().toString().trim()).toString()));
                    dao.setBzjj(Double.parseDouble(NumUtils.getFormatFloat(goodsIn.getText().toString()).toString()));
                    dao.setPinpaino(brandno.getText().toString().trim());
                    dao.setPinpai(brandname.getText().toString().trim());
                    dao.setGuizu(ApplicationUtils.getInstance().getUser().getGuizu());
                    dao.setGuizuno(ApplicationUtils.getInstance().getUser().getGuizuno());

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dao", dao);
                    intent.putExtras(bundle);
                    this.setResult(RESULT_CODE, intent);
                    //执行 当前  查询  如果当前
                    //判断  标识 根据 标识 进行不同的处理：
                    switch(authority){
                        case GoodsInfoActivity.addAuthority:
                            Cursor cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                    " SpNo = '" + goodsSp.getText().toString().trim() + "'",null,null,null,null);
                            //进行 一次判断  当前库中是否已经存在  该编号：
                            if(cursor.getCount()==0){
                                //保存数据到   本地仓库中
                                db.execSQL(String.format(MySqliteHelper.INSERT_GOODS_INFO,
                                        dao.getPinpai(), dao.getPinpaino(), dao.getGuizu(), dao.getGuizuno(),
                                        dao.getDw1(), dao.getSpNo(), dao.getMingcheng(), dao.getBzjj(),
                                        dao.getBzlsj(), dao.getDanwei(), dao.getGuige(), "0"));
                                finish();
                            }else{
                                MyToastUtils.toastInCenter(this,"保存失败，当前商品编号已存在").show();
                            }
                            break;

                        case GoodsInfoActivity.updateAuthority:
                            //更新 数据
                            //更新数据库信息
                            db.execSQL("update " + MySqliteHelper.TABLE_GOODS_NAME + " set pinpai = '" + dao.getPinpai() +
                                    "',pinpaino = '" + dao.getPinpaino() + "',guizu = '" + dao.getGuizu() +
                                    "',guizuno = '" + dao.getGuizuno() + "',Dw1 = '" + dao.getDw1() +
                                    "',Mingcheng = '" + dao.getMingcheng() + "',Bzjjmoney = '" + dao.getBzjj() +
                                    "',Bzlsjmoney = '" + dao.getBzlsj() + "',Danwei = '" + dao.getDanwei() +
                                    "',bNew = '0'  where SpNo = '" + goodsSp.getText().toString().trim() + "'");

                            MyToastUtils.toastInCenter(this,"修改成功").show();

                            finish();
                            break;
                        default:
                            break;
                    }
                }
                break;

            case R.id.goods_add_query:
                // TODO 查询图标    暂时 未处理


                break;

            default:
                break;
        }
    }

    //检查   数据
    private boolean checkInput() {

        String gSp = goodsSp.getText().toString().trim();//商品  编号
        String gName = goodsname.getText().toString().trim(); //商品名
        String gNo = goodsdw1.getText().toString().trim(); // 货号
        String gOutP = goodsOut.getText().toString().trim(); //售价
        String gInP = goodsIn.getText().toString().trim(); //进价
        String bNo = brandno.getText().toString().trim();  //品牌号
        String bName = brandname.getText().toString().trim(); //品牌名

        if(bNo.equals("") || bNo==null){
            MyToastUtils.toastInCenter(this,"品牌号不准为空").show();
            return false;
        }
        if(bName.equals("") || bNo==null){
            MyToastUtils.toastInCenter(this,"品牌名称不准为空").show();
            return false;
        }
        if(gNo.equals("") || gNo==null){
            MyToastUtils.toastInCenter(this,"货号不准为空").show();
            return false;
        }
        if(gName.equals("") || gName==null){
            MyToastUtils.toastInCenter(this,"商品名不准为空").show();
            return false;
        }
        if(gOutP.equals("") || gOutP==null){
            MyToastUtils.toastInCenter(this,"售价不准为空").show();
            return false;
        }
        if(gInP.equals("") || gInP==null){
            MyToastUtils.toastInCenter(this,"进价不准为空").show();
            return false;
        }
        if( gSp.equals("") || gSp==null){
            MyToastUtils.toastInCenter(this,"商品编号不准为空").show();
            return false;
        }
        return true;
    }
}
