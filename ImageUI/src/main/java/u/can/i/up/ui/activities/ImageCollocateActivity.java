package u.can.i.up.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeanGroup;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.fragments.*;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_collocate;
import u.can.i.up.utils.image.MD5Utils;

/**
 * @author dongfeng
 * @data 2015.06.24
 * @sumary 搭配界面：底图选择完毕，往底图贴素材
 */

public class ImageCollocateActivity extends FragmentActivity implements View.OnClickListener  {

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

    private ArrayList<PearlBeans> pearlBeansArrayList =new ArrayList<>();

    private ArrayList<TMaterial> tMaterialArrayList = new ArrayList<>();

    private ImageViewImpl_collocate imageViewImpl_collocate;
    private ImageButton closebtn;
    private ImageButton continuebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_collocate);
        initView();
        initTabView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        pearlBeansArrayList =((IApplication)getApplication()).arrayListPearlBeans;
        tMaterialArrayList=((IApplication)getApplication()).arrayListTMaterial;
        imageViewImpl_collocate = (ImageViewImpl_collocate) findViewById(R.id.ImageViewImpl_allocate);
        if("materialBuild".equals(getIntent().getAction())){
            imageViewImpl_collocate.setBackBitmap(Bitmap.createBitmap(1000,1000, Bitmap.Config.ALPHA_8));
        }
        BitmapCache.setImageViewImpl_collocate(imageViewImpl_collocate);
        continuebtn = (ImageButton)findViewById(R.id.image_collocate_continue);
        closebtn = (ImageButton)findViewById(R.id.image_collocate_close_btn);
        continuebtn.setOnClickListener(this);
        closebtn.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initTabView(){
        mLayoutInflater = LayoutInflater.from(this);
        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = tMaterialArrayList.size();
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf(tMaterialArrayList.get(i).getTMaterialId()))
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            Bundle bundle=new Bundle();
            mTabHost.addTab(tabSpec, CollocateTabFragment.class, bundle);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
        //增加自组串珠素材
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf("-1"))
                .setIndicator(getTabItemViewMat());
        // 将Tab按钮添加进Tab选项卡中
        Bundle bundle=new Bundle();
        mTabHost.addTab(tabSpec, CollocateTabFragment.class, bundle);
        // 设置Tab按钮的背景
        mTabHost.getTabWidget().getChildAt(tMaterialArrayList.size())
                .setBackgroundResource(R.drawable.selector_tab_background);

    }

    private View getTabItemViewMat(){
        View view = mLayoutInflater.inflate(R.layout.item_navigator_material_selected, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.icon_beiyun);
        //
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText("组合素材");
        return view;
    }
    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.item_navigator_material_selected, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        //
        IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(imageView,ImageCollocateActivity.this);
        bitmapAsync.execute(null, tMaterialArrayList.get(index).getTMaterialMd(), "img");
       // imageView.setImageBitmap(IBitmapCache.getBitMapCache().getBitmap(null, tMaterialArrayList.get(index).getTMaterialMd()));
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(tMaterialArrayList.get(index).getTMaterialName());
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_collocate_continue:
            {
                if("materialBuild".equals(getIntent().getAction())){

                    Bitmap mypic = imageViewImpl_collocate.saveBitmapMaterial();

                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

                    mypic.compress(Bitmap.CompressFormat.PNG,90,byteArrayOutputStream);

                    PearlBeanGroup pearlBeanGroup=new PearlBeanGroup();

                    byte[] bytes= byteArrayOutputStream.toByteArray();
                    String md5= MD5Utils.getMD5String(bytes);
                    mypic.recycle();
                    try {
                        FileOutputStream os = this.openFileOutput(md5, Context.MODE_PRIVATE);
                        os.write(bytes);
                        os.close();
                        byteArrayOutputStream.close();

                        pearlBeanGroup.setMD5(md5);

                        pearlBeanGroup.setType(1);

                        ((IApplication)getApplication()).psqLiteOpenHelper.addPearlGroup(pearlBeanGroup);
                        ((IApplication)getApplication()).arrayListPearlBeanGroups.add(pearlBeanGroup);
                        Toast.makeText(getApplicationContext(),"素材保存成功",Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"素材保存失败",Toast.LENGTH_LONG).show();
                    }



                }else {
                    Bitmap mypic = imageViewImpl_collocate.saveBitmapAll();
                    BitmapCache.setBitmapcache(mypic);
                    startActivity(new Intent(ImageCollocateActivity.this, ShareActivity.class));
                }
                break;
            }
            case R.id.image_collocate_close_btn:
            {
//                BitmapCache.getImageViewImpl_collocate().turnLastAction();
                finish();
                break;
            }
            default:
                break;
        }
    }


}
