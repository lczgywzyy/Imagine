package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.ILoginBean;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.net.HttpLoginManager;
import u.can.i.up.ui.net.HttpManager;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 启动闪屏页面
 */
public class SplashActivity extends Activity {
    // Splash screen timer
    String now_playing, earned;
    private static int SPLASH_TIME_OUT = 1000;
   private SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences("setting", 0);
        setContentView(R.layout.activity_splash);
        setParams();

        if(this.getSharedPreferences("setting",0).getInt("START", 0)==0) {
            try {
                copyAssetDirToFiles();
                copyAssetDb();
                SharedPreferences sharedPreferences=getSharedPreferences("setting",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("START", 1);
                editor.commit();
                ((IApplication) getApplication()).psqLiteOpenHelper = new PSQLiteOpenHelper(this);
                ((IApplication) getApplication()).arrayListPearlBeans = ((IApplication) getApplication()).psqLiteOpenHelper.getPearls();
                ((IApplication) getApplication()).arrayListTMaterial = ((IApplication) getApplication()).psqLiteOpenHelper.getTMaterials();

            } catch (IOException e) {

            }
        }else{
            ((IApplication) getApplication()).psqLiteOpenHelper = new PSQLiteOpenHelper(this);
            ((IApplication) getApplication()).arrayListPearlBeans = ((IApplication) getApplication()).psqLiteOpenHelper.getPearls();
            ((IApplication) getApplication()).arrayListTMaterial = ((IApplication) getApplication()).psqLiteOpenHelper.getTMaterials();
        }
        autologin();


    }

    private void setParams(){
        IApplicationConfig.Scale=this.getResources().getDisplayMetrics().density;

        IApplicationConfig.DeviceHeight=this.getResources().getDisplayMetrics().heightPixels;

        IApplicationConfig.DeviceWidth=this.getResources().getDisplayMetrics().widthPixels;
    }

    private void autologin(){
        SharedPreferences sharedPreferences=getSharedPreferences("auth",Activity.MODE_PRIVATE);

        String tel=sharedPreferences.getString("userphone",null);
        String eString=sharedPreferences.getString("estring",null);
        String tString=sharedPreferences.getString("tstring",null);
        String tokenString=sharedPreferences.getString("tokens",null);

        if(!(TextUtils.isEmpty(tel)||TextUtils.isEmpty(eString)||TextUtils.isEmpty(tString)||TextUtils.isEmpty(tokenString))){
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("tel",tel);
            hashMap.put("eString",eString);
            hashMap.put("tString",tString);
            hashMap.put("tokenString",tokenString);
            WeakReference<Handler> handlerWeakReference=new WeakReference<Handler>(new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    Bundle bundle=msg.getData();


                    switch (msg.what) {
                        case IApplicationConfig.HTTP_NET_SUCCESS:
                            //登录成功

                            ILoginBean ILoginBean = (ILoginBean) bundle.getSerializable(IApplicationConfig.HTTP_BEAN);

                            if (ILoginBean != null && Integer.parseInt(ILoginBean.getRetCode()) == IApplicationConfig.HTTP_CODE_SUCCESS) {

                                HttpLoginManager.setLoginStatus(ILoginBean.getData(), (IApplication) getApplication());
                            }
                            break;
                        case IApplicationConfig.HTTP_NET_ERROR:
                            //登录失败
                            break;
                        case IApplicationConfig.HTTP_NET_TIMEOUT:
                            break;
                    }
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });
            HttpLoginManager httpLoginManager=HttpLoginManager.getHttpLoginManagerTInstance();
            httpLoginManager.boundHandler(handlerWeakReference.get());
            httpLoginManager.boundParameter(hashMap);
            httpLoginManager.boundUrl(IApplicationConfig.HTTP_URL_Q_LOGIN);
            httpLoginManager.execute();

        }else{
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }


    }

    private  void copyAssetDirToFiles()
            throws IOException {
        AssetManager assetManager = getResources().getAssets();
        String[] children = assetManager.list("Material");
        for (String child : children) {
            copyAssetFileToFiles(child);
        }
    }

    private  void copyAssetFileToFiles(String filename)
            throws IOException {
        InputStream is = getResources().getAssets().open("Material"+File.separator+filename);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        File of = new File(IApplicationConfig.DIRECTORY_SMATERIAL +File.separator + filename);
        of.createNewFile();
        FileOutputStream os = new FileOutputStream(of);
        os.write(buffer);
        os.close();
    }

    private  void copyAssetDb()throws IOException{

        InputStream is=getResources().getAssets().open("pearls.db");
        (new File("/data/data/u.can.i.up.ui/databases")).mkdir();
        File pearldbf = new File("/data/data/u.can.i.up.ui/databases/pearls.db");
        pearldbf.createNewFile();
        FileOutputStream pearldb=new FileOutputStream(pearldbf);


        byte[] buffer = new byte[1024];
        int length;
        while((length=is.read(buffer))>0){
            pearldb.write(buffer,0,length);
        }
        pearldb.close();
        is.close();
    }





    }







