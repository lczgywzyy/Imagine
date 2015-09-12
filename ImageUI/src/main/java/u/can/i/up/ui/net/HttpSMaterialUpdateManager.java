package u.can.i.up.ui.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpStatus;
import u.can.i.up.ui.beans.IPearlBeans;
import u.can.i.up.ui.beans.IRegisterBean;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.utils.image.Pearl;

/**
 * Created by Pengp on 2015/9/10.
 */
public class HttpSMaterialUpdateManager  extends IHttpManager<IPearlBeans> {

    private Handler handler;

    private static HttpSMaterialUpdateManager httpSMaterialUpdateManager;


    @Override
    public void Post(IHttpStatus status) {
        Message message=new Message();
        if(status.getHttpStatus()==200){
            IPearlBeans iPearlBeans=(IPearlBeans)status.getHttpObj();
            message.what= IApplicationConfig.HTTP_NET_SUCCESS;
            Bundle bundle=new Bundle();
            bundle.putSerializable(IApplicationConfig.HTTP_BEAN, iPearlBeans);
            bundle.putString(IApplicationConfig.MESSAGE, iPearlBeans.getMessage());
            message.setData(bundle);
            handler.sendMessage(message);

        }else if(status.getHttpStatus()==-1){
            message.what=IApplicationConfig.HTTP_NET_ERROR;
            Bundle bundle=new Bundle();
            bundle.putString(IApplicationConfig.MESSAGE,IApplicationConfig.HTTP_NET_ERROR_MSG );
            message.setData(bundle);
            handler.sendMessage(message);
        }else {
            message.what=IApplicationConfig.HTTP_NET_TIMEOUT;
            Bundle bundle=new Bundle();
            bundle.putString(IApplicationConfig.MESSAGE,IApplicationConfig.HTTP_NET_TIMEOUT_MSG );
            message.setData(bundle);
            handler.sendMessage(message);
        }
        handler=null;
    }

    @Override
    public void Progress(Integer... values) {

    }
    private  HttpSMaterialUpdateManager() {
        super(IPearlBeans.class);
    }
    public static HttpSMaterialUpdateManager getSMaterialUpdateHttpInstance(){
        if(httpSMaterialUpdateManager ==null){
            httpSMaterialUpdateManager =new HttpSMaterialUpdateManager();
        }
        httpSMaterialUpdateManager.initHttpManager(IPearlBeans.class);
        httpSMaterialUpdateManager.boundUrl(IApplicationConfig.HTTP_URL_SMATRIAL_UPDATE);
        httpSMaterialUpdateManager.boundType(HttpManager.HttpType.POST);
        return httpSMaterialUpdateManager;
    }
    public void boundHandler(Handler handler){
        this.handler=handler;
    }
}
