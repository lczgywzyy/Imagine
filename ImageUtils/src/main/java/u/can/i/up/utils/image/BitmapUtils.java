package u.can.i.up.utils.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;


/**
 * @author:Xian
 */
public class BitmapUtils {

    /**
     * @description 计算图片的压缩比率
     *
     * @param options 参数
     * @param reqWidth 目标的宽度
     * @param reqHeight 目标的高度
     * @return
     */
    private static final String TAG = "u.can.i.up.imagine." + BitmapUtils.class;
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
     *
     * @param src
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    /**
     * @description 从Resources中加载图片
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置成了true,不占用内存，只获取bitmap宽高
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长宽，目的是得到图片的宽高
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); // 调用上面定义的方法计算inSampleSize值
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize); // 通过得到的bitmap，进一步得到目标大小的缩略图
    }

    /**
     * @description 从SD卡上加载图片
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize);
    }

    /*
         * 该方法用于保存抠出的图片
         */
    public static Bitmap cropImage(Bitmap img, int width, int height) {
        // Create new blank ARGB bitmap.
        Bitmap finalBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Get the coordinates for the middle of combineImg.
        int hMid = img.getHeight() / 2;
        int wMid = img.getWidth() / 2;
        int hfMid = finalBm.getHeight() / 2;
        int wfMid = finalBm.getWidth() / 2;

//        Log.d(TAG, "hMid: " + hMid + "wMid: " + wMid + "hfMid: " + hfMid + "wfMid: " + wfMid);
        int y2 = hfMid;
        int x2 = wfMid;

        for (int y = hMid; y >= 0; y--) {
            boolean template = false;
            // Check Upper-left section of combineImg.
            for (int x = wMid; x >= 0; x--) {
                if (x2 < 0) {
                    break;
                }

                int px = img.getPixel(x, y);
              /*  Log.d(TAG, px + "px---------------");
                Log.d(TAG, "Red: " + Color.red(px) + " Green; " + Color.green(px)
                        + " Blue: " + Color.blue(px) + " Alpha: " + Color.alpha(px));*/
                if (Color.red(px) == 255 && Color.green(px) == 255 && Color.blue(px) == 0) {
                    template = true;
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else if (template) {
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else {
//                    Log.d(TAG, "pix: " + Color.red(px) + Color.green(px) + Color.blue(px));
                    finalBm.setPixel(x2, y2, px);
                }
                x2--;
            }
            // Check upper-right section of combineImage.
            x2 = wfMid;
            template = false;
            for (int x = wMid; x < img.getWidth(); x++) {
                if (x2 >= finalBm.getWidth()) {
                    break;
                }

                int px = img.getPixel(x, y);
                if (Color.red(px) == 255 && Color.green(px) == 255 && Color.blue(px) == 0) {
                    template = true;
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else if (template) {
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else {
                    finalBm.setPixel(x2, y2, px);
                }
                x2++;
            }

            // Once we reach the top-most part on the template line, set pixel value transparent
            // from that point on.
            int px = img.getPixel(wMid, y);
            if (Color.red(px) == 255 && Color.green(px) == 255 && Color.blue(px) == 0) {
                for (int y3 = y2; y3 >= 0; y3--) {
                    for (int x3 = 0; x3 < finalBm.getWidth(); x3++) {
                        finalBm.setPixel(x3, y3, Color.TRANSPARENT);
                    }
                }
                break;
            }

            x2 = wfMid;
            y2--;
        }

        x2 = wfMid;
        y2 = hfMid;
        for (int y = hMid; y < img.getHeight(); y++) {
            boolean template = false;
            // Check bottom-left section of combineImage.
            for (int x = wMid; x >= 0; x--) {
                if (x2 < 0) {
                    break;
                }
                int px = img.getPixel(x, y);
                if (Color.red(px) == 255 && Color.green(px) == 255 && Color.blue(px) == 0) {
                    template = true;
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else if (template) {
//                    Log.d(TAG, "This is one");
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else {
//                    Log.d(TAG, "This is two");
                    finalBm.setPixel(x2, y2, px);
                }
                x2--;
            }

            // Check bottom-right section of combineImage.
            x2 = wfMid;
            template = false;
            for (int x = wMid; x < img.getWidth(); x++) {
                if (x2 >= finalBm.getWidth()) {
                    break;
                }

                int px = img.getPixel(x, y);
                if (Color.red(px) == 255 && Color.green(px) == 255 && Color.blue(px) == 0) {
                    template = true;
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else if (template) {
                    finalBm.setPixel(x2, y2, Color.TRANSPARENT);
                } else {
                    finalBm.setPixel(x2, y2, px);
                }
                x2++;
            }

            // Once we reach the bottom-most part on the template line, set pixel value transparent
            // from that point on.
            int px = img.getPixel(wMid, y);
            if (Color.red(px) == 255 && Color.green(px) == 255 && Color.blue(px) == 0) {
                for (int y3 = y2; y3 < finalBm.getHeight(); y3++) {
                    for (int x3 = 0; x3 < finalBm.getWidth(); x3++) {
                        finalBm.setPixel(x3, y3, Color.TRANSPARENT);
                    }
                }
                break;
            }

            x2 = wfMid;
            y2++;
        }

        // Get rid of images that we finished with to save memory.
        //2015.09.19:dongfeng:回收之后，回退会出错，无法修改抠图的内容
//        img.recycle();

//        bm.recycle();
        return finalBm;
    }

    /**
     *
     * @param colorARGB
     * @param width
     * @param height
     * @param mode
     * @return
     * 创建特定颜色的bitmap
     */
    public static Bitmap createBitmapFromARGB(int colorARGB, int width, int height, Bitmap mode) {
        int[] argb = new int[width * height];
        int[] originalPixels = new int[width * height];
        mode.getPixels(originalPixels, 0, width, 0, 0, width, height);

        for (int i = 0; i< originalPixels.length; i++) {
            /*Log.d(TAG, "Red: " + Color.red(originalPixels[i]) + " Green; " + Color.green(originalPixels[i])
            + " Blue: " + Color.blue(originalPixels[i]) + " Alpha: " + Color.alpha(originalPixels[i]));*/
            if (Color.red(originalPixels[i]) == 255 && Color.green(originalPixels[i]) == 255
                    && Color.blue(originalPixels[i]) == 0) {
                argb[i] = colorARGB;
            }
            else
                argb[i] = Color.TRANSPARENT;
        }
        return Bitmap.createBitmap(argb, width, height, Bitmap.Config.ARGB_8888);
    }
}