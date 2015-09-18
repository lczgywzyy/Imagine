package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_collocate;
import u.can.i.up.ui.utils.ImageViewImpl_cutout;
import u.can.i.up.ui.utils.MD5Utils;
import u.can.i.up.utils.image.Pearl;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 抠图界面：素材抠取
 */

public class CutoutActivity extends Activity {
    private static final String TAG = "u.can.i.up.imagine." + CutoutActivity.class;

    private static final String ToPath = ".2ToPath";

    private PearlBeans pearlBeans;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutout1);
        ImageButton setover = (ImageButton)findViewById(R.id.cutout_1_setover);
//        final ImageButton square_paint = (ImageButton)findViewById(R.id.square_paint);
        final RadioButton circle_paint = (RadioButton)findViewById(R.id.circle_paint);
//        final ImageButton square_eraze = (ImageButton)findViewById(R.id.square_eraze);
        final RadioButton circle_eraze = (RadioButton)findViewById(R.id.circle_eraze);

        final ImageViewImpl_cutout imageViewImpl_cutout = (ImageViewImpl_cutout) findViewById(R.id.ImageViewImpl_cutout);

        String photo_path = getIntent().getStringExtra("photo_path");

        pearlBeans=getIntent().getParcelableExtra("pearl_beans");

//        Bitmap mBitmap1 = null;
//        Uri photoUri = getIntent().getParcelableExtra("photoUri");
//        try {
//             mBitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        imageViewImpl_cutout.setmBitmap(BitmapFactory.decodeFile(photo_path));

//        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.cutout_2_framelayout);
//        final ImageViewImpl_cutout myView_cutout = new ImageViewImpl_cutout(this);
//        RelativeLayout.LayoutParams lParams52 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
//        mainLayout.addView(myView_cutout,lParams52);
        imageViewImpl_cutout.isDrawing = false;
        imageViewImpl_cutout.paintShape = 0;//0 圆形画笔
        imageViewImpl_cutout.paintType = 0;//0 画笔 1 橡皮

        circle_paint.setBackgroundColor(Color.TRANSPARENT);
        circle_paint.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewImpl_cutout.isDrawing) {
                    imageViewImpl_cutout.isDrawing = false;
                    imageViewImpl_cutout.paintType = 0;

//                    square_paint.setBackgroundColor(Color.TRANSPARENT);
                    circle_paint.setBackgroundColor(Color.TRANSPARENT);
//                    square_eraze.setBackgroundColor(Color.TRANSPARENT);
                    circle_eraze.setBackgroundColor(Color.TRANSPARENT);
                    imageViewImpl_cutout.paintShape = 0;
                } else {
                    imageViewImpl_cutout.isDrawing = true;
                    imageViewImpl_cutout.paintType = 0;
//                    square_paint.setBackgroundColor(Color.TRANSPARENT);
                    circle_paint.setBackgroundColor(Color.TRANSPARENT);
//                    square_eraze.setBackgroundColor(Color.TRANSPARENT);
                    circle_eraze.setBackgroundColor(Color.TRANSPARENT);
                    imageViewImpl_cutout.paintShape = 0;
                }
            }
        });
//
        circle_eraze.setBackgroundColor(Color.TRANSPARENT);
        circle_eraze.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "112");
//                        button111.setText("描点");
                if (imageViewImpl_cutout.paintType == 0) {
                    imageViewImpl_cutout.paintType = 1;
//                    square_paint.setBackgroundColor(Color.TRANSPARENT);
//                    square_eraze.setBackgroundColor(Color.TRANSPARENT);
                    circle_paint.setBackgroundColor(Color.TRANSPARENT);
                    circle_eraze.setBackgroundColor(Color.TRANSPARENT);

                } else {
                    imageViewImpl_cutout.paintType = 0;
//                    square_paint.setBackgroundColor(Color.TRANSPARENT);
//                    square_eraze.setBackgroundColor(Color.TRANSPARENT);
                    circle_paint.setBackgroundColor(Color.TRANSPARENT);
                    circle_eraze.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });


        setover.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    savePearlBeans(imageViewImpl_cutout.exportImageByFinger());
                }catch(Exception e){}
                //imageViewImpl_cutout.showImage();
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

    private void savePearlBeans(Bitmap bitmap) throws IOException{

        byte[] bytes=IBitmapCache.Bitmap2Bytes(bitmap);
        String md5= MD5Utils.getMD5String(bytes);
        FileOutputStream os = this.openFileOutput(md5,
                Context.MODE_PRIVATE);
        os.write(bytes);
        os.close();
        pearlBeans.setMD5(md5);
        pearlBeans.setPath("/static/img/png/" + md5);
        PSQLiteOpenHelper psqLiteOpenHelper=new PSQLiteOpenHelper(this);
        psqLiteOpenHelper.addPearl(pearlBeans);
        ((IApplication)getApplication()).arrayListPearlBeans.add(pearlBeans);
        Toast.makeText(this,"素材获取成功",Toast.LENGTH_LONG).show();
    }

}
