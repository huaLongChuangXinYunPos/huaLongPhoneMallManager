package zhaoq.hl.hlphonemallmanager.tasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import zhaoq.hl.hlphonemallmanager.client.ClientApi;
import zhaoq.hl.hlphonemallmanager.db.MySqliteHelper;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;
import zhaoq.hl.hlphonemallmanager.entity.LoginUserEntitiy;
import zhaoq.hl.hlphonemallmanager.entity.TicketsInfoToserver;
import zhaoq.hl.hlphonemallmanager.utils.ApplicationUtils;
import zhaoq.hl.hlphonemallmanager.utils.JSONUtils;
import zhaoq.hl.hlphonemallmanager.utils.NumUtils;
import zhaoq.hl.hlphonemallmanager.utils.TimeUtils;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/26  12:37
 */
public class TicketsToServer extends BaseAsyncTask {

    private Context context;

    private String sellNo;

    private ArrayList<DownGUIGUGoodsEntiity> adapterList;

    public TicketsToServer(TaskCallBack callBack, Context context,ArrayList<DownGUIGUGoodsEntiity> list,String sellNo) {
        super(callBack, context);
        this.adapterList = list;
        this.context = context;
        this.sellNo = sellNo;
    }

    private static final int authority = 5;

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult result = new TaskResult();
        result.task_id = authority; //添加  标识

        if(adapterList!=null){
            toLocalDb(adapterList);//将数据 保存值本地数据库
            ArrayList<String> list1 = new ArrayList<String>();
            for (int i=0;i<adapterList.size();i++){
                TicketsInfoToserver dao = new TicketsInfoToserver();
                dao.setLsdno(sellNo);  //单号
                dao.setSpno(adapterList.get(i).getSpNo());
                dao.setShuliang(adapterList.get(i).getAmount());
                dao.setDanjia(NumUtils.getFormatedNum(adapterList.get(i).getBzlsj()));
                dao.setJine(adapterList.get(i).getMoney());
                dao.setGonghao(ApplicationUtils.getInstance().getUser().getGonghao());
                dao.setMingcheng(ApplicationUtils.getInstance().getUser().getMingcheng());
                dao.setLsriqi(TimeUtils.getSystemNowTime("yyyy-MM-dd"));
                String json = new Gson().toJson(dao);
                list1.add(json);
            }
            JSONObject object = ClientApi.getTicketsToServerResult(list1.toString(),context);
            try {
                if(object!=null){
                    result.result_status = object.getInt("resultStatus");
                    result.data = object;
                    return result;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //保存 数据到本地数据库
    private void toLocalDb(ArrayList<DownGUIGUGoodsEntiity> adapterList) {
        SQLiteDatabase database = ApplicationUtils.getInstance().getHelper(context).getWritableDatabase();
//(lsdno,spno,shuliang,danjia," +
//        "jine,lsriqi,gonghao,Mingcheng,lsdDown)
        database.beginTransaction();
        for (int i=0;i<adapterList.size();i++){
            database.execSQL(String.format(MySqliteHelper.INSERT_TICKETS_INFO,
                    sellNo,adapterList.get(i).getSpNo(),adapterList.get(i).getAmount(),
                    adapterList.get(i).getBzlsj(),adapterList.get(i).getMoney(),
                    TimeUtils.getSystemNowTime("yyyy-MM-dd"),ApplicationUtils.getInstance().getUser().getGonghao(),
                    ApplicationUtils.getInstance().getUser().getMingcheng(),"0"));
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
}
