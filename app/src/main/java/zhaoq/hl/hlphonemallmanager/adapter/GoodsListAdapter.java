package zhaoq.hl.hlphonemallmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.adapter
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  18:05
 */
public class GoodsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DownGUIGUGoodsEntiity> list;

    public GoodsListAdapter(Context context, ArrayList<DownGUIGUGoodsEntiity> list){
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
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.goods_info_list_title,null);

            holder.text1 = (TextView) convertView.findViewById(R.id.text1);
            holder.text1.setTextSize(13);
            holder.text2 = (TextView) convertView.findViewById(R.id.text2);
            holder.text2.setTextSize(13);
            holder.text3 = (TextView) convertView.findViewById(R.id.text3);
            holder.text3.setTextSize(13);
            holder.text4= (TextView) convertView.findViewById(R.id.text4);
            holder.text4.setTextSize(13);
            holder.text5 = (TextView) convertView.findViewById(R.id.text5);
            holder.text5.setTextSize(13);
            holder.text6 = (TextView) convertView.findViewById(R.id.text6);
            holder.text6.setTextSize(13);
            holder.text7 = (TextView) convertView.findViewById(R.id.text7);
            holder.text7.setTextSize(13);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text1.setText(list.get(position).getSpNo());
        holder.text2.setText(list.get(position).getMingcheng());
        holder.text3.setText(list.get(position).getPinpaino());
        holder.text4.setText(list.get(position).getPinpai());
        holder.text5.setText((list.get(position).getDanwei().equals("") ||list.get(position).getDanwei() ==null)?
                                            "无":list.get(position).getDanwei());
        holder.text6.setText(list.get(position).getDw1());
        holder.text7.setText(NumUtils.getFormatedNum(list.get(position).getBzlsj()));

        return convertView;
    }

    public class ViewHolder{
        TextView text1,text2,text3,text4,text5,text6,text7;
    }
}
