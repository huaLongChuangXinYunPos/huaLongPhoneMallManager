package zhaoq.hl.hlphonemallmanager.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * PACKAGE_NAME:zhaoq.hl.fastdelivery.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/03/28  16:22
 */
public final class BitmapUtils {

    /**
     * 生成二维码方法
     * @param str
     * @return
     */
    public static Bitmap  getTwoCode(String str, int widthAndHeight) throws WriterException {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        Hashtable hints = new Hashtable();

        hints.put(EncodeHintType.CHARACTER_SET,"utf-8"); //编码字符集
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//设置  容错率
//        hints.put(EncodeHintType.MARGIN,1);//设置  边白

        //创建 一个  位的 矩阵   以固定宽高和填充数据
        BitMatrix matrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE
                                   ,widthAndHeight,widthAndHeight);

        int width = matrix.getWidth();
        int height = matrix.getHeight();

        int[] pixels = new int[width*height];

        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                if(matrix.get(x, y)){
                    pixels[y*width + x] = BLACK;
                }else{
                    pixels[y*width + x] = WHITE;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(widthAndHeight,widthAndHeight,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0,width,0,0,width,height);

        return bitmap;
    }

    /**
     * 生成    一维码方法
     * @param str
     * @param width
     * @param height
     * @return
     */
    public static Bitmap  getOneCode(String str, Integer width,
                                     Integer height)
    {
        if (width == null || width < 100)
        {
            width = 80;
        }
        if (height == null || height < 50)
        {
            height = 50;
        }
        try
        {
            // 文字编码
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
                    BarcodeFormat.CODE_128, width, height, hints);
            return BitMatrixToBitmap(bitMatrix);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BitMatrix转换成Bitmap
     * @param matrix
     * @return
     */
    private static Bitmap BitMatrixToBitmap(BitMatrix matrix) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }else{
                    pixels[y * width + x] = WHITE;
                }
            }
        }
        return createBitmap(width, height, pixels);
    }

    /**
     * 生成Bitmap
     * @param width
     * @param height
     * @param pixels
     * @return
     */
    private static Bitmap createBitmap(int width, int height, int[] pixels) {
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        bitmap.getScaledWidth(100);
        bitmap.getScaledHeight(50);
        return bitmap;
    }
}
