package zhaoq.hl.hlphonemallmanager.activity;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.adapter.BrandListAdapter;
import zhaoq.hl.hlphonemallmanager.db.CursorToEntity;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.dialog.DialogCallback;
import zhaoq.hl.hlphonemallmanager.dialog.SelectBrandDialog;
import zhaoq.hl.hlphonemallmanager.dialog.UpdateBrandInfoDialog;
import zhaoq.hl.hlphonemallmanager.dialog.WriteBrandInfoDialog;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

public class BrandManageActivity extends BaseActivity implements AdapterView.OnItemClickListener,DialogCallback{

    private ImageView icBack;
    private Button addInfo,updateInfo;
//            private Button deleteInfo;

    private EditText inputName;
    private ImageView query;
    private SQLiteDatabase db;

    private ArrayList<DownBrandEntity> daolist;
    private ListView listView;
    private BrandListAdapter adapter;

    //当数据  发生变化后  将 isSelect置为false
    private DataSetObserver obser = new DataSetObserver() {
        @Override
        public void onChanged() {
            isSelect = false;
        }
    };

    @Override
    protected void findView() {
        setContentView(R.layout.activity_brand_info);
        addInfo = (Button) findViewById(R.id.add_brand_info_btn);
        icBack  = (ImageView) findViewById(R.id.ic_back);
        updateInfo = (Button) findViewById(R.id.update_brand_info_btn);
//        deleteInfo = (Button) findViewById(R.id.delete_brand_info_btn);

        inputName = (EditText) findViewById(R.id.brand_input_name);
        query = (ImageView) findViewById(R.id.image_query_ic);
        listView = (ListView) findViewById(R.id.brand_info_list);

        db  = ApplicationUtils.getInstance().getHelper(this).getReadableDatabase();

        daolist = new ArrayList<DownBrandEntity>();
        adapter = new BrandListAdapter(this,daolist);
        adapter.registerDataSetObserver(obser);  //注册   数据的观察者
        listView.setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        icBack.setOnClickListener(this);
        addInfo.setOnClickListener(this);
        query.setOnClickListener(this);
        updateInfo.setOnClickListener(this);
        listView.setOnItemClickListener(this);
//        deleteInfo.setOnClickListener(this);
    }

    private ArrayList<DownBrandEntity> slectlist;

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.image_query_ic:
                //查询出商品信息
                if(checkInput(inputName)){
                    String brandName = inputName.getText().toString().trim();

                    Cursor cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                " pinpaino like '%"+ brandName +"%' or pinpai like '%"+brandName +"%'",null,null,null,null);

                    switch (cursor.getCount()){
                        case 0:
                            MyToastUtils.toastInCenter(this, "未查询到当前品牌").show();
                            break;
                        case 1:
                            DownBrandEntity dao = null;
                            while(cursor.moveToNext()){
                                dao = CursorToEntity.getBrand(cursor);
                                if(checkIsExitCurrList(dao)){//daolist 中已经存在  该条品牌
                                    daolist.add(dao);
                                    //设置  适配数据
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            break;

                        default:
                            //显示 多条信息让用户选择
                            //创建  s  dialog修改该项品牌信息：  上下文和数据信息
                            slectlist = new ArrayList<DownBrandEntity>();
                            DownBrandEntity entity = null;
                            while(cursor.moveToNext()){
                                entity = CursorToEntity.getBrand(cursor);
                                slectlist.add(entity);
                            }
                            new SelectBrandDialog(this,R.style.LoginAlertDialogStyle,slectlist).show();

                            break;
                    }
                }
                break;

            case R.id.add_brand_info_btn:
                //弹出  对话框  录入新品
                //录入品牌信息
                new WriteBrandInfoDialog(this,R.style.LoginAlertDialogStyle).show();

                break;

            case R.id.update_brand_info_btn:
                //修改选中的信息：
                if(isSelect){
                    //创建    dialog修改该项品牌信息：  上下文和数据信息
                    new UpdateBrandInfoDialog(this,R.style.LoginAlertDialogStyle,daolist.get(selectedPosition)).show();
                }else{
                    MyToastUtils.toastInCenter(this, "请先选中一项品牌信息").show();
                }
                break;

//            case R.id.delete_brand_info_btn:
//                //删除  该条品牌信息：
//                if(isSelect){
//                    //获取  当前
//                    SQLiteDatabase db = ApplicationUtils.getInstance().getHelper(this).getWritableDatabase();
//                    DownBrandEntity brand = daolist.get(selectedPosition);
//                    db.execSQL("delete from "+ MySqliteHelper.TABLE_BRAND_NAME +" where pinpaino = '" +
//                            brand.getPinpaino() +"' and pinpai = '"+brand.getPinpai() +"'");
//                    daolist.remove(selectedPosition);
//                    adapter.notifyDataSetChanged();
//                    MyToastUtils.toastInCenter(getApplicationContext(),"删除成功").show();
//                }else{
//                    MyToastUtils.toastInCenter(this, "请先选中一项品牌信息").show();
//                }
//                break;

            default:
                break;
        }
    }

    //判断数据  是否已经存在列表中
    private boolean checkIsExitCurrList(DownBrandEntity dao) {
        for(int i=0;i<daolist.size();i++){
            if(dao.equals(daolist.get(i))){
                MyToastUtils.toastInCenter(this,"列表中已存在该项，不可重复添加").show();
                return false;
            }
        }
        return true;
    }


    //检查数据
    private boolean checkInput(EditText inputName) {
        String str = inputName.getText().toString().trim();
        if(str != null && !str.equals("")){
            return true;
        }else
            MyToastUtils.toastInCenter(this,"输入不准为空").show();
        return false;
    }

    private static boolean isSelect = false;
    private static int selectedPosition;//记录选中位置

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isSelect = true;
        selectedPosition = position;
        MyToastUtils.toastInCenter(this, "已选中当前项").show();
    }

    @Override
    public void dialogCallbackInputDate(DownBrandEntity result, String authority) {
        //回调 数据信息
        switch(authority){
            case UpdateBrandInfoDialog.AUTHORITY:
                //修改界面信息
                if(result!= null){
                        //设置  当期被点击的数据项数据：
                    daolist.get(selectedPosition).setPinpai(result.getPinpai());
                    daolist.get(selectedPosition).setPinpaino(result.getPinpaino());
                    adapter.notifyDataSetChanged();
                }
                break;

            case WriteBrandInfoDialog.AUTHORITY:
                //添加数据
                if(checkIsExitCurrList(result)){
                    daolist.add(result);
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void dialogCallbackSelectedItem(int position, String authority) {
        //回调 数据信息
        switch(authority){
            case SelectBrandDialog.selectBrandDialog:
                // 回调数据
                DownBrandEntity dao = slectlist.get(position);
                if(checkIsExitCurrList(dao)){
                    daolist.add(dao);
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
