package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import u.can.i.up.ui.R;
import u.can.i.up.ui.factories.FaceConversionUtil;
import u.can.i.up.ui.fragments.*;
import u.can.i.up.ui.utils.ImageViewImpl_allocate;

/**
 * @author dongfeng
 * @data 2015.06.24
 * @sumary 搭配界面：底图选择完毕，往底图贴素材
 */

public class ImageAllocateActivity extends FragmentActivity {

    /**
     * FragmentTabhost
     */
    private FragmentTabHost mTabHost;

    /**
     * 布局填充器
     *
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Fragment数组界面
     *
     */
    private Class mFragmentArray[] = { Fragment1.class, Fragment2.class,
            Fragment3.class, Fragment4.class, Fragment5.class,
            Fragment6.class, Fragment7.class, Fragment8.class, Fragment9.class, Fragment10.class };
    /**
     * 存放图片数组
     *
     */
    private int mImageArray[] = { R.drawable.icon_fotou,
            R.drawable.icon_fota, R.drawable.icon_beiyun,
            R.drawable.icon_qiazi, R.drawable.incon_dizizhu, R.drawable.icon_jishuqi, R.drawable.icon_xiangzhu,
            R.drawable.icon_shengjie, R.drawable.icon_sanzhu, R.drawable.icon_gepian };

    /**
     * 选修卡文字
     *
     */
    private String mTextArray[] = { "佛头", "佛塔", "背云", "卡子", "弟子珠","计数器", "项珠", "绳结", "散珠", "隔片" };
    /**
     *
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_allocate);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.allocate_2_framelayout);


//        final View controlsView = findViewById(R.id.fullscreen_content_controls);
//        final View contentView = findViewById(R.id.ivImage);
//        ImageView logoview = (ImageView) findViewById(R.id.ivImage2);
        ImageButton setover = (ImageButton)findViewById(R.id.match_2_continue);
        final byte[] byteArray = getIntent().getExtras().getByteArray("picture");
        final Bitmap image_bmp = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
//        logoview.setImageBitmap(image_bmp);

        final ImageViewImpl_allocate myView_allocate = new ImageViewImpl_allocate(this, image_bmp);
        RelativeLayout.LayoutParams lParams52 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        mainLayout.addView(myView_allocate,lParams52);



//        Toast.makeText(getApplicationContext(), "导出到ImageViewImpl_10_output_All.png", Toast.LENGTH_SHORT).show();

        setover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap mypic = myView_allocate.saveBitmapAll();


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mypic.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytepicture = baos.toByteArray();

                Intent i = new Intent(view.getContext(), ShareActivity.class);
                i.putExtra("picture", bytepicture);
                startActivity(i);
//                startActivity(new Intent(view.getContext(), ShareActivity.class));
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil.getInstace().getFileText(getApplication());
            }
        }).start();
        initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);

        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_allocate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
