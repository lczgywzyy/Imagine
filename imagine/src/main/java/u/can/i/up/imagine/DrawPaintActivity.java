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
                button71.setText("按钮");
                RelativeLayout.LayoutParams lParams71 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button71, lParams71);   //将按钮放入layout组件
                button71.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "7");
                        if(ImageViewImpl_7.isDrawing){
                            ImageViewImpl_7.isDrawing = false;
                            button71.setText("按钮");
                        }else {
                            ImageViewImpl_7.isDrawing = true;
                            button71.setText("描点中...");
                        }
                    }
                });

                ImageViewImpl_7 myView7 = new ImageViewImpl_7(this);
                ImageViewImpl_7.isDrawing = false;
                myView7.setId(10072);
                RelativeLayout.LayoutParams lParams7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams7.addRule(RelativeLayout.BELOW, 10071);
                mainLayout.addView(myView7, lParams7);
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
