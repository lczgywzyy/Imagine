package u.can.i.up.ui.beans;

/**
 * Created by Pengp on 2015/7/31.
 */
public class User {
    private String userName;

    private String userLoginToken;

    private String phoneNumber;

    private String userEmail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
