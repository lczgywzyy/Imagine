package u.can.i.up.ui.PTRlib.mvc.viewhandler;

import android.view.View;
import android.view.View.OnClickListener;

import u.can.i.up.ui.PTRlib.mvc.IDataAdapter;
import u.can.i.up.ui.PTRlib.mvc.ILoadViewFactory;
import u.can.i.up.ui.PTRlib.mvc.MVCHelper;


public interface ViewHandler {

	/**
	 * 
//	 * @param view
	 * @param adapter
	 * @param loadMoreView
//	 * @param onClickListener
	 * @return 是否有 init ILoadMoreView
	 */
	public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadViewFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener);

	public void setOnScrollBottomListener(View contentView, MVCHelper.OnScrollBottomListener onScrollBottomListener);

}
