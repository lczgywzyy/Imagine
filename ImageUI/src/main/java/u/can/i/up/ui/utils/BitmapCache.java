package u.can.i.up.ui.utils;

import android.graphics.Bitmap;

/**
 * Created by breeze on 2015/7/13.
 */
public class BitmapCache {
    private static Bitmap bitmapcache;
    private static float backBmpScale;
    private static float backBmpTranslateX;
    private static float backBmpTranslateY;
    private static ImageViewImpl_collocate mImageViewImpl_collocate;

    public static void setBitmapcache(Bitmap mbitmap){
        bitmapcache = mbitmap;
    }

    public static Bitmap getBitmapcache(){
        return bitmapcache;
    }

    public static void setBackBmpScale(float scale){
        backBmpScale = scale;
    }

    public static float getBackBmpScale(){
        return backBmpScale;
    }

    public static void setBackBmpTranslateX(float x){
        backBmpTranslateX = x;
    }
    public static float getBackBmpTranslateX(){
        return backBmpTranslateX;
    }
    public static void setBackBmpTranslateY(float y){
        backBmpTranslateY = y;
    }
    public static float getBackBmpTranslateY(){
        return  backBmpTranslateY;
    }
    public static ImageViewImpl_collocate getImageViewImpl_collocate() {
        return mImageViewImpl_collocate;
    }
    public static void setImageViewImpl_collocate(ImageViewImpl_collocate imageViewImpl_collocate) {
        mImageViewImpl_collocate = imageViewImpl_collocate;
    }

}
