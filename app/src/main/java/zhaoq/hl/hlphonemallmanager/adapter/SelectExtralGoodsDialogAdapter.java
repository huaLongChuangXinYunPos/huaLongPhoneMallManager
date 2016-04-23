package zhaoq.hl.hlphonemallmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.adapter
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  11:18
 */
public class SelectExtralGoodsDialogAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DownGUIGUGoodsEntiity> list;

    public SelectExtralGoodsDialogAdapter(Context context, ArrayList<DownGUIGUGoodsEntiity> list){
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(list!=null){
            ret = list.size();
        }
        return ret;
    }

    @Override
    public DownGUIGUGoodsEntiity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView ==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.select_extral_goods_list_item,null);

            holder.info = (TextView) convertView.findViewById(R.id.list_item_info);
            holder.no = (TextView) convertView.findViewById(R.id.list_item_no);
            holder.title = (TextView) convertView.findViewById(R.id.list_item_title);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据

        holder.title.setText((position+1)+","+list.get(position).getMingcheng() +" 牌子:"+list.get(position).getPinpai());
        holder.info.setText("  单价:"+ NumUtils.getFormatedNum(list.get(position).getBzlsj())+ "(元" +(
                (list.get(position).getDanwei().equals("")||list.get(position).getDanwei()==null)? "" :("/"+list.get(position).getDanwei()))
                +")");
        holder.no.setText("商品号:"+list.get(position).getPinpaino() +
                ",规格:"+((list.get(position).getGuige().equals("")||list.get(position).getGuige()==null)?
                        "无":list.get(position).getGuige())
        );

        return convertView;
    }

    public class ViewHolder{
        TextView title,info,no;
    }
}
