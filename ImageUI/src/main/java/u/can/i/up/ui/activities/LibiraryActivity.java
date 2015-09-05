package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import u.can.i.up.ui.R;
import u.can.i.up.ui.customViews.SlidingTabLayout;
import u.can.i.up.ui.customViews.TabViewPagerAdapter;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 素材库主界面
 */
public class LibiraryActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager viewPager;

    private SlidingTabLayout slidingTabLayout;

//    public static LibiraryActivity newInstance(Bundle bundle)
//    {
//        LibiraryActivity libiraryActivity = new LibiraryActivity();
//
//        if (bundle != null)
//        {
//            libiraryActivity.setArguments(bundle);
//        }
//
//        return libiraryActivity;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libirary);
        initView();
    }

    private void initView(){
        // set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.library);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager(),getApplicationContext()));
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        //自定义下划线颜色
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

    }

//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_libirary, container, false);
//
//
//        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        viewPager.setAdapter(new TabViewPagerAdapter(getFragmentManager(), view.getContext()));
//        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
//        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
//        slidingTabLayout.setDistributeEvenly(true);
//        slidingTabLayout.setViewPager(viewPager);
//
//        //自定义下划线颜色
//        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
//            @Override
//            public int getIndicatorColor(int position) {
//                return Color.WHITE;
//            }
//        });
//        return view;
//    }

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