package com.example.admin.data_analysis_tool.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Created by lensar on 2017/5/16.
 */

public class ImagesUtil {
    /**
     * 图片的质量压缩
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100 && options >= 50) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return reduceResolution(bitmap);
    }

    /**
     * 降低图片分辨率，提升图像识别效率
     */
    public static Bitmap reduceResolution(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTempStorage = new byte[100 * 1024];
        opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
        opts.inPurgeable = true;
        opts.inSampleSize = getSampleSize(image);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(isBm, null, opts);
    }

    public static Bitmap reduceResolution(String imagePath){
        int sampleSize = 1;
        if(checkResolution(imagePath)){
            sampleSize = 5;
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTempStorage = new byte[100 * 1024];
        opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
        opts.inPurgeable = true;
        opts.inSampleSize = sampleSize;
        return BitmapFactory.decodeStream(is, null, opts);
    }

    private static boolean checkResolution(String imagePath){
        FileInputStream is = null;
        try {
            is = new FileInputStream(imagePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while(is.read(buffer) > 0){
                baos.write(buffer);
            }
            return baos.size() > 10 * 1024 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 计算缩放比例
     * @param bitmap
     * @return
     */
    private static int getSampleSize(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int temp1 = width;
        int temp2 = height;
        int size = 1;
        while(temp1 > 50 || temp2 > 50){
            temp1 = width;
            temp2 = height;
            size ++;
            temp1 /= size;
            temp2 /= size;
        }
        return size;
    }


    public static void recycleBitmap(Bitmap bitmap){
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

}
