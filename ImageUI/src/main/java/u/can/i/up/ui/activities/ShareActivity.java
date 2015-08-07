package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.ImageUtils;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 分享界面：搭配完成后，分享图片
 */

public class ShareActivity extends Activity {
    private static final String ToPath = ".2ToPath";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        final ImageView share_image = (ImageView) findViewById(R.id.share_image);
        ImageButton cutout_1_close_btn = (ImageButton)findViewById(R.id.cutout_1_close_btn);
        Button back_main = (Button)findViewById(R.id.back_main);
        final Bitmap tempbitmap = BitmapCache.getBitmapcache();

        // Resize the bitmap to 150x100 (width x height)
        int imageH = share_image.getLayoutParams().height;
        Bitmap bMapScaled = scaleToFitHeight(tempbitmap, imageH);

//        Bitmap bMapScaled = Bitmap.createScaledBitmap(tempbitmap, 400, 500, true);
//
        int width = getApplication().getResources().getDisplayMetrics().widthPixels;
        int height = (width*tempbitmap.getHeight())/tempbitmap.getWidth();
        Bitmap mbitmap = Bitmap.createScaledBitmap(tempbitmap, width, height, true);
        share_image.setImageBitmap(mbitmap);
//        ViewTreeObserver vto = share_image.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                share_image.getViewTreeObserver().removeOnPreDrawListener(this);
//                int finalHeight = share_image.getMeasuredHeight();
//                Bitmap bMapScaled = scaleToFitHeight(tempbitmap, finalHeight);
//                share_image.setImageBitmap(bMapScaled);
//                return true;
//            }
//        });



        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportImageByFinger(tempbitmap);
                Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/OUTPUT_11.png", Toast.LENGTH_SHORT).show();
                showImage();
            }
        });
        cutout_1_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

        // Scale and maintain aspect ratio given a desired width

        // BitmapScaler.scaleToFitWidth(bitmap, 100);

        public  Bitmap scaleToFitWidth(Bitmap b, int width)

        {

            float factor = width / (float) b.getWidth();

            return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);

        }





        // Scale and maintain aspect ratio given a desired height

        // BitmapScaler.scaleToFitHeight(bitmap, 100);

        public  Bitmap scaleToFitHeight(Bitmap b, int height)

        {

            float factor = height / (float) b.getHeight();

            return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);

        }


    public static void exportImageByFinger(Bitmap mBitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String fname = "123_test.jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showImage(){
        File file = new File(Environment.getExternalStorageDirectory(), "/saved_images" + "/123_test.jpg");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
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
