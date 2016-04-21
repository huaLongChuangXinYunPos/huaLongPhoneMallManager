package zhaoq.hl.hlphonemallmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.entity.DownBrandEntity;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.adapter
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  18:05
 */
public class BrandListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DownBrandEntity> list;

    public BrandListAdapter(Context context,ArrayList<DownBrandEntity> list){
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

            convertView = LayoutInflater.from(context).inflate(R.layout.brand_info_list_title,null);

            holder.text1 = (TextView) convertView.findViewById(R.id.text_brand_name);
            holder.text1.setTextSize(20);
            holder.text2 = (TextView) convertView.findViewById(R.id.text_brand_no);
            holder.text2.setTextSize(20);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text1.setText(list.get(position).getPinpaino());
        holder.text2.setText(list.get(position).getPinpai());

        return convertView;
    }

    public class ViewHolder{
        TextView text1,text2;
    }
}
