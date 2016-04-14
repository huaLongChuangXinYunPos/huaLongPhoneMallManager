package zhaoq.hl.hlphonemallmanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/14  16:26
 */
public class InputInfoDialog extends Dialog {

    private Context context;

    public InputInfoDialog(Context context) {
        super(context);
        this.context = context;
    }

    public InputInfoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_view,null);

        this.setTitle("输入柜组信息");
        this.setContentView(view);
    }

}
