package u.can.i.up.ui.beans;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Pengp on 2015/8/3.
 */

public class IHttpStatus<T>{


        private int httpStatus;


        private T httpObj;

        private Bitmap bitmap;

        public int getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(int httpStatus) {
            this.httpStatus = httpStatus;
        }


        public T getHttpObj() {
            return httpObj;
        }

        public void setHttpObj(T httpObj) {
            this.httpObj = httpObj;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
             this.bitmap = bitmap;
         }
}
