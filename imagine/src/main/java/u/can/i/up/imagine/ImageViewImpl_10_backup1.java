package u.can.i.up.imagine;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_10_backup1 extends View {

    /**
     * 图片在旋转时x方向的偏移量
     */
    private int offsetX;
    /**
     * 图片在旋转时y方向的偏移量
     */
    private int offsetY;
    /**
     * 图片的最大缩放比例
     */
    public static final float MAX_SCALE = 4.0f;

    /**
     * 图片的最小缩放比例
     */
    public static final float MIN_SCALE = 0.3f;

    /**
     * 控制缩放，旋转图标所在四个点得位置
     */
    public static final int LEFT_TOP = 0;
    public static final int RIGHT_TOP = 1;
    public static final int RIGHT_BOTTOM = 2;
    public static final int LEFT_BOTTOM = 3;

    /**
     * 一些默认的常量
     */
    public static final int DEFAULT_FRAME_PADDING = 8;
    public static final int DEFAULT_FRAME_WIDTH = 2;
    public static final int DEFAULT_FRAME_COLOR = Color.WHITE;
    public static final float DEFAULT_SCALE = 1.0f;
    public static final float DEFAULT_DEGREE = 0;
    public static final int DEFAULT_CONTROL_LOCATION = RIGHT_TOP;
    public static final boolean DEFAULT_EDITABLE = true;
    public static final int DEFAULT_OTHER_DRAWABLE_WIDTH = 50;
    public static final int DEFAULT_OTHER_DRAWABLE_HEIGHT = 50;

    /**
     * 图片四个点坐标
     */
    private Point mLTPoint;
    private Point mRTPoint;
    private Point mRBPoint;
    private Point mLBPoint;
    /**
     * 用于缩放，旋转的控制点的坐标
     */
    private Point mControlPoint = new Point();
    /**
     * 外边框与图片之间的间距, 单位是dip
     */
    private int framePadding = DEFAULT_FRAME_PADDING;

    /**
     * 外边框颜色
     */
    private int frameColor = DEFAULT_FRAME_COLOR;

    /**
     * 外边框线条粗细, 单位是 dip
     */
    private int frameWidth = DEFAULT_FRAME_WIDTH;

    /**
     * 是否处于可以缩放，平移，旋转状态
     */
    private boolean isEditable = DEFAULT_EDITABLE;
    /**
     * 用于缩放，旋转的图标
     */
    private Drawable controlDrawable;
    /**
     * 控制图标所在的位置（比如左上，右上，左下，右下）
     */
    private int controlLocation = DEFAULT_CONTROL_LOCATION;
    /**
     * 缩放，旋转图标的宽和高
     */
    private int mDrawableWidth, mDrawableHeight;
    /**
     * View的宽度和高度，随着图片的旋转而变化(不包括控制旋转，缩放图片的宽高)
     */
    private int mViewWidth, mViewHeight;

    /**
     * 图片的旋转角度
     */
    private float mDegree = DEFAULT_DEGREE;

    /**
     * 图片的缩放比例
     */
    private float mScale = DEFAULT_SCALE;
    /**
     * 用于缩放，旋转，平移的矩阵
     */
    private Matrix matrix = new Matrix();
    /**
     * SingleTouchView的中心点坐标，相对于其父类布局而言的
     */
    private PointF mCenterPoint = new PointF();
    /**
     * SingleTouchView距离父类布局的左间距
     */
    private int mViewPaddingLeft;

    /**
     * SingleTouchView距离父类布局的上间距
     */
    private int mViewPaddingTop;

    Context mContext;
    private DisplayMetrics metrics;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    int widthScreen = -1;
    int heightScreen = -1;
    private Paint mPaint = new Paint();

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_10_backup1.class;
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    public ImageViewImpl_10_backup1(Context context) {
        this(context, null);
    }

    public ImageViewImpl_10_backup1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewImpl_10_backup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        obtainStyledAttributes(attrs);
        init();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.mCanvas = canvas;

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;

        canvas.drawBitmap(mBitmap, matrix, null);
        controlDrawable.draw(canvas);

    }

    private void obtainStyledAttributes(AttributeSet attrs){
        metrics = getContext().getResources().getDisplayMetrics();
        framePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_FRAME_PADDING, metrics);
        frameWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_FRAME_WIDTH, metrics);

        TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageViewImpl_10);

        mBitmap = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/4.png").getAbsolutePath());

        framePadding = mTypedArray.getDimensionPixelSize(R.styleable.ImageViewImpl_10_framePadding, framePadding);
        frameWidth = mTypedArray.getDimensionPixelSize(R.styleable.ImageViewImpl_10_frameWidth, frameWidth);
        frameColor = mTypedArray.getColor(R.styleable.ImageViewImpl_10_frameColor, DEFAULT_FRAME_COLOR);
        mScale = mTypedArray.getFloat(R.styleable.ImageViewImpl_10_scale, DEFAULT_SCALE);
        mDegree = mTypedArray.getFloat(R.styleable.ImageViewImpl_10_degree, DEFAULT_DEGREE);
        controlDrawable = mTypedArray.getDrawable(R.styleable.ImageViewImpl_10_controlDrawable);
        controlLocation = mTypedArray.getInt(R.styleable.ImageViewImpl_10_controlLocation, DEFAULT_CONTROL_LOCATION);
        isEditable = mTypedArray.getBoolean(R.styleable.ImageViewImpl_10_editable, DEFAULT_EDITABLE);
        mTypedArray.recycle();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(frameWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        controlDrawable = getContext().getResources().getDrawable(R.drawable.rotate_icon);

        mDrawableWidth = controlDrawable.getIntrinsicWidth();
        mDrawableHeight = controlDrawable.getIntrinsicHeight();

        transformDraw();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取SingleTouchView所在父布局的中心点
        ViewGroup mViewGroup = (ViewGroup) getParent();
        if(null != mViewGroup){
            int parentWidth = mViewGroup.getWidth();
            int parentHeight = mViewGroup.getHeight();
            mCenterPoint.set(parentWidth/2, parentHeight/2);
        }
    }
    /**
     * 设置Matrix, 强制刷新
     */
    private void transformDraw() {
        if (mBitmap == null) return;
        int bitmapWidth = (int)(mBitmap.getWidth() * mScale);
        int bitmapHeight = (int) (mBitmap.getHeight() * mScale);
        computeRect(-framePadding, -framePadding, bitmapWidth + framePadding, bitmapHeight + framePadding, mDegree);

        //设置缩放比例
        matrix.setScale(mScale, mScale);
        //绕着图片中心进行旋转
        matrix.postRotate(mDegree % 360, bitmapWidth/2, bitmapHeight/2);
        //设置画该图片的起始点
        matrix.postTranslate(offsetX + mDrawableWidth/2, offsetY + mDrawableHeight/2);

        adjustLayout();
    }

    /**
     * 获取四个点和View的大小
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param degree
     */
    private void computeRect(int left, int top, int right, int bottom, float degree){
        Point lt = new Point(left, top);
        Point rt = new Point(right, top);
        Point rb = new Point(right, bottom);
        Point lb = new Point(left, bottom);
        Point cp = new Point((left + right) / 2, (top + bottom) / 2);
        mLTPoint = obtainRoationPoint(cp, lt, degree);
        mRTPoint = obtainRoationPoint(cp, rt, degree);
        mRBPoint = obtainRoationPoint(cp, rb, degree);
        mLBPoint = obtainRoationPoint(cp, lb, degree);

        //计算X坐标最大的值和最小的值
        int maxCoordinateX = getMaxValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);
        int minCoordinateX = getMinValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);;

        mViewWidth = maxCoordinateX - minCoordinateX ;


        //计算Y坐标最大的值和最小的值
        int maxCoordinateY = getMaxValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);
        int minCoordinateY = getMinValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);

        mViewHeight = maxCoordinateY - minCoordinateY ;


        //View中心点的坐标
        Point viewCenterPoint = new Point((maxCoordinateX + minCoordinateX) / 2, (maxCoordinateY + minCoordinateY) / 2);

        offsetX = mViewWidth / 2 - viewCenterPoint.x;
        offsetY = mViewHeight / 2 - viewCenterPoint.y;



        int halfDrawableWidth = mDrawableWidth / 2;
        int halfDrawableHeight = mDrawableHeight /2;

        //将Bitmap的四个点的X的坐标移动offsetX + halfDrawableWidth
        mLTPoint.x += (offsetX + halfDrawableWidth);
        mRTPoint.x += (offsetX + halfDrawableWidth);
        mRBPoint.x += (offsetX + halfDrawableWidth);
        mLBPoint.x += (offsetX + halfDrawableWidth);

        //将Bitmap的四个点的Y坐标移动offsetY + halfDrawableHeight
        mLTPoint.y += (offsetY + halfDrawableHeight);
        mRTPoint.y += (offsetY + halfDrawableHeight);
        mRBPoint.y += (offsetY + halfDrawableHeight);
        mLBPoint.y += (offsetY + halfDrawableHeight);

        mControlPoint = LocationToPoint(controlLocation);
    }

    /**
     * 获取旋转某个角度之后的点
     * @param center
     * @param source
     * @param degree
     * @return
     */
    public static Point obtainRoationPoint(Point center, Point source, float degree) {
        //两者之间的距离
        Point disPoint = new Point();
        disPoint.x = source.x - center.x;
        disPoint.y = source.y - center.y;

        //没旋转之前的弧度
        double originRadian = 0;

        //没旋转之前的角度
        double originDegree = 0;

        //旋转之后的角度
        double resultDegree = 0;

        //旋转之后的弧度
        double resultRadian = 0;

        //经过旋转之后点的坐标
        Point resultPoint = new Point();

        double distance = Math.sqrt(disPoint.x * disPoint.x + disPoint.y * disPoint.y);
        if (disPoint.x == 0 && disPoint.y == 0) {
            return center;
            // 第一象限
        } else if (disPoint.x >= 0 && disPoint.y >= 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(disPoint.y / distance);

            // 第二象限
        } else if (disPoint.x < 0 && disPoint.y >= 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(Math.abs(disPoint.x) / distance);
            originRadian = originRadian + Math.PI / 2;

            // 第三象限
        } else if (disPoint.x < 0 && disPoint.y < 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(Math.abs(disPoint.y) / distance);
            originRadian = originRadian + Math.PI;
        } else if (disPoint.x >= 0 && disPoint.y < 0) {
            // 计算与x正方向的夹角
            originRadian = Math.asin(disPoint.x / distance);
            originRadian = originRadian + Math.PI * 3 / 2;
        }

        // 弧度换算成角度
        originDegree = radianToDegree(originRadian);
        resultDegree = originDegree + degree;

        // 角度转弧度
        resultRadian = degreeToRadian(resultDegree);

        resultPoint.x = (int) Math.round(distance * Math.cos(resultRadian));
        resultPoint.y = (int) Math.round(distance * Math.sin(resultRadian));
        resultPoint.x += center.x;
        resultPoint.y += center.y;

        return resultPoint;
    }

    /**
     * 弧度换算成角度
     *
     * @return
     */
    public static double radianToDegree(double radian) {
        return radian * 180 / Math.PI;
    }
    /**
     * 角度换算成弧度
     * @param degree
     * @return
     */
    public static double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    /**
     * 调整View的大小，位置
     */
    private void adjustLayout(){
        int actualWidth = mViewWidth + mDrawableWidth;
        int actualHeight = mViewHeight + mDrawableHeight;

        int newPaddingLeft = (int) (mCenterPoint.x - actualWidth /2);
        int newPaddingTop = (int) (mCenterPoint.y - actualHeight/2);

        if(mViewPaddingLeft != newPaddingLeft || mViewPaddingTop != newPaddingTop){
            mViewPaddingLeft = newPaddingLeft;
            mViewPaddingTop = newPaddingTop;

            layout(newPaddingLeft, newPaddingTop, newPaddingLeft + actualWidth, newPaddingTop + actualHeight);
        }
    }
    /**
     * 获取变长参数最大的值
     * @param array
     * @return
     */
    public int getMaxValue(Integer...array){
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(list.size() -1);
    }
    /**
     * 获取变长参数最大的值
     * @param array
     * @return
     */
    public int getMinValue(Integer...array){
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(0);
    }
    /**
     * 根据位置判断控制图标处于那个点
     * @return
     */
    private Point LocationToPoint(int location){
        switch(location){
            case LEFT_TOP:
                return mLTPoint;
            case RIGHT_TOP:
                return mRTPoint;
            case RIGHT_BOTTOM:
                return mRBPoint;
            case LEFT_BOTTOM:
                return mLBPoint;
        }
        return mLTPoint;
    }
}
