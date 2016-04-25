package zhaoq.hl.hlphonemallmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.adapter
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  11:18
 */
public class TicketsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DownGUIGUGoodsEntiity> list;

    public TicketsListAdapter(Context context,ArrayList<DownGUIGUGoodsEntiity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.tickets_managment_list_title,null);

            holder.textView1 = (TextView) convertView.findViewById(R.id.text1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.text2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.text3);
            holder.textView4 = (TextView) convertView.findViewById(R.id.text4);
            holder.textView5 = (TextView) convertView.findViewById(R.id.text5);
            holder.textView6 = (TextView) convertView.findViewById(R.id.text6);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //绑定数据
        holder.textView1.setText((position+1)+"");  /****/
        holder.textView2.setText(list.get(position).getMingcheng());
        holder.textView3.setText(list.get(position).getDw1());
        holder.textView4.setText(list.get(position).getBzlsj() + "");
        holder.textView5.setText(list.get(position).getAmount());
        holder.textView6.setText(list.get(position).getMoney());

        return convertView;
    }

    public class ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5,textView6;
    }
}
