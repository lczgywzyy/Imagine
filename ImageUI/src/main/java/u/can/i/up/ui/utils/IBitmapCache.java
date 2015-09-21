package u.can.i.up.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.utils.image.MD5Utils;

/**
 * Created by MZH on 2015/8/1.
 */
public class IBitmapCache {

    public static IBitmapCache BitmapCache=null;

    private Context context;


    private HashMap<String,SoftReference<Bitmap>> cache=new HashMap<>();

    private ReferenceQueue<Bitmap> queue=new ReferenceQueue<>();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private IBitmapCache(){

    }
    public static synchronized IBitmapCache getBitMapCache(Context context){

        if(BitmapCache==null){
            BitmapCache=new IBitmapCache();
            BitmapCache.setContext(context);
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

                   String md5key= MD5Utils.getMD5String(bytesBit);

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

        FileOutputStream fileOutputStream= context.openFileOutput(Md5,
                Context.MODE_PRIVATE);

        fileOutputStream.write(bytes);

        fileOutputStream.flush();

        fileOutputStream.close();

    }

    public Bitmap loadBitmapLocal(String md5) {



    try{
            SoftReference<Bitmap> softReference=new SoftReference<>(BitmapFactory.decodeStream(context.openFileInput(md5)),queue);

          //  String md5key=MD5Utils.getMD5String(Bitmap2Bytes(softReference.get()));

            cache.put(md5, softReference);

            return  softReference.get();
        }catch(Exception e){

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


    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }



    public static  class BitmapAsync extends AsyncTask<String,Integer,Bitmap>{

        private View view;

        private Context context;

        String position;
        public BitmapAsync(View img,Context context) {
            super();
            this.view=img;
            this.context=context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            position=params[2];
            return  IBitmapCache.getBitMapCache(context).getBitmap(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            //匹配不同分辨率的手机,进行一下bitmap的缩放
            Bitmap bitmap=BitmapScale(s, UtilsDevice.dip2px(48),UtilsDevice.dip2px(48));
            if(view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }else if(view instanceof TextView){
                int width=bitmap.getWidth();
                if("bottom".equals(position)){
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(bitmap), null, null);
                }else if("left".equals(position)){
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(bitmap), null, null, null);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



    public static Bitmap BitmapScale(Bitmap bitmap,int scaleMaxWidth,int scaleMaxHeight){



        int bitmap_width_pre = bitmap.getWidth();
        int bitmap_height_pre = bitmap.getHeight();
        Matrix matrixBack =new Matrix();
        float scale_factor=1f;
        //背景位图矩阵缩放
        scale_factor = (float)bitmap_width_pre / (float)scaleMaxWidth > (float)bitmap_height_pre / (float)scaleMaxHeight ? (float)bitmap_width_pre / (float)scaleMaxWidth : (float)bitmap_height_pre / (float)scaleMaxHeight;

        matrixBack.postScale(1 / scale_factor, 1 / scale_factor);

        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrixBack,true);

    }




}
