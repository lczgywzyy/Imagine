package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapUtils;
import u.can.i.up.ui.utils.ImageViewImpl_collocate;
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
        setContentView(R.layout.activity_cutout1);
        ImageButton setover = (ImageButton)findViewById(R.id.cutout_1_setover);
//        final ImageButton square_paint = (ImageButton)findViewById(R.id.square_paint);
        final RadioButton circle_paint = (RadioButton)findViewById(R.id.circle_paint);
//        final ImageButton square_eraze = (ImageButton)findViewById(R.id.square_eraze);
        final RadioButton circle_eraze = (RadioButton)findViewById(R.id.circle_eraze);

        final RadioButton circle_restore = (RadioButton)findViewById(R.id.restore);
        final ImageViewImpl_cutout imageViewImpl_cutout = (ImageViewImpl_cutout) findViewById(R.id.ImageViewImpl_cutout);

        String photo_path = getIntent().getStringExtra("photo_path");

        Log.d("imageView", "Imageview width: " + imageViewImpl_cutout.getWidth() + imageViewImpl_cutout.getHeight());
        imageViewImpl_cutout.setmBitmap(BitmapUtils.decodeSampledBitmapFromFile(photo_path, 1000, 1000));

        imageViewImpl_cutout.isDrawing = false;
        imageViewImpl_cutout.paintShape = 0;//0 圆形画笔
        imageViewImpl_cutout.paintType = 0;//0 画笔 1 橡皮

        circle_paint.setBackgroundColor(Color.TRANSPARENT);
        circle_paint.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewImpl_cutout.isDrawing = true;
                imageViewImpl_cutout.paintType = 0;
            }
        });
//
        circle_eraze.setBackgroundColor(Color.TRANSPARENT);
        circle_eraze.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewImpl_cutout.paintType = 1;
                imageViewImpl_cutout.paintShape = 1;
            }
        });

        circle_restore.setBackgroundColor(Color.TRANSPARENT);
        circle_restore.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageViewImpl_cutout.paintType = 2;
                imageViewImpl_cutout.paintShape = 1;
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
