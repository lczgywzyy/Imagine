package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import u.can.i.up.ui.R;
import u.can.i.up.ui.beans.PearlBeans;
import u.can.i.up.ui.utils.IBitmapCache;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 素材详细页面：展示素材详细信息
 */

public class LibiraryDetailActivity extends AppCompatActivity {

    private PearlBeans pearlBeans;

    private TextView textName;

    private ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libirary_detail);
        setBar();
        getData();
        setViews();
    }
    private void setBar(){
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.library_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setViews(){
        textName=(TextView)findViewById(R.id.name);
        Image=(ImageView)findViewById(R.id.detail_image);
        textName.setText(pearlBeans.getName());
        IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(Image);
        bitmapAsync.execute(pearlBeans.getPath(),pearlBeans.getMD5(),"img");
    }

    private void getData(){
        pearlBeans=getIntent().getParcelableExtra("pearl");
    }
}
