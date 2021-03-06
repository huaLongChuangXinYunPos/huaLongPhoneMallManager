package zhaoq.hl.hlphonemallmanager.utils;

import java.math.BigDecimal;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/21  09:55
 * 格式化   数字类型的  信息
 */
public final class NumUtils {

    //格式化  小数数据
    public static String getFormatedNum(double num){
        String numStr = num+"";
        if(numStr != null && !numStr.equals("") && numStr.contains(".")){
            while(numStr.endsWith("0")){
                numStr = numStr.substring(0,numStr.length()-1);
            }
        }
        if (numStr.endsWith(".")){
            numStr = numStr.substring(0,numStr.length()-1);
        }
        return numStr;
    }

    //保留  两位小数
    public static BigDecimal getFormatFloat(String floatNum){
        BigDecimal bd = new BigDecimal(floatNum);
        bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static String getFormatedNum(String numStr){
        if(numStr != null && !numStr.equals("") && numStr.contains(".")){
            while(numStr.endsWith("0")){
                numStr = numStr.substring(0,numStr.length()-1);
            }
        }
        if (numStr.endsWith(".")){
            numStr = numStr.substring(0,numStr.length()-1);
        }
        return numStr;
    }

    /**
     * 格式化  带有小数点的数据   整数不进行去零
     * @param text
     * @return
     */
    public static String getFormatFloatNumber(String text){
        String str = text;
        if(str!=null&&!str.equals("")&&str.contains(".")){  //格式化  小数数据
            while(str.endsWith("0")){
                str = str.substring(0,str.length()-1);
            }
        }
        if(str.endsWith(".")){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }
}
