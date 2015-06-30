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
import android.graphics.RectF;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;

/**
 * Created by lczgywzyy on 2015/5/31.
 */
public class ImageViewImpl_13 extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl_13.class;
    private static final String FromPath = ".1FromPath/ImageView13";
    private static final String ToPath = ".2ToPath/ImageView13";

    Context context = null;

    Bitmap bmpSuzhu = null;
    Bitmap bmpBack = null;

    Paint paint = null;
    PaintFlagsDrawFilter paintFilter = null;

    public ImageViewImpl_13(Context context) {
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
        bmpSuzhu = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/suzhu.png").getAbsolutePath());
        bmpBack = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/bg.png").getAbsolutePath());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.setDrawFilter(paintFilter);

        /* 绘制背景
        * */
        canvas.drawBitmap(bmpBack, 0, 0, paint);
        canvas.drawBitmap(bmpSuzhu, 100, 100, null);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return true;
    }
}
