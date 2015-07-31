package u.can.i.up.ui.application;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.HashMap;

import u.can.i.up.ui.dbs.PSQLiteOpenHelper;

/**
 * Created by Pengp on 2015/7/31.
 */
public class IApplication extends Application {


    private PSQLiteOpenHelper psqLiteOpenHelper;



    public boolean isLogin(){
            return  false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public IApplication() {
        super();
    }

    public boolean readBitmap(Bitmap bitmap){

        return true;
    }

    public boolean readBitmap(String path){

        return false;

    }

    public boolean readPearls(){
        return  false;
    }

    public boolean readUsers(){
        return false;
    }

}
