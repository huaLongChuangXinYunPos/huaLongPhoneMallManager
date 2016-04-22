package zhaoq.hl.hlphonemallmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.dialog
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/22  13:00
 * 录入  品牌信息  的dialog
 */
public class WriteBrandInfoDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private DialogCallback callback;

    public static final String AUTHORITY = "WRITE_BRAND_DIALOG_WUTHORITY";

    public WriteBrandInfoDialog(Context context) {
        super(context);
    }

    private View view;

    private TextView title,mingcheng;
    private EditText edno,edmingcheng;
    private Button btnexit,btnsure;

    public WriteBrandInfoDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.callback = (DialogCallback) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        view  = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view,null);
        setContentView(view);
        initView(view);

        btnsure.setOnClickListener(this);
        btnexit.setOnClickListener(this);
    }

    //初始化  数据信息
    private void initView(View view) {
        title = (TextView) view.findViewById(R.id.alert_dialog_title);
        title.setText("添加品牌信息");
        edno = (EditText) view.findViewById(R.id.edit_account);
        edno.setInputType(InputType.TYPE_CLASS_NUMBER);//只能输入数字
        edno.setHint("输入品牌编号");
        mingcheng = (TextView) view.findViewById(R.id.text_password);
        mingcheng.setText("名称");
        edmingcheng = (EditText) findViewById(R.id.edit_password);
        edmingcheng.setHint("输入品牌名称");

        btnexit = (Button) view.findViewById(R.id.dialog_input_exit);
        btnsure = (Button) view.findViewById(R.id.dialog_input_sure);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.dialog_input_exit:
                this.dismiss();
                break;
            case R.id.dialog_input_sure:
                String no = edno.getText().toString().trim();
                String name = edmingcheng.getText().toString().trim();
                //检验  输入数据
                if(checkInput(no,name)){
                    //检验  当前品牌号是否已经存在
                    if(ckeckIsExitBrandNo(no)){
                        //输入正确： 保存到  数据库
                        SQLiteDatabase db = ApplicationUtils.getInstance().getHelper(context).getWritableDatabase();
                        db.execSQL("insert into " + MySqliteHelper.TABLE_BRAND_NAME + "(pinpai,pinpaino,guizu,guizuno) values('"
                                + name + "','" + no + "','" + ApplicationUtils.getInstance().getUser().getGuizu() + "','" +
                                ApplicationUtils.getInstance().getUser().getGuizuno() + "')");

                        DownBrandEntity dao = new DownBrandEntity();
                        dao.setPinpaino(no);
                        dao.setPinpai(name);
                        dao.setGuizuNo(ApplicationUtils.getInstance().getUser().getGuizuno());
                        dao.setGuizu(ApplicationUtils.getInstance().getUser().getGuizuno());

                        callback.dialogCallbackInputDate(dao,AUTHORITY);
                        //
                        MyToastUtils.toastInCenter(context,"添加成功").show();

                        this.dismiss();
                    }else{
                        MyToastUtils.toastInBottom(context,"当前品牌号已经存在，不准重复添加").show();
                    }
                }
                break;
            default:
                break;
        }
    }

    //检查当前  号 是否已经存在
    private boolean ckeckIsExitBrandNo(String no) {
        //检查  号码
        SQLiteDatabase db = ApplicationUtils.getInstance().getHelper(context).getReadableDatabase();
        Cursor cursor = db.query(MySqliteHelper.TABLE_BRAND_NAME,new String[]{"*"}," pinpaino = '" + no +"'",
                null,null,null,null);

        if(cursor.getCount() == 0){
            return true;
        }
        return false;
    }


    //检查数据
    private boolean checkInput(String no, String name) {
        if((no != null && !no.equals(""))&&( name!=null && !name.equals(""))){
            return true;
        }else{
            MyToastUtils.toastInBottom(context, "输入不准为空值").show();
        }
        return false;
    }
}
