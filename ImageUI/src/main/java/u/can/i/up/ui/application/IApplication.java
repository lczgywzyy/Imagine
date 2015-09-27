package u.can.i.up.ui.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.ArrayList;

import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.RequestCacheManager;
import u.can.i.up.ui.BuildConfig;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.image.DemoDuiTangImageReSizer;
import u.can.i.up.ui.image.PtrImageLoadHandler;
import u.can.i.up.ui.utils.UtilsDevice;

/**
 * Created by Pengp on 2015/7/31.
 */
public class IApplication extends Application {


    public PSQLiteOpenHelper psqLiteOpenHelper;

    public ArrayList<PearlBeans> arrayListPearlBeans =new ArrayList<>();

    public ArrayList<TMaterial> arrayListTMaterial=new ArrayList<>();

    public String SMSAPPKEY;

    public String SMSAPPSECRET;

    private User uerinfo;

    private boolean isLogin;

    public static int Picker_type=3;;

    public static boolean isUpdateAny=false;


    public boolean isLogin(){
            return  false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //PTR 照片墙下拉刷新框架初始化
//        ImageLoaderFactory.setDefaultImageReSizer(DemoDuiTangImageReSizer.getInstance());
//        ImageLoaderFactory.setDefaultImageLoadHandler(new PtrImageLoadHandler());
//
//        String dir = "request-cache";
//        // ImageLoaderFactory.init(this);
//        RequestCacheManager.init(this, dir, 1024 * 10, 1024 * 10);
//        Cube.onCreate(this);
        //初始化我的相册浏览页
        initImageLoader(getApplicationContext());

    }

    public IApplication() {
        super();
        createDirectory();


    }

   private boolean createDirectory(){
        if(UtilsDevice.hasExternalStorage()){

            File directoryRoot=new File(IApplicationConfig.DIRECTORY_ROOT);


            File dirctoryBG=new File(IApplicationConfig.DIRECTORY_BG);

            if(!directoryRoot.exists()){
                directoryRoot.mkdir();
            }
            if(!dirctoryBG.exists()){
                dirctoryBG.mkdir();
            }
            return true;

        }else {
            Toast.makeText(this,"",Toast.LENGTH_LONG);
            return  false;
        }


    }


    public synchronized User getUerinfo() {
        return uerinfo;
    }

    public synchronized void setUerinfo(User uerinfo) {
        this.uerinfo = uerinfo;
    }

    public synchronized void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    public synchronized boolean getIsLogin() {
        return  isLogin;
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new WeakMemoryCache()).discCacheSize(8 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(builder.build());
    }

}
