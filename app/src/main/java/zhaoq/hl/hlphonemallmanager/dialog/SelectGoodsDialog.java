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
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.dialog
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/20  16:37
 */
public class SelectGoodsDialog extends Dialog implements AdapterView.OnItemClickListener {

    private ArrayList<DownGUIGUGoodsEntiity> list;
    private Context context;

    private View view;

    //构造器
    public SelectGoodsDialog(Context context, int style, ArrayList<DownGUIGUGoodsEntiity> list) {
        super(context,style);
        this.list = list;
        this.context = context;
        this.callback = (DialogCallback) context;
    }

    private ListView listView;
    private ArrayList<String> list2;

    //创建 布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.select_brand_dialog,null);
        setContentView(view);

        TextView textTitle = (TextView) view.findViewById(R.id.title_dialog);
        textTitle.setText("选择商品");

        listView = (ListView) view.findViewById(R.id.list_view);

        initList();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.text_view,list2);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    private void initList() {
        list2 = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            list2.add(list.get(i).getMingcheng());
        }
    }

    public static final String selectGoodsDialog = "SELECT_GOODS_DIALOG";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //回调  回去
        callback.dialogCallbackSelectedItem(position,selectGoodsDialog);
        this.dismiss();
    }

    private DialogCallback callback;
}
