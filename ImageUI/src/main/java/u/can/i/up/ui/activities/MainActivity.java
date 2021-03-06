package u.can.i.up.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.ui.beans.User;
import u.can.i.up.ui.fragments.HomeFragment;
import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.UtilsMiscellaneous;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 抽屉容器页面：构建抽屉和主界面容器
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private final static double sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO = 9d/16d;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout_AccountView;
    private LinearLayout mNavDrawerEntriesRootView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LinearLayout mScrimInsetsFrameLayout;
    private FrameLayout mFrameLayout_Home ;
//    private FrameLayout mFrameLayout_Libirary;
//    private FrameLayout mFrameLayout_MyAlbum;
    private FrameLayout mFrameLayout_Setup;
    private TextView mTextView_AccountDisplayName, mTextView_AccountEmail;
    private TextView mTextView_Home, mTextView_Libirary, mTextView_Setup;

    private CircleImageView headIcon;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        initialise();
    }
    @Override
    protected void onResume(){
        super.onResume();
        initDrawer();
    }

    /**
     * Bind, create and set up the resources
     */
    private void initialise()
    {
        // Toolbar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Layout resources
        mFrameLayout_AccountView = (FrameLayout) findViewById(R.id.navigation_drawer_account_view);
        mNavDrawerEntriesRootView = (LinearLayout)findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayout_Home = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_home);
//        mFrameLayout_Libirary = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_library);
//        mFrameLayout_MyAlbum = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_myalbum);
        mFrameLayout_Setup = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_setup);


        mTextView_AccountDisplayName = (TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
//        mTextView_AccountEmail = (TextView) findViewById(R.id.navigation_drawer_account_information_email);

        mTextView_Home = (TextView) findViewById(R.id.navigation_drawer_items_textView_home);
//        mTextView_Libirary = (TextView) findViewById(R.id.navigation_drawer_items_textView_libirary);
        mTextView_Setup = (TextView) findViewById(R.id.navigation_drawer_items_textView_setup);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (LinearLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                )
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = IApplicationConfig.DeviceWidth -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO);

//         Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Home.setOnClickListener(this);
//        mFrameLayout_Libirary.setOnClickListener(this);
//        mFrameLayout_MyAlbum.setOnClickListener(this);
        mFrameLayout_Setup.setOnClickListener(this);

        // Set the first item as selected for the first time
        getSupportActionBar().setTitle(R.string.toolbar_title_home);


        mFrameLayout_Home.setSelected(true);

        // Create the first fragment to be shown
        Bundle bundle = new Bundle();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_content_frame, HomeFragment.newInstance(bundle))
                .commit();
    }
    private void initDrawer(){
        IApplication iApplication=(IApplication)getApplication();
        LinearLayout linLoginShow=(LinearLayout)findViewById(R.id.login_show);
        TextView txtname=(TextView)findViewById(R.id.navigation_drawer_account_information_display_name);
        headIcon=(CircleImageView)findViewById(R.id.navigation_drawer_user_account_picture_profile);
        Button btn=(Button)findViewById(R.id.btn_login);
        if(iApplication.getIsLogin()){
            User user=iApplication.getUerinfo();
            linLoginShow.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
            if(TextUtils.isEmpty(user.getUserName())) {
                txtname.setText(user.getPhoneNumber());
            }else{
                txtname.setText(user.getUserName());
            }

            //设置用户头像
            if(!TextUtils.isEmpty(user.getPortrait())){
                String imguri=user.getPortrait();
                String[] uriArray=imguri.split("/");
                if(uriArray.length>1) {
                    String md5 = uriArray[uriArray.length - 1].replaceAll(".png", "");
                    IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(headIcon,MainActivity.this.getApplicationContext());

                    bitmapAsync.execute(imguri, md5,"img");
                }
            }


        }else {
            linLoginShow.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

//select drawer
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.navigation_drawer_account_view)
        {
//            mDrawerLayout.closeDrawer(Gravity.START);
            startActivity(new Intent(view.getContext(), PersonalActivity.class));
            // If the user is signed in, go to the profile, otherwise show sign up / sign in
        }
        else
        {
            if (!view.isSelected())
            {
                onRowPressed((FrameLayout) view);

                switch (view.getId())
                {
                    case R.id.navigation_drawer_items_list_linearLayout_home:
                    {
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_home));
                        }


                        view.setSelected(true);

                        Bundle bundle = new Bundle();

                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, HomeFragment.newInstance(bundle))
                                .commit();
                        break;
                    }

//                    case R.id.navigation_drawer_items_list_linearLayout_library:
//                    {
//                        if (getSupportActionBar() != null)
//                        {
//                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_libirary));
//                        }
//
//
//                        view.setSelected(true);
//
//                        Bundle bundle = new Bundle();
//
//                        // Insert the fragment by replacing any existing fragment
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_activity_content_frame, LibiraryActivity.newInstance(bundle))
//                                .commit();
//                        break;
//                    }

//                    case R.id.navigation_drawer_items_list_linearLayout_myalbum:
//                    {
//                        if (getSupportActionBar() != null)
//                        {
//                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_myalbum));
//                        }
//                        view.setSelected(true);
//
//                        Bundle bundle = new Bundle();
//
//                        // Insert the fragment by replacing any existing fragment
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_activity_content_frame, MyAlbumFragment.newInstance(bundle))
//                                .commit();
//                        break;
//                    }
                    case R.id.navigation_drawer_items_list_linearLayout_setup:
                        // Start intent to send an email
                        startActivity(new Intent(view.getContext(), SettingActivity.class));
                        break;

                    default:
                        break;
                }
            }
            else
            {
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        }
    }

//    exit prompt
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Set up the rows when any is pressed
     *
     * @param pressedRow is the pressed row in the drawer
     */
    private void onRowPressed(FrameLayout pressedRow)
    {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry))
        {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++)
            {
                View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry)
                {
                    if (currentView == pressedRow)
                    {
                        currentView.setSelected(true);
                    }
                    else
                    {
                        currentView.setSelected(false);
                    }
                }
            }
        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }
}
