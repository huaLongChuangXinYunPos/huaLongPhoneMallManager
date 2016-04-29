package zhaoq.hl.hlphonemallmanager.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.Toast;


import org.json.JSONObject;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.Configs;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.adapter.TicketsListAdapter;
import zhaoq.hl.hlphonemallmanager.db.CursorToEntity;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.dialog.DialogCallback;
import zhaoq.hl.hlphonemallmanager.dialog.InputInfoDialog;
import zhaoq.hl.hlphonemallmanager.dialog.SelectBrandDialog;
import zhaoq.hl.hlphonemallmanager.dialog.SelectExtralGoodsDialog;
import zhaoq.hl.hlphonemallmanager.dialog.SelectGoodsDialog;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.entity.RequestDataEntity;
import zhaoq.hl.hlphonemallmanager.entity.TicketsInfoToserver;
import zhaoq.hl.hlphonemallmanager.tasks.MainDownInfoAsyncTask;
import zhaoq.hl.hlphonemallmanager.tasks.TaskCallBack;
import zhaoq.hl.hlphonemallmanager.tasks.TaskResult;
import zhaoq.hl.hlphonemallmanager.tasks.TicketsToServer;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.ImageUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyPrinter;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;
import zhaoq.hl.hlphonemallmanager.utils.StateUtils;
import zhaoq.hl.hlphonemallmanager.utils.TimeUtils;

public class TicketManageActivity extends BaseActivity implements DialogCallback,AdapterView.OnItemClickListener ,TaskCallBack{

    private ImageView icBack;

    private EditText brandNo,goodsNo,unitPrice,amount,money; //输入品牌编号

    private ImageView query1,query2;

    private Button btnAdd,print,seePicture;

    private SQLiteDatabase db;

    private ListView listView;

    private SharedPreferences sp;

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

        seePicture = (Button) findViewById(R.id.tickets_up_info_btn);

        listView = (ListView) findViewById(R.id.tickets_ma_goods_list);  //查询  到listView

        adapterList = new ArrayList<DownGUIGUGoodsEntiity>();
        adapter = new TicketsListAdapter(this,adapterList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        initListener();

        sp = getSharedPreferences(Configs.SP_FILE_NAME,MODE_PRIVATE);
    }

    private void initListener() {
        icBack.setOnClickListener(this);
        query1.setOnClickListener(this);
        query2.setOnClickListener(this);
        print.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        seePicture.setOnClickListener(this);

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
                    Cursor cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                "pinpaino like '%"+str+"%' or pinpai like '%"+ str+"%'",null,null,null,null);

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
                    Cursor cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                "pinpaino like '%"+ str1 +"%' or pinpai like '%"+str1+"%' or Dw1 like '%"+ str +"%' or Mingcheng like '%"+str+"%'",null,null,null,null);
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
                                entiity.setMoney(money.getText().toString().trim());
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
                //打印  生成单号：
                sellNo = getSellNo();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("sellNo",sellNo);
                editor.commit();
                if(adapterList != null && adapterList.size()!=0){
                    if(isPrint){ //当前   正在打印
                        MyToastUtils.toastInCenter(this, "当前正在打印").show();
                    }else{
                        //   上传信息   到服务器：   并保存本地
                        new TicketsToServer(this,this,adapterList,sellNo).execute();

                        isPrint = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MyPrinter pr = new MyPrinter();
                                pr.print(TicketManageActivity.this, adapterList, "顾客联", sellNo);
                                pr.print(TicketManageActivity.this, adapterList, "柜组联", sellNo);
                                if(pr.m_printer.Open() == 0){  //关闭  打印机
                                    pr.m_printer.Close();
                                }
                                isPrint = false;

                                //打印完成  通知主线程  更新ui：
                                Message msg = Message.obtain();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    }
                }else{
                    MyToastUtils.toastInCenter(this, "当前单数据为空").show();
                }
                break;

            case R.id.tickets_up_info_btn:
                final String sellTe = sp.getString("sellNo","0");
                if(!sellTe.equals("0")){
                    //查看图片：
                    //从路径下 查看图片
                    if(ImageUtils.getImg(this, "sheetNo")!=null) {
                        Dialog dialog = new Dialog(this,R.style.LoginAlertDialogStyle){
                            @Override
                            protected void onCreate(Bundle savedInstanceState) {
                                View view = (View) LayoutInflater.from(TicketManageActivity.this).inflate(R.layout.image_view,null);
                                ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
                                TextView textView = (TextView) view.findViewById(R.id.text_view);
                                imageView.setImageBitmap(ImageUtils.getImg(TicketManageActivity.this, "sheetNo"));
                                textView.setText(sellTe);
                                setContentView(view);
                            }
                        };
                        dialog.show();
                    }
                }else{
                    MyToastUtils.toastInCenter(this,"当前无打印的条码数据").show();
                }
                break;
            default:
                break;
        }
    }

    //生成销售单号
    private String getSellNo() {
        String no = "";
        no = TimeUtils.getSystemNowTime("yyyyMMdd") + TimeUtils.getSystemNowTime("hhmmss");
        return no.substring(0,12);
    }

    //记录  销售单号
    private static String sellNo;

    private static boolean isPrint = false;  //判断当前  是否正在打印

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

    //上传信息的  回调接口
    @Override
    public void taskFinished(TaskResult result) {
        //保存  判断 数据：
        if(result.data!=null) {
            JSONObject object = result.data;
            //判断 返回数据状态信息：
            int resultStatus = result.result_status;

            switch (resultStatus) {
                //判断  状态码
                case 0:
                    break;
                case 1:
                    //上传成功  数据 保存到本地服务器
                    //将数据库中字段  更新为1
                    db = ApplicationUtils.getInstance().getHelper(this).getWritableDatabase();
                    for(int i=0;i<adapterList.size();i++){
                        db.execSQL("update "+MySqliteHelper.TABLE_TICKETS_NAME+
                                " set lsdDown = 1 where spno = '" +adapterList.get(i).getSpNo()+"'");
                    }
                    //
                    MyToastUtils.toastInCenter(this,"数据已上传至服务器").show();
                    break;
                default:
                    break;
            }
        }
    }


    private Mhandler handler = new Mhandler();

    public class Mhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //处理消息  更新ui数据
            switch (msg.what){
                case 0:
                    //处理  主线程消息：
                    adapterList.clear();
                    adapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }
    }
}
