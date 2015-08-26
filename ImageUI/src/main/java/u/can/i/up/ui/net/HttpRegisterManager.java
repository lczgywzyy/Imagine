package u.can.i.up.ui.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpStatus;
import u.can.i.up.ui.beans.IRegisterBean;

/**
 * Created by Pengp on 2015/8/26.
 */
public class HttpRegisterManager extends IHttpManager<IRegisterBean> {

    private Handler handler;

    private static HttpRegisterManager httpRegisterManager;


    @Override
    public void Post(IHttpStatus status) {
        Message message=new Message();
        if(status.getHttpStatus()==200){
            IRegisterBean IRegisterBean =(IRegisterBean)status.getHttpObj();
            message.what=IApplicationConfig.HTTP_NET_SUCCESS;
            Bundle bundle=new Bundle();
            bundle.putSerializable(IApplicationConfig.HTTP_BEAN, IRegisterBean);
            bundle.putString(IApplicationConfig.MESSAGE, IRegisterBean.getMessage());
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
    private  HttpRegisterManager(){
        super(IRegisterBean.class);
        httpRegisterManager.boundUrl(IApplicationConfig.HTTP_URL_REGISTER);

    }
    public static HttpRegisterManager getRegisterHttpInstance(){
        if(httpRegisterManager==null){
            httpRegisterManager=new HttpRegisterManager();
        }
        return httpRegisterManager;
    }
    public void boundHandler(Handler handler){
        this.handler=handler;
    }
    public void boundParameter(HashMap<String,String> hashMapParameter){
        httpRegisterManager.boundParameter(hashMapParameter);
    }


}
