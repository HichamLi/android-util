package com.itql.module.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import androidx.annotation.NonNull;

public class BitmapUtil {
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        //根据情况，对调reqWidth和reqHeight
        int useW = reqWidth, useH = reqHeight;
        if (width > height && reqWidth < reqHeight) {
            useW = reqHeight;
            useH = reqWidth;
        }

        if (height > useH || width > useW) {
            inSampleSize *= 2;
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight > useH || halfWidth > useW) {
                inSampleSize *= 2;
                halfHeight /= 2;
                halfWidth /= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 从 fileDescriptor 解码图片
     *
     * @param fileDescriptor fileDescriptor
     * @param reqWidth       reqWidth
     * @param reqHeight      reqHeight
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * 从 文件路径解码图片
     *
     * @param path      图片路径
     * @param reqWidth  reqWidth
     * @param reqHeight reqHeight
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromFilePath(String path, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    /**
     * 从资源文件解码图片
     *
     * @param res       Resources
     * @param resId     资源id
     * @param reqWidth  reqWidth
     * @param reqHeight reqHeight
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 创建缩略图
     *
     * @param sourceBitmap 原图
     * @param reqWidth     要求的宽度
     * @param reqHeight    要求的高度
     * @param isFilter     是否适配
     * @return Bitmap
     */
    public static Bitmap scaleBitmapFromBitmap(@NonNull Bitmap sourceBitmap, int reqWidth, int reqHeight, boolean isFilter) {
        return Bitmap.createScaledBitmap(sourceBitmap, reqWidth, reqHeight, isFilter);
    }

    public static Bitmap scaleBitmapFromBitmap(@NonNull Bitmap sourceBitmap, float scale, boolean isFilter) {
        int width = (int) (sourceBitmap.getWidth() * scale + 0.5);
        int height = (int) (sourceBitmap.getHeight() * scale + 0.5);
        return scaleBitmapFromBitmap(sourceBitmap, width, height, isFilter);
    }

    public static void scaleBitmapAndSave(@NonNull String sourcePath, String destPath, Bitmap.CompressFormat compressFormat, int scale, int quality) {
        try {
            FileUtil.checkFilePath(destPath);
            Bitmap bitmap = BitmapFactory.decodeFile(sourcePath);
            bitmap = scaleBitmapFromBitmap(bitmap, scale, true);
            bitmap.compress(compressFormat, quality, new FileOutputStream(destPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scaleBitmapAndSave(@NonNull String sourcePath, String destPath, Bitmap.CompressFormat compressFormat, int width, int height, int quality) {
        try {
            FileUtil.checkFilePath(destPath);
            Bitmap bitmap = decodeSampledBitmapFromFilePath(sourcePath, width, height);
            bitmap.compress(compressFormat, quality, new FileOutputStream(destPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap str2Bitmap(String str) {
        Bitmap bitmap = null;
        if (null == str || str.equals("")) return null;
        try {
            byte[] bitmapArr;
            bitmapArr = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArr, 0, bitmapArr.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void saveBitmap(Bitmap bitmap, String path) {
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
