package u.can.i.up.ui.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpStatus;
import u.can.i.up.ui.beans.IRegisterBean;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.utils.image.Pearl;

/**
 * Created by Pengp on 2015/9/10.
 */
public class HttpSMaterialUpdateManager  extends IHttpManager<PearlBeans> {

    private Handler handler;

    private static HttpSMaterialUpdateManager httpSMaterialUpdateManager;


    @Override
    public void Post(IHttpStatus status) {
        Message message=new Message();
        if(status.getHttpStatus()==200){
            IRegisterBean IRegisterBean =(IRegisterBean)status.getHttpObj();
            message.what= IApplicationConfig.HTTP_NET_SUCCESS;
            Bundle bundle=new Bundle();
            bundle.putSerializable(IApplicationConfig.HTTP_BEAN, IRegisterBean);
            bundle.putString(IApplicationConfig.MESSAGE, IRegisterBean.getMessage());
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
        super(PearlBeans.class);
    }
    public static HttpSMaterialUpdateManager getRegisterHttpInstance(){
        if(httpSMaterialUpdateManager ==null){
            httpSMaterialUpdateManager =new HttpSMaterialUpdateManager();
        }
        httpSMaterialUpdateManager.initHttpManager(PearlBeans.class);
        httpSMaterialUpdateManager.boundUrl(IApplicationConfig.HTTP_URL_SMATRIAL_UPDATE);
        httpSMaterialUpdateManager.boundType(HttpManager.HttpType.POST);
        return httpSMaterialUpdateManager;
    }
    public void boundHandler(Handler handler){
        this.handler=handler;
    }
}
