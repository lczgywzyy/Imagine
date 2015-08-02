package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.utils.IBitmapCache;

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
                getSharedPreferences("setting",0).edit().putInt("START", 1);
                getSharedPreferences("setting",0).edit().commit();
                ((IApplication)getApplication()).psqLiteOpenHelper =new PSQLiteOpenHelper(this);
                ((IApplication)getApplication()).arrayListPearl=((IApplication)getApplication()).psqLiteOpenHelper.getPearls();
                ((IApplication)getApplication()).arrayListTMaterial=((IApplication)getApplication()).psqLiteOpenHelper.getTMaterials();

            } catch (IOException e) {

            }
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


    private  void copyAssetDirToFiles()
            throws IOException {
        File dir = new File( IApplicationConfig.DIRECTORY_MATERIAL);

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

        File of = new File(IApplicationConfig.DIRECTORY_MATERIAL+File.separator + filename);
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







