package u.can.i.up.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.Image;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.application.IApplicationConfig;
import u.can.i.up.utils.image.*;


/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_PearlBuild extends ImageView {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_PearlBuild.class;
    private static final String FromPath = ".1FromPath/ImageView13";
    private static final String ToPath = ".2ToPath/ImageView13";

    Context context = null;
    //素珠矩阵
    Matrix PointSuzhuMatrix = new Matrix();
    Matrix matrixPaint = null;

    Canvas mCanvas = null;

    Bitmap bmpSuzhu = null;

    private Bitmap bmpBack = null;
    private Bitmap bmpMotion = null;
    private Bitmap bmpRotate = null;
    private Bitmap bmpDelete = null;
    private Bitmap bmpOk = null;

    ViewStatus status = ViewStatus.STATUS_MOVE;

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
    RectF rectOkMark = new RectF();
    RectF rectOkPre = new RectF();
    RectF rectOk = new RectF();

    // 记录图片中心点
    PointF pointMotionMid = new PointF();
    PointF prePoint = new PointF();
    PointF curPoint = new PointF();
    PointF rotateCenterP = new PointF();
    PointF deleteCenterP = new PointF();
    PointF okCenterP = new PointF();

    //背景中心
    PointF mBgCenterPoint = null;
    //整个珠子列表
    List<Pearl> mPearlList = new ArrayList<Pearl>();

    //第一颗素珠的中心
    PointF mFirstSuzhuCenterPoint = null;
    //第一颗素珠的矩形位置
    RectF mFirstSuzhuRec = null;
    RectF mFirstSuzhuRecPre = null;
    //输入素珠个数，默认为-1
    int mSuzhuNum = -1;

    //整串珠子的半径，用来计算素珠的缩放比例
    float mCircleRadius = 0.0f;
    //素珠缩放比例
    float mSuzhuScale = 1.0f;

    float mCurrentRadius = 0.0f;

    Paint paint = null;
    PaintFlagsDrawFilter paintFilter = null;



    public ImageViewImpl_PearlBuild(Context context) {
        super(context);
        init(context);
    }

    public ImageViewImpl_PearlBuild(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setBmpBack(Bitmap mbitmap){
        Bitmap dst = Bitmap.createScaledBitmap(mbitmap, IApplicationConfig.DeviceWidth, (int)((float)IApplicationConfig.DeviceWidth*0.9), true);
        mbitmap.recycle();
        bmpBack=dst;
        //缩放
        this.setImageBitmap(bmpBack);
//        int height=  IApplicationConfig.DeviceWidth;
//        int width= (int)((float)IApplicationConfig.DeviceWidth*0.9);
        int height=  bmpBack.getHeight();
        int width= bmpBack.getWidth();
        mBgCenterPoint = new PointF(width/ 2, height / 2);
    }

    public void setBmpSuzhu(Bitmap mbitmap){
        bmpSuzhu = mbitmap;
        mInitial();
//        mPearlList.add(new Pearl(bmpSuzhu, mFirstSuzhuCenterPoint, PointSuzhuMatrix, -1));
//        invalidate();
    }

    /** TODO 当且仅当构造函数中可以调用init()
     * */
    private void init(Context context) {
        //        // TODO Auto-generated constructor stub
        this.context = context;
        //创建画笔
        paint = new Paint();
        //画笔抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        //设置画笔绘制空心图形
        paint.setStyle(Paint.Style.STROKE);

        //创建素珠点的Matrix
        PointSuzhuMatrix = new Matrix();
        matrixPaint = new Matrix();

        //加载相应的图片资源
       // bmpSuzhu = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/suzhu.png").getAbsolutePath());
       // bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/bg.png").getAbsolutePath());

        bmpRotate = BitmapFactory.decodeResource(getResources(), R.drawable.rotate_icon);
        bmpDelete = BitmapFactory.decodeResource(getResources(), R.drawable.delete_icon);
        bmpOk = BitmapFactory.decodeResource(getResources(), R.drawable.icon_ok);

        //记录表情矩形的中点


        //默认素珠个数为1
        //mSuzhuNum = 1;
        //初始化函数，在第一次初始化之后将第一个素珠显示出来，作为展示。
        //mInitial();
        //mPearlList.add(new Pearl(bmpSuzhu, mFirstSuzhuCenterPoint, PointSuzhuMatrix, -1));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mCanvas = canvas;
        canvas.setDrawFilter(paintFilter);

        /* 绘制背景
        * */
      //  canvas.drawBitmap(bmpBack, 0, 0, paint);
        /* 绘制素珠点
        * */
        for(Pearl p: mPearlList){
            canvas.drawBitmap(p.getBitmap(), p.getMatrix(), null);
        }
        if (bmpMotion != null){
            canvas.drawBitmap(bmpMotion, matrixPaint, null);
            canvas.drawBitmap(bmpRotate, null, rectRotate, null);
            canvas.drawBitmap(bmpDelete, null, rectDelete, null);
            canvas.drawBitmap(bmpOk, null, rectOk, null);

            int index = ImageAlgrithms.isOverlayed(mPearlList, rectMotion, mBgCenterPoint);
            if (index >= 0) {
                float deltaAngle = 0.0f;
                for (int i = 0; i < index; i++) {
                    deltaAngle += (float) Math.toDegrees(Math.asin(mPearlList.get(i).getRadius() / mCircleRadius)) * 2;
                }
                deltaAngle = deltaAngle - (float) Math.toDegrees(Math.asin(mPearlList.get(0).getRadius() / mCircleRadius));
                Matrix tmpMatrix = new Matrix();
                float[] pts = new float[2];
                pts[0] = mPearlList.get(0).getCenter().x;
                pts[1] = mPearlList.get(0).getCenter().y;
                tmpMatrix.postRotate(deltaAngle, mBgCenterPoint.x, mBgCenterPoint.y);
                tmpMatrix.mapPoints(pts);
                Paint tmpPaint = new Paint();
                tmpPaint.setStyle(Paint.Style.STROKE);
                tmpPaint.setColor(Color.RED);
                tmpPaint.setStrokeWidth(2);
                canvas.drawLine(pts[0] - 300f, pts[1], pts[0] + 300f, pts[1], tmpPaint);
                canvas.drawLine(pts[0], pts[1] - 300f, pts[0], pts[1] + 300f, tmpPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prePoint.x = x;
                prePoint.y = y;
                if(ImageAlgrithms.isInRect(x, y, rectRotate)){
                    status = ViewStatus.STATUS_ROTATE;
                }else if(ImageAlgrithms.isInRect(x, y, rectDelete)){
                    status = ViewStatus.STATUS_DELETE;
                }else if(ImageAlgrithms.isInRect(x, y, rectOk)){
                    status = ViewStatus.STATUS_OK;
                }else{
                    status = ViewStatus.STATUS_MOVE;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "--------->" + status);
                if(status == ViewStatus.STATUS_DELETE){
                    deleteCurrentMotion();
                } else if(status == ViewStatus.STATUS_OK) {
                    if (bmpMotion == null) {
                        break;
                    }
                    int index = ImageAlgrithms.isOverlayed(mPearlList, rectMotion, mBgCenterPoint);
                    if (index >= 0) {
                        float halfCircleLenth = 0f;
                        float halfAngle = 0f;
                        for (int i = 0; i < mPearlList.size(); i++) {
                            halfCircleLenth += mPearlList.get(i).getRadius();
                            halfAngle += Math.asin(mPearlList.get(i).getRadius() / mCircleRadius);
                        }
                        Log.d(TAG, "" + halfCircleLenth);
                        Log.d(TAG, "" + mCurrentRadius);

                        float tmpRe = halfAngle / (halfAngle + (float) Math.asin(mCurrentRadius / mCircleRadius));

                        List<Pearl> tmpPearlList = new ArrayList<Pearl>();
                        Matrix tmpMatrix = null;

                        if (index == 0) {
                            index = mPearlList.size();
                        }

                        //预处理
                        RectF tmpFirstRect = new RectF(0, 0, mPearlList.get(0).getBitmap().getWidth(), mPearlList.get(0).getBitmap().getHeight());
                        RectF tmpFirstRectPre = new RectF(tmpFirstRect);
                        //第一个珠子进行处理
                        Pearl pearlZero = mPearlList.get(0);
                        tmpMatrix = pearlZero.getMatrix();
                        tmpMatrix.postScale(tmpRe, tmpRe, pearlZero.getCenter().x, pearlZero.getCenter().y);
                        pearlZero.setMatrix(tmpMatrix);
                        tmpMatrix.mapRect(tmpFirstRect, tmpFirstRectPre);
                        PointF tmpFirstCenterPF = new PointF((tmpFirstRect.left + tmpFirstRect.right) / 2, (tmpFirstRect.top + tmpFirstRect.bottom) / 2);
                        pearlZero.setCenter(tmpFirstCenterPF);
                        tmpPearlList.add(pearlZero);

                        //根据第一个珠子的处理结果，进行后续珠子的处理
                        for (int i = 1; i < index; i++) {
                            Pearl cPearl = mPearlList.get(i);
                            tmpMatrix = cPearl.getMatrix();
                            tmpMatrix.postScale(tmpRe, tmpRe, cPearl.getCenter().x, cPearl.getCenter().y);
                            float deltaAngle = Math.abs(ImageAlgrithms.getPointsDegree(pearlZero.getCenter(), mBgCenterPoint, cPearl.getCenter())) * (1 - tmpRe);
                            tmpMatrix.postRotate(0 - deltaAngle, mBgCenterPoint.x, mBgCenterPoint.y);
                            RectF targetRect = new RectF(0, 0, mPearlList.get(i).getBitmap().getWidth(), mPearlList.get(i).getBitmap().getHeight());
                            RectF targetRectPre = new RectF(targetRect);
                            tmpMatrix.mapRect(targetRect, targetRectPre);
                            PointF tmpCenterPF = new PointF((targetRect.left + targetRect.right) / 2, (targetRect.top + targetRect.bottom) / 2);
                            tmpPearlList.add(new Pearl(cPearl.getBitmap(), tmpCenterPF, tmpMatrix, cPearl.getRadius()));
                        }
                        //添加当前珠子
                        {
                            float deltaAngle = 0.0f;
                            for (int i = 0; i < index; i++) {
                                deltaAngle += (float) Math.toDegrees(Math.asin(mPearlList.get(i).getRadius() / mCircleRadius)) * 2;
                            }
                            //第一颗珠子
                            Pearl prePearl = mPearlList.get(0);
                            tmpMatrix = new Matrix(prePearl.getMatrix());
                            tmpMatrix.mapRect(tmpFirstRect, tmpFirstRectPre);

                            //将要插进去的珠子挪到第一个珠子的中心
                            RectF tmpCurrentRect = new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight());
                            RectF tmpCurrentRectPre = new RectF(tmpCurrentRect);
                            tmpMatrix.mapRect(tmpCurrentRect, tmpCurrentRectPre);
                            float deltaX = ((tmpFirstRect.right - tmpFirstRect.left) - (tmpCurrentRect.right - tmpCurrentRect.left)) / 2;
                            float deltaY = ((tmpFirstRect.bottom - tmpFirstRect.top) - (tmpCurrentRect.bottom - tmpCurrentRect.top)) / 2;
                            tmpMatrix.postTranslate(deltaX, deltaY);
                            tmpMatrix.mapRect(tmpCurrentRect, tmpCurrentRectPre);

                            PointF tmpCenterPF = new PointF((tmpCurrentRect.left + tmpCurrentRect.right) / 2, (tmpCurrentRect.top + tmpCurrentRect.bottom) / 2);
                            tmpMatrix.postScale(mCurrentRadius / prePearl.getRadius() * prePearl.getBitmap().getWidth() / bmpMotion.getWidth(),
                                    mCurrentRadius / prePearl.getRadius() * prePearl.getBitmap().getWidth() / bmpMotion.getWidth(),
                                    tmpCenterPF.x, tmpCenterPF.y);
                            deltaAngle = deltaAngle * tmpRe - (float) Math.toDegrees(Math.asin(prePearl.getRadius() / mCircleRadius)) * tmpRe + (float) Math.toDegrees(Math.asin(mCurrentRadius / mCircleRadius)) * tmpRe;
                            Log.d(TAG, "deltaAngle2:" + deltaAngle);
                            tmpMatrix.postRotate(deltaAngle, mBgCenterPoint.x, mBgCenterPoint.y);
                            tmpMatrix.mapRect(tmpCurrentRect, tmpCurrentRectPre);
                            tmpCenterPF = new PointF((tmpCurrentRect.left + tmpCurrentRect.right) / 2, (tmpCurrentRect.top + tmpCurrentRect.bottom) / 2);
                            tmpPearlList.add(new Pearl(bmpMotion, tmpCenterPF, tmpMatrix, mCurrentRadius));
                        }

                        //添加之后的珠子
                        for (int i = index; i < mPearlList.size(); i++) {
                            Pearl cPearl = mPearlList.get(i);
                            tmpMatrix = cPearl.getMatrix();
                            tmpMatrix.postScale(tmpRe, tmpRe, cPearl.getCenter().x, cPearl.getCenter().y);
                            float deltaAngle = Math.abs(360 - ImageAlgrithms.getPointsDegree(pearlZero.getCenter(), mBgCenterPoint, cPearl.getCenter())) * (1 - tmpRe);
                            tmpMatrix.postRotate(deltaAngle, mBgCenterPoint.x, mBgCenterPoint.y);
                            RectF targetRect = new RectF(0, 0, mPearlList.get(i).getBitmap().getWidth(), mPearlList.get(i).getBitmap().getHeight());
                            RectF targetRectPre = new RectF(targetRect);
                            tmpMatrix.mapRect(targetRect, targetRectPre);
                            PointF tmpCenterPF = new PointF((targetRect.left + targetRect.right) / 2, (targetRect.top + targetRect.bottom) / 2);
                            tmpPearlList.add(new Pearl(cPearl.getBitmap(), tmpCenterPF, tmpMatrix, cPearl.getRadius()));
                        }
                        for (int i = 0; i < tmpPearlList.size(); i++) {
                            float tmpR = (float) (mCircleRadius * Math.sin(Math.asin(tmpPearlList.get(i).getRadius() / mCircleRadius) * tmpRe));
                            tmpPearlList.get(i).setRadius(tmpR);
                        }
                        mPearlList = tmpPearlList;
                        //清空画布
                        ImageUtils.clearCanvas(mCanvas);
                        bmpMotion = null;
                        //更新界面
                        invalidate();
                    }
                }
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
                        mCurrentRadius = mCurrentRadius * re;
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
                matrixPaint.mapRect(rectOkMark, rectOkPre);
                ImageAlgrithms.getRectCenter(rectMotion, pointMotionMid);
                ImageAlgrithms.getRectCenter(rectRotateMark, rotateCenterP);
                ImageAlgrithms.getRectCenter(rectDeleteMark, deleteCenterP);
                ImageAlgrithms.getRectCenter(rectOkMark, okCenterP);
                rectRotate.set(rotateCenterP.x - bmpRotate.getWidth() * 0.5f,
                        rotateCenterP.y - bmpRotate.getHeight() * 0.5f,
                        rotateCenterP.x + bmpRotate.getWidth() * 0.5f,
                        rotateCenterP.y + bmpRotate.getHeight() * 0.5f);
                rectDelete.set(deleteCenterP.x - bmpDelete.getWidth() * 0.5f,
                        deleteCenterP.y - bmpDelete.getHeight() * 0.5f,
                        deleteCenterP.x + bmpDelete.getWidth()  * 0.5f,
                        deleteCenterP.y + bmpDelete.getHeight() * 0.5f);
                rectOk.set(okCenterP.x - bmpOk.getWidth() * 0.5f,
                        okCenterP.y - bmpOk.getHeight() * 0.5f,
                        okCenterP.x + bmpOk.getWidth()  * 0.5f,
                        okCenterP.y + bmpOk.getHeight() * 0.5f);
                postInvalidate();
                break;
        }
        return true;
    }
    public void updateImage(int vSuzhuNum){
        Log.i(TAG, "素珠数量：" + vSuzhuNum);
        mSuzhuNum = vSuzhuNum;
        mInitial();
        /* 计算素珠点
        * */
        //对素珠进行缩放
        PointSuzhuMatrix.postScale(mSuzhuScale, mSuzhuScale, (mFirstSuzhuRec.left + mFirstSuzhuRec.right) / 2, (mFirstSuzhuRec.top + mFirstSuzhuRec.bottom) / 2);
        PointSuzhuMatrix.mapRect(mFirstSuzhuRec, mFirstSuzhuRecPre);
        float tmpRudis = (mFirstSuzhuRec.right - mFirstSuzhuRec.left) / 2;
        mPearlList.add(new Pearl(bmpSuzhu, mFirstSuzhuCenterPoint, PointSuzhuMatrix, tmpRudis));
        //根据素珠个数，生成整串珠子
        for (int i = 1; i < mSuzhuNum; i++ ){
            //对Matrix进行旋转
            PointSuzhuMatrix.postRotate(360f/mSuzhuNum, mBgCenterPoint.x, mBgCenterPoint.y);
            PointSuzhuMatrix.mapRect(mFirstSuzhuRec, mFirstSuzhuRecPre);
            PointF tmpCenterPF = new PointF((mFirstSuzhuRec.left + mFirstSuzhuRec.right)/2, (mFirstSuzhuRec.top + mFirstSuzhuRec.bottom)/2);
            mPearlList.add(new Pearl(bmpSuzhu,tmpCenterPF, PointSuzhuMatrix, tmpRudis));
        }
        //清空画布
        ImageUtils.clearCanvas(mCanvas);
        //更新界面
        invalidate();
    }
    private void mInitial(){
        mPearlList.clear();
        PointSuzhuMatrix.reset();

        //素珠初始位置
        mFirstSuzhuRec = new RectF(0, 0, bmpSuzhu.getWidth(), bmpSuzhu.getHeight());
        mFirstSuzhuRecPre = new RectF(mFirstSuzhuRec);

        //第一颗素珠位置
        PointSuzhuMatrix.postTranslate((bmpBack.getWidth() - bmpSuzhu.getWidth()) / 2, (bmpBack.getHeight() - bmpSuzhu.getHeight()) / 6);
        PointSuzhuMatrix.mapRect(mFirstSuzhuRec, mFirstSuzhuRecPre);
        mFirstSuzhuCenterPoint = new PointF((mFirstSuzhuRec.left + mFirstSuzhuRec.right) / 2, (mFirstSuzhuRec.top + mFirstSuzhuRec.bottom)/2);
//        mFirstSuzhuCenterPoint = new PointF(mFirstSuzhuRec.left + bmpSuzhu.getWidth()/2, mFirstSuzhuRec.top + bmpSuzhu.getHeight()/2);

        //计算整串珠子的半径
//        mCircleRadius =(int)((float)IApplicationConfig.DeviceWidth)/2-bmpSuzhu.getWidth()-UtilsDevice.dip2px(10);
        mCircleRadius = bmpBack.getHeight()/2 - mFirstSuzhuCenterPoint.y;
        //计算素珠的缩放比例
        mSuzhuScale = (float) ((mCircleRadius * Math.sin(Math.toRadians(360f/mSuzhuNum/2))) / (bmpSuzhu.getHeight()/2));
    }

    public void setBmpMotion(Bitmap mbitmap){
        if(bmpMotion != null){
//            Pearl tmpPearl = new Pearl(bmpMotion, matrixPaint);
//            mPearlList.add(tmpPearl);
        }
        bmpMotion = mbitmap;
        if(bmpMotion != null){
            //记录表情最初的矩形
            rectMotionPre = new RectF(0, 0, bmpMotion.getWidth(), bmpMotion.getHeight());
            //记录表情当前的矩形
            rectMotion = new RectF(rectMotionPre);
            //标记旋转图标位置的矩形
            rectRotateMark = new RectF(rectMotion.right - bmpRotate.getWidth() * 0.3f,
                    rectMotion.bottom - bmpRotate.getHeight() * 0.3f,
                    rectMotion.right + bmpRotate.getWidth() * 0.7f,
                    rectMotion.bottom + bmpRotate.getHeight() * 0.7f);
            //记录旋转图标矩形最初的矩形
            rectRotatePre = new RectF(rectRotateMark);
            //记录当前旋转图标位置的矩形
            rectRotate = new RectF(rectRotateMark);

            //标记删除图标位置的矩形
            rectDeleteMark = new RectF(rectMotion.left - bmpDelete.getWidth() * 0.7f,
                    rectMotion.bottom - bmpDelete.getHeight() * 0.3f,
                    rectMotion.left + bmpDelete.getWidth() * 0.3f,
                    rectMotion.bottom + bmpDelete.getHeight() * 0.7f);
            //记录删除图标矩形最初的矩形
            rectDeletePre = new RectF(rectDeleteMark);
            //记录当前删除图标矩形位置的矩形
            rectDelete = new RectF(rectDeletePre);

            //标记确认图标位置的矩形
            rectOkMark = new RectF(rectMotion.right - bmpDelete.getWidth() * 0.3f,
                    rectMotion.top - bmpDelete.getHeight() * 0.7f,
                    rectMotion.right + bmpDelete.getWidth() * 0.7f,
                    rectMotion.top + bmpDelete.getHeight() * 0.3f);
            //记录确认图标矩形最初的矩形
            rectOkPre = new RectF(rectOkMark);
            //记录当前确认图标矩形位置的矩形
            rectOk = new RectF(rectOkPre);

            //记录表情矩形的中点
            pointMotionMid = new PointF(bmpMotion.getWidth() / 2, bmpMotion.getHeight() / 2);
            //记录上次动作的坐标
            prePoint = new PointF();
            //记录当前动作的坐标
            curPoint = new PointF();
            //记录旋转图标中点
            rotateCenterP = new PointF(rectMotion.right, rectMotion.bottom);

            mCurrentRadius = (rectMotion.right - rectMotion.left) / 2;
            matrixPaint.reset();
        }
        invalidate();
    }

    /**
     * 将当前表情合并到背景并保存
     */
    public Bitmap saveBitmapAll() {
//        File f = new File(savePathAll);
        //使用背景图的宽高创建一张bitmap
        Bitmap bmpSave = Bitmap.createBitmap(IApplicationConfig.DeviceWidth,(int)((float)IApplicationConfig.DeviceWidth*0.9), Bitmap.Config.ARGB_8888);
        //创建canvas
        Canvas canvas = new Canvas(bmpSave);
        //将背景图和表情画在bitmap上
        canvas.drawBitmap(Bitmap.createScaledBitmap(bmpBack, IApplicationConfig.DeviceWidth,(int)((float)IApplicationConfig.DeviceWidth*0.9), true), new Matrix(), null);
        //将素材画在bitmap上
        if(mPearlList != null && !mPearlList.isEmpty()){
            for (Pearl pearl: mPearlList){
                Matrix tmpMatrix = new Matrix(pearl.getMatrix());

                canvas.drawBitmap(pearl.getBitmap(), tmpMatrix, null);
            }
        }
        if(bmpMotion != null){
            canvas.drawBitmap(bmpMotion, matrixPaint, null);
        }
        return bmpSave;
    }
    /**
     * 删除素材函数
     * */
    private void deleteCurrentMotion(){
        bmpMotion=null;
        invalidate();
    }


}
