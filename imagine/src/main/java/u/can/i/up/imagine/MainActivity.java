package u.can.i.up.imagine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";


    private static final String TAG = "u.can.i.up.imagine." + MainActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageViewImpl myView = new ImageViewImpl(this);
//        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
//        mainLayout.addView(myView);

//        extractImageTest();
//        combineImageTest();

        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.main_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Button button1 = new Button(this);
        button1.setId(10100);
        button1.setText("测试1:描点测试");
        RelativeLayout.LayoutParams lParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        mainLayout.addView(button1, lParams1);   //将按钮放入layout组件
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 1);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button2 = new Button(this);
        button2.setId(10200);
        button2.setText("测试2:拖拽、旋转、缩放测试");
        RelativeLayout.LayoutParams lParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams2.addRule(RelativeLayout.RIGHT_OF, 10100);
        mainLayout.addView(button2, lParams2);   //将按钮放入layout组件
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 2);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button3 = new Button(this);
        button3.setId(10300);
        button3.setText("测试3:Flood填充测试(点填充|失败！)");
        RelativeLayout.LayoutParams lParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams3.addRule(RelativeLayout.BELOW, 10100);
        mainLayout.addView(button3, lParams3);   //将按钮放入layout组件
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 3);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button4 = new Button(this);
        button4.setId(10400);
        button4.setText("测试4:Flood填充测试(ImageMagick)");
        RelativeLayout.LayoutParams lParams4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams4.addRule(RelativeLayout.BELOW, 10300);
        mainLayout.addView(button4, lParams4);   //将按钮放入layout组件
        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 4);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button5 = new Button(this);
        button5.setId(10500);
        button5.setText("测试5:手动点击去除背景测试");
        RelativeLayout.LayoutParams lParams5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams5.addRule(RelativeLayout.BELOW, 10200);
        lParams5.addRule(RelativeLayout.RIGHT_OF, 10400);
        mainLayout.addView(button5, lParams5);   //将按钮放入layout组件
        button5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 5);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button6 = new Button(this);
        button6.setId(10600);
        button6.setText("测试6:自动去除背景测试");
        RelativeLayout.LayoutParams lParams6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams6.addRule(RelativeLayout.BELOW, 10400);
        mainLayout.addView(button6, lParams6);   //将按钮放入layout组件
        button6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 6);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button7 = new Button(this);
        button7.setId(10700);
        button7.setText("测试7:拖拽、缩放、描点综合测试1(见测试11)");
        RelativeLayout.LayoutParams lParams7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams7.addRule(RelativeLayout.BELOW, 10400);
        lParams7.addRule(RelativeLayout.RIGHT_OF, 10600);
        mainLayout.addView(button7, lParams7);   //将按钮放入layout组件
        button7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 7);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button8 = new Button(this);
        button8.setId(10800);
        button8.setText("测试8:拖拽、缩放、描点综合测试2（见测试11）");
        RelativeLayout.LayoutParams lParams8 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams8.addRule(RelativeLayout.BELOW, 10600);
        mainLayout.addView(button8, lParams8);   //将按钮放入layout组件
        button8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 8);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button9 = new Button(this);
        button9.setId(10900);
        button9.setText("测试9：画笔形状，橡皮(11)");
        RelativeLayout.LayoutParams lParams9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams9.addRule(RelativeLayout.BELOW, 10800);
        mainLayout.addView(button9, lParams9);   //将按钮放入layout组件
        button9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 9);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button10 = new Button(this);
        button10.setId(11000);
        button10.setText("测试10：素材角落控件");
        RelativeLayout.LayoutParams lParams10 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams10.addRule(RelativeLayout.RIGHT_OF, 10900);
        lParams10.addRule(RelativeLayout.BELOW, 10800);
        mainLayout.addView(button10, lParams10);   //将按钮放入layout组件
        button10.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 10);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button11 = new Button(this);
        button11.setId(11100);
        button11.setText("测试11：拖拽、缩放、描点综合测试3 -- FINISH");
        RelativeLayout.LayoutParams lParams11 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams11.addRule(RelativeLayout.BELOW, 11000);
        mainLayout.addView(button11, lParams11);   //将按钮放入layout组件
        button11.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 11);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button12 = new Button(this);
        button12.setId(11200);
        button12.setText("测试12：圆形缩放blabla（待实现）");
        RelativeLayout.LayoutParams lParams12 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams12.addRule(RelativeLayout.BELOW, 11100);
        mainLayout.addView(button12, lParams12);   //将按钮放入layout组件
        button12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 12);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button button13 = new Button(this);
        button13.setId(11300);
        button13.setText("测试13：标尺缩放（待实现）");
        RelativeLayout.LayoutParams lParams13 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams13.addRule(RelativeLayout.BELOW, 11200);
        mainLayout.addView(button13, lParams13);   //将按钮放入layout组件
        button13.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 13);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        setContentView(mainLayout);
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
}
