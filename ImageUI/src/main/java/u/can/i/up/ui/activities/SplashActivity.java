package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
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
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    private void testUpload(){

        HashMap<String,String> param=new HashMap<>();
        param.put("name","Pengp");
        param.put("password","password");

        File[] files=new File[10];

        for(int i=1;i<11;i++){
            File file=new File(IApplicationConfig.DIRECTORY_SMATERIAL+File.separator+"emoji_"+String.valueOf(i)+".png");
            files[i-1]=file;

        }

        HttpManager<PearlBeans> http=new HttpManager<>("http://192.168.106.1:39915/getPearls.ashx", HttpManager.HttpType.POST,param,PearlBeans.class,null,files);

        http.execute();


    }

    private void testGet(){
        HashMap<String,String> param=new HashMap<>();
        HttpManager<PearlBeans> http=new HttpManager<>("http://45.55.12.70/AppImageFetch?username=%E6%9D%8E%E6%89%BF%E6%B3%BD&email=xyq547133@163.com&tString=MjAxNS0wOC0wMSAyMjo1MToyMA==&eString=af1de97da311c37feaecde33ba87c6b0&tokenString=TWpBeE5TMHdPQzB3TVNBeU1qbzFNVG95TUE9PWFmMWRlOTdkYTMxMWMzN2ZlYWVjZGUzM2JhODdjNmIw&category=1&type=1", HttpManager.HttpType.GET,param,PearlBeans.class);
        http.execute();
    }



    private void testPost(){
        HashMap<String,String> param=new HashMap<>();
        param.put("name","Pengp");
        param.put("password","password");
        HttpManager<PearlBeans> http=new HttpManager<>("http://192.168.106.1:39915/getPearls.ashx", HttpManager.HttpType.POST,param,PearlBeans.class);
        http.execute();
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







