package u.can.i.up.ui.utils;

import android.graphics.Bitmap;

/**
 * Created by breeze on 2015/7/13.
 */
public class BitmapCache {
    public static Bitmap bitmapcache;

    public static void setBitmapcache(Bitmap mbitmap){
        bitmapcache = mbitmap;
    }

    public static Bitmap getBitmapcache(){
        return bitmapcache;
    }

}
