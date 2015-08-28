package u.can.i.up.ui.pulltorefresh.data;

import java.util.ArrayList;


import in.srain.cube.request.CacheAbleRequest;
import in.srain.cube.request.CacheAbleRequestDefaultHandler;
import in.srain.cube.request.FailData;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestData;
import in.srain.cube.views.list.ListPageInfo;
import in.srain.cube.views.list.PagedListDataModel;
import u.can.i.up.ui.pulltorefresh.event.EventCenter;
import u.can.i.up.ui.pulltorefresh.event.ImageListDataEvent;
import u.can.i.up.ui.pulltorefresh.request.API;
import u.can.i.up.ui.pulltorefresh.request.DemoCacheRequest;

public class ImageListDataModel extends PagedListDataModel<ImageListItem> {

    public ImageListDataModel(int numPerPage) {
        mListPageInfo = new ListPageInfo<ImageListItem>(numPerPage);
    }

    @Override
    protected void doQueryData() {

        DemoCacheRequest<ImageListDataEvent> request = new DemoCacheRequest<ImageListDataEvent>(new CacheAbleRequestDefaultHandler<ImageListDataEvent>() {

            @Override
            public ImageListDataEvent processOriginData(JsonData jsonData) {
                JsonData data = jsonData.optJson("data");
                ArrayList<JsonData> rawList = data.optJson("list").toArrayList();

                ArrayList<ImageListItem> imageList = new ArrayList<ImageListItem>();
                for (int i = 0; i < rawList.size(); i++) {
                    ImageListItem item = new ImageListItem(rawList.get(i));
                    imageList.add(item);
                }

                ImageListDataEvent event = new ImageListDataEvent();
                event.imageList = imageList;
                event.hasMore = data.optBoolean("has_more");
                return event;
            }

            @Override
            public void onCacheAbleRequestFinish(ImageListDataEvent data, CacheAbleRequest.ResultType type, boolean outOfDate) {
                setRequestResult(data.imageList, data.hasMore);
                EventCenter.getInstance().post(data);
            }

            @Override
            public void onRequestFail(FailData failData) {
                setRequestFail();
            }
        });

        String cacheKey = "api/image-list-first-page";
        boolean disableCache = mListPageInfo.getStart() != 0;
        request.setCacheTime(3).setCacheKey(cacheKey).setDisableCache(disableCache);

        RequestData requestData = request.getRequestData();
        requestData.addQueryData("start", mListPageInfo.getStart());
        requestData.addQueryData("num", mListPageInfo.getNumPerPage());
        request.getRequestData().setRequestUrl(API.API_IMAGE_LIST);
        request.send();
    }
}
