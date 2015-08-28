package u.can.i.up.ui.pulltorefresh.request;

import in.srain.cube.request.CacheAbleRequest;
import in.srain.cube.request.CacheAbleRequestHandler;
import u.can.i.up.ui.pulltorefresh.request.DemoRequestManager;

public class DemoCacheRequest<T> extends CacheAbleRequest<T> {

    public DemoCacheRequest() {
        super();
    }

    public DemoCacheRequest(CacheAbleRequestHandler<T> handler) {
        super(handler);
    }

    @Override
    public void prepareRequest() {
        DemoRequestManager.getInstance().prepareRequest(this);
        super.prepareRequest();
    }
}
