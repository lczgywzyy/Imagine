package u.can.i.up.imagine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;

import u.can.i.up.utils.image.FloodFillAsyncTask;
import u.can.i.up.utils.image.ImageAlgrithms;

/**
 * Created by lczgywzyy on 2015/5/11.
 */
public class ImageViewImpl_3 extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_3.class;
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    int mode = NONE;

    float x_down = 0;
    float y_down = 0;
    float oldDist = 1f;
    float oldRotation = 0;

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
    boolean matrixCheck = false;

    public ImageViewImpl_3(Context context) {
        super(context);
        mContext = context;

        Bitmap mOriginBitmap = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/3.png").getAbsolutePath());
        int[] pixels1 = new int[mOriginBitmap.getHeight() * mOriginBitmap.getWidth()];
        mOriginBitmap.getPixels(pixels1, 0, mOriginBitmap.getWidth(), 0, 0, mOriginBitmap.getWidth(), mOriginBitmap.getHeight());

        mBitmap = Bitmap.createBitmap(mOriginBitmap.getWidth(), mOriginBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.setPixels(pixels1, 0, mOriginBitmap.getWidth(), 0, 0, mOriginBitmap.getWidth(), mOriginBitmap.getHeight());
        mLayer = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.mCanvas = canvas;

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;

        canvas.drawBitmap(mBitmap, matrix, null);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
//        mPaint.setStyle(Paint.Style.STROKE);   //空心
//        mPaint.setAlpha(45);   //
//        canvas.drawBitmap(mLayer, matrix, mPaint);
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
//                savedMatrix.set(matrix);
//                ImageAlgrithms.FloodFill(mBitmap, new Point((int)x_down, (int)y_down), mBitmap.getPixel((int)x_down, (int)y_down) , Color.RED);
                new FloodFillAsyncTask(mBitmap, new Point((int)x_down, (int)y_down), mBitmap.getPixel((int)x_down, (int)y_down) , Color.RED).execute();
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "ACTION_POINTER_DOWN");
                mode = ZOOM;
                oldDist = spacing(event);
                oldRotation = rotation(event);
                savedMatrix.set(matrix);
                midPoint(mid, event);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                if (mode == ZOOM){
                    matrix1.set(savedMatrix);
                    float rotation = rotation(event) - oldRotation;
                    float newDist = spacing(event);
                    float scale = newDist / oldDist;
                    matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
                    matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        invalidate();
                    }
//                }else if(mode == DRAG){
//                    matrix1.set(savedMatrix);
//                    matrix1.postTranslate(event.getX() - x_down, event.getY()
//                            - y_down);// 平移
//                    matrixCheck = matrixCheck();
//                    matrixCheck = matrixCheck();
//                    if (matrixCheck == false) {
//                        matrix.set(matrix1);
//                        invalidate();
//                    }
                } else {
                    int newX = (int) event.getX();
                    int newY = (int) event.getY();
//                    for (int i = -30; i < 30; i++) {
//                        for (int j = -30; j < 30; j++) {
//                            if ((i + newX) >= mBitmap.getWidth() || j + newY >= mBitmap.getHeight() || i + newX < 0 || j + newY < 0) {
//                                return false;
//                            }
//                            mLayer.setPixel(i + newX, j + newY, Color.RED);
//                            invalidate();
//                        }
//                    }
                    ImageAlgrithms.FloodFill(mBitmap, new Point(newX, newY), mBitmap.getPixel(newX, newY) , Color.RED);
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

    public void clear() {
//        path.reset();
//        invalidate();
    }

//    public int getCurrentPaintColor() {
//        return paint.getColor();
//    }
}
