package u.can.i.up.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by MZH on 2015/8/1.
 */
public class IBitmapCache {

    public static IBitmapCache BitmapCache=null;

    private Bitmap bitmap;

    private HashMap<String,SoftReference<Bitmap>> cache=new HashMap<>();

    private ReferenceQueue<Bitmap> queue=new ReferenceQueue<>();



    private IBitmapCache(){

    }
    public static synchronized IBitmapCache  getBitMapCache(){

        if(BitmapCache==null){
            BitmapCache=new IBitmapCache();
        }
        return  BitmapCache;

    }

    /**ͼƬ�������**/
    public Bitmap loadBitmapHttp(String uri){

        return null;
    }

    public Bitmap loadBitmapLocal(String path){

        File file=new File(path.toString());

        if(file.exists()){
            bitmap=BitmapFactory.decodeFile(path);

            if(bitmap==null) return null;

            SoftReference<Bitmap> softReference=new SoftReference<>(bitmap,queue);

            cache.put(path,softReference);

            return softReference.get();
        }
        return  null;
    }
    public Bitmap getBitmap(String uri){

        if(uri==null){
            return  null;
        }
        if(!cache.containsKey(uri)){
            if(uri.startsWith("http")||uri.startsWith("https")){
                return loadBitmapHttp(uri);
            }else {
              return loadBitmapLocal(uri);
            }
        }else{
            SoftReference<Bitmap> softReference=cache.get(uri);
            Bitmap bitmap=softReference.get();
            if(bitmap==null){
                cache.remove(uri);
                return loadBitmapLocal(uri);
            }
        }
        return null;
    }








}
