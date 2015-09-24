package u.can.i.up.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.customViews.ImagePagerAdapter;
import u.can.i.up.ui.utils.IBitmapCache;
import uk.co.senab.photoview.PhotoView;

/**
 * @author dongfeng
 * @data 2015.09.24
 * @sumary 我的相册图片展示界面：展示选中的图片
 */

public class MyAlbumDisplayActivity extends AppCompatActivity {
    /**
     * 图片列表
     */
    public static final String EXTRA_IMAGES = "extra_images";

    /**
     * 位置
     */
    public static final String EXTRA_INDEX = "extra_index";

    /**
     * 图片列表数据源
     */
    private ArrayList<String> mDatas = new ArrayList<String>();

    /**
     * 进入到该界面时的索引
     */
    private int mPageIndex = 0;

    /**
     * 图片适配器
     */
    private ImagePagerAdapter mImageAdapter = null;

    /**
     * viewpager
     */
    private ViewPager mViewPager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myalbum_display);
        setBar();
        mViewPager = (ViewPager)findViewById(R.id.image_vp);
//        mViewPager.setAdapter(new SamplePagerAdapter());
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IMAGES)) {
            mDatas = intent.getStringArrayListExtra(EXTRA_IMAGES);
            mPageIndex = intent.getIntExtra(EXTRA_INDEX, 0);
            mImageAdapter = new ImagePagerAdapter(mDatas);
            mViewPager.setAdapter(mImageAdapter);
            mViewPager.setCurrentItem(mPageIndex);
        }
    }

    private void setBar(){
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.library_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
