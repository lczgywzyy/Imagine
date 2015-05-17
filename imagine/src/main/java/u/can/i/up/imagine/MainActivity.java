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

        Button firstButton = new Button(this);
        firstButton.setId(10001);
        firstButton.setText("测试1:描点测试");
        RelativeLayout.LayoutParams lParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        mainLayout.addView(firstButton, lParams1);   //将按钮放入layout组件
        firstButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 1);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button secondButton = new Button(this);
        secondButton.setId(10002);
        secondButton.setText("测试2:拖拽测试");
        RelativeLayout.LayoutParams lParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams2.addRule(RelativeLayout.BELOW, 10001);
        mainLayout.addView(secondButton, lParams2);   //将按钮放入layout组件
        secondButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 2);
                intent.putExtras(bundleSimple);
                startActivity(intent);
            }
        });

        Button thirdButton = new Button(this);
        thirdButton.setId(10003);
        thirdButton.setText("测试3:Flood填充测试");
        RelativeLayout.LayoutParams lParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//这个属性是设置空间的长宽，其实还可以设置其他的控件的其他属性；
        lParams3.addRule(RelativeLayout.BELOW, 10002);
        mainLayout.addView(thirdButton, lParams3);   //将按钮放入layout组件
        thirdButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawPaintActivity.class);
                Bundle bundleSimple = new Bundle();
                bundleSimple.putInt("BUTTON", 3);
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
