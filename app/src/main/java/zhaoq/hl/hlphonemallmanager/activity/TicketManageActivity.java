package zhaoq.hl.hlphonemallmanager.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.dialog.SelectBrandDialog;
import zhaoq.hl.hlphonemallmanager.dialog.SelectGoodsDialog;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

public class TicketManageActivity extends BaseActivity implements SelectBrandDialog.DialogCallback {

    private ImageView icBack;

    private EditText brandNo,goodsNo,unitPrice,amount,money; //输入品牌编号

    private ImageView query1,query2;

    private Button btnAdd,updateInfo,print;

    private SQLiteDatabase db;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_ticket);

        db = ApplicationUtils.getInstance().getHelper(this).getReadableDatabase();

        icBack  = (ImageView) findViewById(R.id.ic_back);

        brandNo = (EditText) findViewById(R.id.input_brand_no);
        goodsNo = (EditText) findViewById(R.id.input_goods_no);
        unitPrice = (EditText) findViewById(R.id.goods_unit_price);
        amount = (EditText) findViewById(R.id.goods_amount);
        money = (EditText) findViewById(R.id.goods_money);

        query1 = (ImageView) findViewById(R.id.image_query_ic1);
        query2 = (ImageView) findViewById(R.id.image_query_ic2);
        btnAdd = (Button) findViewById(R.id.btn_add);
        print = (Button) findViewById(R.id.save_and_print);
        updateInfo = (Button) findViewById(R.id.tickets_up_info_btn);

        initListener();
    }

    private void initListener() {
        icBack.setOnClickListener(this);
        updateInfo.setOnClickListener(this);
        query1.setOnClickListener(this);
        query2.setOnClickListener(this);
        print.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    private ArrayList<DownBrandEntity> listBrand;

    private ArrayList<DownGUIGUGoodsEntiity> listGoods;

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.image_query_ic1:
                //根据品牌号查询
                if(checkInput(brandNo)){
                    //获取 数据库
                    String str = brandNo.getText().toString().trim(); //获取  输入内容
                    //执行查询：
                    Cursor cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                "pinpaino like '%"+str+"%'",null,null,null,null);

                    switch (cursor.getCount()){
                        case 0:
                            MyToastUtils.toastInCenter(this, "未查询到当前品牌").show();
                            break;
                        case 1:
                            while(cursor.moveToNext()){
                                brandNo.setText(cursor.getString(cursor.getColumnIndex("pinpai")));
                            }
                            break;
                        default:
                            //弹出 对话框 让用户选择：
                            listBrand = new ArrayList<DownBrandEntity>();
                            while(cursor.moveToNext()){
                                //将  所有品牌信息添加到 list中
                                DownBrandEntity downBrandEntity = new DownBrandEntity();
                                downBrandEntity.setGuizu(cursor.getString(cursor.getColumnIndex("guizu")));
                                downBrandEntity.setGuizuNo(cursor.getString(cursor.getColumnIndex("guizuno")));
                                downBrandEntity.setPinpai(cursor.getString(cursor.getColumnIndex("pinpai")));
                                downBrandEntity.setPinpaino(cursor.getString(cursor.getColumnIndex("pinpaino")));
                                listBrand.add(downBrandEntity);
                            }
                            if(listBrand.size()!=0){
                                new SelectBrandDialog(this,R.style.LoginAlertDialogStyle,listBrand).show();
                            }else{
                                MyToastUtils.toastInCenter(this,"未查询到当前品牌").show();
                            }
                            break;
                    }
                }
                break;
            case R.id.image_query_ic2:
                //根据 货号查询：
                //根据品牌号查询
                if(checkInput(brandNo) && checkInput(goodsNo)){
                    //获取 数据库
                    String str1 = brandNo.getText().toString().trim();
                    String str = goodsNo.getText().toString().trim(); //获取  输入内容
                    //执行查询：
                    Cursor cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                            "pinpai = '"+ str1 +"' and Dw1 = '"+ str +"'",null,null,null,null);
                    switch (cursor.getCount()){
                        case 0:
                            MyToastUtils.toastInCenter(this, "未查询到当前商品").show();
                            break;
                        case 1:
                            while(cursor.moveToNext()){
                                goodsNo.setText(cursor.getString(cursor.getColumnIndex("Mingcheng")));
                            }
                            break;
                        default:
                            //弹出 对话框 让用户选择：
                            listGoods = new ArrayList<DownGUIGUGoodsEntiity>();
                            while(cursor.moveToNext()){
                                DownGUIGUGoodsEntiity goodsEntity = new DownGUIGUGoodsEntiity();
                                goodsEntity.setGuizu(cursor.getString(cursor.getColumnIndex("guizu")));
                                goodsEntity.setGuizuno(cursor.getString(cursor.getColumnIndex("guizuno")));
                                goodsEntity.setPinpai(cursor.getString(cursor.getColumnIndex("pinpai")));
                                goodsEntity.setPinpaino(cursor.getString(cursor.getColumnIndex("pinpaino")));
                                goodsEntity.setBzjj(cursor.getDouble(cursor.getColumnIndex("Bzjjmoney")));
                                goodsEntity.setDw1(cursor.getString(cursor.getColumnIndex("Dw1")));
                                goodsEntity.setSpNo(cursor.getString(cursor.getColumnIndex("SpNo")));
                                goodsEntity.setMingcheng(cursor.getString(cursor.getColumnIndex("Mingcheng")));
                                goodsEntity.setBzlsj(cursor.getDouble(cursor.getColumnIndex("Bzlsjmoney")));
                                goodsEntity.setDanwei(cursor.getString(cursor.getColumnIndex("Danwei")));
                                goodsEntity.setGuige(cursor.getString(cursor.getColumnIndex("guige")));

                                listGoods.add(goodsEntity);
                            }
                            if(listBrand.size()!=0){
                                //弹出选择对话框
                                new SelectGoodsDialog(this,R.style.LoginAlertDialogStyle,listBrand).show();
                            }else{
                                MyToastUtils.toastInCenter(this,"未查询到当前商品").show();
                            }
                            break;
                    }
                }
                break;

            case R.id.btn_add:
                //添加  商品按钮
                break;

            case R.id.save_and_print:
                //保存打印

                break;

            case R.id.tickets_up_info_btn:
//                上传信息

                break;
            default:
                break;
        }
    }

    //检查  数据
    private boolean checkInput(EditText editText) {
        String str = editText.getText().toString().trim();
        if((!TextUtils.isEmpty(str))){
            editText.setError(null);
            return  true;
        }else{
            editText.setError("不准输入为空");
        }
        return false;
    }

    /**
     * 接受  dialog的   回调
     * @param position
     */
    @Override
    public void dialogCallback(int position) {
        brandNo.setText(listBrand.get(position).getPinpai());
    }
}
