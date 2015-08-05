package u.can.i.up.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;

/**
 * Created by MZH on 2015/8/1.
 */
public class IBitmapCache {

    public static IBitmapCache BitmapCache=null;


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

    public Bitmap loadBitmapHttp(String uri){

        //网络获取图片数据

        if(uri.startsWith("http")||uri.startsWith("https")){

        }else{
            uri= IApplicationConfig.HTTP_URL_BASE+uri;
        }
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(uri).openConnection();

            urlConnection.connect();

            switch (urlConnection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:


                    InputStream is=urlConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

                    byte[] bytes=new byte[1024];
                    int m;
                    while ((m=is.read(bytes))>0){
                        byteArrayOutputStream.write(bytes, 0, m);
                    }
                    byte[] bytesBit=byteArrayOutputStream.toByteArray();

                    SoftReference<Bitmap> softReference=new SoftReference<Bitmap>(BitmapFactory.decodeByteArray(bytesBit,0,bytesBit.length),queue);

                   String md5key=MD5Utils.getMD5String(bytesBit);

                    saveBitmapLocal(md5key,bytesBit);
                    is.close();
                    return  softReference.get();
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return  null;
                case HttpURLConnection.HTTP_FORBIDDEN:
                    return  null;
                default:
                    return  null;
            }


        }catch (Exception e){
            return null;
        }

    }

    private void saveBitmapLocal(String Md5,byte[] bytes) throws IOException{

        File file=new File(IApplicationConfig.DIRECTORY_SMATERIAL+File.separator+Md5+".png");

        if(!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fileOutputStream=new FileOutputStream(file);

        fileOutputStream.write(bytes);

        fileOutputStream.flush();

        fileOutputStream.close();

    }

    public Bitmap loadBitmapLocal(String md5){

        String path=IApplicationConfig.DIRECTORY_SMATERIAL+File.separator+md5+".png";
        File file=new File(path);

        if(file.exists()){


            SoftReference<Bitmap> softReference=new SoftReference<>(BitmapFactory.decodeFile(path),queue);

          //  String md5key=MD5Utils.getMD5String(Bitmap2Bytes(softReference.get()));

            cache.put(md5, softReference);

            return  softReference.get();
        }
        return  null;
    }



    public Bitmap getBitmap(String uri,String md5){

        if(!cache.containsKey(md5)){

            if(loadBitmapLocal(md5)==null){
                    return loadBitmapHttp(uri);
            }else{
                return cache.get(md5).get();
            }

        }else{
            SoftReference<Bitmap> softReference=cache.get(md5);
            Bitmap bitmap=softReference.get();
            if(bitmap==null){
                cache.remove(md5);
                return loadBitmapLocal(md5);
            }
            return bitmap;
        }
    }


    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }






}
