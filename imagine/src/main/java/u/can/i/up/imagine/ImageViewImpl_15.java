package u.can.i.up.imagine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.utils.image.ImageUtils;

/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_15 extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_15.class;
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    Context context = null;
    Matrix matrixPaint = null;
    Bitmap bmpMotion = null;
    Bitmap bmpRotate = null;
    Bitmap bmpBack = null;

    RectF rectMotionPre = null;
    RectF rectMotion = null;
    RectF rectRotateMark = null;
    RectF rectRotatePre = null;
    RectF rectRotate = null;

    Paint paint = null;
    PaintFlagsDrawFilter paintFilter = null;
    ViewStatus status = ViewStatus.STATUS_MOVE;

    PointF pointMotionMid = null;
    PointF prePoint = null;
    PointF curPoint = null;
    PointF rotateCenterP = null;

    String savePathAll = null;
    String savePathCovered = null;

    enum ViewStatus{
        STATUS_ROTATE,
        STATUS_MOVE,
    }
    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }
    public ImageViewImpl_15(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        //创建画笔
        paint = new Paint();
        //画笔抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        //设置画笔绘制空心图形
        paint.setStyle(Paint.Style.STROKE);
        //加载相应的图片资源
        bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageView15" + "/bg.png").getAbsolutePath());
        //画布参数
        paintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        Paint tmpPaint = new Paint();
        tmpPaint.setAlpha(70);

        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bmpBack, 0, 0, paint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            //手指按下的时候
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                testCircle();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return true;
    }

    private void testCircle(){
        int iCannyUpperThreshold = 100;
        int iMinRadius = 20;
        int iMaxRadius = 400;
        int iAccumulator = 300;

        Mat thresholdImage = Imgcodecs.imread(new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageView15" + "/bg.png").getAbsolutePath(), Imgproc.COLOR_BGR2GRAY);
//        thresholdImage = Imgproc.filter2D();
        //http://stackoverflow.com/questions/30700537/android-opencv-detecting-circles-of-a-color-gives-image-must-be-8-bit-single
        Mat circles = new Mat();
        Imgproc.HoughCircles(thresholdImage, circles, Imgproc.COLOR_BGR2GRAY,
                2.0, thresholdImage.rows() / 8, iCannyUpperThreshold, iAccumulator,
                iMinRadius, iMaxRadius);

        if (circles.cols() > 0)
            for (int x = 0; x < circles.cols(); x++)
            {
                double vCircle[] = circles.get(0,x);

                if (vCircle == null)
                    break;

                Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
                int radius = (int)Math.round(vCircle[2]);

                // draw the found circle
//                Core.circle(destination, pt, radius, new Scalar(0, 255, 0), iLineThickness);
//                Core.circle(destination, pt, 3, new Scalar(0,0,255), iLineThickness);
            }
    }
}
