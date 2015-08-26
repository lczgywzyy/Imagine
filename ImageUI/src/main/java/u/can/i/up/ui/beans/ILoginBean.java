package u.can.i.up.ui.beans;

import java.io.Serializable;

/**
 * Created by MZH on 2015/8/25.
 */
public class ILoginBean implements Serializable{
    private String RetCode;

    private User data;

    private String message;

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
