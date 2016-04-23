package zhaoq.hl.hlphonemallmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.BrandSellInfoEntity;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.adapter
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/23  15:56
 */
public final class SellQueryAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<BrandSellInfoEntity> list;
    public SellQueryAdapter(Context context,ArrayList<BrandSellInfoEntity> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(list.size()!=0){
            ret = list.size();
        }
        return ret;
    }

    @Override
    public BrandSellInfoEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.sell_query_list_title,null);

            holder.textView1 = (TextView) convertView.findViewById(R.id.text1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.text2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.text3);
            holder.textView4 = (TextView) convertView.findViewById(R.id.text4);
            holder.textView5 = (TextView) convertView.findViewById(R.id.text5);
            holder.textView6 = (TextView) convertView.findViewById(R.id.text6);
            holder.textView7 = (TextView) convertView.findViewById(R.id.text7);
            holder.textView8 = (TextView) convertView.findViewById(R.id.text8);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据：
        holder.textView1.setText(list.get(position).getLsdNo());
        holder.textView2.setText(list.get(position).getMingcheng());
        holder.textView3.setText(list.get(position).getSpNo());
        holder.textView4.setText(list.get(position).getDanwei());
        holder.textView5.setText(NumUtils.getFormatedNum(list.get(position).getDanjia())); //单价
        holder.textView6.setText(NumUtils.getFormatedNum(list.get(position).getShuliang()));//数量
        holder.textView7.setText(NumUtils.getFormatedNum(list.get(position).getJine())); //金额
        holder.textView8.setText(list.get(position).getLsriqi()); //日期

        return convertView;
    }

    class ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8;
    }
}
