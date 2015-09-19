package u.can.i.up.ui.application;

import android.os.Environment;

import java.io.File;

/**
 * Created by MZH on 2015/8/1.
 */
public class IApplicationConfig {


   public static final String DIRECTORY_ROOT=Environment.getExternalStorageDirectory()+ File.separator+"PearlString";

    public static  final String DIRECTORY_BG=DIRECTORY_ROOT+File.separator+"BGS";


    public static final String HTTP_URL_BASE="http://123.57.33.13";

    public static final String HTTP_URL_LOGIN="http://123.57.33.13/AppLoginVerify";

    public static final String HTTP_URL_Q_LOGIN="http://123.57.33.13/AppQLoginVerify";

    public static final String HTTP_URL_REGISTER="http://123.57.33.13/AppRegister";

    public static final String HTTP_URL_CHECKSUM="http://123.57.33.13/AppChecksum";

    public static final String HTTP_URL_PORTRAIT="http://123.57.33.13/AppPortraitEdit";

    public static final String HTTP_URL_PARAMETERS="http://123.57.33.13/AppInforEdit";
    public static final String HTTP_URL_SMATRIAL_UPDATE="http://123.57.33.13/AppImageFetch";

    public static final int HTTP_CODE_SUCCESS=0;

    public static final int HTTP_NET_TIMEOUT=0x10;

    public static final int HTTP_NET_ERROR=0x11;

    public static final int HTTP_NET_SUCCESS=0x12;

    public static final String HTTP_BEAN="Bean";

    public static final String HTTP_NET_ERROR_MSG="请检查网络连接..";

    public static final String HTTP_NET_TIMEOUT_MSG="服务器连接超时,请稍候重试..";

    public static final String MESSAGE="message";

    public static float Scale=0f;

    public static int DeviceWidth=0;

    public static int DeviceHeight=0;










}
