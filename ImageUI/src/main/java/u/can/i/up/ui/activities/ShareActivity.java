package u.can.i.up.ui.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;

/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 分享界面：搭配完成后，分享图片
 */

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ImageView share_image = (ImageView) findViewById(R.id.share_image);
        ImageButton cutout_1_close_btn = (ImageButton)findViewById(R.id.cutout_1_close_btn);


//        final byte[] byteArray = getIntent().getExtras().getByteArray("picture");
//        final Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
//                byteArray.length);
        share_image.setImageBitmap(BitmapCache.getBitmapcache());

        cutout_1_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
