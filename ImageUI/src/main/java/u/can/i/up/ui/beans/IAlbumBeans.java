package u.can.i.up.ui.beans;

import java.util.ArrayList;

/**
 * Created by LPengp on 2015/10/5.
 */
public class IAlbumBeans {
    private String message;

    private ArrayList<AlbumBean> data;

    private String RetCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AlbumBean> getData() {
        return data;
    }

    public void setData(ArrayList<AlbumBean> data) {
        this.data = data;
    }

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }
}
