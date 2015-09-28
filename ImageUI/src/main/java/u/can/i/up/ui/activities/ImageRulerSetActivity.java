package u.can.i.up.ui.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_ruler;

/**
 * @author dongfeng
 * @data 2015.09.26
 * @sumary 比例尺设置页面：用户对图片进行裁剪，旋转，调整后，在此界面设置比例尺
 */
public class ImageRulerSetActivity extends Activity implements View.OnClickListener
{

    // Instance variables
    private ImageViewImpl_ruler rulerImageView;
    private ImageButton closebtn;
    private ImageButton continuebtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_ruler_set);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //         Initialize components of the app
        rulerImageView = (ImageViewImpl_ruler) findViewById(R.id.imageruler_set);
        closebtn = (ImageButton)findViewById(R.id.imageset_close_btn);
        continuebtn = (ImageButton)findViewById(R.id.imageset_continue);

        rulerImageView.setImageBitmap(BitmapCache.getBitmapcache());
        continuebtn.setOnClickListener(this);
        closebtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageset_continue:
            {
                Intent i = new Intent(ImageRulerSetActivity.this, ImageCollocateActivity.class);
                startActivity(i);
                break;
            }
            case R.id.imageset_close_btn:
            {
//                SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
//                        .setTitle("注意")
//                        .setMessage("放弃本次操作，你的编辑将丢失")
//                        .setPositiveButtonText("确定")
//                        .setNegativeButtonText("取消")
//                        .setRequestCode(REQUEST_SIMPLE_DIALOG)
//                        .show();
                finish();
//                BitmapCache.getBitmapcacheruler().recycle();
                break;
            }
            default:
                break;
        }
    }

//    // ISimpleDialogCancelListener
//
//    @Override
//    public void onCancelled(int requestCode) {
//        switch (requestCode) {
//            case REQUEST_SIMPLE_DIALOG:
//                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//    // ISimpleDialogListener
//
//    @Override
//    public void onPositiveButtonClicked(int requestCode) {
//        if (requestCode == REQUEST_SIMPLE_DIALOG) {
//            finish();
//        }
//    }
//
//    @Override
//    public void onNegativeButtonClicked(int requestCode) {
//        if (requestCode == REQUEST_SIMPLE_DIALOG) {
//            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onNeutralButtonClicked(int requestCode) {
//        if (requestCode == REQUEST_SIMPLE_DIALOG) {
//            Toast.makeText(this, "no selected", Toast.LENGTH_SHORT).show();
//        }
//    }

}