package u.can.i.up.ui.beans;

/**
 * Created by MZH on 2015/8/25.
 */
public class LoginBean {
    private String RetCode;

    private User UserInfo;

    private String message;

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }

    public User getUserInfo() {
        return UserInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserInfo(User userInfo) {
        UserInfo = userInfo;
    }
}
