package u.can.i.up.ui.pulltorefresh.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.util.CLog;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.list.PagedListViewDataAdapter;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreGridViewContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import u.can.i.up.ui.R;
import u.can.i.up.ui.fragments.DemoTitleBaseFragment;
import u.can.i.up.ui.pulltorefresh.data.ImageListDataModel;
import u.can.i.up.ui.pulltorefresh.data.ImageListItem;
import u.can.i.up.ui.pulltorefresh.event.DemoSimpleEventHandler;
import u.can.i.up.ui.pulltorefresh.event.ErrorMessageDataEvent;
import u.can.i.up.ui.pulltorefresh.event.EventCenter;
import u.can.i.up.ui.pulltorefresh.event.ImageListDataEvent;
import u.can.i.up.ui.pulltorefresh.viewholders.ImageListItemMiddleImageViewHolder;

public class MyAlbumFragment1 extends TitleBaseFragment {

    private PagedListViewDataAdapter<ImageListItem> mAdapter;
    private ImageListDataModel mDataModel;
    private ImageLoader mImageLoader;
    private PtrFrameLayout mPtrFrameLayout;
    private GridViewWithHeaderAndFooter mGridView;

    public static MyAlbumFragment1 newInstance(Bundle bundle)
    {
        MyAlbumFragment1 myAlbumFragment = new MyAlbumFragment1();

        if (bundle != null)
        {
            myAlbumFragment.setArguments(bundle);
        }

        return myAlbumFragment;
    }
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHeaderTitle("test");



        // set up data
        mDataModel = new ImageListDataModel(8);

        mAdapter = new PagedListViewDataAdapter<ImageListItem>();
        mAdapter.setViewHolderClass(this, ImageListItemMiddleImageViewHolder.class, mImageLoader);
        mAdapter.setListPageInfo(mDataModel.getListPageInfo());

        // set up views
        final View view = inflater.inflate(R.layout.fragment_load_more_grid_view_test, null);
        // pull to refresh
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.load_more_grid_view_ptr_frame_test);
        mImageLoader = ImageLoaderFactory.create(view.getContext());

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mGridView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mDataModel.queryFirstPage();
            }
        });

        mGridView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.load_more_grid_view_test);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CLog.d("grid-view", "onItemClick: %s %s", position, id);
            }
        });

        // header place holder
        View headerMarginView = new View(view.getContext());
        headerMarginView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LocalDisplay.dp2px(20)));
        mGridView.addHeaderView(headerMarginView);

        // load more container
        final LoadMoreGridViewContainer loadMoreContainer = (LoadMoreGridViewContainer) view.findViewById(R.id.load_more_grid_view_container_test);
        loadMoreContainer.setAutoLoadMore(false);
        loadMoreContainer.useDefaultHeader();

        // binding view and data
        mGridView.setAdapter(mAdapter);
        loadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });

        // data
        EventCenter.bindContainerAndHandler(this, new DemoSimpleEventHandler() {

            public void onEvent(ImageListDataEvent event) {

                // ptr refresh complete
                mPtrFrameLayout.refreshComplete();

                // load more complete
                loadMoreContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());

                mAdapter.notifyDataSetChanged();
            }

            public void onEvent(ErrorMessageDataEvent event) {
                loadMoreContainer.loadMoreError(0, event.message);
            }

        }).tryToRegisterIfNot();

        // auto load data
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(false);
            }
        }, 150);

        return view;
    }

}
