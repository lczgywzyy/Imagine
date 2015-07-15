package u.can.i.up.ui.utils;

import android.graphics.Bitmap;

/**
 * Created by breeze on 2015/7/13.
 */
public class BitmapCache {
    public static Bitmap bitmapcache;
    public static float backBmpScale;
    public static float backBmpTranslateX;
    public static float backBmpTranslateY;

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
}
