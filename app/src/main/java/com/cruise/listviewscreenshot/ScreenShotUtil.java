package com.cruise.listviewscreenshot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cruise on 2015/11/29.
 * 保存当前屏幕
 */
public class ScreenShotUtil {


    /**
     * 截取listView的屏幕
     */
    public static Bitmap getBitmapByView(ListView listView) {

        int totalHeight = 0;
        Bitmap bitmap;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return null;
        }
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        totalHeight += (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        bitmap = Bitmap.createBitmap(listView.getWidth(), totalHeight, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        listView.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 保存到文件
     */
    public static String savePic(Bitmap bitmap) {
        if (bitmap == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);
        File outfile = new File(Environment.getExternalStorageDirectory() + "/image");
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
                Log.i("qqq", "ee = " + outfile.mkdir());
            } catch (Exception e) {
                Log.i("qqq", "e = " + e.toString());
                e.printStackTrace();
            }
        }
        String fileName = outfile + "/" + sdf.format(new Date()) + ".png";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.i("qqq", "e2 = " + e.toString());
            e.printStackTrace();
        }
        return fileName;
    }

}
