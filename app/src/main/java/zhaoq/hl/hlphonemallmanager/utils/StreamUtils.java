package zhaoq.hl.hlphonemallmanager.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  17:04
 * 操作流的数据类：
 */
public final class StreamUtils {

    /**
     * 将输入流读取成String
     */
    public static byte[] readStream(InputStream inputStream) throws IOException {
        byte[] ret = null;

        if(inputStream != null){
            ByteArrayOutputStream out  = new ByteArrayOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while((len = inputStream.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            buffer = null;
            ret = out.toByteArray();
            inputStream.close();
            out.close();
        }
        return ret;
    }


    /**
     * 关闭流的  方法：
     */
    public static void close(Object o){
        if(o!=null){
            try {
                if(o instanceof InputStream){
                    ((InputStream) o).close();
                }else if(o instanceof OutputStream){
                    ((OutputStream) o).close();
                }else if(o instanceof Reader){
                    ((Reader) o).close();
                }else if(o instanceof Writer){
                    ((Writer) o).close();
                }else if(o instanceof HttpURLConnection){
                    ((HttpURLConnection) o).disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
