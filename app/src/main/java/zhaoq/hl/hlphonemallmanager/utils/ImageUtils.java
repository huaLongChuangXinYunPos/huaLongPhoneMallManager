package zhaoq.hl.hlphonemallmanager.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ImageUtils {

    public static void saveImg(String fileName, Bitmap bitmap,Context context) throws IOException {
        File dir = new File(context.getApplicationContext().getCacheDir()+"/images/");
        if (!dir.exists()) dir.mkdirs();
        //将图片对象写入到指定输出流中
        bitmap.compress(CompressFormat.JPEG, 100,
                new FileOutputStream(new File(dir, fileName+".jpg")));
    }

    public static Bitmap getImg(Context context,String fileName) {
        File imgFile = new File(context.getApplicationContext().getCacheDir()+"/images/", fileName + ".jpg");
        if (imgFile.exists()) {
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return null;
    }

}
