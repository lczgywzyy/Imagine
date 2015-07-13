package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

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

    static {
        System.loadLibrary("ImageUtils");
    }
    public native static String testString();

    /** @author 李承泽
     *  @param FromPath
     *  @param ToPath
     *  @param WithTransparent 是否需要透明区域
     *  @since 从FromPath中提取图片，并以ToPath保存
     * */
    public static void extractImage(String FromPath, String ToPath, boolean WithTransparent){
        Bitmap firstBmp = BitmapFactory.decodeFile(FromPath);
        Bitmap bmp = Bitmap.createBitmap(firstBmp.getWidth(), firstBmp.getHeight(), Bitmap.Config.ARGB_8888);
        int minX = firstBmp.getWidth();
        int minY = firstBmp.getHeight();
        int maxX = -1;
        int maxY = -1;
/*        for (int i = 0; i < firstBmp.getWidth(); i++){
            for (int j = 0; j < firstBmp.getHeight(); j++){
                int color = firstBmp.getPixel(i, j);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                if (r == 237 && g == 27 && b == 36){

                } else {
                    if( i < minX) minX = i;
                    if( i > maxX) maxX = i;
                    if( j < minY) minY = j;
                    if( j > maxY) maxY = j;
                    bmp.setPixel(i, j, color);
                }
            }
        }*/
        int[] pixels1 = new int[firstBmp.getHeight() * firstBmp.getWidth()];
        int[] pixels2 = new int[firstBmp.getHeight() * firstBmp.getWidth()];
        firstBmp.getPixels(pixels1, 0, firstBmp.getWidth(), 0, 0, firstBmp.getWidth(), firstBmp.getHeight());
        for (int j = 0; j < firstBmp.getHeight(); j++){
            for (int i = 0; i < firstBmp.getWidth(); i++){
                int color = pixels1[j * firstBmp.getWidth() + i];
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                if (r == 237 && g == 27 && b == 36){
                    pixels2[j * firstBmp.getWidth() + i] = 0;
                } else {
                    if( i < minX) minX = i;
                    if( i > maxX) maxX = i;
                    if( j < minY) minY = j;
                    if( j > maxY) maxY = j;
                    pixels2[j * firstBmp.getWidth() + i] = color;
                }
            }
        }
        bmp.setPixels(pixels2, 0, firstBmp.getWidth(), 0, 0, firstBmp.getWidth(), firstBmp.getHeight());

        // Bitmap is entirely transparent
        if((maxX < minX) || (maxY < minY)){
            return;
        }
        File fImage = new File(ToPath);
        try {
            fImage.createNewFile();
            FileOutputStream iStream = new FileOutputStream(fImage);
            if(WithTransparent){
                bmp.compress(Bitmap.CompressFormat.PNG, 100, iStream);
            } else{
                Bitmap tmpBmp = Bitmap.createBitmap(bmp, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
                tmpBmp.compress(Bitmap.CompressFormat.PNG, 100, iStream);
            }
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** @author 李承泽
     *  @param FromBitmap 从Bitmap实例
     *  @param pixels 像素数组
     *  @param ToPath 待保存路径
     *  @param WithTransparent 是否需要透明区域
     *  @since 从FromPath中提取图片，并以ToPath保存
     * */
    public static void extractImageFromBitmapPixels(Bitmap FromBitmap, int[] pixels, String ToPath, boolean WithTransparent){
        Bitmap bmp = Bitmap.createBitmap(FromBitmap.getWidth(), FromBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int minX = FromBitmap.getWidth();
        int minY = FromBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int j = 0; j < FromBitmap.getHeight(); j++){
            for (int i = 0; i < FromBitmap.getWidth(); i++){
                int color = pixels[j * FromBitmap.getWidth() + i];
                if (color == 0){
//                    pixels2[j * FromBitmap.getWidth() + i] = 0;
                } else {
                    if( i < minX) minX = i;
                    if( i > maxX) maxX = i;
                    if( j < minY) minY = j;
                    if( j > maxY) maxY = j;
//                    pixels2[j * FromBitmap.getWidth() + i] = color;
                }
            }
        }
        bmp.setPixels(pixels, 0, FromBitmap.getWidth(), 0, 0, FromBitmap.getWidth(), FromBitmap.getHeight());

        // Bitmap is entirely transparent
        if((maxX < minX) || (maxY < minY)){
            return;
        }
        File fImage = new File(ToPath);
        try {
            fImage.createNewFile();
            FileOutputStream iStream = new FileOutputStream(fImage);
            if(WithTransparent){
                bmp.compress(Bitmap.CompressFormat.PNG, 100, iStream);
            } else{
                Bitmap tmpBmp = Bitmap.createBitmap(bmp, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
                tmpBmp.compress(Bitmap.CompressFormat.PNG, 100, iStream);
            }
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
    /** @author 李承泽
     *  @param canvas 待清空图层
     *  @since 重置图层
     * */
    public static void clearCanvas(Canvas canvas){
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0, 0, 0, 0, clearPaint);
    }
}
