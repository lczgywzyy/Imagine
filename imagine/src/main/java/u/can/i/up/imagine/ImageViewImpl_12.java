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
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.utils.image.ImageUtils;

/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_12 extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_12.class;
    private static final String FromPath = ".1FromPath/ImageView12";
    private static final String ToPath = ".2ToPath/ImageView12";

    private float mScale = 0.8f;

    Context context = null;
//    Matrix matrixPaint = null;
    Matrix matrixCirclePaint = null;
    Bitmap bmpCircle = null;
    Bitmap bmpBack = null;

    RectF rectCirclePre = null;
    RectF rectCircle = null;

    Paint paint = null;
    PaintFlagsDrawFilter paintFilter = null;
    ViewStatus status = ViewStatus.STATUS_MOVE;

    PointF pointCircleMid = null;
    PointF pointBgMid = null;
    PointF prePoint = null;
    PointF curPoint = null;

    enum ViewStatus{
        STATUS_ROTATE,
        STATUS_MOVE,
    }

    public ImageViewImpl_12(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        //创建变幻图形用的Matrix
        matrixCirclePaint = new Matrix();
        //创建画笔
        paint = new Paint();
        //画笔抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        //设置画笔绘制空心图形
        paint.setStyle(Paint.Style.STROKE);
        //加载相应的图片资源
        bmpCircle = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageView12_circle2.png").getAbsolutePath());
        bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageView12_bg2.png").getAbsolutePath());

        //记录绳子最初的矩形
        rectCirclePre = new RectF(0, 0, bmpCircle.getWidth(), bmpCircle.getHeight());
        //记录绳子当前的矩形
        rectCircle = new RectF(rectCirclePre);

        //记录绳子矩形的中点
        pointCircleMid = new PointF(bmpCircle.getWidth() / 2, bmpCircle.getHeight() / 2);
        //记录背景矩形的中点
        pointBgMid = new PointF(bmpBack.getWidth() / 2, bmpBack.getHeight() / 2);

        //记录上次动作的坐标
        prePoint = new PointF();
        //记录当前动作的坐标
        curPoint = new PointF();
        //画布参数
        paintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

        /* 对绳子图片进行平移，使两中点重合，然后以中点来缩放比例
        * */
        matrixCirclePaint.postTranslate(pointBgMid.x - pointCircleMid.x, pointBgMid.y - pointCircleMid.y);
        matrixCirclePaint.postScale(mScale, mScale, pointBgMid.x, pointBgMid.y);
        matrixCirclePaint.mapRect(rectCircle, rectCirclePre);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.setDrawFilter(paintFilter);

        /* 绘制背景
        * */
        canvas.drawBitmap(bmpBack, 0, 0, paint);
        canvas.drawBitmap(bmpCircle, matrixCirclePaint, null);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
       /* float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            //手指按下的时候
            case MotionEvent.ACTION_DOWN:
                prePoint.x = x;
                prePoint.y = y;
                //按到了旋转图标上
                if(isInRect(x, y, rectRotate)){
                    status = ViewStatus.STATUS_ROTATE;
                }else{
                    status = ViewStatus.STATUS_MOVE;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(status == ViewStatus.STATUS_ROTATE){
//                    saveBitmap();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                curPoint.x = x;
                curPoint.y = y;
                if(status == ViewStatus.STATUS_ROTATE){
                    rectRotateMark.set(x,
                            y,
                            x + bmpRotate.getWidth() / 2,
                            y + bmpRotate.getHeight() / 2);
                    //获取旋转的角度
                    float de = getPointsDegree(prePoint, pointMotionMid, curPoint);
                    //获取缩放的比例
                    float re = getPointsDistance(pointMotionMid, curPoint) / getPointsDistance(pointMotionMid, prePoint);
                    if(re > 0.0001){
                        //对Matrix进行缩放
                        matrixPaint.postScale(re, re, pointMotionMid.x, pointMotionMid.y);
                    }
                    if(de > 0.0001 || de < -0.0001){
                        //对Matrix进行旋转
                        matrixPaint.postRotate(de, pointMotionMid.x, pointMotionMid.y);
                    }
                }else if(status == ViewStatus.STATUS_MOVE){
                    //对Matrix进行移位
                    matrixPaint.postTranslate(x - prePoint.x, y - prePoint.y);
                }
                prePoint.x = x;
                prePoint.y = y;
                //将矩阵map到表情矩形上
                matrixPaint.mapRect(rectMotion, rectMotionPre);
                matrixPaint.mapRect(rectRotateMark, rectRotatePre);
                getRectCenter(rectRotateMark, rotateCenterP);
                getRectCenter(rectMotion, pointMotionMid);
                rectRotate.set(rotateCenterP.x,
                        rotateCenterP.y,
                        rotateCenterP.x + bmpRotate.getWidth() / 2,
                        rotateCenterP.y + bmpRotate.getHeight() / 2);
                postInvalidate();
                break;
            default:
                break;
        }*/
        return true;
    }

    //根据矩形获取中心点
    private void getRectCenter(RectF rect, PointF p){
        p.x = rect.left + (rect.right - rect.left) / 2;
        p.y = rect.top + (rect.bottom - rect.top) / 2;
    }

    //判断点是否在矩形内
    private boolean isInRect(float x, float y, RectF rect){
        boolean ret = false;
        if(x > rect.left && x < rect.right && y > rect.top && y < rect.bottom){
            ret = true;
        }
        return ret;
    }

    //求两点间距离
    private float getPointsDistance(PointF p1, PointF p2) {
        float ret = (float) Math.sqrt(Math.abs((p1.x - p2.x) * (p1.x - p2.x)
                + (p1.y - p2.y) * (p1.y - p2.y)));
        return ret;
    }

    //求角ABC
    private float getPointsDegree(PointF a, PointF b, PointF c){
        if(Math.abs(a.x - c.x) < 2 && Math.abs(a.y - c.y) < 2){
            return 0.0f;
        }
        float ret = (float) (  Math.toDegrees(Math.atan2(c.y - b.y, c.x - b.x)
                - Math.atan2(a.y - b.y, a.x - b.x)));
        return ret;
    }
}
