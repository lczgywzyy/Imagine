package u.can.i.up.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.list.ViewHolderBase;
import in.srain.cube.views.list.ViewHolderCreator;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import u.can.i.up.ui.R;
import u.can.i.up.ui.customViews.MyAlbumGridViewAdapter;
import u.can.i.up.ui.utils.DemoRequestData;


public class MyAlbumPTRActivity extends AppCompatActivity {
	private static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;
	private ImageLoader mImageLoader;
	private ListViewDataAdapter<JsonData> mAdapter;
	private PtrClassicFrameLayout mPtrFrame;
	private Toolbar mToolbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myalbumpull);
		initView();
	}

	private void initView(){
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setTitle(R.string.myalbum);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mImageLoader = ImageLoaderFactory.create(getApplicationContext());
		final GridView gridListView = (GridView)findViewById(R.id.rotate_header_grid_view);
		gridListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= 0) {
					final String url = mAdapter.getItem(position).optString("pic");
					if (!TextUtils.isEmpty(url)) {
						Toast.makeText(getApplicationContext(),url, Toast.LENGTH_SHORT);
//						getContext().pushFragmentToBackStack(MaterialStyleFragment.class, url);
					}
				}
			}
		});

		mAdapter = new ListViewDataAdapter<JsonData>(new ViewHolderCreator<JsonData>() {
			@Override
			public ViewHolderBase<JsonData> createViewHolder(int position) {
				return new ViewHolder();
			}
		});
		gridListView.setAdapter(mAdapter);

		mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				updateData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		// the following are default settings
		mPtrFrame.setResistance(1.7f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
		mPtrFrame.setDurationToClose(200);
		mPtrFrame.setDurationToCloseHeader(1000);
		// default is false
		mPtrFrame.setPullToRefresh(false);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
		mPtrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				// mPtrFrame.autoRefresh();
			}
		}, 100);
		// updateData();
		setupViews(mPtrFrame);

	}

	protected void setupViews(final PtrClassicFrameLayout ptrFrame) {

	}

	protected void updateData() {

		DemoRequestData.getImageList(new RequestFinishHandler<JsonData>() {
			@Override
			public void onRequestFinish(final JsonData data) {
				mPtrFrame.postDelayed(new Runnable() {
					@Override
					public void run() {
						mAdapter.getDataList().clear();
						mAdapter.getDataList().addAll(data.optJson("data").optJson("list").toArrayList());
						mPtrFrame.refreshComplete();
						mAdapter.notifyDataSetChanged();
					}
				}, 0);
			}
		});
	}

	private class ViewHolder extends ViewHolderBase<JsonData> {

		private CubeImageView mImageView;

		@Override
		public View createView(LayoutInflater inflater) {
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.with_grid_view_item_image_list_grid, null);
			mImageView = (CubeImageView) view.findViewById(R.id.with_grid_view_item_image);
			mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(sGirdImageSize, sGirdImageSize);
			mImageView.setLayoutParams(lyp);
			return view;
		}

		@Override
		public void showData(int position, JsonData itemData) {
			String url = itemData.optString("pic");
			mImageView.loadImage(mImageLoader, url);
		}
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