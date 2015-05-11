package u.can.i.up.imagine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;

/**
 * Created by lczgywzyy on 2015/5/11.
 */
public class ImageViewImpl extends View {

    private static final String TAG = "u.can.i.up.imagine." + ImageViewImpl.class;
    private static final String FromPath = ".1FromPath";
    private static final String ToPath = ".2ToPath";

    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;
    private Bitmap mLayer;

    public ImageViewImpl(Context context) {
        super(context);
        mBitmap = BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory(), ToPath + "/1.png").getAbsolutePath());
        mLayer = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.mCanvas = canvas;
//        this.paint.setColor(Color.GREEN);
        canvas.drawBitmap(mBitmap, 0, 0, null);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        mPaint.setStyle(Paint.Style.STROKE);   //空心
        mPaint.setAlpha(45);   //
        canvas.drawBitmap(mLayer, 0, 0, mPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                p1.x = (int) x;
//                p1.y = (int) y;
//                final int sourceColor = mBitmap.getPixel((int) x, (int) y);
//                final int targetColor = paint.getColor();
//                new TheTask(mBitmap, p1, sourceColor, targetColor).execute();
//                invalidate();
//        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getX();
                int newY = (int) event.getY();
                for (int i = -30; i < 30; i++) {
                    for (int j = -30; j < 30; j++) {
                        if ((i + newX) >= mBitmap.getWidth() || j + newY >= mBitmap.getHeight() || i + newX < 0 || j + newY < 0) {
                            return false;
                        }
                        mLayer.setPixel(i + newX, j + newY, Color.RED);
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void clear() {
//        path.reset();
//        invalidate();
    }

//    public int getCurrentPaintColor() {
//        return paint.getColor();
//    }
}
