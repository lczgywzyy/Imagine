package u.can.i.up.ui.beans;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Pengp on 2015/8/3.
 */

public class HttpStatus <T>{


        private int httpStatus;

        private String httpMsg;

        private List<T> httpObj;

        private Bitmap bitmap;

        public String getRectCode() {
        return rectCode;
        }

        public void setRectCode(String rectCode) {
        this.rectCode = rectCode;
        }

        private String rectCode;
        public int getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(int httpStatus) {
            this.httpStatus = httpStatus;
        }

        public String getHttpMsg() {
            return httpMsg;
        }

        public void setHttpMsg(String httpMsg) {
            this.httpMsg = httpMsg;
        }

        public List<T> getHttpObj() {
            return httpObj;
        }

        public void setHttpObj(List<T> httpObj) {
            this.httpObj = httpObj;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
             this.bitmap = bitmap;
         }
}
