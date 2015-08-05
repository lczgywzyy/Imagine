package u.can.i.up.ui.utils;

/**
 * Created by Pengp on 2015/8/3.
 */
public class StringUtils {

    public static boolean  isEmpty(String str){

        if(str==null||"".equals(str)||"null".equals(str)){
            return true;
        }
        return  false;

    }

}


