package u.can.i.up.imagine;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
                button51.setId(10501);
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
                button52.setId(10502);
                button52.setText("模式2");
                RelativeLayout.LayoutParams lParams52 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams52.addRule(RelativeLayout.BELOW, 10501);
                mainLayout.addView(button52, lParams52);   //将按钮放入layout组件
                button52.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "2");
                    }
                });

                ImageViewImpl_5 myView5 = new ImageViewImpl_5(this);
                RelativeLayout.LayoutParams lParams53 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams53.addRule(RelativeLayout.BELOW, 10502);
                mainLayout.addView(myView5, lParams53);
                break;
            case 6:
                Button button61 = new Button(this);
                button61.setId(10601);
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
                button62.setId(10602);
                button62.setText("模式2");
                RelativeLayout.LayoutParams lParams62 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams62.addRule(RelativeLayout.BELOW, 10601);
                mainLayout.addView(button62, lParams62);   //将按钮放入layout组件
                button62.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "62");
                    }
                });

                ImageViewImpl_6 myView6 = new ImageViewImpl_6(this);
                myView6.setId(10603);
                RelativeLayout.LayoutParams lParams63 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams63.addRule(RelativeLayout.BELOW, 10602);
                mainLayout.addView(myView6, lParams63);
                break;
            case 7:
                final Button button71 = new Button(this);
                button71.setId(10701);
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
                button72.setId(10702);
                button72.setText("导出");
                RelativeLayout.LayoutParams lParams72 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams72.addRule(RelativeLayout.RIGHT_OF, 10701);
                mainLayout.addView(button72, lParams72);   //将按钮放入layout组件

                final Button button73 = new Button(this);
                button73.setId(10703);
                button73.setText("查看");
                RelativeLayout.LayoutParams lParams73 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams73.addRule(RelativeLayout.RIGHT_OF, 10702);
                mainLayout.addView(button73, lParams73);   //将按钮放入layout组件

                final ImageViewImpl_7 myView7 = new ImageViewImpl_7(this);
                ImageViewImpl_7.isDrawing = false;
                myView7.setId(10704);
                RelativeLayout.LayoutParams lParams7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams7.addRule(RelativeLayout.BELOW, 10701);
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
                button81.setId(10801);
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
                button82.setId(10802);
                button82.setText("导出");
                RelativeLayout.LayoutParams lParams82 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams82.addRule(RelativeLayout.RIGHT_OF, 10801);
                mainLayout.addView(button82, lParams82);   //将按钮放入layout组件

                final Button button83 = new Button(this);
                button83.setId(10803);
                button83.setText("查看");
                RelativeLayout.LayoutParams lParams83 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams83.addRule(RelativeLayout.RIGHT_OF, 10802);
                mainLayout.addView(button83, lParams83);   //将按钮放入layout组件

                final ImageViewImpl_8 myView8 = new ImageViewImpl_8(this);
                ImageViewImpl_8.isDrawing = false;
                myView8.setId(10804);
                RelativeLayout.LayoutParams lParams8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams8.addRule(RelativeLayout.BELOW, 10801);
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
            case 9:
                final Button button91 = new Button(this);
                button91.setId(10901);
                button91.setText("描点");
                RelativeLayout.LayoutParams lParams91 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button91, lParams91);   //将按钮放入layout组件
                button91.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "9");
                        if (ImageViewImpl_9.isDrawing) {
                            ImageViewImpl_9.isDrawing = false;
                            button91.setText("描点");
                        } else {
                            ImageViewImpl_9.isDrawing = true;
                            button91.setText("描点中...");
                        }
                    }
                });
                final Button button92 = new Button(this);
                button92.setId(10902);
                button92.setText("导出");
                RelativeLayout.LayoutParams lParams92 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams92.addRule(RelativeLayout.RIGHT_OF, 10901);
                mainLayout.addView(button92, lParams92);   //将按钮放入layout组件

                final Button button93 = new Button(this);
                button93.setId(10903);
                button93.setText("查看");
                RelativeLayout.LayoutParams lParams93 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams93.addRule(RelativeLayout.RIGHT_OF, 10902);
                mainLayout.addView(button93, lParams93);   //将按钮放入layout组件

                final Button button94 = new Button(this);
                button94.setId(10904);
                button94.setText("圆形");
                RelativeLayout.LayoutParams lParams94 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams94.addRule(RelativeLayout.BELOW, 10901);
                mainLayout.addView(button94, lParams94);   //将按钮放入layout组件

                final Button button95 = new Button(this);
                button95.setId(10905);
                button95.setText("方形");
                RelativeLayout.LayoutParams lParams95 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams95.addRule(RelativeLayout.RIGHT_OF, 10904);
                lParams95.addRule(RelativeLayout.BELOW, 10901);
                mainLayout.addView(button95, lParams95);   //将按钮放入layout组件

                final Button button96 = new Button(this);
                button96.setId(10906);
                button96.setText("橡皮");
                RelativeLayout.LayoutParams lParams96 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams96.addRule(RelativeLayout.RIGHT_OF, 10905);
                lParams96.addRule(RelativeLayout.BELOW, 10901);
                mainLayout.addView(button96, lParams96);   //将按钮放入layout组件

                final ImageViewImpl_9 myView9 = new ImageViewImpl_9(this);
                ImageViewImpl_9.isDrawing = false;
                myView9.setId(10907);
                RelativeLayout.LayoutParams lParams9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams9.addRule(RelativeLayout.BELOW, 10904);
                mainLayout.addView(myView9, lParams9);
                button92.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "92：导出图片到/sdcard/.2ToPath/5.png");
                        myView9.exportImageByFinger();
                        Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/5.png", Toast.LENGTH_SHORT).show();
                    }
                });
                button93.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "93：加载/sdcard/.2ToPath/5.png");
                        myView9.showImage();
                    }
                });
                button94.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "94");
                        myView9.paintShape = 0;
                    }
                });
                button95.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "95");
                        myView9.paintShape = 1;
                    }
                });
                button96.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "96");
                        myView9.paintShape = 2;
                    }
                });
                break;
            case 10:
                final Button button101 = new Button(this);
                button101.setId(11001);
                button101.setText("导出");
                RelativeLayout.LayoutParams lParams101 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button101, lParams101);   //将按钮放入layout组件

                final Button button102 = new Button(this);
                button102.setId(11002);
                button102.setText("局部导出");
                RelativeLayout.LayoutParams lParams102 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams102.addRule(RelativeLayout.RIGHT_OF, 11001);
                mainLayout.addView(button102, lParams102);   //将按钮放入layout组件

                final ImageViewImpl_10 myView10 = new ImageViewImpl_10(this);
                myView10.setId(11003);
                RelativeLayout.LayoutParams lParams10 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams10.addRule(RelativeLayout.BELOW, 11001);
                mainLayout.addView(myView10, lParams10);
                button101.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "导出到ImageViewImpl_10_output_All.png", Toast.LENGTH_SHORT).show();
                        myView10.saveBitmapAll();
                    }
                });
                button102.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "导出到ImageViewImpl_10_output_Covered.png", Toast.LENGTH_SHORT).show();
                        myView10.saveBitmapCovered();
                    }
                });
                break;
            case 11:
                final Button button111 = new Button(this);
                button111.setId(11101);
                button111.setText("描点");
                RelativeLayout.LayoutParams lParams111 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(button111, lParams111);   //将按钮放入layout组件

                final Button button112 = new Button(this);
                button112.setId(11102);
                button112.setText("橡皮");
                RelativeLayout.LayoutParams lParams112 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams112.addRule(RelativeLayout.RIGHT_OF, 11101);
                mainLayout.addView(button112, lParams112);   //将按钮放入layout组件

                final Button button113 = new Button(this);
                button113.setId(11103);
                button113.setText("导出");
                RelativeLayout.LayoutParams lParams113 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams113.addRule(RelativeLayout.RIGHT_OF, 11102);
                mainLayout.addView(button113, lParams113);   //将按钮放入layout组件

                final Button button114 = new Button(this);
                button114.setId(11104);
                button114.setText("查看");
                RelativeLayout.LayoutParams lParams114 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams114.addRule(RelativeLayout.RIGHT_OF, 11103);
                mainLayout.addView(button114, lParams114);   //将按钮放入layout组件

                final Button button115 = new Button(this);
                button115.setId(11105);
                button115.setText("圆形");
                RelativeLayout.LayoutParams lParams115 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams115.addRule(RelativeLayout.BELOW, 11101);
                mainLayout.addView(button115, lParams115);   //将按钮放入layout组件

                final Button button116 = new Button(this);
                button116.setId(11106);
                button116.setText("方形");
                RelativeLayout.LayoutParams lParams116 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams116.addRule(RelativeLayout.RIGHT_OF, 11105);
                lParams116.addRule(RelativeLayout.BELOW, 11101);
                mainLayout.addView(button116, lParams116);   //将按钮放入layout组件

                final ImageViewImpl_11 myView11 = new ImageViewImpl_11(this);
                myView11.setId(11107);
                RelativeLayout.LayoutParams lParams11 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams11.addRule(RelativeLayout.BELOW, 11105);
                mainLayout.addView(myView11, lParams11);

                myView11.isDrawing = false;
                myView11.paintShape = 0;
                myView11.paintType = 0;
                button111.setBackgroundColor(Color.RED);
                button111.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "11");
                        if (myView11.isDrawing) {
                            myView11.isDrawing = false;
                            myView11.paintType = 0;
                            button111.setText("描点");
                            button111.setBackgroundColor(Color.RED);
                            button112.setBackgroundColor(Color.RED);
                            button115.setBackgroundColor(Color.RED);
                            button116.setBackgroundColor(Color.RED);
                            myView11.paintShape = 0;
                        } else {
                            myView11.isDrawing = true;
                            myView11.paintType = 0;
                            button111.setText("描点中...");
                            button111.setBackgroundColor(Color.GREEN);
                            button112.setBackgroundColor(Color.RED);
                            button115.setBackgroundColor(Color.GREEN);
                            myView11.paintShape = 0;
                        }
                    }
                });

                button112.setBackgroundColor(Color.RED);
                button112.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "112");
//                        button111.setText("描点");
                        if (myView11.paintType == 0) {
                            myView11.paintType = 1;
//                            button111.setBackgroundColor(Color.RED);
                            button112.setBackgroundColor(Color.GREEN);
                        } else {
                            myView11.paintType = 0;
//                            button111.setBackgroundColor(Color.RED);
                            button112.setBackgroundColor(Color.RED);
                        }
                    }
                });

                button113.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "113：导出图片到/sdcard/.2ToPath/OUTPUT_11.png");
                        myView11.exportImageByFinger();
                        Toast.makeText(getApplicationContext(), "导出图片到/sdcard/.2ToPath/OUTPUT_11.png", Toast.LENGTH_SHORT).show();
                    }
                });
                button114.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "114：加载/sdcard/.2ToPath/OUTPUT_11.png");
                        myView11.showImage();
                    }
                });
                button115.setBackgroundColor(Color.RED);
                button115.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "115");
                        myView11.paintShape = 0;
                        button115.setBackgroundColor(Color.GREEN);
                        button116.setBackgroundColor(Color.RED);
                    }
                });
                button116.setBackgroundColor(Color.RED);
                button116.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "116");
                        myView11.paintShape = 1;
                        button115.setBackgroundColor(Color.RED);
                        button116.setBackgroundColor(Color.GREEN);
                    }
                });
                break;
            case 12:
//                SeekBar sb = new SeekBar(this);
//                sb.setId(11201);
//                RelativeLayout.LayoutParams lParams12 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
//                mainLayout.addView(sb, lParams12);
//                sb.setFocusable(true);
//                sb.setMax(200);
//                sb.setMinimumHeight(3);
//                sb.setProgress(100);
                ImageViewImpl_12 myView12 = new ImageViewImpl_12(this);
                mainLayout.addView(myView12);
                break;
            case 13:
                final EditText editText131 = new EditText(this);
                editText131.setId(11301);
                editText131.setHintTextColor(Color.GRAY);
                editText131.setHint("请输入素珠个数(10-120)：");
                //android:numeric
                //integer 0x01 Input is numeric.
                //signed 0x03 Input is numeric, with sign allowed.
                //decimal 0x05 Input is numeric, with decimals allowed.
//                editText131.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                editText131.setInputType(InputType.TYPE_CLASS_NUMBER);
                RelativeLayout.LayoutParams lParams131 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(editText131, lParams131);
                editText131.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int withs = editText131.getWidth();
                        editText131.setHint(null);
                        editText131.setWidth(withs);
                    }
                });

                final Button button132 = new Button(this);
                button132.setId(11302);
                button132.setText("确定");
                RelativeLayout.LayoutParams lParams132 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams132.addRule(RelativeLayout.RIGHT_OF, 11301);
                mainLayout.addView(button132, lParams132);

                final ImageViewImpl_13 myView13 = new ImageViewImpl_13(this);
                myView13.setId(11303);
                RelativeLayout.LayoutParams lParams133 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams133.addRule(RelativeLayout.BELOW, 11301);
                mainLayout.addView(myView13, lParams133);

                button132.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Log.i(TAG, "" + editText131.getText());
                        int inputNum = Integer.parseInt(editText131.getText().toString());
                        editText131.setText(null);
                        if(inputNum >= 10 && inputNum <=120){
                            editText131.setHintTextColor(Color.GRAY);
                            editText131.setHint("请输入素珠个数(10-120)：");
                            myView13.updateImage(inputNum);
                        } else {
                            int withs = editText131.getWidth();
                            editText131.setHintTextColor(Color.RED);
                            editText131.setHint("请输入素珠个数(10-120)：");
                            editText131.setWidth(withs);
                        }
                    }
                });
                break;
            case 14:
                final EditText editText141 = new EditText(this);
                editText141.setId(11401);
                editText141.setHintTextColor(Color.GRAY);
                editText141.setHint("请输入素珠个数(10-120)：");
                editText141.setInputType(InputType.TYPE_CLASS_NUMBER);
                RelativeLayout.LayoutParams lParams141 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                mainLayout.addView(editText141, lParams141);
                editText141.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int withs = editText141.getWidth();
                        editText141.setHint(null);
                        editText141.setWidth(withs);
                    }
                });

                final Button button142 = new Button(this);
                button142.setId(11402);
                button142.setText("确定");
                RelativeLayout.LayoutParams lParams142 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams142.addRule(RelativeLayout.RIGHT_OF, 11401);
                mainLayout.addView(button142, lParams142);

                final Button button143 = new Button(this);
                button143.setId(11403);
                button143.setText("添加");
                RelativeLayout.LayoutParams lParams143 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams143.addRule(RelativeLayout.RIGHT_OF, 11402);
                mainLayout.addView(button143, lParams143);

                final ImageViewImpl_14 myView14 = new ImageViewImpl_14(this);
                myView14.setId(11404);
                RelativeLayout.LayoutParams lParams144 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
                lParams144.addRule(RelativeLayout.BELOW, 11401);
                mainLayout.addView(myView14, lParams144);

                button142.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int inputNum = Integer.parseInt(editText141.getText().toString());
                        editText141.setText(null);
                        if (inputNum >= 10 && inputNum <= 120) {
                            editText141.setHintTextColor(Color.GRAY);
                            editText141.setHint("请输入素珠个数(10-120)：");
                            myView14.updateImage(inputNum);
                        } else {
                            int withs = editText141.getWidth();
                            editText141.setHintTextColor(Color.RED);
                            editText141.setHint("请输入素珠个数(10-120)：");
                            editText141.setWidth(withs);
                        }
                    }
                });

                button143.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        myView14.setBmpMotion(BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageView14/a.png").getAbsolutePath()));
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
