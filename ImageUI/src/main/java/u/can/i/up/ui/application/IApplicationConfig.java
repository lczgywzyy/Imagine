package u.can.i.up.ui.application;

import android.os.Environment;

import java.io.File;

/**
 * Created by MZH on 2015/8/1.
 */
public class IApplicationConfig {


    public static final String DIRECTORY_ROOT=Environment.getExternalStorageDirectory()+ File.separator+"PearlString";

    public static  final String DIRECTORY_SMATERIAL=DIRECTORY_ROOT+File.separator+"SMaterial";


    public static final String HTTP_URL_BASE="http://45.55.12.70";

    public static final String HTTP_URL_LOGIN="";

    public static final int HTTP_REGISTER_CODE_SUCCESS=0;

    public static final int HTTP_REGISTER_CODE_CP_ERROR=102;

    public static final int HTTP_REGISTER_CODE_EX_ERROR=101;

    public static final int HTTP_LOGIN_CODE_SUCCESS=0;

    public static final int HTTP_LOGIN_CODE_FIAL=-1;










}
