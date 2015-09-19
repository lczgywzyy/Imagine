package u.can.i.up.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;

/**
 * Created by lczgywzyy on 2015/5/11.
 *
 * Use opencv to draw a region
 *
 * 1. Convert bitmap to Mat
 * 2. Draw small circle according to finger's moving
 * 3. Crop the region
 */
public class ImageViewImpl_cutout extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_cutout.class;
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    // 画笔模式下在canvas上画线
    private static final int DRAWPATH = 3;
    int mode = NONE;

    private static final int CIRCLE = 0;
    private static final int SQUARE = 1;
    public int paintShape = SQUARE;

    private static final int PAINTING = 0;
    //对选择的区域进行微调
    private static final int ERASER = 1;
    // 对区域进行擦除
    private static final int RESTORE = 2;
    public int paintType = PAINTING;

    public boolean isDrawing = false;

    int SideLenth = 5;
//    int[] SectionPixels;

    float x_down = 0;
    float y_down = 0;
    float oldDist = 1f;
    float newDist = 1f;
    float deltaScale = 1;
    float lastDeltaScale = 1;
    float totalScale = 1;


    int widthScreen = -1;
    int heightScreen = -1;

    Context mContext;

    private Canvas mCanvas;
    // 用来在缓冲中绘图的canvas
    private Canvas cacheCanvas;
    // 用户区域选择的路径
    private Path path;
    float preX, preY;
    /*  选取区域时调用的画笔
    * */
    private Paint mPaint = new Paint();
    private Paint bmpPaint = new Paint();

    // 橡皮擦模式时用的画笔
    private Paint eraserPaint = new Paint();

    private Bitmap mBitmap, tmp;

    private Matrix matrix = new Matrix();
    private Matrix matrix1 = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private Matrix invertMatrix = new Matrix();
    private PointF mid = new PointF();
    private PointF mid_org = new PointF();
    boolean matrixCheck = false;

    RectF rectMotionPre = null;
    RectF rectMotion = null;

    /*  用来标记点击出现的小圆
    * */
    private Boolean tmpCircleFlag = false;
    private float tmpCircleRadius = 10L;
    private float tmpCircleRadiusRatio = 1L;

    // 设置不同模式下的画笔
    private int alpha = 0;

    public void setmBitmap(Bitmap bitmap){

        mBitmap = bitmap;
        cacheCanvas = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(mBitmap);

        /**
         * 设置画笔模式时的画笔
         */
        // 设置画笔颜色
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setColor(Color.DKGRAY);
        // 设置画笔风格
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        // 反锯齿
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        /**
         * 设置eraser模式时画笔的属性
         */
        // 设置画笔颜色
        eraserPaint = new Paint(Paint.DITHER_FLAG);
        eraserPaint.setColor(Color.YELLOW);
        // 设置画笔风格
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeWidth(15);
        // 反锯齿
        eraserPaint.setAntiAlias(true);
        eraserPaint.setDither(true);

        //记录表情最初的矩形
        rectMotionPre = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        //记录表情当前的矩形
        rectMotion = new RectF(rectMotionPre);

    }

    public ImageViewImpl_cutout(Context context,Bitmap bitmap) {
        super(context);
        mContext = context;


    }

    public ImageViewImpl_cutout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.mCanvas = canvas;

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;

        canvas.drawBitmap(mBitmap, matrix, bmpPaint);
        if (paintType == PAINTING) {
            canvas.drawPath(path, mPaint);
        } else if (paintType == ERASER) {
            canvas.drawPath(path, eraserPaint);
        }



    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tmpCircleFlag = false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            /*  第一个手指按下手势
            * */
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                mode = DRAG;
                x_down = event.getX();
                y_down = event.getY();

                if (isDrawing) {
                    path.moveTo(x_down, y_down);
                    preX = x_down;
                    preY = y_down;
                    if (paintType == RESTORE) {

                    }
                }

                savedMatrix.set(matrix);
                break;
            /*  第二个手指按下手势，与第一个手指的位置共同确定缩放中心。
            * */
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
            /*  第一个手指抬起手势，没有做任何实质操作
            * */
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP " + mode);
                if (isDrawing && paintType != RESTORE) {
                    if (matrix.invert(invertMatrix)) {
                        path.transform(invertMatrix);
                    }
                    if (mode == DRAWPATH && paintType == PAINTING) {
                        cacheCanvas.drawPath(path, mPaint);
                        cacheCanvas.save();
                        cacheCanvas.clipPath(path, Region.Op.XOR);
                        cacheCanvas.drawColor(Color.YELLOW);
                        cacheCanvas.restore();
                    } else if (mode == DRAWPATH && paintType == ERASER) {
                        cacheCanvas.drawPath(path, eraserPaint);
                    }
                    path.reset();
                    invalidate();
                } else if (isDrawing && paintType == RESTORE) {

                }

                /*if(mode == DRAG && x_down == event.getX() && y_down == event.getY()){
                    Log.i(TAG, "Quick Click...");
                    tmpCircleFlag = true;
                    invalidate();
                }*/

                break;
            /*  第二个手指抬起手势，第二个手指的位置能够判定是是否进行了缩放。
            * */
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "ACTION_POINTER_UP");
                if (mode == ZOOM){
                    lastDeltaScale = totalScale;
                    deltaScale = newDist / oldDist;
                    totalScale = totalScale * deltaScale;
                }
                break;
            /* 滑动手势
            * */
            case MotionEvent.ACTION_MOVE:
                /* 缩放模式
                * */
                if (mode == ZOOM){
                    matrix1.set(savedMatrix);
//                    float rotation = rotation(event) - oldRotation;
                    /*  计算距离和缩放比
                    * */
                    newDist = spacing(event);
                    float scale = newDist / oldDist;
//                    Log.i(TAG, "scale:" + scale);
                    /*  使用matrix对图片进行处理
                    * */
                    matrix1.postScale(scale, scale, mid.x, mid.y);// 縮放
//                    matrix1.postRotate(rotation, mid.x, mid.y);// 旋轉
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        /*  使用rectMotion记录现在图片的位置、缩放程度等等；
                        * */
                        matrix.mapRect(rectMotion, rectMotionPre);
                        invalidate();
                    }
                }
                /*  拖拽模式
                * */
                else if(mode == DRAG && !isDrawing){
                    matrix1.set(savedMatrix);
                    matrix1.postTranslate(event.getX() - x_down, event.getY() - y_down);// 平移
                    matrixCheck = matrixCheck();
                    if (matrixCheck == false) {
                        matrix.set(matrix1);
                        matrix.mapRect(rectMotion, rectMotionPre);
                        invalidate();
                    }
                }
                /*  描点模式
                * */
                else if(isDrawing) {
                    mode = DRAWPATH;
                    float newX = event.getX();
                    float newY = event.getY();
                    path.quadTo(preX, preY, newX, newY);
                    preX = event.getX();
                    preY = event.getY();

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

    public Bitmap exportImageByFinger() throws InterruptedException {
        Log.d(TAG, "Test Join " + new Date().getTime());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tmp = BitmapUtils.cropImage(mBitmap, mBitmap.getWidth(), mBitmap.getHeight());
//                invalidate();
                /*String fileName = "/mnt/sdcard/tmp/debug01.png";
                File bitmapFile = new File(fileName);
                FileOutputStream bitmapWtriter = null;
                try {
                    bitmapWtriter = new FileOutputStream(bitmapFile);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                tmp.compress(Bitmap.CompressFormat.PNG, 90, bitmapWtriter);*/
            }
        });
        thread.start();
        thread.join();
        Log.d(TAG, "Test Join() " + new  Date().getTime());
        return tmp;
    }

    public void showImage(){
        /*File file = new File(Environment.getExternalStorageDirectory(), ToPath + "/OUTPUT_11.png");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image*//*");
        mContext.startActivity(intent);*/


//        invalidate();
//        mCanvas.drawBitmap(exportImageByFinger(), 0f, 0f, null);
    }

    public void clear() {
//        path.reset();
//        invalidate();
    }


}
