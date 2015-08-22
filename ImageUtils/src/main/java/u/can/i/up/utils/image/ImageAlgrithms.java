package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by lczgywzyy on 2015/5/11.
 */
public class ImageAlgrithms {

    private static final String TAG = "u.can.i.up.imagine." + ImageAlgrithms.class;


    /** @author by http://www.28im.com/android/a203290.html, Edited by lcz.
     *  @param image 位图
     *  @param node 点
     *  @param replacementColor
     *  @param targetColor
     * */
    public static void FloodFill(Bitmap image, Point node, int targetColor, int replacementColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor;
        int replacement = replacementColor;
        if (target != replacement) {
            Queue<Point> queue = new LinkedList<Point>();
            do {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getPixel(x - 1, y) == target) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getPixel(x, y) == target) {
                    image.setPixel(x, y, replacement);
                    if (!spanUp && y > 0
                            && image.getPixel(x, y - 1) == target) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0
                            && image.getPixel(x, y - 1) != target) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1
                            && image.getPixel(x, y + 1) == target) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1
                            && image.getPixel(x, y + 1) != target) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.poll()) != null);
        }
    }
    /** @author lcz.
     *  @param x
     *  @param y
     *  @param rect
     *  @since   判断点是否在矩形内
     * */
    public static boolean isInRect(float x, float y, RectF rect){
        boolean ret = false;
        if(rect != null && x > rect.left && x < rect.right && y > rect.top && y < rect.bottom){
            ret = true;
        }
        return ret;
    }
    //根据矩形获取中心点
    public static void getRectCenter(RectF rect, PointF p){
        p.x = rect.left + (rect.right - rect.left) / 2;
        p.y = rect.top + (rect.bottom - rect.top) / 2;
    }
    //求两点间距离
    public static float getPointsDistance(PointF p1, PointF p2) {
        float ret = (float) Math.sqrt(Math.abs((p1.x - p2.x) * (p1.x - p2.x)
                + (p1.y - p2.y) * (p1.y - p2.y)));
        return ret;
    }
    //求角ABC
    public static float getPointsDegree(PointF a, PointF b, PointF c){
        if(Math.abs(a.x - c.x) < 2 && Math.abs(a.y - c.y) < 2){
            return 0.0f;
        }
        float ret = (float) (  Math.toDegrees(Math.atan2(c.y - b.y, c.x - b.x)
                - Math.atan2(a.y - b.y, a.x - b.x)));
//        if(ret >= 180){
//            ret = 360 - ret;
//        }else if(ret <= -180){
//            ret = 360 + ret;
//        }
        if(ret < 0){
            ret = 360 + ret;
        }
        return ret;
    }
    /** @author lcz.
     *  @param pearlList
     *  @param rectMotion
     *  @return 如果重叠，返回重叠的最小值。
     *  @since  得到指定珠子是否重叠
     * */
    public static int isOverlayed(List<Pearl> pearlList, RectF rectMotion, PointF mBgCenterPoint){
        if(pearlList == null || pearlList.isEmpty()){
            return -1;
        }
        if(rectMotion == null){
            return -1;
        }
        PointF targetPoint = new PointF((rectMotion.left + rectMotion.right) / 2, (rectMotion.top + rectMotion.bottom) / 2);
        float targetRadius = rectMotion.width() / 2;
        //最小值的key
        int minKey1 = -1;
        float minRadius1 = 100000;

        for (int i = 0; i < pearlList.size(); i++){
            Pearl p = pearlList.get(i);
            PointF tmpCenter = p.getCenter();
            float tmpRadius = p.getBitmap().getWidth() / 2;
            float tmpDistance = getPointsDistance(targetPoint, tmpCenter);
            if(tmpDistance < (targetRadius + tmpRadius)){
                if(tmpDistance < minRadius1){
                    minRadius1 = tmpDistance;
                    minKey1 = i;
                }
            }
        }
        int index = -1;
        if(minKey1 >= 0){
            float angle1 = ImageAlgrithms.getPointsDegree(targetPoint, mBgCenterPoint, pearlList.get(minKey1 % pearlList.size()).getCenter());
            float angle2 = ImageAlgrithms.getPointsDegree(targetPoint, mBgCenterPoint, pearlList.get((minKey1 + 1) % pearlList.size()).getCenter());
            Log.d(TAG, "angle1:" + angle1);
            Log.d(TAG, "angle2:" + angle2);
            if(angle1 * angle2 <= 0){
                Log.d(TAG, "BETWEEN!");
                index = (minKey1 + 1) % pearlList.size();
            } else{
                Log.d(TAG, "BEFORE!");
                index = minKey1 % pearlList.size();
            }
            Log.d(TAG, "插入点:" + index);
        }
        return index;
    }
}