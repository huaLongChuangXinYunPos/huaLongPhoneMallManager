package zhaoq.hl.hlphonemallmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 操作   时间的  工具类
 */
public final class TimeUtils {
	
	public static String getSystemNowTime(String formatPattern) {
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		String time = df.format(new Date());
		return time;
	}

	//获取  前一天的日期
	public static String getBeforeDay(){
		//获取  当天销售日期：
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());//将日历设置为当前  日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);//将  这月中当前一天时间  减一   获取  前一天时间

		Date beforeDay = calendar.getTime();//获取前一天时间：
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//格式化时间
		String form = format.format(beforeDay);
		return form;
	}
	

}
