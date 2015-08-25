package u.can.i.up.ui.net;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import java.util.HashMap;

import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.HttpStatus;
import u.can.i.up.ui.beans.LoginBean;
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
        //httpManager=new HttpManager<LoginBean>(IApplicationConfig.HTTP_URL_LOGIN, HttpManager.HttpType.POST);
    }

    public synchronized static HttpLoginManager getHttpLoginManager(HashMap<String,String> hashMapParams,IApplication iApplication){

        if(httpLoginManager==null){
            httpLoginManager=new HttpLoginManager();
            httpLoginManager.setHttpTask();
        }else {
            httpLoginManager.httpManager.cancel(true);
            httpLoginManager.setHttpTask();
        }
        httpLoginManager.setParam(hashMapParams);
        httpLoginManager.setiApplication(iApplication);
        return httpLoginManager;
    }
    public synchronized static HttpLoginManager getHttpLoginManagerLoginOut(IApplication iApplication){
        if(httpLoginManager==null){
            httpLoginManager=new HttpLoginManager();

        }else {
        }
        httpLoginManager.setiApplication(iApplication);
        return  httpLoginManager;
    }
    public void setiApplication(IApplication iApplication){
        this.iApplication=iApplication;
    }

    private void setHttpTask(){
        httpManager=new HttpManager<LoginBean>(IApplicationConfig.HTTP_URL_LOGIN, HttpManager.HttpType.POST,LoginBean.class){

            @Override
            protected void onPostExecute(HttpStatus s) {
                super.onPostExecute(s);
                Message message=new Message();
                if(s!=null&&s.getHttpStatus()==200&&s.getHttpObj()!=null&&s.getHttpObj()!=null){
                    //登录成功
                    LoginBean LoginBean=(LoginBean)s.getHttpObj();
                    if(LoginBean.getRetCode().equals("0")) {
                        setLoginStatus(LoginBean.getUserInfo());
                        message.what = IApplicationConfig.HTTP_LOGIN_CODE_SUCCESS;
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "登录成功");
                        message.setData(bundle);
                    }else{
                        message.what=IApplicationConfig.HTTP_LOGIN_CODE_FIAL;
                        Bundle bundle=new Bundle();
                        bundle.putString("msg","登录失败");
                        message.setData(bundle);
                    }
                }else if(s!=null){
                    //登录失败
                    message.what=IApplicationConfig.HTTP_LOGIN_CODE_FIAL;
                    Bundle bundle=new Bundle();
                    bundle.putString("msg",s.getHttpMsg());
                    message.setData(bundle);
                }

                handler.sendMessage(message);
            }
        };
   }
    private  HttpManager<LoginBean> httpManager;

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

        editor.putString("username", user.getUserName());

        editor.putString("userphone",user.getPhoneNumber());

        editor.putString("useremail",user.getUserEmail());

        editor.putString("estring", user.geteString());

        editor.putString("portait", user.getPortrait());

        editor.putString("usertype", user.getUserType());

        editor.putString("tstring",user.gettString());

        editor.commit();
        iApplication.setUerinfo(user);

        iApplication.setIsLogin(true);

    }

    public void bundHandlers(Handler handler){

        this.handler=handler;

    }


    public synchronized void loginOut(){
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







}
