package u.can.i.up.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import cropper.CropImageView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 底图照片修改页面：用户选择照片后，对照片进行裁剪，调整
 */
public class ImageSetActivity extends Activity {

    // Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 20;

    private static final int ROTATE_NINETY_DEGREES = 90;

    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";

    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";

    private static final int ON_TOUCH = 1;

    // Instance variables
    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;

    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap croppedImage;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_set);
        // Initialize components of the app
        final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        //Sets the rotate button
        final ImageButton rotateButton = (ImageButton) findViewById(R.id.Button_rotate);

        ImageButton loadimage = (ImageButton)findViewById(R.id.match_1_close_btn);
        ImageButton crop = (ImageButton)findViewById(R.id.match_1_continue);

        Uri photoUri = getIntent().getParcelableExtra("photoUri");
        cropImageView.setImageUri(photoUri);

        rotateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
            }
        });

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                croppedImage = cropImageView.getCroppedImage();
                BitmapCache.setBitmapcache(croppedImage);
                //设置缩放比例、背景图片移动距离
                float tmpScaleX = ((float)cropImageView.getWidth()) / croppedImage.getWidth();
                float tmpScaleY = ((float)cropImageView.getHeight()) / croppedImage.getHeight();
                if(tmpScaleX <= tmpScaleY){
                    BitmapCache.setBackBmpScale(tmpScaleX);
                    BitmapCache.setBackBmpTranslateX(0);
                    BitmapCache.setBackBmpTranslateY(((float)cropImageView.getHeight() - (float)croppedImage.getHeight() * tmpScaleX) / 2);
                } else{
                    BitmapCache.setBackBmpScale(tmpScaleY);
                    BitmapCache.setBackBmpTranslateX(((float) cropImageView.getWidth() - (float) croppedImage.getWidth() * tmpScaleY) / 2);
                    BitmapCache.setBackBmpTranslateY(0);
                }

                Intent i = new Intent(ImageSetActivity.this, ImageCollocateActivity.class);
                startActivity(i);

            }
        });
    }


//    private class Saveimage extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
////            super.onPreExecute();
////            pDialog = new ProgressDialog(ImageSetActivity.this);
////            pDialog.setMessage("图片加载中...");
////            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String file) {
//            try {
//                    //Write file
//
//                    FileOutputStream stream = getApplication().openFileOutput(file, Context.MODE_PRIVATE);
//                    croppedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    //Cleanup
//                    stream.close();
//                    croppedImage.recycle();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//        }
//    }

}
