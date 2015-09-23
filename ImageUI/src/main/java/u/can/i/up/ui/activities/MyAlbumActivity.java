package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import pulltoRefresh.OnLoadListener;
import pulltoRefresh.OnRefreshListener;
import pulltoRefresh.RefreshGridView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.customViews.MyAlbumGridViewAdapter;
import u.can.i.up.ui.utils.BitmapCache;


public class MyAlbumActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

	private MyAlbumGridViewAdapter myAlBumGridAdapter;
//	private RefreshGridView refreshGridView;
	public static ArrayList<String> imageList = new ArrayList<String>();;
	private Toolbar mToolbar;

	private SwipeRefreshLayout mSwipeLayout;
	private GridView mGridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myalbum);
		initView();
	}

	private void initView(){
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setTitle(R.string.myalbum);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
		mGridView = (GridView)findViewById(R.id.myalbum_gridview);
//		refreshGridView = (RefreshGridView) findViewById(R.id.myalbum_refreshgridview);
//		refreshGridView = new RefreshGridView(this)

		//设置适配器

//		String imageUri = IApplicationConfig.DIRECTORY_BG + File.separator +"bg2.jpg";
//		imageList.add(imageUri);
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_1));
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_2));
		myAlBumGridAdapter = new MyAlbumGridViewAdapter(this, imageList);
		mGridView.setAdapter(myAlBumGridAdapter);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
//		setContentView(refreshGridView);

//		refreshGridView.setOnRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
//				Toast.makeText(getApplicationContext(), "refreshing", Toast.LENGTH_SHORT)
//						.show();
//
//				refreshGridView.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						// 更新数据
//						myAlBumGridAdapter.notifyDataSetChanged();
////                        getPearlBeans();
////                        gridAdapter.notifyDataSetChanged();
//						refreshGridView.refreshComplete();
//					}
//				}, 1500);
//			}
//		});
//
//		// 不设置的话到底部不会自动加载
//		refreshGridView.setOnLoadListener(new OnLoadListener() {
//
//			@Override
//			public void onLoadMore() {
//				Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT)
//						.show();
//
//				refreshGridView.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						myAlBumGridAdapter.notifyDataSetChanged();
////                        datas.add(new Date().toGMTString());
////                        adapter.notifyDataSetChanged();
//						// 加载完后调用该方法
//						refreshGridView.loadCompelte();
//					}
//				}, 1500);
//			}
//		});

	}

	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				myAlBumGridAdapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
			}
		}, 1500);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; goto parent activity.
				this.finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}