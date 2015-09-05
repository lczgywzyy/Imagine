package u.can.i.up.ui.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import cropper.CropImageView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.fragments.AdjustImageSetFragment;
import u.can.i.up.ui.fragments.RotateImageSetFragment;
import u.can.i.up.ui.fragments.RulerImageSetFragment;
import u.can.i.up.ui.utils.BitmapCache;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 底图照片修改页面：用户选择照片后，对照片进行裁剪，旋转，调整，比例尺
 */
public class ImageSetActivity extends FragmentActivity implements View.OnClickListener{

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
    private Class mFragmentArray[] = { RotateImageSetFragment.class,
            AdjustImageSetFragment.class, RulerImageSetFragment.class };
    /**
     * 存放图片数组
     *
     */
    private int mImageArray[] = { R.drawable.icon_rotate,
            R.drawable.icon_adjust, R.drawable.icon_ruler};

    /**
     * 选修卡文字
     *
     */
    private String mTextArray[] = { "旋转", "调整", "比例尺" };
    // Instance variables
    private Bitmap croppedImage;
    private CropImageView cropImageView;
    ImageButton closebtn;
    ImageButton continuebtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_set);
        initView();
        initTabView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //         Initialize components of the app
        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        closebtn = (ImageButton)findViewById(R.id.imageset_close_btn);
        continuebtn = (ImageButton)findViewById(R.id.imageset_continue);
        continuebtn.setOnClickListener(this);
        Uri photoUri = getIntent().getParcelableExtra("photoUri");
//        String photoUri = getIntent().getStringExtra("photoUri");
//        cropImageView.setImageBitmap(BitmapFactory.decodeFile(photoUri));
        cropImageView.setImageUri(photoUri);
    }


    /**
     * 初始化Tab
     */
    private void initTabView() {

        mLayoutInflater = LayoutInflater.from(this);
        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.imageset_tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.imageset_realtabcontent);

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
        View view = mLayoutInflater.inflate(R.layout.item_navigator_imageset_selected, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageset_continue:
            {
                croppedImage = cropImageView.getCroppedImage();
                BitmapCache.setBitmapcache(croppedImage);
                //设置缩放比例、背景图片移动距离
                float tmpScaleX = ((float) cropImageView.getWidth()) / croppedImage.getWidth();
                float tmpScaleY = ((float) cropImageView.getHeight()) / croppedImage.getHeight();
                if (tmpScaleX <= tmpScaleY) {
                    BitmapCache.setBackBmpScale(tmpScaleX);
                    BitmapCache.setBackBmpTranslateX(0);
                    BitmapCache.setBackBmpTranslateY(((float) cropImageView.getHeight() - (float) croppedImage.getHeight() * tmpScaleX) / 2);
                } else {
                    BitmapCache.setBackBmpScale(tmpScaleY);
                    BitmapCache.setBackBmpTranslateX(((float) cropImageView.getWidth() - (float) croppedImage.getWidth() * tmpScaleY) / 2);
                    BitmapCache.setBackBmpTranslateY(0);
                }
                Intent i = new Intent(ImageSetActivity.this, ImageCollocateActivity.class);
                startActivity(i);
                break;
            }
            default:
        }
    }
}