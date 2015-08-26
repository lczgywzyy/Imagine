package u.can.i.up.ui.net;

import java.util.HashMap;

import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpStatus;
import u.can.i.up.ui.beans.ILoginBean;
import u.can.i.up.ui.beans.User;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Pengp on 2015/8/26.
 */
public class HttpLoginManager extends IHttpManager<ILoginBean>{

    private static HttpLoginManager httpLoginManagerT;


    private Handler handler;


    @Override
    public void Post(IHttpStatus<ILoginBean> status) {
        Message message=new Message();
        if(status.getHttpStatus()==200){
            ILoginBean ILoginBean =(ILoginBean)status.getHttpObj();
            message.what=IApplicationConfig.HTTP_NET_SUCCESS;
            Bundle bundle=new Bundle();
            bundle.putSerializable(IApplicationConfig.HTTP_BEAN, ILoginBean);
            bundle.putString(IApplicationConfig.MESSAGE, ILoginBean.getMessage());
            message.setData(bundle);
            handler.sendMessage(message);

        }else if(status.getHttpStatus()==-1){
            message.what=IApplicationConfig.HTTP_NET_ERROR;
            Bundle bundle=new Bundle();
            bundle.putString(IApplicationConfig.MESSAGE,IApplicationConfig.HTTP_NET_ERROR_MSG );
            handler.sendMessage(message);
        }else {
            message.what=IApplicationConfig.HTTP_NET_TIMEOUT;
            Bundle bundle=new Bundle();
            bundle.putString(IApplicationConfig.MESSAGE,IApplicationConfig.HTTP_NET_TIMEOUT_MSG );
            handler.sendMessage(message);
        }
        handler=null;
    }

    @Override
    public void Progress(Integer... values) {

    }
    private HttpLoginManager(){
        super(ILoginBean.class);
        httpLoginManagerT.boundUrl(IApplicationConfig.HTTP_URL_LOGIN);
    }

    public static HttpLoginManager getHttpLoginManagerTInstance(){
        if(httpLoginManagerT==null){
            httpLoginManagerT=new HttpLoginManager();
        }
        return httpLoginManagerT;
    }
    public void boundHandler(Handler handler){
        this.handler=handler;
    }
    public void boundParameter(HashMap<String,String> hashMapParameter){
        httpLoginManagerT.boundParameter(hashMapParameter);
    }

    public static synchronized void loginOut(IApplication iApplication){
        SharedPreferences preferences=iApplication.getSharedPreferences("auth", Application.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();

        editor.remove("tokens");

        editor.remove("username");

        editor.remove("userphone");

        editor.remove("useremail");

        editor.remove("estring");

        editor.remove("portait");

        editor.remove("usertype");
        editor.remove("tstring");

        editor.commit();
        iApplication.setUerinfo(null);

        iApplication.setIsLogin(false);



    }
    public static synchronized void setLoginStatus(User user,IApplication iApplication){
        SharedPreferences preferences=iApplication.getSharedPreferences("auth", Application.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();

        editor.putString("tokens", user.getUserLoginToken());

        editor.putString("username", user.getUserName());

        editor.putString("userphone", user.getPhoneNumber());

        editor.putString("useremail", user.getUserEmail());

        editor.putString("estring", user.geteString());

        editor.putString("portait", user.getPortrait());

        editor.putString("usertype", user.getUserType());

        editor.putString("tstring", user.gettString());

        editor.commit();
        iApplication.setUerinfo(user);

        iApplication.setIsLogin(true);

    }
}
