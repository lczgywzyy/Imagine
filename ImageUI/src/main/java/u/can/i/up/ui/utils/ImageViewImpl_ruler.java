package u.can.i.up.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.utils.image.ImageAlgrithms;
import u.can.i.up.utils.image.ViewStatus;

/**
 * @data 2015/7/14
 * @author dongfeng
 * @sumary 搭配界面中，搭配部分的画图工具
 *
 */
public class ImageViewImpl_ruler extends ImageView {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_ruler.class;
    private static final ScaleType[] VALID_SCALE_TYPES = new ScaleType[]{ScaleType.FIT_CENTER, ScaleType.CENTER_INSIDE, ScaleType.FIT_CENTER};
    public static final int DEFAULT_SCALE_TYPE_INDEX = 0;
    private ScaleType mScaleType = VALID_SCALE_TYPES[DEFAULT_SCALE_TYPE_INDEX];

    private Paint mainPaint = null;

    /**
     * Bitmap Back  底图
     * Bitmap Motion 素材图片
     * Bitmap Rotate. 控制图标
     * Bitmap Delete 删除图标
     */

    private Bitmap bmpBack = null;
    private Bitmap bmpRulerL = null;
    private Bitmap bmpRulerR = null;
    private Bitmap bmpRotate = null;
    private Bitmap bmpSlide = null;

    Context context = null;

    //图片变换矩阵
    Matrix matrixPaint = null;

    Matrix matrixBack=null;

    //图片变换点阵集合
    RectF rectRulerLPre = new RectF();
    RectF rectRulerL = new RectF();
    RectF rectRulerRPre = new RectF();
    RectF rectRulerR = new RectF();
    RectF rectRotateMark = new RectF();
    RectF rectRotatePre = new RectF();
    RectF rectRotate = new RectF();
    RectF rectSlideMark = new RectF();
    RectF rectSlidePre = new RectF();
    RectF rectSlide = new RectF();

    PaintFlagsDrawFilter paintFilter = null;
    ViewStatus status = ViewStatus.STATUS_MOVE;
    // 记录图片中心点
    PointF pointMotionMid = new PointF();
    PointF prePoint = new PointF();
    PointF curPoint = new PointF();
    PointF rotateCenterP = new PointF();

    float canvas_width_pre;
    float canvas_height_pre;
    float bitmap_width_pre ;
    float bitmap_height_pre;

    float scale_factor=1f;

    public ImageViewImpl_ruler(Context context) {
        super(context);
        init(context);
    }

    public ImageViewImpl_ruler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /** TODO 当且仅当构造函数中可以调用init()
     * */
    private void init(Context context) {
        //创建变幻图形用的Matrix
        matrixPaint = new Matrix();
        //创建画笔
        mainPaint = new Paint();
        //画笔抗锯齿
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(Color.BLUE);
        //设置画笔绘制空心图形
        mainPaint.setStyle(Paint.Style.STROKE);
        //加载相应的图片资源
        bmpBack = BitmapCache.getBitmapcache();

        bmpRulerL = BitmapFactory.decodeResource(getResources(), R.drawable.ruler_with_line_left);
        bmpRulerR = BitmapFactory.decodeResource(getResources(), R.drawable.ruler_with_line_right);
        bmpRotate = BitmapFactory.decodeResource(getResources(), R.drawable.rotate_icon);
        bmpSlide = BitmapFactory.decodeResource(getResources(), R.drawable.rotate_slide);
        {
            //记录最初左侧尺子的矩形
            rectRulerLPre = new RectF(0, 0, bmpRulerL.getWidth(), bmpRulerL.getHeight());
            //记录当前左侧尺子的矩形
            rectRulerL = new RectF(rectRulerLPre);
            //记录最初左侧尺子的矩形
            rectRulerRPre = new RectF(0, 0, bmpRulerR.getWidth(), bmpRulerR.getHeight());
            //记录当前左侧尺子的矩形
            rectRulerR = new RectF(rectRulerRPre);

            //标记旋转图标位置的矩形
            rectRotateMark = new RectF(rectRulerR.right - bmpRotate.getWidth() * 0.3f,
                    rectRulerR.bottom - bmpRotate.getHeight() * 0.3f,
                    rectRulerR.right + bmpRotate.getWidth() * 0.7f,
                    rectRulerR.bottom + bmpRotate.getHeight() * 0.7f);
            //记录旋转图标矩形最初的矩形
            rectRotatePre = new RectF(rectRotateMark);
            //记录当前旋转图标位置的矩形
            rectRotate = new RectF(rectRotateMark);

            //标记滑动图标位置的矩形
            rectSlideMark = new RectF((rectRulerR.right + rectRulerR.left) * 0.5f,
                    rectRulerR.bottom - bmpSlide.getWidth() * 1.3f,
                    (rectRulerR.right + rectRulerR.left) * 0.5f + bmpSlide.getWidth(),
                    rectRulerR.bottom - bmpSlide.getWidth() * 0.3f);
            //记录滑动图标矩形最初的矩形
            rectSlidePre = new RectF(rectSlideMark);
            //记录当前滑动图标位置的矩形
            rectSlide = new RectF(rectSlideMark);

            //记录表情矩形的中点
//            pointMotionMid = new PointF(bmpMotion.getWidth() / 2, bmpMotion.getHeight() / 2);
            //记录上次动作的坐标
            prePoint = new PointF();
            //记录当前动作的坐标
            curPoint = new PointF();
            //记录旋转图标中点
            rotateCenterP = new PointF((rectRotate.left + rectRotate.right) / 2, (rectRotate.top + rectRotate.bottom) / 2);
            matrixPaint.reset();
        }
        //画布参数
        paintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        this.context=context;
        initTranslate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFilter);
        canvas.drawBitmap(bmpRulerL, matrixPaint, null);
        canvas.drawBitmap(bmpRulerR, matrixPaint, null);
        canvas.drawBitmap(bmpRotate, null, rectRotate, null);
        canvas.drawBitmap(bmpSlide, null, rectSlide, null);
    }
    public int getMaxWidth(){
        return IApplicationConfig.DeviceWidth;
    }

    public int getMaxHeight(){
        return UtilsDevice.dip2px(360);
    }

    private void initTranslate(){
        canvas_width_pre = getMaxWidth();
        canvas_height_pre = getMaxHeight();
        bitmap_width_pre = bmpBack.getWidth();
        bitmap_height_pre = bmpBack.getHeight();
        //背景位图矩阵缩放
        scale_factor = bitmap_width_pre / canvas_width_pre > bitmap_height_pre / canvas_height_pre ? bitmap_width_pre / canvas_width_pre : bitmap_height_pre / canvas_height_pre;
        matrixBack =new Matrix();
        matrixBack.postScale(1 / scale_factor, 1 / scale_factor);
        Bitmap bitmapNew = Bitmap.createBitmap(bmpBack, 0, 0, bmpBack.getWidth(), bmpBack.getHeight(), matrixBack, true);
        bmpBack=bitmapNew;
        this.setImageBitmap(bmpBack);
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
                RectF rectR=new RectF(rectRotate);
                //rectR.offset(bitmap_translate_x,bitmap_translate_y);
                if(ImageAlgrithms.isInRect(x, y, rectR)){
                    status = ViewStatus.STATUS_ROTATE;
                }else if(ImageAlgrithms.isInRect(x, y, rectRulerL) || ImageAlgrithms.isInRect(x, y, rectRulerR)){
                    status = ViewStatus.STATUS_MOVE;
                }else{
                    status = ViewStatus.STATUS_MOVE;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(status == ViewStatus.STATUS_ROTATE){
//                    saveBitmap();
                } else if(status == ViewStatus.STATUS_DELETE){
//                    deleteCurrentMotion();
                } else if(status == ViewStatus.STATUS_OK){
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                curPoint.x = x;
                curPoint.y = y;
                if(status == ViewStatus.STATUS_ROTATE){
                    rectRotateMark.set(x - bmpRotate.getWidth() / 2,
                            y - bmpRotate.getHeight() / 2,
                            x + bmpRotate.getWidth() / 2,
                            y + bmpRotate.getHeight() / 2);
                    //获取旋转的角度
                    float de = ImageAlgrithms.getPointsDegree(prePoint, pointMotionMid, curPoint);
                    //获取缩放的比例
                    float re = ImageAlgrithms.getPointsDistance(pointMotionMid, curPoint) / ImageAlgrithms.getPointsDistance(pointMotionMid, prePoint);
                    if(re > 0.0001){
                        //对Matrix进行缩放
                        matrixPaint.postScale(re, re, pointMotionMid.x, pointMotionMid.y);
                    }
                    if(de > 0.0001 || de < -0.0001){
                        //对Matrix进行旋转
                        matrixPaint.postRotate(de, pointMotionMid.x, pointMotionMid.y);
                    }
                }else if(status == ViewStatus.STATUS_MOVE) {
                    //对Matrix进行移位
                    matrixPaint.postTranslate(x - prePoint.x, y - prePoint.y);

                }
                prePoint.x = x;
                prePoint.y = y;
                //将矩阵map到表情矩形上
//                if (!isOutOfBounds()) {
                matrixPaint.mapRect(rectRulerL, rectRulerLPre);
                matrixPaint.mapRect(rectRulerR, rectRulerRPre);
                matrixPaint.mapRect(rectSlide, rectSlidePre);
//                }
                matrixPaint.mapRect(rectRotateMark, rectRotatePre);
                ImageAlgrithms.getRectCenter(rectRotateMark, rotateCenterP);
//                ImageAlgrithms.getRectCenter(rectMotion, pointMotionMid);
                float[] pts = new float[2];
                Matrix tmpMatrix = new Matrix();
                PointF tmpCenterPoint = new PointF();
                tmpCenterPoint.x = (rectRulerL.left + rectRulerR.right) / 2;
                tmpCenterPoint.y = (rectRulerL.top + rectRulerR.bottom) / 2;
                pts[0] = rotateCenterP.x;
                pts[1] = rotateCenterP.y;
                tmpMatrix.postRotate(180, tmpCenterPoint.x, tmpCenterPoint.y);
                tmpMatrix.mapPoints(pts);
                pointMotionMid.x = pts[0];
                pointMotionMid.y = pts[1];
                rectRotate.set(rotateCenterP.x - bmpRotate.getWidth() * 0.5f,
                        rotateCenterP.y - bmpRotate.getHeight() * 0.5f,
                        rotateCenterP.x + bmpRotate.getWidth() * 0.5f,
                        rotateCenterP.y + bmpRotate.getHeight() * 0.5f);
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }

//    private Boolean isOutOfBounds(){
//        float left= (rectRulerL.left < rectRulerR.left) ? rectRulerL.left: rectRulerR.left;
//        float right= (rectRulerL.right > rectRulerR.right) ? rectRulerL.right: rectRulerR.right;
//
//        float top= (rectRulerL.top < rectRulerR.top) ? rectRulerR.top: rectRulerL.top;
//        float bottom= (rectRulerL.bottom < rectRulerR.bottom) ? rectRulerR.bottom: rectRulerL.bottom;
//
//        Log.e("top",String.valueOf(top));
//
//        if(0>left){
//            rectMotion.left=0;
//            return true;
//        }
//        if(getWidth()<right){
//            rectMotion.right=getWidth();
//            return true;
//        }
//        if(0>top){
//            rectMotion.top=0;
//            return true;
//        }
//        if(getHeight()<bottom){
//            rectMotion.bottom=getHeight();
//            return true;
//        }
//        return false;
//    }

//    public void setBmpMotion(Bitmap mbitmap){
////        if(bmpMotion != null){
////            Pearl tmpPearl = new Pearl(bmpMotion, matrixPaint);
////            mPearlList.add(tmpPearl);
////        }
//        bmpMotion = mbitmap;
//        if(bmpMotion != null){
//
//            //记录表情最初的矩形
//            rectMotionPre = new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight());
//            //记录表情当前的矩形
//            rectMotion = new RectF(rectMotionPre);
//            //标记旋转图标位置的矩形
//            rectRotateMark = new RectF(rectMotion.right - bmpRotate.getWidth() * 0.3f,
//                    rectMotion.bottom - bmpRotate.getHeight() * 0.3f,
//                    rectMotion.right + bmpRotate.getWidth() * 0.7f,
//                    rectMotion.bottom + bmpRotate.getHeight() * 0.7f);
//            //记录旋转图标矩形最初的矩形
//            rectRotatePre = new RectF(rectRotateMark);
//            //记录当前旋转图标位置的矩形
//            rectRotate = new RectF(rectRotateMark);
//
//            //记录表情矩形的中点
//            pointMotionMid = new PointF(bmpMotion.getWidth() / 2, bmpMotion.getHeight() / 2);
//            //记录上次动作的坐标
//            prePoint = new PointF();
//            //记录当前动作的坐标
//            curPoint = new PointF();
//            //记录旋转图标中点
//            rotateCenterP = new PointF(rectMotion.right, rectMotion.bottom);
//
//            matrixPaint.reset();
//        }
//        invalidate();
//    }

}
