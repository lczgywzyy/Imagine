package u.can.i.up.ui.beans;

import java.io.Serializable;

/**
 * Created by MZH on 2015/8/29.
 */
public class IHttpNormalBean implements Serializable{

    private String message;

    private String data;

    private String RetCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }
}
