package com.bob.gank_client.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by bob on 17-5-1.
 */

public class FileUtil {
        private FileUtil() {
                throw new UnsupportedOperationException("can not be instanced");
        }

        public static boolean isSDCardEnable() {
                return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        public static Uri saveBitmapToSDCard(Bitmap bitmap, String title) {
                File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
                if (!appDir.exists()) {
                        appDir.mkdirs();
                }
                String fileName = title.replace("/", "-") + "girl.jpg";
                File file = new File(appDir, fileName);
                FileOutputStream outputStream;
                try{
                        outputStream = new FileOutputStream(file);
                        assert bitmap != null;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                        return null;
                }
                return Uri.fromFile(file);
        }
}
