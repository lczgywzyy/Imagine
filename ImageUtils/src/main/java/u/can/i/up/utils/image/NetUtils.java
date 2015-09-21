package u.can.i.up.utils.image;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Pengp on 2015/7/31.
 */
public class NetUtils {

    public static boolean checkConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return Boolean.TRUE;
    }

    public static void netlogin(){

    }

    public static void netloginOut(){

    }

    public static void newCheckUpdate(){

    }

    public static void netGetTMaterial(){

    }

    public static void netGetPearls(){

    }

    public static void netUpdateApp(){

    }
    public static void netRawStore(){

    }









}

