package u.can.i.up.ui.PTRlib.mvc.viewhandler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import u.can.i.up.ui.PTRlib.mvc.IDataAdapter;
import u.can.i.up.ui.PTRlib.mvc.ILoadViewFactory;
import u.can.i.up.ui.PTRlib.mvc.MVCHelper;
import u.can.i.up.ui.PTRlib.mvc.recyclerview.HFAdapter;
import u.can.i.up.ui.PTRlib.mvc.recyclerview.HFRecyclerAdapter;


public class RecyclerViewHandler implements ViewHandler {

	@Override
	public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadViewFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener) {
		final RecyclerView recyclerView = (RecyclerView) contentView;
		boolean hasInit = false;
		Adapter<?> adapter2 = (Adapter<?>) adapter;
		if (loadMoreView != null) {
			final HFAdapter hfAdapter;
			if (adapter instanceof HFAdapter) {
				hfAdapter = (HFAdapter) adapter;
			} else {
				hfAdapter = new HFRecyclerAdapter(adapter2);
			}
			adapter2 = hfAdapter;
			final Context context = recyclerView.getContext().getApplicationContext();
			loadMoreView.init(new ILoadViewFactory.FootViewAdder() {

				@Override
				public View addFootView(int layoutId) {
					View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
					return addFootView(view);
				}

				@Override
				public View addFootView(View view) {
					hfAdapter.addFooter(view);
					return view;
				}
			}, onClickLoadMoreListener);
			hasInit = true;
		}
		recyclerView.setAdapter(adapter2);
		return hasInit;
	}

	@Override
	public void setOnScrollBottomListener(View contentView, MVCHelper.OnScrollBottomListener onScrollBottomListener) {
		final RecyclerView recyclerView = (RecyclerView) contentView;
		recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
	}

	/**
	 * 滑动监听
	 */
	private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
		private MVCHelper.OnScrollBottomListener onScrollBottomListener;

		public RecyclerViewOnScrollListener(MVCHelper.OnScrollBottomListener onScrollBottomListener) {
			super();
			this.onScrollBottomListener = onScrollBottomListener;
		}

		@Override
		public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
			if (newState == RecyclerView.SCROLL_STATE_IDLE && isScollBottom(recyclerView)) {
				if (onScrollBottomListener != null) {
					onScrollBottomListener.onScorllBootom();
				}
			}
		}

		private boolean isScollBottom(RecyclerView recyclerView) {
			return !isCanScollVertically(recyclerView);
		}

		private boolean isCanScollVertically(RecyclerView recyclerView) {
			if (android.os.Build.VERSION.SDK_INT < 14) {
				return ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight();
			} else {
				return ViewCompat.canScrollVertically(recyclerView, 1);
			}
		}

		@Override
		public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {

		}

	};
}
