package u.can.i.up.ui.application;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.utils.UtilsDevice;
import u.can.i.up.ui.beans.Pearl;

/**
 * Created by Pengp on 2015/7/31.
 */
public class IApplication extends Application {


    public PSQLiteOpenHelper psqLiteOpenHelper;

    public ArrayList<Pearl> arrayListPearl=new ArrayList<>();

    public ArrayList<TMaterial> arrayListTMaterial=new ArrayList<>();


    public boolean isLogin(){
            return  false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public IApplication() {
        super();
        createDirectory();


    }

   private boolean createDirectory(){
        if(UtilsDevice.hasExternalStorage()){

            File directoryRoot=new File(IApplicationConfig.DIRECTORY_ROOT);


            File dirctorySMaterial=new File(IApplicationConfig.DIRECTORY_SMATERIAL);

            if(!directoryRoot.exists()){
                directoryRoot.mkdir();
            }
            if(!dirctorySMaterial.exists()){
                dirctorySMaterial.mkdir();
            }
            return true;

        }else {
            Toast.makeText(this,"",Toast.LENGTH_LONG);
            return  false;
        }


    }

}
