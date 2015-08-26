package u.can.i.up.ui.net;

import android.graphics.Bitmap;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import u.can.i.up.ui.beans.IHttpStatus;

/**
 * Created by Pengp on 2015/8/26.
 */
public  abstract  class IHttpManager<T> {
    private HttpManager<T> httpManager;



    private synchronized void initHttpManager(Class<T> classT){
        if(httpManager!=null) {
            httpManager.cancel(true);
        }
        httpManager=new HttpManager<T>(){

            @Override
            protected void onPostExecute(IHttpStatus s) {
                super.onPostExecute(s);
                Post(s);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Progress(values);
            }
        };
        httpManager.setClassT(classT);

    }

    public IHttpManager(Class<T> classT ){
            initHttpManager(classT);
    }

    public synchronized void execute(){
        if(!httpManager.isCancelled()){
            httpManager.cancel(true);
        }
        httpManager.execute();
    }

    public synchronized void boundType(HttpManager.HttpType httpType){
        this.httpManager.setType(httpType);
    }

    public synchronized void boundParamrter(HashMap<String,String> hashMapParameter){
        this.httpManager.setHashParam(hashMapParameter);
    }

    public synchronized void boundImage(HashMap<String, SoftReference<Bitmap>> imgs){
        this.httpManager.setImgs(imgs);
    }

    public synchronized void boundFiles(File... files){
        this.httpManager.setFiles(files);
    }
    public synchronized void boundUrl(String url){
        this.httpManager.setUrl(url);
    }

    public  abstract void Post(IHttpStatus<T> status);
    public abstract void Progress(Integer... values);
    public synchronized void cancleRequest(){
        httpManager.cancel(true);
        httpManager=null;
    }



}
