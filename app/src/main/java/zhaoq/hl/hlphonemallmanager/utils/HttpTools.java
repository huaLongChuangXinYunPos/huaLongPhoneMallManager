package zhaoq.hl.hlphonemallmanager.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  16:25
 * 请求网络
 */
public final class HttpTools {

    //私有化  构造方法：该类 不可以被实例化
    private HttpTools(){}

    //设置   链接超时时间：
    private static final int CONNECTION_TIMEOUT = 10000;

    //设置  连接    读取超时时间：
    private static final int READ_OUTTIME = 10000;

    /**
     * get方式  访问数据
     * @param urlStr
     * @return
     */
    public static byte[] doGet(String urlStr){
        byte[] ret = null;
        if(urlStr != null){
            HttpURLConnection conn = null;
            try {
                URL url = new URL(urlStr);
                conn = (HttpURLConnection) url.openConnection();

                //设置  连接属性：
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setReadTimeout(READ_OUTTIME);

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept","application/*,text/*,*/*");
                //设置内容压缩格式：
                conn.setRequestProperty("Accept-Encoding", "gzip");
                conn.connect();

                int code = conn.getResponseCode();
                if(code == 200){
                    InputStream fin = conn.getInputStream();

                    String encoding = conn.getHeaderField("Content-Encoding");

                    if ("gzip".equals(encoding)){
                        fin = new GZIPInputStream(fin);
                    }
                    //读取  字节流
                    ret = StreamUtils.readStream(fin);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static byte[] doPost(String url,HashMap<String,String> map){
        InputStream in = null;
        ByteArrayOutputStream bos = null;

        if(url!=null){
            StringBuilder sb = new StringBuilder();
            try{

                for(Map.Entry<String,String> en : map.entrySet()){
                    sb.append(en.getKey())
                            .append("=")
                            .append(URLEncoder.encode(en.getValue(),"utf-8"));
                }
                Log.i("info",map.toString()+"00000000");

                URL u = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                connection.setReadTimeout(READ_OUTTIME);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Accept", "application/*,text/*,image/*,*/*");

                connection.setDoOutput(true);
                connection.setDoInput(true);

                //提交数据
                byte[] b = sb.toString().getBytes();
                //写入数据
                OutputStream out = connection.getOutputStream();
                out.write(b);
                out.close();
                //读取服务器返回数据：
                in = connection.getInputStream();
                byte[] bytes = StreamUtils.readStream(in);

                return  bytes;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }





}
