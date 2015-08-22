package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;

/**
 * Created by lczgywzyy on 2015/7/3.
 */
public class Pearl {

    private PointF center = null;
    private Matrix mMatrix = null;
    private float mRadius = -1;
    private int PearlType = -1;
    private String IconPath = null;
    private Bitmap mBitmap = null;

    enum PearlTypeEnum{
        HEADER,
        NECK,
    }

    public Pearl(Bitmap bmp, Matrix matrix){
        this.mBitmap = bmp;
        this.mMatrix = new Matrix(matrix);
    }

    public Pearl(Matrix matrix){
        this.mMatrix = new Matrix(matrix);
    }

    public Pearl(PointF point, Matrix matrix){
        this.center = new PointF(point.x, point.y);
        this.mMatrix = new Matrix(matrix);
    }
    //    public Pearl(Bitmap bm, PointF point, Matrix matrix){
//        this.mBitmap = bm;
//        this.center = new PointF(point.x, point.y);
//        this.mMatrix = new Matrix(matrix);
//    }
    public Pearl(Bitmap bm, PointF point, Matrix matrix, float radius){
        this.mBitmap = bm;
        this.center = new PointF(point.x, point.y);
        this.mMatrix = new Matrix(matrix);
        this.mRadius = radius;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }


    public void setCenter(PointF point){
        this.center = point;
    }

    public PointF getCenter(){
        return this.center;
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public void setMatrix(Matrix pointSuzhuMatrix) {
        this.mMatrix = new Matrix(pointSuzhuMatrix);
    }

    public int getPearlType() {
        return this.PearlType;
    }

    public void setPearlType(int pearlType) {
        this.PearlType = pearlType;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
    }
}