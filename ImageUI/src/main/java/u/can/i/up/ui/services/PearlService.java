package u.can.i.up.ui.services;

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
import u.can.i.up.ui.beans.AlbumBean;
import u.can.i.up.ui.beans.IHttpNormalBean;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.net.HttpAlbumUploadManager;
import u.can.i.up.ui.net.HttpManager;
import u.can.i.up.ui.net.HttpNormalManager;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.UtilsDevice;

public class PearlService extends Service {



    private User user;

    public static final int TIME=180000;

    private PearlBeans pearlBeansUploading;

    private AlbumBean albumBeanUploading;


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

                uploadSMaterial();

            }else if(IApplicationConfig.DATA_TYPE_ALBUM.equals(intent.getType())) {

               uploadAlbum();

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

                uploadAlbum();

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
        if(getPeatBeansNotUpload()!=null) {
            HttpNormalManager httpNormalManager = HttpNormalManager.getHttpNormalManagerInstance();
            httpNormalManager.boundParameter(getParameterBase());
            httpNormalManager.boundHandler(initHandlerCSCT());
            httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_CHECKSUM);
            httpNormalManager.boundType(HttpManager.HttpType.POST);
            httpNormalManager.execute();
        }

    }

    private void startAlbumUpload(){

        if(getAlbumBeanNotUpload()!=null) {
            HttpNormalManager httpNormalManager = HttpNormalManager.getHttpNormalManagerInstance();
            httpNormalManager.boundParameter(getParameterBase());
            httpNormalManager.boundHandler(initHandlerCSCA());
            httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_CHECKSUM);
            httpNormalManager.boundType(HttpManager.HttpType.POST);
            httpNormalManager.execute();
        }

    }


    private void uploadAlbum(){
        if(((IApplication)getApplication()).getIsLogin()) {
            if (user == null) {
                user=((IApplication)getApplication()).getUerinfo();
            }
            if (UtilsDevice.isWifiConnected(PearlService.this)) {
                startAlbumUpload();
            } else {
                if (IApplication.isUpdateAny) {
                    startAlbumUpload();
                }
            }
        }
    }
    private void updateAlbum(){

    }
    private void registerNetBroadcast(){

    }

    private synchronized PearlBeans getPeatBeansNotUpload(){

        Iterator<PearlBeans> iterator=((IApplication)getApplication()).arrayListPearlBeans.iterator();

        while (iterator.hasNext()){
            PearlBeans pearlBeans=iterator.next();
            if(!pearlBeans.isSynchronized()){
                pearlBeansUploading=pearlBeans;
            }

        }
        return pearlBeansUploading;
    }

    private synchronized  AlbumBean getAlbumBeanNotUpload(){
        Iterator<AlbumBean> iterator=((IApplication)getApplication()).arrayListAlbumBeans.iterator();

        while (iterator.hasNext()){
            AlbumBean albumBean=iterator.next();
            if(!albumBean.isSynchronizd()){
                albumBeanUploading=albumBean;
            }

        }



        return albumBeanUploading;

    }

    private Handler initHandlerTMaterialUpload(){
        WeakReference<Handler> weakReferenceHandler=new WeakReference<Handler>(new Handler(){

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
                            updateTMateialState();
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
        return weakReferenceHandler.get();
    }



    private Handler initHandlerAlbumUpdate(){
        //更新


        return null;
    }

    private Handler initHandlerAlbumUpload(){
        //上传
        WeakReference<Handler> weakReferenceHandler=new WeakReference<Handler>(new Handler(){

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
                            updateAlbumState();
                            if (getAlbumBeanNotUpload() != null) {
                                uploadAlbum();
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
        return weakReferenceHandler.get();

    }

    private Handler initHandlerCSCT(){
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
                           // PearlBeans pearlBeans=getPeatBeansNotUpload();
                            httpNormalManager.boundHandler(initHandlerTMaterialUpload());
                            if(pearlBeansUploading!=null) {
                                hashMapImg.put("image_file", new SoftReference<>(IBitmapCache.getBitMapCache(PearlService.this.getApplicationContext()).loadBitmapLocal(pearlBeansUploading.getMD5())));
                                httpNormalManager.boundImage(hashMapImg);
                                HashMap<String, String> hashMap = getParameterBase();
                                hashMap.put("csc", IHttpNormalBean.getData());
                                hashMap.put("suffix", "png");
                                hashMap.put("name", pearlBeansUploading.getName());
                                hashMap.put("category", "2");
                                hashMap.put("material", pearlBeansUploading.getMaterial());
                                hashMap.put("size", pearlBeansUploading.getSize());
                                hashMap.put("weight", pearlBeansUploading.getWeight());
                                hashMap.put("aperture", pearlBeansUploading.getAperture());
                                hashMap.put("price", pearlBeansUploading.getPrice());
                                hashMap.put("description", pearlBeansUploading.getDescription());
                                hashMap.put("url", pearlBeansUploading.getPath());
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
        return weakReferenceHandlerCSC.get();
    }

    private Handler initHandlerCSCA(){
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

                            HttpAlbumUploadManager httpNormalManager = HttpAlbumUploadManager.getHttpNormalManagerInstance();
                            HashMap<String, SoftReference<Bitmap>> hashMapImg = new HashMap<>();
                            httpNormalManager.boundHandler(initHandlerAlbumUpload());
                            if(albumBeanUploading!=null) {
                                hashMapImg.put("image_file", new SoftReference<>(IBitmapCache.loadAlbumsSD(albumBeanUploading.getMD5())));
                                httpNormalManager.boundImage(hashMapImg);
                                HashMap<String, String> hashMap = getParameterBase();
                                hashMap.put("csc", IHttpNormalBean.getData());
                                hashMap.put("suffix", "jpg");
                                hashMap.put("type",String.valueOf(albumBeanUploading.getType()));

                                httpNormalManager.boundParameter(hashMap);
                                httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_ALBUM_UPLOAD);
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
        return weakReferenceHandlerCSC.get();
    }

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

    private  void updateTMateialState(){
        pearlBeansUploading.setIsSynchronized(true);
        PSQLiteOpenHelper psqLiteOpenHelper=new PSQLiteOpenHelper(this);

        psqLiteOpenHelper.updatePearl(pearlBeansUploading);

        pearlBeansUploading=null;

    }
    private void updateAlbumState(){
        albumBeanUploading.setIsSynchronizd(true);

        ((IApplication)getApplication()).psqLiteOpenHelper.updateAlbum(albumBeanUploading);

        albumBeanUploading=null;

    }


}
