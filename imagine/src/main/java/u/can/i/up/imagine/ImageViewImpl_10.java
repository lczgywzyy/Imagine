package u.can.i.up.imagine;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_10 extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_10.class;
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

    String savePath = null;

    enum ViewStatus{
        STATUS_ROTATE,
        STATUS_MOVE,
    }

    public ImageViewImpl_10(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        //创建变幻图形用的Matrix
        matrixPaint = new Matrix();
        //创建画笔
        paint = new Paint();
        //画笔抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        //设置画笔绘制空心图形
        paint.setStyle(Paint.Style.STROKE);
        //加载相应的图片资源
        bmpMotion = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/motion.png").getAbsolutePath());
        bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/4.png").getAbsolutePath());
        bmpRotate = BitmapFactory.decodeResource(getResources(), R.drawable.rotate_icon);

        //记录表情最初的矩形
        rectMotionPre = new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight());
        //记录表情当前的矩形
        rectMotion = new RectF(rectMotionPre);
        //标记旋转图标位置的矩形
        rectRotateMark = new RectF(rectMotion.right,
                rectMotion.bottom,
                rectMotion.right + bmpRotate.getWidth() / 2,
                rectMotion.bottom + bmpRotate.getHeight() / 2);
        //记录旋转图标矩形最初的矩形
        rectRotatePre = new RectF(rectRotateMark);
        //记录当前旋转图标位置的矩形
        rectRotate = new RectF(rectRotateMark);
        //记录表情矩形的中点
        pointMotionMid = new PointF(bmpMotion.getWidth() / 2, bmpMotion.getHeight() / 2);
        //记录上次动作的坐标
        prePoint = new PointF();
        //记录当前动作的坐标
        curPoint = new PointF();
        //记录旋转图标中点
        rotateCenterP = new PointF(rectMotion.right, rectMotion.bottom);
        //画布参数
        paintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

        savePath = new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageViewImpl_10_output.png").getAbsolutePath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bmpBack, 0, 0, paint);
        canvas.drawBitmap(bmpMotion, matrixPaint, null);
        canvas.drawBitmap(bmpRotate, null, rectRotate, null);
//		canvas.drawRect(rectPaint, paint);
//		canvas.drawRect(rectRotate, paint);
//		canvas.drawCircle(picMidPoint.x, picMidPoint.y, 5, paint);
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
        }
        return true;
    }

    /**
     * 将当前表情合并到背景并保存
     */
    public void saveBitmap() {
        File f = new File(savePath);
        //使用背景图的宽高创建一张bitmap
        Bitmap bmpSave = Bitmap.createBitmap(bmpBack.getWidth(), bmpBack.getHeight(), Bitmap.Config.ARGB_8888);
        //创建canvas
        Canvas canvas = new Canvas(bmpSave);
        //将背景图和表情画在bitmap上
        canvas.drawBitmap(bmpBack, 0, 0, paint);
        canvas.drawBitmap(bmpMotion, matrixPaint, paint);
        //保存bitmap
//		canvas.save(Canvas.ALL_SAVE_FLAG);
//		canvas.restore();
        try{
            FileOutputStream out = new FileOutputStream(f);
            bmpSave.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bmpBack.recycle();
//        bmpBack = bmpSave;
//        //重置Matrix
//        matrixPaint.reset();
//        //重置旋转图标
//        rectRotate.set(rectRotatePre);
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
