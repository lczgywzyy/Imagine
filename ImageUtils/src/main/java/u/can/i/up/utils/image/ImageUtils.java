package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lczgywzyy on 2015/5/10.
 */
public class ImageUtils {

    /** @author 李承泽
     *  @param FromPath
     *  @param ToPath
     *  @since 从FromPath中提取图片，并以ToPath保存
     * */
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

    /** @author 李承泽
     *  @param FromPath
     *  @param ToPath
     *  @since 从FromPath拷贝到ToPath中
     * */
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
    /** @author 李承泽
     *  @param lowImgPath 下层图片，即背景
     *  @param upImgPath  上层图标，即要写入的图片
     *  @param newImgPath 生成图片位置
     *  @since 把两个图片合并，生成新图片
     * */
    public static void combineImage(String upImgPath, String lowImgPath, String newImgPath){
        Bitmap upImg = BitmapFactory.decodeFile(upImgPath);
        Bitmap lowImg = BitmapFactory.decodeFile(lowImgPath);
        Bitmap newBitMap = Bitmap.createBitmap(lowImg.getWidth(), lowImg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitMap);
        canvas.drawBitmap(lowImg, new Matrix(), null);
        canvas.drawBitmap(upImg, 0, 0, null);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newImgPath);
            newBitMap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
