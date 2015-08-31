package u.can.i.up.ui.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.IHttpNormalBean;
import u.can.i.up.ui.beans.IHttpStatus;

/**
 * Created by MZH on 2015/8/29.
 */
public class HttpNormalManager extends IHttpManager<IHttpNormalBean> {

    private static HttpNormalManager httpNormalManager;

    private Handler handler;

    private HttpNormalManager(){
        super(IHttpNormalBean.class);
    }

    @Override
    public void Post(IHttpStatus<IHttpNormalBean> status) {
        if(handler!=null) {
            Message message = new Message();
            if (status.getHttpStatus() == 200) {
                IHttpNormalBean iChecksumBean = (IHttpNormalBean) status.getHttpObj();
                message.what = IApplicationConfig.HTTP_NET_SUCCESS;
                Bundle bundle = new Bundle();
                bundle.putSerializable(IApplicationConfig.HTTP_BEAN, iChecksumBean);
                bundle.putString(IApplicationConfig.MESSAGE, iChecksumBean.getMessage());
                message.setData(bundle);
                handler.sendMessage(message);

            } else if (status.getHttpStatus() == -1) {
                message.what = IApplicationConfig.HTTP_NET_ERROR;
                Bundle bundle = new Bundle();
                bundle.putString(IApplicationConfig.MESSAGE, IApplicationConfig.HTTP_NET_ERROR_MSG);
                message.setData(bundle);
                handler.sendMessage(message);
            } else {
                message.what = IApplicationConfig.HTTP_NET_TIMEOUT;
                Bundle bundle = new Bundle();
                bundle.putString(IApplicationConfig.MESSAGE, IApplicationConfig.HTTP_NET_TIMEOUT_MSG);
                message.setData(bundle);
                handler.sendMessage(message);
            }
            handler = null;
        }
    }

    @Override
    public void Progress(Integer... values) {

    }

    public static HttpNormalManager getHttpChecksumManagerInstance(){
        if(httpNormalManager ==null){
            httpNormalManager =new HttpNormalManager();
        }
        httpNormalManager.initHttpManager(IHttpNormalBean.class);
        httpNormalManager.boundUrl(IApplicationConfig.HTTP_URL_LOGIN);
        httpNormalManager.boundType(HttpManager.HttpType.POST);
        return httpNormalManager;
    }
    public void boundHandler(Handler handler){
        this.handler=handler;
    }

}
