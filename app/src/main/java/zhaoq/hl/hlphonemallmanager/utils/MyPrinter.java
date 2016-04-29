package zhaoq.hl.hlphonemallmanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.ArrayList;

import hardware.print.printer;
import hardware.print.printer.PrintType;
import zhaoq.hl.hlphonemallmanager.Configs;
import zhaoq.hl.hlphonemallmanager.entity.DownGUIGUGoodsEntiity;

/**
 * PACKAGE_NAME:zhaoq.hl.fastdelivery.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/03/25  10:49
 * 用于打印的  工具类
 */
public final class MyPrinter{

    public static printer m_printer = null;

    static{
        m_printer = new printer();
    }

    public MyPrinter() {
        if (m_printer!=null){
            m_printer.Open();
        }
    }

    public static void print(Context context,ArrayList<DownGUIGUGoodsEntiity> list,String theme,String sellNo) {
        //打印数据信息
        m_printer.PrintLineInit(40);
        m_printer.PrintLineString("欢迎光临华联商厦", 40, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(24);
        m_printer.PrintLineString("(" + theme + ")", 24, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        Bitmap bm1 = null;
        try {
            bm1 = BitmapUtils.getTwoCode("http://www.warelucent.com/index.php",
                    130);
            ImageUtils.saveImg("downUrl", bm1, context);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ImageUtils.getImg(context,"downUrl")!=null) {
            m_printer.PrintBitmap(ImageUtils.getImg(context,"downUrl"));
        }

        m_printer.PrintLineInit(24);
        m_printer.PrintLineString("加密码:" + sellNo, 24, PrintType.Left, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(24);
        m_printer.PrintLineString("柜组("+ApplicationUtils.getInstance().getUser().getGuizuno()+"):" + ApplicationUtils.getInstance().getUser().getGuizu(),
                24, printer.PrintType.Left, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(22);
        m_printer.PrintLineString("-----------------------------------------", 22, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(25);
        m_printer.PrintLineString("商品名称", 25, printer.PrintType.Left, false);
        m_printer.PrintLineString("数量    单价", 25, printer.PrintType.Centering, false);
        m_printer.PrintLineString("金额      ", 25, printer.PrintType.Right, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(22);
        m_printer.PrintLineString("-----------------------------------------", 22, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        for(int i=0;i<list.size();i++){
            m_printer.PrintLineInit(24);
            m_printer.PrintLineString((i + 1) + "," + list.get(i).getSpNo(), 24, printer.PrintType.Left, false);
            m_printer.PrintLineEnd();

            m_printer.PrintLineInit(24);
            m_printer.PrintLineString("   " + list.get(i).getMingcheng(), 24, printer.PrintType.Left, false);
            m_printer.PrintLineEnd();

            m_printer.PrintLineInit(24);
            m_printer.PrintLineString(NumUtils.getFormatFloatNumber(list.get(i).getAmount())+"  *  ￥"+
                    NumUtils.getFormatFloatNumber(list.get(i).getBzlsj()+"") + " =￥" +
                    NumUtils.getFormatFloatNumber(list.get(i).getMoney()) + "     .", 24, printer.PrintType.Right, false);
            m_printer.PrintLineEnd();
        }

        m_printer.PrintLineInit(22);
        m_printer.PrintLineString("-----------------------------------------", 22, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(28);
        m_printer.PrintLineString("合计:￥ " + NumUtils.getFormatFloatNumber(getListMoney(list)), 28, printer.PrintType.Left, false);
        m_printer.PrintLineString("       数量：" + list.size(), 28, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(22);
        m_printer.PrintLineString("打印时间:" + TimeUtils.getSystemNowTime("yyyy-MM-dd hh:mm:ss"), 22, printer.PrintType.Left, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(22);
        m_printer.PrintLineString("-----------------------------------------", 22, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(24);
        m_printer.PrintLineString("扫描条码或二维码确认订单", 24, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        Bitmap bm2 = BitmapUtils.getOneCode(sellNo, 480, 130);
        try {
            ImageUtils.saveImg("sheetNo", bm2, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ImageUtils.getImg(context,"sheetNo")!=null) {
            m_printer.PrintBitmap(ImageUtils.getImg(context,"sheetNo"));
        }
        m_printer.PrintLineInit(24);
        m_printer.PrintLineString(sellNo, 24, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(22);
        m_printer.PrintLineString("-----------------------------------------", 22, printer.PrintType.Centering, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(24);
        m_printer.PrintLineString("服务电话:18513667437", 24, printer.PrintType.Left, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(24);
        m_printer.PrintLineString("商家地址:"+"北京市海淀区", 24, printer.PrintType.Left, false);
        m_printer.PrintLineEnd();

        m_printer.PrintLineInit(100);
        m_printer.PrintLineString("", 100, printer.PrintType.Left, false);
        m_printer.PrintLineEnd();
    }

    //遍历  所有金额  获取金额
    private static String getListMoney(ArrayList<DownGUIGUGoodsEntiity> list) {
        double money = 0;
        if(list!=null && list.size()!=0){
            for(int i=0;i<list.size();i++){
                money = money + Double.parseDouble(list.get(i).getMoney());
            }
        }
        return NumUtils.getFormatedNum(money);
    }
}
