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
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cropper.cropwindow.edge.Edge;
import cropper.util.PaintUtil;
import u.can.i.up.ui.R;
import u.can.i.up.utils.image.Pearl;

/**
 * @data 2015/7/14
 * @author dongfeng
 * @sumary 搭配界面中，搭配部分的画图工具
 *
 */
public class ImageViewImpl_allocate extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_allocate.class;

    /**
     * The Paint used to draw the guidelines .
     */
    private Paint mGuidelinePaint;
    /**
     * The Paint used to draw the white rectangle around the crop area.
     */
    private Paint mBorderPaint;
    private Paint mainPaint = null;

    /**
     * Bitmap Back  底图
     * Bitmap Motion 素材图片
     * Bitmap Rotate. 控制图标
     * Bitmap Delete 删除图标
     */

    private Bitmap bmpBack = null;
    private Bitmap bmpMotion = null;
    private Bitmap bmpRotate = null;
    private Bitmap bmpDelete = null;

    Context context = null;

    //图片变换矩阵
    Matrix matrixBack = new Matrix();
    Matrix matrixPaint = null;

    //图片变换点阵集合
    RectF rectBack = new RectF();
    RectF rectMotionPre = new RectF();
    RectF rectMotion = new RectF();
    RectF rectRotateMark = new RectF();
    RectF rectRotatePre = new RectF();
    RectF rectRotate = new RectF();
    RectF rectDeleteMark = new RectF();
    RectF rectDeletePre = new RectF();
    RectF rectDelete = new RectF();


    PaintFlagsDrawFilter paintFilter = null;
    ViewStatus status = ViewStatus.STATUS_MOVE;
    // 记录图片中心点
    PointF pointMotionMid = new PointF();
    PointF prePoint = new PointF();
    PointF curPoint = new PointF();
    PointF rotateCenterP = new PointF();
    PointF deleteCenterP = new PointF();

    //记录所有珠子布局的List
    List<Pearl> mPearlList = new ArrayList<Pearl>();

    enum ViewStatus{
        STATUS_ROTATE,
        STATUS_DELETE,
        STATUS_MOVE,
        STATUS_SWITCH
    }

    public ImageViewImpl_allocate(Context context) {
        super(context);
        init(context);
    }

    public ImageViewImpl_allocate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /** TODO 当且仅当构造函数中可以调用init()
     * */
    private void init(Context context) {

        //PearList重置
        mPearlList.clear();

        mGuidelinePaint = PaintUtil.newGuidelinePaint();
        mBorderPaint = PaintUtil.newBorderPaint(context);
//     mGuidelines = CropImageView.DEFAULT_GUIDELINES;
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
//        bmpMotion = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/motion10.png").getAbsolutePath());
//        bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/ImageView10_bg.png").getAbsolutePath());
//        bmpMotion = BitmapFactory.decodeResource(getResources(), R.drawable.emoji_1);
        bmpBack = BitmapCache.getBitmapcache();
        RectF tmpRectBack = new RectF(0, 0, bmpBack.getWidth(), bmpBack.getHeight());
        rectBack = new RectF(tmpRectBack);
        //背景图片缩放比例
        matrixBack.postScale(BitmapCache.getBackBmpScale(), BitmapCache.getBackBmpScale(), 0, 0);
        matrixBack.postTranslate(BitmapCache.getBackBmpTranslateX(), BitmapCache.getBackBmpTranslateY());
        matrixBack.mapRect(rectBack, tmpRectBack);

        bmpRotate = BitmapFactory.decodeResource(getResources(), R.drawable.rotate_icon);
        bmpDelete = BitmapFactory.decodeResource(getResources(), R.drawable.delete_icon);
//
//        if(bmpMotion != null){
//            //记录表情最初的矩形
//            rectMotionPre = new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight());
//            //记录表情当前的矩形
//            rectMotion = new RectF(rectMotionPre);
//            //标记旋转图标位置的矩形
//            rectRotateMark = new RectF(rectMotion.right,
//                    rectMotion.bottom,
//                    rectMotion.right + bmpRotate.getWidth() / 2,
//                    rectMotion.bottom + bmpRotate.getHeight() / 2);
//            //标记删除图标位置的矩形
//            rectDeleteMark = new RectF(rectMotion.right,
//                    rectMotion.top,
//                    rectMotion.right + bmpDelete.getWidth() / 2,
//                    rectMotion.top - bmpDelete.getHeight() / 2);
//            //记录旋转图标矩形最初的矩形
//            rectRotatePre = new RectF(rectRotateMark);
//            //记录当前旋转图标位置的矩形
//            rectRotate = new RectF(rectRotateMark);
//
//            //记录删除图标矩形最初的矩形
//            rectDeletePre = new RectF(rectDeleteMark);
//            //记录当前删除图标矩形位置的矩形
//            rectDelete = new RectF(rectDeletePre);
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
//        }
        //画布参数
        paintFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//
//        mHandleRadius = HandleUtil.getTargetRadius(context);
//
//        mSnapRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                SNAP_RADIUS_DP,
//                displayMetrics);

//        mBorderPaint = PaintUtil.newBorderPaint(context);
//        mGuidelinePaint = PaintUtil.newGuidelinePaint();
//        mBackgroundPaint = PaintUtil.newBackgroundPaint(context);
//        mCornerPaint = PaintUtil.newCornerPaint(context);

//        // Sets the values for the corner sizes
//        mCornerOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                DEFAULT_CORNER_OFFSET_DP,
//                displayMetrics);
//        mCornerExtension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                DEFAULT_CORNER_EXTENSION_DP,
//                displayMetrics);
//        mCornerLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                DEFAULT_CORNER_LENGTH_DP,
//                displayMetrics);

//        // Sets guidelines to default until specified otherwise
//        mGuidelines = CropImageView.DEFAULT_GUIDELINES;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
//        Paint tmpPaint = new Paint();
//        tmpPaint.setAlpha(70);
        //paint guideline
        drawRuleOfThirdsGuidelines(canvas);

        canvas.setDrawFilter(paintFilter);

        if(bmpBack != null) {
            canvas.drawBitmap(bmpBack, matrixBack, mainPaint);
        }
        if(mPearlList != null && !mPearlList.isEmpty()){
            for (Pearl pearl: mPearlList){
                canvas.drawBitmap(pearl.getBitmap(), pearl.getMatrix(), null);
            }
        }
        if (bmpMotion != null){
            canvas.drawBitmap(bmpMotion, matrixPaint, null);
            canvas.drawBitmap(bmpRotate, null, rectRotate, null);
            canvas.drawBitmap(bmpDelete, null, rectDelete, null);
//		    canvas.drawRect(rectPaint, mainPaint);
//          canvas.drawRect(rectRotate, mainPaint);
        }
//		canvas.drawCircle(picMidPoint.x, picMidPoint.y, 5, mainPaint);

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
                }else if(isInRect(x, y, rectDelete)){
                    status = ViewStatus.STATUS_DELETE;
                }else if(isInRect(x,y,rectMotion)){
                    status = ViewStatus.STATUS_MOVE;
                }else{
                    //按到了别的珠子上
                    int index=-1;
                    for(int i=0;i<mPearlList.size();i++){
                        Pearl pearl=mPearlList.get(mPearlList.size()-1-i);

                        Bitmap bitmap=pearl.getBitmap();

                        RectF rectF=new RectF(0,0,bitmap.getWidth(), bitmap.getHeight());

                        pearl.getMatrix().mapRect(rectF);

                        if(rectF.contains(x,y)){
                            index=mPearlList.size()-1-i;
                            break;
                        }
                    }

                    if(index==-1){
                        status = ViewStatus.STATUS_MOVE;
                    }else{
                        status=ViewStatus.STATUS_SWITCH;
                        Pearl pearl=mPearlList.get(index);

                        mPearlList.remove(index);
                        Pearl pearlBmp=new Pearl(bmpMotion,matrixPaint);
                        mPearlList.add(pearlBmp);

                        bmpMotion=pearl.getBitmap();
                        matrixPaint=new Matrix(pearl.getMatrix());
                    }


                }
                break;
            case MotionEvent.ACTION_UP:
                if(status == ViewStatus.STATUS_ROTATE){
//                    saveBitmap();
                } else if(status == ViewStatus.STATUS_DELETE){
                    deleteCurrentMotion();
                }else if(status==ViewStatus.STATUS_SWITCH){
                    invalidate();

                }
                break;
            case MotionEvent.ACTION_MOVE:
                curPoint.x = x;
                curPoint.y = y;
                if(status == ViewStatus.STATUS_ROTATE){
                    rectRotateMark.set(x,
                            y,
                            x + bmpRotate.getWidth(),
                            y + bmpRotate.getHeight());
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
                matrixPaint.mapRect(rectDeleteMark, rectDeletePre);
                getRectCenter(rectRotateMark, rotateCenterP);
                getRectCenter(rectDeleteMark, deleteCenterP);
                getRectCenter(rectMotion, pointMotionMid);
                rectRotate.set(rectRotateMark.left,
                        rectRotateMark.top,
                        rectRotateMark.left + bmpRotate.getWidth(),
                        rectRotateMark.top + bmpRotate.getHeight());
                rectDelete.set(rectDeleteMark.left,
                        rectDeleteMark.top - bmpDelete.getHeight(),
                        rectDeleteMark.left + bmpDelete.getWidth(),
                        rectDeleteMark.top);
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }

    public void setBmpMotion(Bitmap mbitmap){
        if(bmpMotion != null){
            Pearl tmpPearl = new Pearl(bmpMotion, matrixPaint);
            mPearlList.add(tmpPearl);
        }
        bmpMotion = mbitmap;
        if(bmpMotion != null){

            //记录表情最初的矩形
            rectMotionPre = new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight());
            //记录表情当前的矩形
            rectMotion = new RectF(rectMotionPre);
            //标记旋转图标位置的矩形
            rectRotateMark = new RectF(rectMotion.right,
                    rectMotion.bottom,
                    rectMotion.right + bmpRotate.getWidth(),
                    rectMotion.bottom + bmpRotate.getHeight());
            //标记删除图标位置的矩形
            rectDeleteMark = new RectF(rectMotion.right,
                    rectMotion.top - bmpDelete.getHeight(),
                    rectMotion.right + bmpDelete.getWidth(),
                    rectMotion.top);
            //记录旋转图标矩形最初的矩形
            rectRotatePre = new RectF(rectRotateMark);
            //记录当前旋转图标位置的矩形
            rectRotate = new RectF(rectRotateMark);

            //记录删除图标矩形最初的矩形
            rectDeletePre = new RectF(rectDeleteMark);
            //记录当前删除图标矩形位置的矩形
            rectDelete = new RectF(rectDeletePre);

            //记录表情矩形的中点
            pointMotionMid = new PointF(bmpMotion.getWidth() / 2, bmpMotion.getHeight() / 2);
            //记录上次动作的坐标
            prePoint = new PointF();
            //记录当前动作的坐标
            curPoint = new PointF();
            //记录旋转图标中点
            rotateCenterP = new PointF(rectMotion.right, rectMotion.bottom);

            matrixPaint.reset();
        }
        invalidate();
    }

    public void turnLastAction(){
        Pearl tmpPearl = null;
        if(mPearlList != null && !mPearlList.isEmpty()){
            tmpPearl = mPearlList.get(mPearlList.size() - 1);
            mPearlList.remove(mPearlList.size() - 1);

        bmpMotion = tmpPearl.getBitmap();
        matrixPaint = tmpPearl.getMatrix();
        matrixPaint.mapRect(rectMotion, new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight()));
        matrixPaint.mapRect(rectRotate, new RectF(bmpMotion.getWidth(), bmpMotion.getHeight(), bmpMotion.getWidth() + bmpRotate.getWidth(), bmpMotion.getHeight() + bmpRotate.getHeight()));
        matrixPaint.mapRect(rectDelete, new RectF(bmpMotion.getWidth(), 0 - bmpDelete.getHeight(), bmpMotion.getWidth() + bmpDelete.getWidth(), 0));
        rectRotate.set(rectRotate.left,
                rectRotate.top,
                rectRotate.left + bmpRotate.getWidth(),
                rectRotate.top + bmpRotate.getHeight());
        rectDelete.set(rectDelete.left,
                rectDelete.top - bmpDelete.getHeight(),
                rectDelete.left + bmpDelete.getWidth(),
                rectDelete.top);
        }else if(bmpMotion != null){
            bmpMotion = null;
        }
//        rectRotate = new RectF(rectMotion.right,
//                rectMotion.bottom,
//                rectMotion.right + bmpRotate.getWidth(),
//                rectMotion.bottom + bmpRotate.getHeight());
//        rectDelete = new RectF(rectMotion.right,
//                rectMotion.top - bmpDelete.getHeight(),
//                rectMotion.right + bmpDelete.getWidth(),
//                rectMotion.top);
        invalidate();
    }

    private void deleteCurrentMotion(){



        if(mPearlList.size()>0) {
            bmpMotion = mPearlList.get(mPearlList.size() - 1).getBitmap();

            matrixPaint = mPearlList.get(mPearlList.size() - 1).getMatrix();

            mPearlList.remove(mPearlList.size() - 1);

            rectRotate.set(0, 0, 0, 0);

            rectDelete.set(0, 0, 0, 0);

            //

        }else{
            bmpMotion=null;
        }
        invalidate();
    }

    private void drawRuleOfThirdsGuidelines(Canvas canvas) {
        float w = mBorderPaint.getStrokeWidth();
        float l = Edge.LEFT.getCoordinate() + w;
        float t = Edge.TOP.getCoordinate() + w;
        float r = Edge.RIGHT.getCoordinate() - w;
        float b = Edge.BOTTOM.getCoordinate() - w;

//        if (mCropShape == CropImageView.CropShape.OVAL) {
//            l += 15 * mGuidelinePaint.getStrokeWidth();
//            t += 15 * mGuidelinePaint.getStrokeWidth();
//            r -= 15 * mGuidelinePaint.getStrokeWidth();
//            b -= 15 * mGuidelinePaint.getStrokeWidth();
//        }

        // Draw vertical guidelines.
        final float oneThirdCropWidth = Edge.getWidth() / 3;

        final float x1 = l + oneThirdCropWidth;
        canvas.drawLine(x1, t, x1, b, mGuidelinePaint);
        final float x2 = r - oneThirdCropWidth;
        canvas.drawLine(x2, t, x2, b, mGuidelinePaint);

        // Draw horizontal guidelines.
        final float oneThirdCropHeight = Edge.getHeight() / 3;

        final float y1 = t + oneThirdCropHeight;
        canvas.drawLine(l, y1, r, y1, mGuidelinePaint);
        final float y2 = b - oneThirdCropHeight;
        canvas.drawLine(l, y2, r, y2, mGuidelinePaint);
    }

    /**
     * 将当前表情合并到背景并保存
     */
    public Bitmap saveBitmapAll() {
//        File f = new File(savePathAll);
        //使用背景图的宽高创建一张bitmap
        Bitmap bmpSave = Bitmap.createBitmap(bmpBack.getWidth(), bmpBack.getHeight(), Bitmap.Config.ARGB_8888);
        //创建canvas
        Canvas canvas = new Canvas(bmpSave);
        //将背景图和表情画在bitmap上


        canvas.drawBitmap(bmpBack, 0, 0, mainPaint);

//        if(mPearlList != null && !mPearlList.isEmpty()){
//            for (Pearl pearl: mPearlList){
//                canvas.drawBitmap(pearl.getBitmap(), pearl.getMatrix(), null);
//            }
//        }
        for(int i=0;i<mPearlList.size();i++){
            canvas.drawBitmap(mPearlList.get(i).getBitmap(),mPearlList.get(i).getMatrix(),mainPaint);
        }
        canvas.drawBitmap(bmpMotion, matrixPaint, mainPaint);
        //保存bitmap
//		canvas.save(Canvas.ALL_SAVE_FLAG);
//		canvas.restore();
//        try{
//            FileOutputStream out = new FileOutputStream(f);
//            bmpSave.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bmpBack.recycle();
        return bmpSave;
//        bmpBack = bmpSave;
//        //重置Matrix
//        matrixPaint.reset();
//        //重置旋转图标
//        rectRotate.set(rectRotatePre);
    }

//    /**
//     * 将当前表情合并到背景并保存
//     */
//    public void saveBitmapCovered() {
//        File f = new File(savePathCovered);
//        //使用背景图的宽高创建一张bitmap
//        Bitmap bmpSave = Bitmap.createBitmap(bmpBack.getWidth(), bmpBack.getHeight(), Bitmap.Config.ARGB_8888);
//        //创建canvas
//        Canvas canvas = new Canvas(bmpSave);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//
//        //将背景图和表情画在bitmap上
//        canvas.drawBitmap(bmpMotion, matrixPaint, null);
//        canvas.drawBitmap(bmpBack, 0, 0, paint);
//
//        paint.setXfermode(null);
//
//        int[] pixels = new int[bmpSave.getHeight() * bmpSave.getWidth()];
//        bmpSave.getPixels(pixels, 0, bmpSave.getWidth(), 0, 0, bmpSave.getWidth(), bmpSave.getHeight());
//
//        ImageUtils.extractImageFromBitmapPixels(bmpSave, pixels, savePathCovered, false);
//        bmpBack.recycle();
//    }

    //根据矩形获取中心点
    private void getRectCenter(RectF rect, PointF p){
        p.x = rect.left + (rect.right - rect.left) / 2;
        p.y = rect.top + (rect.bottom - rect.top) / 2;
    }

    //判断点是否在矩形内
    private boolean isInRect(float x, float y, RectF rect){
        boolean ret = false;
        if(rect != null && x > rect.left && x < rect.right && y > rect.top && y < rect.bottom){
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
