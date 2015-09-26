package u.can.i.up.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
import com.avast.android.dialogs.iface.ISimpleDialogListener;

import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.dbs.PSQLiteOpenHelper;
import u.can.i.up.ui.utils.BitmapCache;
import u.can.i.up.utils.image.BitmapUtils;
import u.can.i.up.ui.utils.IBitmapCache;
import u.can.i.up.ui.utils.ImageViewImpl_cutout;
import u.can.i.up.utils.image.MD5Utils;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 抠图界面：素材抠取
 */

public class CutoutActivity extends FragmentActivity implements View.OnClickListener,
        ISimpleDialogListener, ISimpleDialogCancelListener {
    private static final String TAG = "u.can.i.up.imagine." + CutoutActivity.class;
    private static final int REQUEST_SIMPLE_DIALOG = 42;
    private ImageButton setover;
    private ImageButton cutout_close;
    private RadioButton circle_paint;
    private RadioButton circle_eraze;
    private RadioButton circle_restore;
    private ImageViewImpl_cutout imageViewImpl_cutout;

//    private PearlBeans pearlBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutout1);
        initView();
    }


    private void initView(){
        setover = (ImageButton)findViewById(R.id.cutout_1_setover);
        cutout_close = (ImageButton)findViewById(R.id.cutout_1_close_btn);
        circle_paint = (RadioButton)findViewById(R.id.circle_paint);
        circle_eraze = (RadioButton)findViewById(R.id.circle_eraze);
        circle_restore = (RadioButton)findViewById(R.id.restore);
        imageViewImpl_cutout = (ImageViewImpl_cutout) findViewById(R.id.ImageViewImpl_cutout);

        String photo_path = getIntent().getStringExtra("photo_path");
//        pearlBeans=getIntent().getParcelableExtra("pearl_beans");
        Log.d("imageView", "Imageview width: " + imageViewImpl_cutout.getWidth() + imageViewImpl_cutout.getHeight());
        //需要修改压缩方法，图片压缩失真了
        imageViewImpl_cutout.setmBitmap(BitmapUtils.decodeSampledBitmapFromFile(photo_path, 1000, 1000));

        //设置绘图相关参数
        imageViewImpl_cutout.isDrawing = false;
        imageViewImpl_cutout.paintShape = 0;//0 圆形画笔
        imageViewImpl_cutout.paintType = 0;//0 画笔 1 橡皮

        circle_paint.setBackgroundColor(Color.TRANSPARENT);
        circle_paint.setOnClickListener(this);

        circle_eraze.setBackgroundColor(Color.TRANSPARENT);
        circle_eraze.setOnClickListener(this);

        circle_restore.setBackgroundColor(Color.TRANSPARENT);
        circle_restore.setOnClickListener(this);
        setover.setOnClickListener(this);
        cutout_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_paint:
            {
                imageViewImpl_cutout.isDrawing = true;
                imageViewImpl_cutout.paintType = 0;
                break;
            }
            case R.id.circle_eraze:
            {
                imageViewImpl_cutout.paintType = 1;
                imageViewImpl_cutout.paintShape = 1;
                break;
            }
            case R.id.restore:
            {
                imageViewImpl_cutout.paintType = 2;
                imageViewImpl_cutout.paintShape = 1;
                break;
            }
            case R.id.cutout_1_setover:
            {
                try {
                    Bitmap mypic = imageViewImpl_cutout.exportImageByFinger();
                    BitmapCache.setBitmapcache(mypic);
//                    try {
//                        savePearlBeans(mypic);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    startActivity(new Intent(CutoutActivity.this, CutoutSaveActivity.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.cutout_1_close_btn:
            {
                SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                        .setTitle("注意")
                        .setMessage("放弃本次操作，你的编辑将丢失")
                        .setPositiveButtonText("确定")
                        .setNegativeButtonText("取消")
                        .setRequestCode(REQUEST_SIMPLE_DIALOG)
                        .show();
                break;
            }
            default:
                break;
        }
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

    // ISimpleDialogCancelListener

    @Override
    public void onCancelled(int requestCode) {
        switch (requestCode) {
            case REQUEST_SIMPLE_DIALOG:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // ISimpleDialogListener

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == REQUEST_SIMPLE_DIALOG) {
            finish();
        }
    }

    @Override
    public void onNegativeButtonClicked(int requestCode) {
        if (requestCode == REQUEST_SIMPLE_DIALOG) {
            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {
        if (requestCode == REQUEST_SIMPLE_DIALOG) {
            Toast.makeText(this, "no selected", Toast.LENGTH_SHORT).show();
        }
    }
//    private void savePearlBeans(Bitmap bitmap) throws IOException {
//
//        Bitmap bitmapStore=Bitmap.createScaledBitmap(bitmap, 120,120, true);
//        byte[] bytes= IBitmapCache.Bitmap2Bytes(bitmapStore);
//        String md5= MD5Utils.getMD5String(bytes);
//        bitmapStore.recycle();
//        FileOutputStream os = this.openFileOutput(md5, Context.MODE_PRIVATE);
//        os.write(bytes);
//        os.close();
//        pearlBeans.setMD5(md5);
//        pearlBeans.setPath("/static/img/png/" + md5);
//        PSQLiteOpenHelper psqLiteOpenHelper=new PSQLiteOpenHelper(this);
//        psqLiteOpenHelper.addPearl(pearlBeans);
//        ((IApplication)getApplication()).arrayListPearlBeans.add(pearlBeans);
//        Toast.makeText(this,"素材获取成功",Toast.LENGTH_LONG).show();
//    }
}
