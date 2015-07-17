package u.can.i.up.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
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
import java.io.FileInputStream;

import u.can.i.up.ui.R;
import u.can.i.up.ui.customViews.LibiraryRelativeLayout;
import u.can.i.up.ui.factories.FaceConversionUtil;
import u.can.i.up.ui.fragments.*;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_allocate;

/**
 * @author dongfeng
 * @data 2015.06.24
 * @sumary 搭配界面：底图选择完毕，往底图贴素材
 */

public class ImageAllocateActivity extends FragmentActivity {

    private final String TAG = this.getClass().getName();

    ProgressDialog pDialog;
    Bitmap mbitmap;
//    ImageView logoview;
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
        setContentView(R.layout.activity_image_collocate);
//        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.allocate_2_framelayout);
        final ImageViewImpl_allocate imageViewImpl_allocate = (ImageViewImpl_allocate) findViewById(R.id.ImageViewImpl_allocate);
        BitmapCache.setImageViewImpl_allocate(imageViewImpl_allocate);
//        imageViewImpl_allocate.setBackImage();
//        LibiraryRelativeLayout sLibiraryRelativeLayout = (LibiraryRelativeLayout) findViewById(R.id.LibiraryRelativeLayout);
//        sLibiraryRelativeLayout.setmImageViewImpl_allocate(imageViewImpl_allocate);

//        logoview = (ImageView) findViewById(R.id.ivImage2);
        ImageButton setover = (ImageButton)findViewById(R.id.match_2_continue);
        ImageButton closeBtm = (ImageButton)findViewById(R.id.match_1_close_btn);
//        String filename = getIntent().getStringExtra("Imagefile");
//        Bitmap bitmap = (Bitmap) getIntent().getExtras().getParcelable("bitmap");
//        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("Image");
//        Bitmap bitmap = BitmapCache.getBitmapcache();
//        final byte[] byteArray = getIntent().getExtras().getByteArray("Image");
//        final Bitmap image_bmp = BitmapFactory.decodeByteArray(byteArray, 0,
//                byteArray.length);
//        logoview.setImageBitmap(BitmapCache.getBitmapcache());

//        final ImageViewImpl_allocate myView_allocate = new ImageViewImpl_allocate(this, image_bmp);
//        RelativeLayout.LayoutParams lParams52 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
//        mainLayout.addView(myView_allocate,lParams52);



//        Toast.makeText(getApplicationContext(), "导出到ImageViewImpl_10_output_All.png", Toast.LENGTH_SHORT).show();
        closeBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapCache.getImageViewImpl_allocate().turnLastAction();
            }
        });
        setover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap mypic = imageViewImpl_allocate.saveBitmapAll();
                BitmapCache.setBitmapcache(mypic);
//                Intent i = new Intent(view.getContext(), ShareActivity.class);
//                i.putExtra("picture", bytepicture);
//                startActivity(i);
                startActivity(new Intent(view.getContext(), ShareActivity.class));
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil.getInstace().getFileText(getApplication());
            }
        }).start();
        initView();


        //异步任务加载图片
//        Loadimage loadimage = new Loadimage();
//        loadimage.execute(filename);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        ImageViewImpl_allocate imageViewImpl_allocate = (ImageViewImpl_allocate) findViewById(R.id.ImageViewImpl_allocate);
//        LibiraryRelativeLayout sLibiraryRelativeLayout = (LibiraryRelativeLayout) findViewById(R.id.LibiraryRelativeLayout);
//        sLibiraryRelativeLayout.setmImageViewImpl_allocate(imageViewImpl_allocate);
//    }

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
        View view = mLayoutInflater.inflate(R.layout.item_navigator_material_selected, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);

        return view;
    }

//异步任务加载图片
//    private  class Loadimage extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(ImageAllocateActivity.this);
//            pDialog.setMessage("图片加载中...");
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... file) {
//            try {
//                FileInputStream is = getApplication().openFileInput(file[0]);
//                mbitmap = BitmapFactory.decodeStream(is);
//                is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            pDialog.dismiss();
//            logoview.setImageBitmap(mbitmap);
//        }
//    }



}
