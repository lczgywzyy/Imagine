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
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.utils.image.Pearl;


/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_PearlBuild extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_PearlBuild.class;
    private static final String FromPath = ".1FromPath/ImageView13";
    private static final String ToPath = ".2ToPath/ImageView13";

    /**
     * The Paint used to draw the pic.
     */
    Paint paint = null;
    PaintFlagsDrawFilter paintFilter = null;

    /**
     * Bitmap Back  底图
     * Bitmap Suzhu 素珠图片
     */
    Bitmap bmpSuzhu = null;
    Bitmap bmpBack = null;

    Context context = null;
    Canvas mCanvas = null;

    //素珠变换矩阵
    Matrix PointSuzhuMatrix = new Matrix();
    //图片变换点阵集合
    RectF rectBack = new RectF();
    //第一颗素珠的矩形位置
    RectF mFirstSuzhuRec = null;
    RectF mFirstSuzhuRecPre = null;
    //背景图片中心点
    PointF mBgCenterPoint = null;
    //第一颗素珠的中心
    PointF mFirstSuzhuCenterPoint = null;
    //整个珠子列表
    List<Pearl> mPearlList = new ArrayList<Pearl>();

    //输入素珠个数，默认为-1
    int mSuzhuNum = -1;

    //整串珠子的半径，用来计算素珠的缩放比例
    float radius = 0.0f;
    //素珠缩放比例
    float mSuzhuScale = 1.0f;



    public ImageViewImpl_PearlBuild(Context context) {
        super(context);
        init(context);
    }

    public ImageViewImpl_PearlBuild(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setBmpBack(Bitmap mbitmap){
        bmpBack = mbitmap;
    }

    public void setBmpSuzhu(Bitmap mbitmap){
        bmpSuzhu = mbitmap;
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
//        bmpBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.pearbuild_background);
//        bmpSuzhu = BitmapFactory.decodeResource(context.getResources(),R.drawable.pearlbuild_suzhu);
        //加载相应的图片资源
        bmpSuzhu = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/suzhu.png").getAbsolutePath());
        bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/bg.png").getAbsolutePath());

        //记录表情矩形的中点
        mBgCenterPoint = new PointF(bmpBack.getWidth() / 2, bmpBack.getHeight() / 2);

        //默认素珠个数为1
        mSuzhuNum = 1;
        //初始化函数，在第一次初始化之后将第一个素珠显示出来，作为展示。
        mInitial();
        mPearlList.add(new Pearl(mFirstSuzhuCenterPoint, PointSuzhuMatrix));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mCanvas = canvas;

        canvas.setDrawFilter(paintFilter);
        /* 绘制背景
        * */
        canvas.drawBitmap(bmpBack, 0, 0, paint);
        /* 绘制素珠点
        * */
        for(Pearl p: mPearlList){
            canvas.drawBitmap(bmpSuzhu, p.getMatrix(), null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return true;
    }
    public void updateImage(int vSuzhuNum){
        Log.i(TAG, "素珠数量：" + vSuzhuNum);
        mSuzhuNum = vSuzhuNum;
        mInitial();
        /* 计算素珠点
        * */
        //对素珠进行缩放
        PointSuzhuMatrix.postScale(mSuzhuScale, mSuzhuScale, (mFirstSuzhuRec.left + mFirstSuzhuRec.right)/2, (mFirstSuzhuRec.top + mFirstSuzhuRec.bottom)/2);
        mPearlList.add(new Pearl(mFirstSuzhuCenterPoint, PointSuzhuMatrix));
        //根据素珠个数，生成整串珠子
        for (int i = 1; i <= mSuzhuNum; i++ ){
            //对Matrix进行旋转
            PointSuzhuMatrix.postRotate(360f/mSuzhuNum, mBgCenterPoint.x, mBgCenterPoint.y);
            PointSuzhuMatrix.mapRect(mFirstSuzhuRec, mFirstSuzhuRecPre);
            PointF tmpCenterPF = new PointF((mFirstSuzhuRec.left + mFirstSuzhuRec.right)/2, (mFirstSuzhuRec.top + mFirstSuzhuRec.bottom)/2);
            mPearlList.add(new Pearl(tmpCenterPF, PointSuzhuMatrix));
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
        mFirstSuzhuCenterPoint = new PointF(mFirstSuzhuRec.left + bmpSuzhu.getWidth()/2, mFirstSuzhuRec.top + bmpSuzhu.getHeight()/2);

        //计算整串珠子的半径
        radius = bmpBack.getHeight()/2 - mFirstSuzhuRec.top - bmpSuzhu.getHeight()/2;
        //计算素珠的缩放比例
        mSuzhuScale = (float) ((radius * Math.sin(Math.toRadians(360f/mSuzhuNum/2))) / (bmpSuzhu.getHeight()/2));
    }
}
