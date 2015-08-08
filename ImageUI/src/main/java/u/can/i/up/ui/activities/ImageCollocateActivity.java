package u.can.i.up.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.Pearl;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.fragments.*;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_collocate;

/**
 * @author dongfeng
 * @data 2015.06.24
 * @sumary 搭配界面：底图选择完毕，往底图贴素材
 */

public class ImageCollocateActivity extends FragmentActivity {

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
//    private Class mFragmentArray[] = { Fragment1.class, Fragment2.class,
//            Fragment3.class, Fragment4.class, Fragment5.class,
//            Fragment6.class, Fragment7.class, Fragment8.class, Fragment9.class, Fragment10.class };

    private ArrayList<Pearl> pearlArrayList=new ArrayList<>();

    private ArrayList<TMaterial> tMaterialArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_collocate);
        pearlArrayList=((IApplication)getApplication()).arrayListPearl;

        tMaterialArrayList=((IApplication)getApplication()).arrayListTMaterial;

        final ImageViewImpl_collocate imageViewImpl_collocate = (ImageViewImpl_collocate) findViewById(R.id.ImageViewImpl_allocate);
        BitmapCache.setImageViewImpl_collocate(imageViewImpl_collocate);

        ImageButton setover = (ImageButton)findViewById(R.id.match_2_continue);
        ImageButton closeBtm = (ImageButton)findViewById(R.id.match_1_close_btn);

        closeBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapCache.getImageViewImpl_collocate().turnLastAction();
            }
        });
        setover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap mypic = imageViewImpl_collocate.saveBitmapAll();
//                ShareActivity.exportImageByFinger(mypic);

                BitmapCache.setBitmapcache(mypic);
                startActivity(new Intent(view.getContext(), ShareActivity.class));
            }
        });
        //初始化素材导航资源图片
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FaceConversionUtil.getInstace().getFileText(getApplication());
//            }
//        }).start();
        initView();

        //异步任务加载图片
//        Loadimage loadimage = new Loadimage();
//        loadimage.execute(filename);
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
        int count = tMaterialArrayList.size();
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tMaterialArrayList.get(i).getTMaterialName())
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中

            Bundle bundle=new Bundle();

            mTabHost.addTab(tabSpec, Fragment1.class, bundle);
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
        imageView.setImageBitmap(IBitmapCache.getBitMapCache().getBitmap(null,tMaterialArrayList.get(index).getTMaterialMd()));
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(tMaterialArrayList.get(index).getTMaterialName());

        return view;
    }

//异步任务加载图片
//    private  class Loadimage extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(ImageCollocateActivity.this);
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
