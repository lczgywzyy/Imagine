package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lczgywzyy on 2015/5/11.
 */
public class ImageAlgrithms {

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
    //判断点是否在矩形内
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
        return ret;
    }
}
