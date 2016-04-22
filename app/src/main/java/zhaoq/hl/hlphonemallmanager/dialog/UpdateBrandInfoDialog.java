package zhaoq.hl.hlphonemallmanager.dialog;

import android.app.Dialog;
import android.content.Context;
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
 * DATE: 2016/04/22  10:13
 */
public class UpdateBrandInfoDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private DownBrandEntity downbrandentitiy;

    public static final String AUTHORITY = "UPDATE_BRAND_INPUT_DIALOG_AUTHORITY";

    public UpdateBrandInfoDialog(Context context, int themeResId,
                                 DownBrandEntity downBrandEntity){
        super(context, themeResId);
        this.context = context;
        this.downbrandentitiy = downBrandEntity;
        this.callback = (DialogCallback) context;
    }

    private View view ;

    private TextView title,mingcheng;
    private EditText edno,edmingcheng;
    private Button btnexit,btnsure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view,null);
        setContentView(view);
        initView(view);

        btnsure.setOnClickListener(this);
        btnexit.setOnClickListener(this);
    }

    private void initView(View view) {
        title = (TextView) view.findViewById(R.id.alert_dialog_title);
        title.setText("编辑品牌信息");
        edno = (EditText) view.findViewById(R.id.edit_account);
        edno.setText(downbrandentitiy.getPinpaino());
        edno.setInputType(InputType.TYPE_CLASS_NUMBER);//只能输入数字
        mingcheng = (TextView) view.findViewById(R.id.text_password);
        mingcheng.setText("名称");
        edmingcheng = (EditText) findViewById(R.id.edit_password);
        edmingcheng.setText(downbrandentitiy.getPinpai());

        btnexit = (Button) view.findViewById(R.id.dialog_input_exit);
        btnsure = (Button) view.findViewById(R.id.dialog_input_sure);
    }

    private DialogCallback callback;

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.dialog_input_sure:
                String no = edno.getText().toString().trim();
                String name = edmingcheng.getText().toString().trim();

                if(checkInput(no,name)){
                    if(no.equals(downbrandentitiy.getPinpaino()) && name.equals(downbrandentitiy.getPinpai())){
                        MyToastUtils.toastInBottom(context,"当前未做任何修改").show();
                    }else{
                        SQLiteDatabase db = ApplicationUtils.getInstance().getHelper(context).getWritableDatabase();
                        //TODO 应当  在校验一次  当前号的信息是否重复 添加

                        //修改  数据库信息
                        db.execSQL("update " + MySqliteHelper.TABLE_BRAND_NAME
                                + " set pinpai = '" + name + "',pinpaino = '" + no +
                                "' where pinpai = '" + downbrandentitiy.getPinpai() + "' and pinpaino = '"
                                + downbrandentitiy.getPinpaino() + "' and guizu = '" + downbrandentitiy.getGuizu()
                                + "' and guizuno = '" + downbrandentitiy.getGuizuNo() + "'");

                        downbrandentitiy.setPinpaino(no);
                        downbrandentitiy.setPinpai(name);
                        //回调数据
                        callback.dialogCallbackInputDate(downbrandentitiy, AUTHORITY);
                        this.dismiss();
                        MyToastUtils.toastInCenter(context, "信息修改成功").show();
                    }
                }

                break;
            case R.id.dialog_input_exit:
                //
                this.dismiss();
                break;

            default:
                break;
        }
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
