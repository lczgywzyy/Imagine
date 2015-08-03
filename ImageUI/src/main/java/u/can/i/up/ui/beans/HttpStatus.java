package u.can.i.up.ui.beans;

/**
 * Created by Pengp on 2015/8/3.
 */
public class HttpStatus <T>{


        private int httpStatus;

        private String httpMsg;

        private T httpObj;

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

        public T getHttpObj() {
            return httpObj;
        }

        public void setHttpObj(T httpObj) {
            this.httpObj = httpObj;
        }
    }
