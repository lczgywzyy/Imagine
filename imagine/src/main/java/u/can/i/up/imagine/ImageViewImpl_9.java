package u.can.i.up.imagine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;

import u.can.i.up.utils.image.ImageUtils;

/**
 * Created by lczgywzyy on 2015/5/11.
 */
public class ImageViewImpl_9 extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_9.class;
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    int mode = NONE;

    private static final int CIRCLE = 0;
    private static final int SQUARE = 1;
    private static final int ERASER = 2;
    public static int paintShape = SQUARE;

    public static boolean isDrawing = false;

    int SideLenth = 50;
    int[] SectionPixels;

    float x_down = 0;
    float y_down = 0;
    float oldDist = 1f;
    float newDist = 1f;
    float deltaX = 0;
    float deltaY = 0;
    float deltaScale = 1;
    float lastDeltaScale = 1;
    float totalScale = 1;


    int widthScreen = -1;
    int heightScreen = -1;

    Context mContext;

    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;
    private Bitmap mLayer;
    private Matrix matrix = new Matrix();
    private Matrix matrix1 = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private PointF mid = new PointF();
    private PointF mid_org = new PointF();
    boolean matrixCheck = false;

    public ImageViewImpl_9(Context context) {
        super(context);
        mContext = context;
        mBitmap = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/4.png").getAbsolutePath());
        mLayer = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        SectionPixels = new int[mBitmap.getHeight() * mBitmap.getWidth()];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.mCanvas = canvas;

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;

        canvas.drawBitmap(mBitmap, matrix, null);

        mPaint.setStyle(Paint.Style.STROKE);   //空心
        mPaint.setAlpha(45);   //
        canvas.drawBitmap(mLayer, matrix, mPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 主点按下
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                mode = DRAG;
                x_down = event.getX();
                y_down = event.getY();
                savedMatrix.set(matrix);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "ACTION_POINTER_DOWN");
                mode = ZOOM;
                oldDist = spacing(event);
//                oldRotation = rotation(event);
                savedMatrix.set(matrix);
                mid_org.x = mid.x;
                mid_org.y = mid.y;
                midPoint(mid, event);
                mid_org.x = (mid_org.x == 0) ? mid.x: mid_org.x;
                mid_org.y = (mid_org.y == 0) ? mid.y: mid_org.y;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                if(mode == DRAG && !isDrawing){
                    deltaX += event.getX() - x_down;
                    deltaY += event.getY() - y_down;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "ACTION_POINTER_UP");
                if (mode == ZOOM){
                    lastDeltaScale = totalScale;
                    deltaScale = newDist / oldDist;
                    totalScale = totalScale * deltaScale;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM){
                    matrix1.set(savedMatrix);
//                    float rotation = rotation(event) - oldRotation;
                    newDist = spacing(event);
                    float scale = newDist / oldDist;
                    Log.i(TAG, "scale:" + scale);
                    matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
//                    matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                }else if(mode == DRAG && !isDrawing){
                    matrix1.set(savedMatrix);
                    matrix1.postTranslate(event.getX() - x_down, event.getY() - y_down);// 平移
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                } else if(isDrawing){
                    int newX = (int) event.getX();
                    int newY = (int) event.getY();
//                    int trueX = (int) ((newX - deltaX + 0.5 * (mid.x + mid_org.x) * (totalScale - 1)) / totalScale);
//                    int tureY = (int) ((newY - deltaY + 0.5 * (mid.y + mid_org.y) * (totalScale - 1)) / totalScale);
                    int tureX = (int)((newX - deltaX + (mid.x -(mid_org.x * (1 - lastDeltaScale)) / totalScale) * (totalScale - 1)) / totalScale);
                    int tureY = (int)((newY - deltaY + (mid.y -(mid_org.y * (1 - lastDeltaScale)) / totalScale) * (totalScale - 1)) / totalScale);
                    if (paintShape == CIRCLE){
                        for (int i = 0 - (int) (SideLenth / totalScale); i < (int) (SideLenth / totalScale); i++) {
                            for (int j = 0 - (int) (SideLenth / totalScale); j < (int) (SideLenth / totalScale); j++) {
//                                FloatMath.sqrt(x * x + y * y);
                                if(FloatMath.sqrt(i * i + j * j) < SideLenth / totalScale){
                                    SectionPixels[(tureY + j) * mBitmap.getWidth() + (tureX + i)] = Color.RED;
                                }
                            }
                        }
                        mLayer.setPixels(SectionPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    } else if (paintShape == SQUARE) {
/*                        for (int i = 0 - (int) (SideLenth / deltaScale); i < (int) (SideLenth / deltaScale); i++) {
                            for (int j = 0 - (int) (SideLenth / deltaScale); j < (int) (SideLenth / deltaScale); j++) {
                                int trueX = (int) (i + (newX - deltaX + mid.x * (deltaScale - 1)) / deltaScale);
                                int tureY = (int) (j + (newY - deltaY + mid.y * (deltaScale - 1)) / deltaScale);
//                            int trueX = (int) (i + (newX - deltaX + mid.x * (deltaScale - 1)) / deltaScale - mid.x * (1 - deltaScale) - originX1 * deltaScale);
//                            int tureY = (int) (j + (newY - deltaY + mid.y * (deltaScale - 1)) / deltaScale - mid.y * (1 - deltaScale) - originY1 * deltaScale);
                                if (trueX >= mBitmap.getWidth() || tureY >= mBitmap.getHeight() || trueX < 0 || tureY < 0) {
                                    return false;
                                }
                                mLayer.setPixel(trueX, tureY, Color.RED);
                            }
                        }*/
                        for (int i = 0 - (int) (SideLenth / totalScale); i < (int) (SideLenth / totalScale); i++) {
                            for (int j = 0 - (int) (SideLenth / totalScale); j < (int) (SideLenth / totalScale); j++) {
                                SectionPixels[(tureY + j) * mBitmap.getWidth() + (tureX + i)] = Color.RED;
                            }
                        }
                        mLayer.setPixels(SectionPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    } else if (paintShape == ERASER){
                        for (int i = 0 - (int) (SideLenth / totalScale); i < (int) (SideLenth / totalScale); i++) {
                            for (int j = 0 - (int) (SideLenth / totalScale); j < (int) (SideLenth / totalScale); j++) {
                                SectionPixels[(tureY + j) * mBitmap.getWidth() + (tureX + i)] = 0;
                            }
                        }
                        mLayer.setPixels(SectionPixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }
    private boolean matrixCheck() {
        float[] f = new float[9];
        matrix1.getValues(f);
        // 图片4个顶点的坐标
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * mBitmap.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * mBitmap.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * mBitmap.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * mBitmap.getHeight() + f[5];
        float x4 = f[0] * mBitmap.getWidth() + f[1] * mBitmap.getHeight() + f[2];
        float y4 = f[3] * mBitmap.getWidth() + f[4] * mBitmap.getHeight() + f[5];
        // 图片现宽度
        double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        // 缩放比率判断
        if (width < widthScreen / 3 || width > widthScreen * 3) {
            return true;
        }
        // 出界判断
        if ((x1 < widthScreen / 3 && x2 < widthScreen / 3
                && x3 < widthScreen / 3 && x4 < widthScreen / 3)
                || (x1 > widthScreen * 2 / 3 && x2 > widthScreen * 2 / 3
                && x3 > widthScreen * 2 / 3 && x4 > widthScreen * 2 / 3)
                || (y1 < heightScreen / 3 && y2 < heightScreen / 3
                && y3 < heightScreen / 3 && y4 < heightScreen / 3)
                || (y1 > heightScreen * 2 / 3 && y2 > heightScreen * 2 / 3
                && y3 > heightScreen * 2 / 3 && y4 > heightScreen * 2 / 3)) {
            return true;
        }
        return false;
    }

    // 触碰两点间距离
    private float spacing(MotionEvent event) {
        if(event.getPointerCount() >= 2) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return FloatMath.sqrt(x * x + y * y);
        }else return 0;
    }
    // 取旋转角度
    private float rotation(MotionEvent event) {
        if(event.getPointerCount() >= 2){
            double delta_x = (event.getX(0) - event.getX(1));
            double delta_y = (event.getY(0) - event.getY(1));
            double radians = Math.atan2(delta_y, delta_x);
            return (float) Math.toDegrees(radians);
        }
        else return 0;
    }
    // 取手势中心点
    private void midPoint(PointF point, MotionEvent event) {
        if(event.getPointerCount() >= 2) {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
//            point.set(0, 0);
        }
    }

    // 将移动，缩放以及旋转后的图层保存为新图片
    // 本例中沒有用到該方法，需要保存圖片的可以參考
    public Bitmap CreatNewPhoto() {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(),
                Bitmap.Config.ARGB_8888); // 背景图片
        Canvas canvas = new Canvas(bitmap); // 新建画布
        canvas.drawBitmap(mBitmap, matrix, null); // 画图片
        canvas.save(Canvas.ALL_SAVE_FLAG); // 保存画布
        canvas.restore();
        return bitmap;
    }

    public void exportImageByFinger(){
//        Paint paint = new Paint();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        mCanvas.drawPaint(paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
//        mCanvas.drawBitmap(mBitmap, matrix, mPaint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
//        mCanvas.drawBitmap(mLayer, matrix, mPaint);
        int[] pixels1 = new int[mBitmap.getHeight() * mBitmap.getWidth()];
        mBitmap.getPixels(pixels1, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        int[] pixels2 = new int[mLayer.getHeight() * mLayer.getWidth()];
        mLayer.getPixels(pixels2, 0, mLayer.getWidth(), 0, 0, mLayer.getWidth(), mLayer.getHeight());
        int[] pixels3 = new int[mLayer.getHeight() * mLayer.getWidth()];
        for (int i = 0; i < pixels2.length; i ++){
            if (pixels2[i] != 0){
                pixels3[i] = pixels1[i];
            }
        }
        ImageUtils.extractImageFromBitmapPixels(mBitmap, pixels3, (new File(Environment.getExternalStorageDirectory(), ToPath + "/5.png").getAbsolutePath()), false);
    }

    public void showImage(){
        File file = new File(Environment.getExternalStorageDirectory(), ToPath + "/5.png");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        mContext.startActivity(intent);
    }

    public void clear() {
//        path.reset();
//        invalidate();
    }

//    public int getCurrentPaintColor() {
//        return paint.getColor();
//    }
}
