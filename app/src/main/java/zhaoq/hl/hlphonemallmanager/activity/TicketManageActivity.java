package zhaoq.hl.hlphonemallmanager.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.adapter.TicketsListAdapter;
import zhaoq.hl.hlphonemallmanager.db.CursorToEntity;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.dialog.DialogCallback;
import zhaoq.hl.hlphonemallmanager.dialog.SelectBrandDialog;
import zhaoq.hl.hlphonemallmanager.dialog.SelectExtralGoodsDialog;
import zhaoq.hl.hlphonemallmanager.dialog.SelectGoodsDialog;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;

public class TicketManageActivity extends BaseActivity implements DialogCallback,AdapterView.OnItemClickListener{

    private ImageView icBack;

    private EditText brandNo,goodsNo,unitPrice,amount,money; //输入品牌编号

    private ImageView query1,query2;

    private Button btnAdd,updateInfo,print;

    private SQLiteDatabase db;

    private ListView listView;

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

        listView = (ListView) findViewById(R.id.tickets_ma_goods_list);  //查询  到listView

        adapterList = new ArrayList<DownGUIGUGoodsEntiity>();
        adapter = new TicketsListAdapter(this,adapterList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        initListener();
    }

    private void initListener() {
        icBack.setOnClickListener(this);
        updateInfo.setOnClickListener(this);
        query1.setOnClickListener(this);
        query2.setOnClickListener(this);
        print.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        //监听  价格的变化
        unitPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=null && !s.toString().equals("")){
                    //修改价格中的内容
                    String am = amount.getText().toString().trim();  //获取数量
                    money.setText(
                            NumUtils.getFormatFloat(
                                    (Float.parseFloat(am)*Float.parseFloat(s.toString()))+""
                            ).toString()
                    );
                }
            }
        });

        //监听数量的变化
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null && !s.toString().equals("")){
                    //修改  价格中的内容
                    String price  = unitPrice.getText().toString().trim();
                    money.setText(
                            NumUtils.getFormatFloat(
                                    (Float.parseFloat(price) * Float.parseFloat(s.toString()))
                                            + "")
                                    .toString()
                    );
                }
            }
        });
    }

    private ArrayList<DownBrandEntity> listBrand;  //存放品牌号

    private ArrayList<DownGUIGUGoodsEntiity> listGoods; //存放  点击图标2获取的商品  商品

    private ArrayList<DownGUIGUGoodsEntiity> selectExtralGoodslist; //点击添加按钮  存放获取的  商品数据

    private static ArrayList<DownGUIGUGoodsEntiity> adapterList;
    private static TicketsListAdapter adapter;

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
                    Cursor cursor = null;
                    if(str.matches("[0-9]+")){ //如果是   数字串  根据
                        cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                "pinpaino like '%"+str+"%'",null,null,null,null);
                    }else{
                        cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                "pinpai like '%"+str+"%'",null,null,null,null);
                    }

                    switch (cursor.getCount()){
                        case 0:  //没查询到
                            MyToastUtils.toastInCenter(this, "未查询到当前品牌").show();
                            break;
                        default:  //查询到多个
                            //弹出 对话框 让用户选择：
                            listBrand = new ArrayList<DownBrandEntity>();
                            while(cursor.moveToNext()){
                                //将  所有品牌信息添加到 list中
                                DownBrandEntity downBrandEntity = CursorToEntity.getBrand(cursor);
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
                    String str1 = brandNo.getText().toString().trim(); //品牌号
                    String str = goodsNo.getText().toString().trim(); //获取  输入内容  货号
                    // 执行查询：
                    Cursor cursor = null;
                    if(str1.matches("[0-9.]+") && str.matches("[0-9.]+")){ //品牌号为数字    商品号为数字
                         cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                "pinpaino like '%"+ str1 +"%' and Dw1 like '%"+ str +"%'",null,null,null,null);
                    }else if(str1.matches("[0-9.]+") && !str.matches("[0-9.]+")){ //品牌号为数字  商品号为汉字
                        cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                "pinpaino like '%"+ str1 +"%' and Mingcheng like '%"+ str +"%'",null,null,null,null);
                    }else if(!str1.matches("[0-9.]+") && str.matches("[0-9.]+")){//品牌号为汉字  商品号为数字
                        cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                "pinpai like '%"+ str1 +"%' and Dw1 like '%"+ str +"%'",null,null,null,null);
                    }else if(!str1.matches("[0-9.]+") && !str.matches("[0-9.]+")){//品牌号为汉字  商品号为汉字
                        cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                "pinpai like '%"+ str1 +"%' and Mingcheng like '%"+ str +"%'",null,null,null,null);
                    }
                    switch (cursor.getCount()){
                        case 0: //未查询到
                            MyToastUtils.toastInCenter(this, "未查询到当前商品").show();
                            break;
                        default:  //查询到  多个
                            //弹出 对话框 让用户选择：
                            listGoods = new ArrayList<DownGUIGUGoodsEntiity>();
                            while(cursor.moveToNext()){
                                DownGUIGUGoodsEntiity  goodsEntity = CursorToEntity.getGoodsEntity(cursor);
                                listGoods.add(goodsEntity);
                            }
                            if(listGoods.size()!=0){
                                //弹出选择对话框
                                new SelectGoodsDialog(this,R.style.LoginAlertDialogStyle,listGoods).show();
                            }else{
                                MyToastUtils.toastInCenter(this,"未查询到当前商品").show();
                            }
                            break;
                    }
                }
                break;

            case R.id.btn_add:
                //检测  是否已经被添加：
                //添加  商品按钮
                //更新  listView中的内容  创建 商品类  //根据当前  品牌和  货号 查询商品
                Cursor cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"}
                    ," pinpai = '"+brandNo.getText().toString().trim()+"' and Mingcheng = '"+
                        goodsNo.getText().toString().trim()+"'",null,null,null,null);

                switch(cursor.getCount()){
                    case 0:
                        MyToastUtils.toastInCenter(this, "未查询到当前商品").show();
                        break;
                    case 1:
                        //获取  数据
                        DownGUIGUGoodsEntiity  entiity = null;
                        while(cursor.moveToNext()){
                            if(checkIsAdd(cursor)){
                                entiity = CursorToEntity.getGoodsEntity(cursor);
                                entiity.setBzlsj(Double.parseDouble(unitPrice.getText().toString().trim()));
                                entiity.setAmount(amount.getText().toString().trim());
                                entiity.setMoney(amount.getText().toString().trim());
                                adapterList.add(entiity);
                            }else{
                                MyToastUtils.toastInCenter(this,"添加失败，当前商品已存在于列表中").show();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        break;

                    default:
                        //查询到多个  数据
                        selectExtralGoodslist = new ArrayList<DownGUIGUGoodsEntiity>();
                        //获取  数据
                        DownGUIGUGoodsEntiity  downGUIGUGoodsEntiity = null;
                        while(cursor.moveToNext()){
                            downGUIGUGoodsEntiity = CursorToEntity.getGoodsEntity(cursor);
                            selectExtralGoodslist.add(downGUIGUGoodsEntiity);
                        }
                        //弹出 对话框 让用户选择：
                        if(selectExtralGoodslist.size()!=0){
                            //弹出选择对话框
                            new SelectExtralGoodsDialog(this,R.style.LoginAlertDialogStyle,selectExtralGoodslist).show();
                        }else{
                            MyToastUtils.toastInCenter(this,"未查询到当前商品").show();
                        }
                        break;
                }
                break;

            case R.id.save_and_print:
                // TODO  保存打印 暂时  未处理
                MyToastUtils.toastInCenter(this, "当前无打印机").show();
                break;

            case R.id.tickets_up_info_btn:
                // TODO  上传信息 暂时未处理，服务端没写接口
                MyToastUtils.toastInCenter(this,"请检查服务器").show();

                break;
            default:
                break;
        }
    }

    //检查  是否已经被添加进过集合中
    private boolean checkIsAdd(Cursor cursor) {
        String spno = cursor.getString(cursor.getColumnIndex("SpNo"));
        for(int i=0;i<adapterList.size();i++){
            if(spno.equals(adapterList.get(i).getSpNo())){
                return false;
            }
        }
        return true;
    }

    //检查  数据
    private boolean checkInput(EditText editText) {
        String str = editText.getText().toString().trim();
        if((!TextUtils.isEmpty(str))){
            editText.setError(null);
            return  true;
        }else{
            editText.setError("不准输入为空");
            editText.requestFocus();
        }
        return false;
    }

    /**
     * 接受  dialog的   回调
     * @param position
     */
    @Override
    public void dialogCallbackSelectedItem(int position,String authority) {
        point:switch(authority){
            case SelectBrandDialog.selectBrandDialog:
                brandNo.setText(listBrand.get(position).getPinpai());
                break;
            case SelectGoodsDialog.selectGoodsDialog:
                brandNo.setText(listGoods.get(position).getPinpai());
                goodsNo.setText(listGoods.get(position).getMingcheng());
                unitPrice.setText(NumUtils.getFormatedNum(listGoods.get(position).getBzlsj()));//单价
                amount.setText("1");
                break;

            case SelectExtralGoodsDialog.AUTHORITY:
                DownGUIGUGoodsEntiity dao = selectExtralGoodslist.get(position);
                dao.setBzlsj(Double.parseDouble(unitPrice.getText().toString().trim()));
                dao.setAmount(amount.getText().toString().trim());
                dao.setMoney(amount.getText().toString().trim());

                for(int i=0;i<adapterList.size();i++){
                    if (adapterList.get(i).getSpNo().equals(dao.getSpNo())){
                        MyToastUtils.toastInCenter(this,"添加失败，当前商品已存在于列表中").show();
                        break point;
                    }
                }
                adapterList.add(dao);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    @Override
    public void dialogCallbackInputDate(DownBrandEntity result,String authority) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //设置  数据：
        brandNo.setText(adapterList.get(position).getPinpai());
        goodsNo.setText(adapterList.get(position).getDw1());
        unitPrice.setText(NumUtils.getFormatedNum(adapterList.get(position).getBzlsj()));
        amount.setText(adapterList.get(position).getAmount());
        money.setText(adapterList.get(position).getMoney());

        showPopWindow(view, position);
    }

    //弹出上下文  窗口：
    private void showPopWindow(View view, final int position) {
        //弹出  菜单：
        final View popView = LayoutInflater.from(this).inflate(R.layout.pop_window_layout,null);
        //设置TextView 点击事件：
        final TextView delete = (TextView) popView.findViewById(R.id.delete_item);


        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
        //添加  事件：
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除 该项
                adapterList.remove(position);
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
        //设置  点击事件：
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_view_item_selector));
        popupWindow.showAsDropDown(view);
    }
}
