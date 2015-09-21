package u.can.i.up.utils.image;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by MZH on 2015/8/31.
 */
public class ShareUtils {

    public static String getMetaDataValue(String name,Context context) {

        Object value = null;

        PackageManager packageManager = context.getPackageManager();

        ApplicationInfo applicationInfo;

        try {

            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);

            if (applicationInfo != null && applicationInfo.metaData != null) {

                value = applicationInfo.metaData.get(name);

            }

        } catch (PackageManager.NameNotFoundException e) {

            throw new RuntimeException(

                    "Could not read the name in the manifest file.", e);

        }

        if (value == null) {

            throw new RuntimeException("The name '" + name

                    + "' is not defined in the manifest file's meta data.");

        }

        return value.toString();

    }
}
