package u.can.i.up.utils.image;

import android.graphics.Matrix;
import android.graphics.PointF;

/**
 * Created by lczgywzyy on 2015/7/3.
 */
public class Pearl {

    private PointF center = null;
    private Matrix PointSuzhuMatrix = null;
    private int PearlType = -1;

    enum PearlTypeEnum{
        HEADER,
        NECK,
    }

    public Pearl(PointF point, Matrix matrix){
        this.center = new PointF(point.x, point.y);
        this.PointSuzhuMatrix = new Matrix(matrix);
    }

    public void setCenter(PointF point){
        this.center = point;
    }

    public PointF getCenter(){
        return this.center;
    }

    public Matrix getPointSuzhuMatrix() {
        return this.PointSuzhuMatrix;
    }

    public void setPointSuzhuMatrix(Matrix pointSuzhuMatrix) {
        this.PointSuzhuMatrix = pointSuzhuMatrix;
    }

    public int getPearlType() {
        return this.PearlType;
    }

    public void setPearlType(int pearlType) {
        this.PearlType = pearlType;
    }
}
