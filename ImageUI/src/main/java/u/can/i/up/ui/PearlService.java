package u.can.i.up.ui;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpNormalBean;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.net.HttpManager;
import u.can.i.up.ui.net.HttpNormalManager;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.UtilsDevice;

public class PearlService extends Service {

    private ArrayList<PearlBeans> arrayListPearlBeans=new ArrayList<>();

    private User user;

    public static final int TIME=180000;


    @Override
    public void onCreate() {
        super.onCreate();
        //设置一个定时器，每隔3min开启上传线程；
        getWeakReferenceHandlerTimer.get().post(runnable);
        //注册一个广播，每次生成新的素材(相册)之后发送广播，执行上传素材(相册)线程;

        IntentFilter intentFilter=new IntentFilter();

        intentFilter.addAction(IApplicationConfig.ACTION_PEARL);

        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(broadcastReceiver, intentFilter);

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(IApplicationConfig.DATA_TYPE_SMATERIAL.equals(intent.getType())){
                startPearlBeansUpload();

            }else if(IApplicationConfig.DATA_TYPE_ALBUM.equals(intent.getType())) {

            }else if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){

            }
        }
    };


    WeakReference<Handler> getWeakReferenceHandlerTimer=new WeakReference<Handler>(new Handler(){

    });

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                getWeakReferenceHandlerTimer.get().postDelayed(this, TIME);
                uploadSMaterial();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void updateTMaterial(){

    }

    private void updateCollection(){

    }

    private void uploadTMaterial(){

    }

    private void updateSMaterial(){

    }
    private void uploadSMaterial(){

        if(((IApplication)getApplication()).getIsLogin()) {
            if (user == null) {
                user=((IApplication)getApplication()).getUerinfo();
            }
                if (UtilsDevice.isWifiConnected(PearlService.this)) {
                    startPearlBeansUpload();
                } else {
                    if (IApplication.isUpdateAny) {
                        startPearlBeansUpload();
                    }
                }
            }
        }

    private void startPearlBeansUpload(){
        HttpNormalManager httpNormalManager = HttpNormalManager.getHttpNormalManagerInstance();
        httpNormalManager.boundParameter(getParameterBase());
        httpNormalManager.boundHandler(weakReferenceHandlerCSC.get());
        httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_CHECKSUM);
        httpNormalManager.boundType(HttpManager.HttpType.POST);
        httpNormalManager.execute();

    }


    private void uploadAlbum(){

    }
    private void updateAlbum(){

    }
    private void registerNetBroadcast(){

    }

    private synchronized PearlBeans getPeatBeansNotUpload(){
        arrayListPearlBeans=new ArrayList<>();

        Iterator<PearlBeans> iterator=((IApplication)getApplication()).arrayListPearlBeans.iterator();

        while (iterator.hasNext()){
            PearlBeans pearlBeans=iterator.next();
            if(!pearlBeans.isSynchronized()){
                arrayListPearlBeans.add(pearlBeans);
            }

        }
        if(arrayListPearlBeans.size()>0) {
            return arrayListPearlBeans.get(0);
        }else{
            return null;
        }
    }
   private WeakReference<Handler> weakReferenceHandler=new WeakReference<Handler>(new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case IApplicationConfig.HTTP_NET_SUCCESS:
                    //开启下一个上传流程
                    IHttpNormalBean IHttpNormalBean = (IHttpNormalBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);
                    if (IHttpNormalBean != null && Integer.parseInt(IHttpNormalBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {
                        //上传成功更新数据库

                            if (getPeatBeansNotUpload() != null) {
                                uploadSMaterial();
                            }
                        }
                    break;
                case IApplicationConfig.HTTP_NET_ERROR:
                    break;
                case IApplicationConfig.HTTP_NET_TIMEOUT:
                    break;
            }
        }
    });
    WeakReference<Handler> weakReferenceHandlerCSC=new WeakReference<Handler>(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            switch (msg.what) {
                case IApplicationConfig.HTTP_NET_SUCCESS:
                    //开启一个上传线程
                    IHttpNormalBean IHttpNormalBean = (IHttpNormalBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);


                    if (IHttpNormalBean != null && Integer.parseInt(IHttpNormalBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {

                        HttpNormalManager httpNormalManager = HttpNormalManager.getHttpNormalManagerInstance();
                        HashMap<String, SoftReference<Bitmap>> hashMapImg = new HashMap<>();
                        PearlBeans pearlBeans=getPeatBeansNotUpload();
                        httpNormalManager.boundHandler(weakReferenceHandler.get());
                        if(pearlBeans!=null) {
                            hashMapImg.put("image_file", new SoftReference<>(IBitmapCache.getBitMapCache(PearlService.this.getApplicationContext()).loadBitmapLocal(pearlBeans.getMD5())));
                            httpNormalManager.boundImage(hashMapImg);
                            HashMap<String, String> hashMap = getParameterBase();
                            hashMap.put("csc", IHttpNormalBean.getData());
                            hashMap.put("suffix", "png");
                            hashMap.put("name", pearlBeans.getName());
                            hashMap.put("category", "2");
                            hashMap.put("material", pearlBeans.getMaterial());
                            hashMap.put("size", pearlBeans.getSize());
                            hashMap.put("weight", pearlBeans.getWeight());
                            hashMap.put("aperture", pearlBeans.getAperture());
                            hashMap.put("price", pearlBeans.getPrice());
                            hashMap.put("description", pearlBeans.getDescription());
                            hashMap.put("url", pearlBeans.getPath());
                            httpNormalManager.boundParameter(hashMap);
                            httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_CREATION_UPLOAD);
                            httpNormalManager.execute();
                        }

                    }
                    break;
                case IApplicationConfig.HTTP_NET_ERROR:
                    break;
                case IApplicationConfig.HTTP_NET_TIMEOUT:
                    break;
            }

        }
    });
    private HashMap<String,String> getParameterBase() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tel", user.getPhoneNumber());
        hashMap.put("eString", user.geteString());
        hashMap.put("tString", user.gettString());
        hashMap.put("tokenString", user.getUserLoginToken());
        return hashMap;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void updateState(){

    }
}
