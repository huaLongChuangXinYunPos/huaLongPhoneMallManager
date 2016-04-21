package zhaoq.hl.hlphonemallmanager.activity;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
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
import zhaoq.hl.hlphonemallmanager.db.CursorToEntity;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToast;

public class BrandManageActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ImageView icBack;
    private Button addInfo,updateInfo;

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
    }

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
                    Cursor cursor = null;
                    if(brandName.matches("[0-9]+")){
                        cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                " pinpaino = '"+brandName+"'",null,null,null,null);
                    }else{
                        cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"},
                                " pinpai = '"+brandName+"'",null,null,null,null);
                    }

                    switch (cursor.getCount()){
                        case 0:
                            MyToast.ToastIncenter(this,"未查询到当前品牌").show();
                            break;
                        case 1:
                            DownBrandEntity dao = null;
                            while(cursor.moveToNext()){
                                dao = CursorToEntity.getBrand(cursor);
                                daolist.add(dao);
                            }
                            //设置  适配数据
                            adapter.notifyDataSetChanged();
                            break;

                        default:
                            MyToast.ToastIncenter(this,"当前号检测到多条信息，请检查后台数据").show();
                            break;
                    }
                }
                break;

            case R.id.add_brand_info_btn:
                //弹出  对话框  录入新品

                break;

            case R.id.update_brand_info_btn:
                //修改选中的信息：
                if(isSelect){
                    //创建 dialog修改该项品牌信息：

                }else{
                    MyToast.ToastIncenter(this,"请先选中一条品牌信息").show();
                }
                break;
            case R.id.delete_brand_info_btn:
                //删除  该条品牌信息：

                break;
            default:
                break;
        }
    }

    //检查数据
    private boolean checkInput(EditText inputName) {
        String str = inputName.getText().toString().trim();
        if(str != null && !str.equals("")){
            return true;
        }
        return false;
    }

    private static boolean isSelect = false;
    private static int selectedPosition;//记录选中位置

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isSelect = true;
        selectedPosition = position;
    }
}
