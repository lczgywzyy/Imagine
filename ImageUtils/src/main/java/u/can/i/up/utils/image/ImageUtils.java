package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lczgywzyy on 2015/5/10.
 */
public class ImageUtils {
    public static void extractImage(String FromPath, String ToPath){
        Bitmap firstBmp = BitmapFactory.decodeFile(FromPath);
        Bitmap bmp = Bitmap.createBitmap(firstBmp.getWidth(), firstBmp.getHeight(), Bitmap.Config.ARGB_8888);
        for (int i = 0; i < firstBmp.getWidth(); i++){
            for (int j = 0; j < firstBmp.getHeight(); j++){
                int color = firstBmp.getPixel(i, j);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                if (r == 237 && g == 27 && b == 36){

                } else {
                    bmp.setPixel(i, j, color);
                }
            }
        }
        File fImage = new File(ToPath);
        try {
            fImage.createNewFile();
            FileOutputStream iStream = new FileOutputStream(fImage);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, iStream);
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(String FromPath, String ToPath) {
        try {
            InputStream fosfrom = new FileInputStream(FromPath);
            OutputStream fosto = new FileOutputStream(ToPath);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
