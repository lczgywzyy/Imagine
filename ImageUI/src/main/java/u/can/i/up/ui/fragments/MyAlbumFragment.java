package u.can.i.up.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import pulltoRefresh.OnLoadListener;
import pulltoRefresh.OnRefreshListener;
import pulltoRefresh.RefreshGridView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.customViews.MyAlbumGridViewAdapter;
import u.can.i.up.ui.utils.BitmapCache;


public class MyAlbumFragment extends Fragment {

	private MyAlbumGridViewAdapter myAlBumGridAdapter;
	private RefreshGridView refreshGridView;
	private ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public static MyAlbumFragment newInstance(Bundle bundle)
	{
		MyAlbumFragment myAlbumFragment = new MyAlbumFragment();

		if (bundle != null)
		{
			myAlbumFragment.setArguments(bundle);
		}

		return myAlbumFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{

		refreshGridView = new RefreshGridView(getActivity());
		//准备数据
		BitmapCache.getAlbumImageList().add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_1));
		BitmapCache.getAlbumImageList().add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_2));
		BitmapCache.getAlbumImageList().add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_3));
		imageList = BitmapCache.getAlbumImageList();
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_1));
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_2));
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_3));
		//设置适配器
		myAlBumGridAdapter = new MyAlbumGridViewAdapter(getActivity(), imageList);
		refreshGridView.setAdapter(myAlBumGridAdapter);

		refreshGridView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(getActivity().getApplicationContext(), "refreshing", Toast.LENGTH_SHORT)
						.show();

				refreshGridView.postDelayed(new Runnable() {

					@Override
					public void run() {
						// 更新数据
						myAlBumGridAdapter.notifyDataSetChanged();
//                        getPearlBeans();
//                        gridAdapter.notifyDataSetChanged();
						refreshGridView.refreshComplete();
					}
				}, 1500);
			}
		});

		// 不设置的话到底部不会自动加载
		refreshGridView.setOnLoadListener(new OnLoadListener() {

			@Override
			public void onLoadMore() {
				Toast.makeText(getActivity().getApplicationContext(), "loading", Toast.LENGTH_SHORT)
						.show();

				refreshGridView.postDelayed(new Runnable() {

					@Override
					public void run() {
						myAlBumGridAdapter.notifyDataSetChanged();
//                        datas.add(new Date().toGMTString());
//                        adapter.notifyDataSetChanged();
						// 加载完后调用该方法
						refreshGridView.loadCompelte();
					}
				}, 1500);
			}
		});
		return refreshGridView;
	}


}