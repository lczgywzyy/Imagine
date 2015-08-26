package u.can.i.up.ui.net;

import android.os.Handler;

/**
 * Created by Pengp on 2015/8/26.
 */
public abstract  class HttpUserEditManager<T>  {

    private HttpManager<T> httpManager;

    private Handler handler;

    private final void boundHandler(Handler handler){
        this.handler=handler;
    }




}

