package u.can.i.up.utils.image;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;

/**
 * Created by lczgywzyy on 2015/5/17.
 */
public class FloodFillAsyncTask extends AsyncTask<Integer, Integer, String> {
    Bitmap mImage;
    Point mNode;
    int mTargetColor;
    int mReplacementColor;
    public FloodFillAsyncTask(Bitmap image, Point node, int targetColor, int replacementColor){
        mImage = image;
        mNode = node;
        mTargetColor = targetColor;
        mReplacementColor = replacementColor;
    }
    @Override
    protected String doInBackground(Integer... integers) {
        ImageAlgrithms.FloodFill(mImage, mNode, mTargetColor, mReplacementColor);
        return null;
    }
}
