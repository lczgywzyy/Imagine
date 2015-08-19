package u.can.i.up.ui.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import cropper.CropImageView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.fragments.AdjustImageSetFragment;
import u.can.i.up.ui.fragments.CropImageSetFragment;
import u.can.i.up.ui.fragments.RotateImageSetFragment;
import u.can.i.up.ui.fragments.RulerImageSetFragment;
import u.can.i.up.ui.utils.BitmapCache;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 底图照片修改页面：用户选择照片后，对照片进行裁剪，旋转，调整，比例尺
 */
public class ImageSetActivity extends Fragment {

    // Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 20;

    private static final int ROTATE_NINETY_DEGREES = 90;

    // Instance variables
    Bitmap croppedImage;

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
    private Class mFragmentArray[] = { CropImageSetFragment.class, RotateImageSetFragment.class,
            AdjustImageSetFragment.class, RulerImageSetFragment.class };
    /**
     * 存放图片数组
     *
     */
    private int mImageArray[] = { R.drawable.icon_cut, R.drawable.icon_rotate,
            R.drawable.icon_adjust, R.drawable.icon_ruler};

    /**
     * 选修卡文字
     *
     */
    private String mTextArray[] = { "剪裁", "旋转", "调整", "比例尺" };

    public static ImageSetActivity newInstance(Bundle bundle)
    {
        ImageSetActivity imageSetActivity = new ImageSetActivity();

        if (bundle != null)
        {
            imageSetActivity.setArguments(bundle);
        }

        return imageSetActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_image_set, container, false);
        mLayoutInflater = LayoutInflater.from(view.getContext());
        // 找到TabHost
        mTabHost = (FragmentTabHost) view.findViewById(R.id.imageset_tabhost);
        mTabHost.setup(getActivity(), getFragmentManager(), R.id.imageset_realtabcontent);

        // Initialize components of the app
//        final  CropImageView cropImageView = (CropImageView) view.findViewById(R.id.CropImageView);
//        BitmapCache.setCropImageView(cropImageView);
//        ImageButton loadimage = (ImageButton)view.findViewById(R.id.match_1_close_btn);
//        ImageButton crop = (ImageButton)view.findViewById(R.id.match_1_continue);
//
//        Uri photoUri = getActivity().getIntent().getParcelableExtra("photoUri");
//        cropImageView.setImageUri(photoUri);

//        rotateButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
//            }
//        });

//        crop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                croppedImage = cropImageView.getCroppedImage();
//                BitmapCache.setBitmapcache(croppedImage);
//                //设置缩放比例、背景图片移动距离
//                float tmpScaleX = ((float) cropImageView.getWidth()) / croppedImage.getWidth();
//                float tmpScaleY = ((float) cropImageView.getHeight()) / croppedImage.getHeight();
//                if (tmpScaleX <= tmpScaleY) {
//                    BitmapCache.setBackBmpScale(tmpScaleX);
//                    BitmapCache.setBackBmpTranslateX(0);
//                    BitmapCache.setBackBmpTranslateY(((float) cropImageView.getHeight() - (float) croppedImage.getHeight() * tmpScaleX) / 2);
//                } else {
//                    BitmapCache.setBackBmpScale(tmpScaleY);
//                    BitmapCache.setBackBmpTranslateX(((float) cropImageView.getWidth() - (float) croppedImage.getWidth() * tmpScaleY) / 2);
//                    BitmapCache.setBackBmpTranslateY(0);
//                }
//
//                Intent i = new Intent(view.getContext(), ImageCollocateActivity.class);
//                startActivity(i);
//
//            }
//        });
        initView();
        return view;

    }

    /**
     * 初始化组件
     */
    private void initView() {



        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
//            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(null)
//                    .setIndicator(getTabItemView(i));
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

}
