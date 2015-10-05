package u.can.i.up.ui.beans;

/**
 * Created by LPengp on 2015/10/5.
 */
public class AlbumBean {

    private int AlbumId;

    private String MD5;

    private String path;

    private int type;

    private boolean isSynchronizd;

    public boolean isSynchronizd() {
        return isSynchronizd;
    }

    public void setIsSynchronizd(boolean isSynchronizd) {
        this.isSynchronizd = isSynchronizd;
    }

    public String getMD5() {

        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAlbumId() {
        return AlbumId;
    }

    public void setAlbumId(int albumId) {
        AlbumId = albumId;
    }
}
