package zhaoq.hl.hlphonemallmanager.activity;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import zhaoq.hl.hlphonemallmanager.BaseActivity;
import zhaoq.hl.hlphonemallmanager.R;
import zhaoq.hl.hlphonemallmanager.adapter.SellQueryAdapter;
import zhaoq.hl.hlphonemallmanager.entity.BrandSellInfoEntity;
import zhaoq.hl.hlphonemallmanager.entity.RequestSellEntitiy;
import zhaoq.hl.hlphonemallmanager.tasks.SellQueryAsyncTask;
import zhaoq.hl.hlphonemallmanager.tasks.TaskCallBack;
import zhaoq.hl.hlphonemallmanager.tasks.TaskResult;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.JSONUtils;
import zhaoq.hl.hlphonemallmanager.utils.MyToastUtils;

public class SellQueryActivity extends BaseActivity implements TaskCallBack {

    private ImageView icBack;

    private TextView timeStart,timeEnd;

    private ImageView query;

    private EditText brandNo;

    private ListView listView;

    @Override
    protected void findView() {
        setContentView(R.layout.activity_sell_query);
        icBack  = (ImageView) findViewById(R.id.ic_back);
        icBack.setOnClickListener(this);

        timeStart = (TextView) findViewById(R.id.input_start_time);
        timeEnd = (TextView) findViewById(R.id.input_end_time);
        brandNo = (EditText) findViewById(R.id.input_brandno);
        query = (ImageView) findViewById(R.id.query_ic);
        listView = (ListView) findViewById(R.id.sell_query_listview);
        //获取当天时间
        initTime();

        daolist = new ArrayList<BrandSellInfoEntity>();
        adapter = new SellQueryAdapter(this,daolist);
        listView.setAdapter(adapter);
    }

    private ArrayList<BrandSellInfoEntity> daolist;
    private SellQueryAdapter adapter;

    private void initTime() {
        //初始化  当前时间：
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long currD = System.currentTimeMillis();
        Date date = new Date(currD);
        String text = format.format(date);
        timeEnd.setText(text);
        timeStart.setText(text);

        timeStart.setOnClickListener(this);
        timeEnd.setOnClickListener(this);
        query.setOnClickListener(this);
    }

    @Override
    protected void myOnclick(View v) {
        switch(v.getId()){
            case R.id.ic_back:
                finish();
                break;

            case R.id.input_start_time:
                //开始时间：
                showSelectDialog(timeStart);

                break;

            case R.id.input_end_time:
//                结束时间
                showSelectDialog(timeEnd);

                break;

            case R.id.query_ic:
                if(brandNo.getText().toString().trim()==null || brandNo.getText().toString().trim().equals("")){
                    MyToastUtils.toastInCenter(this,"品牌号输入不准为空").show();
                }else{
                    //开始  查询数据：
                    //拼接参数 信息
                    String startTime = timeStart.getText().toString().trim();
                    String endTime = timeEnd.getText().toString().trim();
                    String no = brandNo.getText().toString().trim();

                    RequestSellEntitiy dao = new RequestSellEntitiy();
                    dao.setDDate1(startTime);
                    dao.setDDate2(endTime);
                    dao.setPinpaiNo(no);
                    dao.setGuizuNo(ApplicationUtils.getInstance().getUser().getGuizuno());
                    String json = new Gson().toJson(dao);

                    List<String> list = new ArrayList<String>();
                    list.add(json);
                    new SellQueryAsyncTask(this,this).execute(list.toString());
                }
                break;
            default:
                break;
        }
    }

    private void showSelectDialog(final TextView textView) {
        //时间  时间 选择器：
        Calendar c = Calendar.getInstance();//获取日历
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textView.setText(year+"-"+(monthOfYear+1)+"-"+getFormatedDay(dayOfMonth));
                    }
                },
                c.get(Calendar.YEAR), //传入 年份
                c.get(Calendar.MONTH), //传入  月份
                c.get(Calendar.DAY_OF_MONTH) //传入日期
        );
        dialog.show();
    }

    private String getFormatedDay(int dayOfMonth) {
        //格式化  数据为 09 ，08 的格式
        String str = dayOfMonth+"";
        if(str.length() == 1){
            str = "0"+str;
        }
        return str;
    }

    @Override
    public void taskFinished(TaskResult result) {
        switch (result.task_id){
            case SellQueryAsyncTask.authority:
                if(result.data!=null){
                    JSONObject object = result.data;
                    try {
                        int status = object.getInt("resultStatus");
                        if(status != 0){
                            //清空数据
                            daolist.clear();
                            adapter.notifyDataSetChanged();

                            //解析数据
                            JSONArray array = object.getJSONArray("data");
                            ArrayList<BrandSellInfoEntity> list = JSONUtils.parseBrandSellInfo(array);
                            for (int i=0;i<list.size();i++){
                                daolist.add(list.get(i));
                            }
                            //将数据添加到  listview中：
                            adapter.notifyDataSetChanged();
                        }else{
                            MyToastUtils.toastInCenter(this,"该时间段无当前品牌销售信息").show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    MyToastUtils.toastInCenter(this,"未获取到数据，请检查网络").show();
                }
                break;

            default:
                break;
        }
    }
}
