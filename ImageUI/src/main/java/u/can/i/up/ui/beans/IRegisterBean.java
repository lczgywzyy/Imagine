package u.can.i.up.ui.beans;

import java.io.Serializable;

/**
 * Created by MZH on 2015/8/25.
 */
public class IRegisterBean implements Serializable{

    private String RetCode;

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
