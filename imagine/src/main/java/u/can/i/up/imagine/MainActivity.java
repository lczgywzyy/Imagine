package u.can.i.up.imagine;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

import u.can.i.up.utils.image.ImageUtils;


public class MainActivity extends ActionBarActivity {

    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    private static final String TAG = "u.can.i.up.imagine." + MainActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageViewImpl myView = new ImageViewImpl(this);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.addView(myView);

//        extractImageTest();
//        combineImageTest();

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
