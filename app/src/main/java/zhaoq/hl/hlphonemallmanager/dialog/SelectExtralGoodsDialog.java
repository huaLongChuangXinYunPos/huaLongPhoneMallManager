package zhaoq.hl.hlphonemallmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.adapter.SelectExtralGoodsDialogAdapter;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.dialog
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/20  16:37
 */
public class SelectExtralGoodsDialog extends Dialog implements AdapterView.OnItemClickListener {

    private ArrayList<DownGUIGUGoodsEntiity> list;
    private Context context;

    private View view;

    //构造器
    public SelectExtralGoodsDialog(Context context, int style, ArrayList<DownGUIGUGoodsEntiity> list) {
        super(context,style);
        this.list = list;
        this.context = context;
        this.callback = (DialogCallback) context;
    }

    private ListView listView;

    //创建 布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.select_brand_dialog,null);
        setContentView(view);
        TextView textTitle = (TextView) view.findViewById(R.id.title_dialog);
        textTitle.setText("选择商品");

        listView = (ListView) view.findViewById(R.id.list_view);

        SelectExtralGoodsDialogAdapter adapter = new SelectExtralGoodsDialogAdapter(context,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    public static final String AUTHORITY = "SELECT_EXTRAL_GOODS_DIALOG";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //回调  回去
        callback.dialogCallbackSelectedItem(position,AUTHORITY);
        this.dismiss();
    }

    private DialogCallback callback;
}
