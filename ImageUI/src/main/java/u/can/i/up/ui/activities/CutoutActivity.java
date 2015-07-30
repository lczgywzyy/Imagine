package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.ImageViewImpl_cutout;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 抠图界面：素材抠取
 */

public class CutoutActivity extends Activity {
    private static final String TAG = "u.can.i.up.imagine." + CutoutActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutout);
        ImageButton setover = (ImageButton)findViewById(R.id.cutout_1_setover);
        final ImageButton square_paint = (ImageButton)findViewById(R.id.square_paint);
        final ImageButton circle_paint = (ImageButton)findViewById(R.id.circle_paint);
        final ImageButton square_eraze = (ImageButton)findViewById(R.id.square_eraze);
        final ImageButton circle_eraze = (ImageButton)findViewById(R.id.circle_eraze);

        final ImageViewImpl_cutout imageViewImpl_cutout = (ImageViewImpl_cutout) findViewById(R.id.ImageViewImpl_cutout);

        Bitmap mBitmap1 = null;
        Uri photoUri = getIntent().getParcelableExtra("photoUri");
        try {
             mBitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
        }catch (Exception e){
            e.printStackTrace();
        }
        imageViewImpl_cutout.setmBitmap(mBitmap1);

//        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.cutout_2_framelayout);
//        final ImageViewImpl_cutout myView_cutout = new ImageViewImpl_cutout(this);
//        RelativeLayout.LayoutParams lParams52 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
//        mainLayout.addView(myView_cutout,lParams52);
        imageViewImpl_cutout.isDrawing = false;
        imageViewImpl_cutout.paintShape = 0;//0 圆形画笔
        imageViewImpl_cutout.paintType = 0;//0 画笔 1 橡皮
        square_paint.setBackgroundColor(Color.RED);
        square_paint.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewImpl_cutout.isDrawing) {
                    imageViewImpl_cutout.isDrawing = false;
                    imageViewImpl_cutout.paintType = 0;

                    square_paint.setBackgroundColor(Color.RED);
                    circle_paint.setBackgroundColor(Color.RED);
                    square_eraze.setBackgroundColor(Color.RED);
                    circle_eraze.setBackgroundColor(Color.RED);
                    imageViewImpl_cutout.paintShape = 0;
                } else {
                    imageViewImpl_cutout.isDrawing = true;
                    imageViewImpl_cutout.paintType = 0;
                    square_paint.setBackgroundColor(Color.GREEN);
                    circle_paint.setBackgroundColor(Color.RED);
                    square_eraze.setBackgroundColor(Color.RED);
                    circle_eraze.setBackgroundColor(Color.RED);
                    imageViewImpl_cutout.paintShape = 0;
                }
            }
        });

        square_eraze.setBackgroundColor(Color.RED);
        square_eraze.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "112");
//                        button111.setText("描点");
                if (imageViewImpl_cutout.paintType == 0) {
                    imageViewImpl_cutout.paintType = 1;
                    square_paint.setBackgroundColor(Color.RED);
                    square_eraze.setBackgroundColor(Color.GREEN);
                    circle_paint.setBackgroundColor(Color.RED);
                    circle_eraze.setBackgroundColor(Color.RED);

                } else {
                    imageViewImpl_cutout.paintType = 0;
                    square_paint.setBackgroundColor(Color.RED);
                    square_eraze.setBackgroundColor(Color.RED);
                    circle_paint.setBackgroundColor(Color.RED);
                    circle_eraze.setBackgroundColor(Color.RED);
                }
            }
        });

//        button113.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "113：导出图片到/sdcard/.2ToPath/OUTPUT_11.png");
//                myView11.exportImageByFinger();
//                Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/OUTPUT_11.png", Toast.LENGTH_SHORT).show();
//            }
//        });

        circle_paint.setBackgroundColor(Color.RED);
        circle_paint.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "115");
                imageViewImpl_cutout.paintShape = 1;
                square_paint.setBackgroundColor(Color.RED);
                square_eraze.setBackgroundColor(Color.RED);
                circle_paint.setBackgroundColor(Color.GREEN);
                circle_eraze.setBackgroundColor(Color.RED);

            }
        });

        setover.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "114：加载/sdcard/.2ToPath/OUTPUT_11.png");
                imageViewImpl_cutout.exportImageByFinger();
                Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/OUTPUT_11.png", Toast.LENGTH_SHORT).show();
                imageViewImpl_cutout.showImage();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imagetest, menu);
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
