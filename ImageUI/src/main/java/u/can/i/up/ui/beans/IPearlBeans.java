package u.can.i.up.ui.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LPengp on 2015/9/12.
 */
public class IPearlBeans implements Serializable {
    private String RetCode;

    private ArrayList<PearlBeans> data;

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

    public ArrayList<PearlBeans> getData() {
        return data;
    }

    public void setData(ArrayList<PearlBeans> data) {
        this.data = data;
    }
}
