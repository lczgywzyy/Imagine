package u.can.i.up.imagine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

import u.can.i.up.utils.image.ImageUtils;

/**
 * Created by lczgywzyy on 2015/5/15.
 */
public class DrawPaintActivity extends ActionBarActivity {
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    private static final String TAG = "u.can.i.up.imagine." + DrawPaintActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_paint);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.draw_paint_layout);

        switch (getIntent().getExtras().getInt("BUTTON")) {
            case 1:
                ImageViewImpl_1 myView1 = new ImageViewImpl_1(this);
                mainLayout.addView(myView1);
                break;
            case 2:
                ImageViewImpl_2 myView2 = new ImageViewImpl_2(this);
                mainLayout.addView(myView2);
                break;
            case 3:
                ImageViewImpl_3 myView3 = new ImageViewImpl_3(this);
                mainLayout.addView(myView3);
                break;
            case 4:
                ImageViewImpl_4 myView4 = new ImageViewImpl_4(this);
                mainLayout.addView(myView4);
                break;
            case 5:
                Button button51 = new Button(this);
                button51.setId(10051);
                button51.setText("模式1");
                RelativeLayout.LayoutParams lParams51 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button51, lParams51);   //将按钮放入layout组件
                button51.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "1");
                    }
                });

                Button button52 = new Button(this);
                button52.setId(10052);
                button52.setText("模式2");
                RelativeLayout.LayoutParams lParams52 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams52.addRule(RelativeLayout.BELOW, 10051);
                mainLayout.addView(button52, lParams52);   //将按钮放入layout组件
                button52.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "2");
                    }
                });

                ImageViewImpl_5 myView5 = new ImageViewImpl_5(this);
                RelativeLayout.LayoutParams lParams53 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams53.addRule(RelativeLayout.BELOW, 10052);
                mainLayout.addView(myView5, lParams53);
                break;
            case 6:
                Button button61 = new Button(this);
                button61.setId(10061);
                button61.setText("模式1");
                RelativeLayout.LayoutParams lParams61 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button61, lParams61);   //将按钮放入layout组件
                button61.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "61");
                    }
                });

                Button button62 = new Button(this);
                button62.setId(10062);
                button62.setText("模式2");
                RelativeLayout.LayoutParams lParams62 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams62.addRule(RelativeLayout.BELOW, 10061);
                mainLayout.addView(button62, lParams62);   //将按钮放入layout组件
                button62.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "62");
                    }
                });

                ImageViewImpl_6 myView6 = new ImageViewImpl_6(this);
                myView6.setId(10063);
                RelativeLayout.LayoutParams lParams63 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams63.addRule(RelativeLayout.BELOW, 10062);
                mainLayout.addView(myView6, lParams63);
                break;
            case 7:
                final Button button71 = new Button(this);
                button71.setId(10071);
                button71.setText("描点");
                RelativeLayout.LayoutParams lParams71 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button71, lParams71);   //将按钮放入layout组件
                button71.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "7");
                        if (ImageViewImpl_7.isDrawing) {
                            ImageViewImpl_7.isDrawing = false;
                            button71.setText("描点");
                        } else {
                            ImageViewImpl_7.isDrawing = true;
                            button71.setText("描点中...");
                        }
                    }
                });
                final Button button72 = new Button(this);
                button72.setId(10072);
                button72.setText("导出");
                RelativeLayout.LayoutParams lParams72 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams72.addRule(RelativeLayout.RIGHT_OF, 10071);
                mainLayout.addView(button72, lParams72);   //将按钮放入layout组件

                final Button button73 = new Button(this);
                button73.setId(10073);
                button73.setText("查看");
                RelativeLayout.LayoutParams lParams73 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams73.addRule(RelativeLayout.RIGHT_OF, 10072);
                mainLayout.addView(button73, lParams73);   //将按钮放入layout组件

                final ImageViewImpl_7 myView7 = new ImageViewImpl_7(this);
                ImageViewImpl_7.isDrawing = false;
                myView7.setId(10074);
                RelativeLayout.LayoutParams lParams7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams7.addRule(RelativeLayout.BELOW, 10071);
                mainLayout.addView(myView7, lParams7);
                button72.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "72：导出图片到/sdcard/.2ToPath/5.png");
                        myView7.exportImageByFinger();
                        Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/5.png", Toast.LENGTH_SHORT).show();
                    }
                });
                button73.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "73：加载/sdcard/.2ToPath/5.png");
                        myView7.showImage();
                    }
                });
                break;
            case 8:
                final Button button81 = new Button(this);
                button81.setId(10081);
                button81.setText("描点");
                RelativeLayout.LayoutParams lParams81 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button81, lParams81);   //将按钮放入layout组件
                button81.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "8");
                        if (ImageViewImpl_8.isDrawing) {
                            ImageViewImpl_8.isDrawing = false;
                            button81.setText("描点");
                        } else {
                            ImageViewImpl_8.isDrawing = true;
                            button81.setText("描点中...");
                        }
                    }
                });
                final Button button82 = new Button(this);
                button82.setId(10082);
                button82.setText("导出");
                RelativeLayout.LayoutParams lParams82 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams82.addRule(RelativeLayout.RIGHT_OF, 10081);
                mainLayout.addView(button82, lParams82);   //将按钮放入layout组件

                final Button button83 = new Button(this);
                button83.setId(10083);
                button83.setText("查看");
                RelativeLayout.LayoutParams lParams83 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams83.addRule(RelativeLayout.RIGHT_OF, 10082);
                mainLayout.addView(button83, lParams83);   //将按钮放入layout组件

                final ImageViewImpl_8 myView8 = new ImageViewImpl_8(this);
                ImageViewImpl_8.isDrawing = false;
                myView8.setId(10084);
                RelativeLayout.LayoutParams lParams8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams8.addRule(RelativeLayout.BELOW, 10081);
                mainLayout.addView(myView8, lParams8);
                button82.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "82：导出图片到/sdcard/.2ToPath/5.png");
                        myView8.exportImageByFinger();
                        Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/5.png", Toast.LENGTH_SHORT).show();
                    }
                });
                button83.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "83：加载/sdcard/.2ToPath/5.png");
                        myView8.showImage();
                    }
                });
                break;
            default:
                break;
        }

//        extractImageTest();
//        combineImageTest();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void extractImageTest(){
        File FromFile = new File(Environment.getExternalStorageDirectory(), FromPath);
        File ToFile = new File(Environment.getExternalStorageDirectory(), ToPath);
        if (FromFile == null || ToFile == null){
            Log.i(TAG, "FromFile or ToFile is [NULL]");
            Toast.makeText(getApplicationContext(), "FromFile or ToFile is [NULL]", Toast.LENGTH_LONG);
        }else if (!FromFile.isDirectory() || !ToFile.isDirectory()){
            Log.i(TAG, "FromFile or ToFile is [NOT DIRECTORY]");
            Toast.makeText(getApplicationContext(), "FromFile or ToFile is [NOT DIRECTORY]", Toast.LENGTH_LONG);
            FromFile.delete();
            ToFile.delete();
            FromFile.mkdir();
            ToFile.mkdir();
        } else {
            File[] currentFiles = FromFile.listFiles();
            if (currentFiles != null && currentFiles.length != 0){
                for (File f: currentFiles){
//                    ImageUtils.copy(f.getAbsolutePath(), f.getAbsolutePath().replace(FromPath, ToPath));
                    ImageUtils.extractImage(f.getAbsolutePath(), f.getAbsolutePath().replace(FromPath, ToPath).replace("jpg", "png"), false);
                }
            }
        }
    }
    private void combineImageTest(){
        File upImgFile = new File(Environment.getExternalStorageDirectory(), ToPath + "/1.png");
        File lowImgFile = new File(Environment.getExternalStorageDirectory(), ToPath + "/2.png");
        ImageUtils.combineImage(upImgFile.getAbsolutePath(), lowImgFile.getAbsolutePath(), lowImgFile.getAbsolutePath().replace("2.png", "3.png"));
    }
}
