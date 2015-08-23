package u.can.i.up.ui.net;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import java.util.HashMap;

import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.HttpStatus;
import u.can.i.up.ui.beans.User;


/**
 * Created by MZH on 2015/8/23.
 */
public class HttpLoginManager {

    private static HttpLoginManager httpLoginManager;

    private Handler handler;

    private boolean isLogin;

    private IApplication iApplication;

    private HttpLoginManager(){
        httpManager=new HttpManager<User>(IApplicationConfig.HTTP_URL_LOGIN, HttpManager.HttpType.POST);
    }

    public synchronized static HttpLoginManager getHttpLoginManager(HashMap<String,String> hashMapParams,IApplication iApplication){

        if(httpLoginManager==null){
            httpLoginManager=new HttpLoginManager();
        }
        httpLoginManager.setParam(hashMapParams);
        httpLoginManager.setiApplication(iApplication);
        return httpLoginManager;
    }

    public void setiApplication(IApplication iApplication){
        this.iApplication=iApplication;
    }
    private  HttpManager<User> httpManager=new HttpManager<User>(){

        @Override
        protected void onPostExecute(HttpStatus s) {
            super.onPostExecute(s);
            Message message=new Message();
            if(s!=null&&s.getHttpStatus()==200&&s.getHttpObj()!=null&&s.getHttpObj().size()>0){
                //登录成功
                User user=(User)s.getHttpObj().get(0);
                setLoginStatus(user);
                message.what=IApplicationConfig.HTTP_LOGIN_CODE_SUCCESS;
                Bundle bundle=new Bundle();
                bundle.putString("msg", "登录成功");
                message.setData(bundle);
            }else if(s!=null){
                //登录失败
                message.what=IApplicationConfig.HTTP_LOGIN_CODE_SUCCESS;
                Bundle bundle=new Bundle();
                bundle.putString("msg",s.getHttpMsg());
                message.setData(bundle);
            }

            handler.sendMessage(message);
        }
    };

    public void setParam(HashMap<String,String> hashMapParams){
        this.httpManager.setHashParam(hashMapParams);
    }

    public synchronized void  login(){

        httpManager.execute();

    }


    private synchronized void setLoginStatus(User user){
        SharedPreferences preferences=iApplication.getSharedPreferences("auth", Application.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();

        editor.putString("tokens",user.getUserLoginToken());

        editor.putString("username",user.getUserName());

        editor.putString("userphone",user.getPhoneNumber());

        editor.putString("useremail",user.getUserEmail());

        editor.commit();
        setLogin();

    }

    public void bundHandlers(Handler handler){

        this.handler=handler;

    }

    public synchronized boolean isLogin(){
        return isLogin;
    }
    private void setLogin(){
        isLogin=true;
    }

    public synchronized void loginOut(){
        isLogin=false;
    }







}
