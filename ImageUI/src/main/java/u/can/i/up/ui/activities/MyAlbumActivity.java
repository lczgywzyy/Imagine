package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import pulltoRefresh.OnLoadListener;
import pulltoRefresh.OnRefreshListener;
import pulltoRefresh.RefreshGridView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.customViews.MyAlbumGridViewAdapter;
import u.can.i.up.ui.utils.BitmapCache;


public class MyAlbumActivity extends AppCompatActivity {

	private MyAlbumGridViewAdapter myAlBumGridAdapter;
	private RefreshGridView refreshGridView;
	private ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();;
	private Toolbar mToolbar;

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
		refreshGridView = (RefreshGridView) findViewById(R.id.myalbum_refreshgridview);
//		refreshGridView = new RefreshGridView(this);
		//准备数据
//		BitmapCache.getAlbumImageList().add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_1));
//		BitmapCache.getAlbumImageList().add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_2));
//		BitmapCache.getAlbumImageList().add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_3));
//		imageList = BitmapCache.getAlbumImageList();
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_1));
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_2));
//		imageList.add(BitmapFactory.decodeResource(getResources(), R.drawable.myalbum_demo_3));
		//设置适配器
		myAlBumGridAdapter = new MyAlbumGridViewAdapter(this, imageList);
		refreshGridView.setAdapter(myAlBumGridAdapter);
//		setContentView(refreshGridView);

		refreshGridView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(getApplicationContext(), "refreshing", Toast.LENGTH_SHORT)
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
				Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT)
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