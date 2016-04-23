package zhaoq.hl.hlphonemallmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.adapter.BrandListAdapter;
import zhaoq.hl.hlphonemallmanager.adapter.GoodsListAdapter;
import zhaoq.hl.hlphonemallmanager.db.CursorToEntity;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.dialog.DialogCallback;
import zhaoq.hl.hlphonemallmanager.dialog.SelectExtralGoodsDialog;
import zhaoq.hl.hlphonemallmanager.dialog.WriteBrandInfoDialog;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

public class GoodsInfoActivity extends BaseActivity implements DialogCallback, AdapterView.OnItemClickListener {

    private ImageView icBack;

    private EditText goodsInput;
    private ImageView query;
    private Button addInfo,updateInfo;
//    private Button deleteInfo;
    private SQLiteDatabase db;

    private ArrayList<DownGUIGUGoodsEntiity> daolist;
    private ListView listView;
    private GoodsListAdapter adapter;

    //当数据  发生变化后  将 isSelect置为false
    private DataSetObserver obser = new DataSetObserver() {
        @Override
        public void onChanged() {
            isSelect = false;
        }
    };

    private static boolean isSelect = false;
    private static int selectedPosition;//记录选中位置

    private static final int REQUEST_CODE = 2;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_goods_info);

        icBack  = (ImageView) findViewById(R.id.ic_back);
        goodsInput = (EditText) findViewById(R.id.goods_input_no);
        addInfo = (Button) findViewById(R.id.goods_add_info);
        updateInfo = (Button) findViewById(R.id.goods_update_info);
//        deleteInfo = (Button) findViewById(R.id.goods_delete_info);
        query = (ImageView) findViewById(R.id.goods_query_ic);

        goodsInput = (EditText) findViewById(R.id.goods_input_no);
        listView = (ListView) findViewById(R.id.goods_info_list);

        db  = ApplicationUtils.getInstance().getHelper(this).getReadableDatabase();

        daolist = new ArrayList<DownGUIGUGoodsEntiity>();
        adapter = new GoodsListAdapter(this,daolist);
        listView.setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        icBack.setOnClickListener(this);
        query.setOnClickListener(this);
        addInfo.setOnClickListener(this);
        updateInfo.setOnClickListener(this);
//        deleteInfo.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private ArrayList<DownGUIGUGoodsEntiity> selectExtralGoodslist; //选择确切的    商品数据

    public static final String updateAuthority = "UPDATE_INFO";

    public static final String addAuthority = "ADD_INFO";

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.goods_query_ic:
                //开始查询：
                //查询出商品信息
                if(checkInput(goodsInput) && checkIsExitCurrList(goodsInput)){
                    String input = goodsInput.getText().toString().trim();
                    Cursor cursor = null;
                    if(input.matches("[0-9.]+")){
                        cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                "  SpNo = '"+ input +"'",null,null,null,null);
                    }else{
                        cursor = db.query(MySqliteHelper.TABLE_GOODS_NAME,new String[]{"*"},
                                " Mingcheng like '"+input+"'",null,null,null,null);
                    }

                    switch (cursor.getCount()){
                        case 0:
                            MyToastUtils.toastInCenter(this, "未查询到当前商品").show();
                            break;
                        case 1:
                            DownGUIGUGoodsEntiity dao = null;
                            while(cursor.moveToNext()){
                                dao = CursorToEntity.getGoodsEntity(cursor);
                                daolist.add(dao);
                            }
                            //设置  适配数据
                            adapter.notifyDataSetChanged();
                            break;

                        default:
                            //查询到多个  数据
                           if(input.matches("[0-9.]+")){//输入为数字
                               MyToastUtils.toastInCenter(this,"当前查询到多个商品，请检查后台数据").show();
                           }else{
                               //查询到多个信息：  显示对话框
                                //查询到多个  数据
                               selectExtralGoodslist = new ArrayList<DownGUIGUGoodsEntiity>();
                               //获取  数据
                               DownGUIGUGoodsEntiity  downGUIGUGoodsEntiity = null;
                               while(cursor.moveToNext()){
                                   downGUIGUGoodsEntiity = CursorToEntity.getGoodsEntity(cursor);
                                   selectExtralGoodslist.add(downGUIGUGoodsEntiity);
                               }
                               //弹出 对话框 让用户选择：
                               //弹出选择对话框
                               new SelectExtralGoodsDialog(this,R.style.LoginAlertDialogStyle,selectExtralGoodslist).show();
                           }

                           break;
                    }
                }
                break;

            case R.id.goods_add_info:
                //添加信息
                //弹出  对话框  录入新品
                //录入品牌信息
                Intent intent = new Intent();
                intent.setClass(this,AddGoodsActivity.class);
                intent.putExtra("authority", addAuthority);
                startActivityForResult(intent, REQUEST_CODE);

                break;

            case R.id.goods_update_info:
                //更新信息
                if(isSelect){
                    // 修改当前商品信息项
                    Intent in = new Intent();
                    in.setClass(this, AddGoodsActivity.class);
                    //添加  标识
                    in.putExtra("authority", updateAuthority);
                    //传递  选中数据
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dao",daolist.get(selectedPosition));
                    in.putExtra("bundle",bundle);

                    startActivityForResult(in, REQUEST_CODE);
                }else{
                    MyToastUtils.toastInCenter(this,"请先选中一项商品").show();
                }
                break;

//            case R.id.goods_delete_info:
//                //删除信息
//
//                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case AddGoodsActivity.RESULT_CODE:
                //
                if( data != null && requestCode == REQUEST_CODE ){
                    Bundle bundle = data.getExtras();
                    DownGUIGUGoodsEntiity dao = (DownGUIGUGoodsEntiity) bundle.getSerializable("dao");
                    if(AddGoodsActivity.authority.equals(updateAuthority)){  //当前 是刷新 页面的返回值
                        daolist.set(selectedPosition,dao);
                    }else{
                        daolist.add(dao);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;

            default:
                break;
        }
    }

    private boolean checkIsExitCurrList(EditText inputName) {
        //检查当前   输入的数据  是否已经存在list中
        String input = inputName.getText().toString().trim();
        if(input.matches("[0-9.]+")){//输入是数字
            for(int i = 0;i< daolist.size();i++){
                if(input.equals(daolist.get(i).getSpNo())){
                    MyToastUtils.toastInCenter(this,"当前商品已经存在于列表中").show();
                    return false;
                }
            }
        }
        return true;
    }

    //检查数据
    private boolean checkInput(EditText inputName) {
        String str = inputName.getText().toString().trim();
        if(str != null && !str.equals("")){
            return true;
        }else{
            MyToastUtils.toastInCenter(this,"输入不准为空").show();
        }
        return false;
    }


    @Override
    public void dialogCallbackSelectedItem(int position, String authority) {
        switch (authority){
            case SelectExtralGoodsDialog.AUTHORITY:
                //从   集合中取出数据：
                daolist.add(selectExtralGoodslist.get(position));
                adapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }
    @Override
    public void dialogCallbackInputDate(DownBrandEntity result,String authority) {
    }

    //listView 点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isSelect = true;
        selectedPosition = position;
        MyToastUtils.toastInCenter(this, "已选中当前项").show();
    }
}
