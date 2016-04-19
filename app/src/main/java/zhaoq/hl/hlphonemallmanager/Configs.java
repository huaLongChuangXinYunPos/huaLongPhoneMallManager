package zhaoq.hl.hlphonemallmanager;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:51
 * 配置全局  请求数据的  url网址
 */
public final class Configs {

    //服务器地址是
    public static final  String BASE_SERVER_URL =
            "http://192.168.3.200:1236/MpServer/AppPubServer.asmx/";

    //获取   柜组信息 的url   用于初始化  数据到本地  并添加数据到本地信息中:  在login中获取
    public static final String LOGIN_GET_GUIZU_INFO_URL =
            "http://192.168.3.200:1236/MpServer/AppPubServer.asmx/GetGuizuCode?";

    //获取   柜组信息 的url   用于初始化  数据到本地  并添加数据到本地信息中:  在  main中获取
    public static final String MAIN_DOWN_INFO_URL =
            "http://192.168.3.200:1236/MpServer/AppPubServer.asmx/GetGuizuInfor?";

    //获取  柜组信息号的  异步任务标识
    public static final int GET_GUIZU_INFO_AUTHORITY = 1;


    //主界面中  下载信息的  异步任务类
    public static final int MAIN_DOWN_INFO_AUTHORITY = 2;



}
