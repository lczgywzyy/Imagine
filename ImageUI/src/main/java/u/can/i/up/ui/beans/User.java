package u.can.i.up.ui.beans;

/**
 * Created by Pengp on 2015/7/31.
 */
public class User {
    private String userName;

    private String userLoginToken;

    private String phoneNumber;

    private String userEmail;

    private String userType;

    private String portrait;

    private String tString;

    private String eString;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String gettString() {
        return tString;
    }

    public void settString(String tString) {
        this.tString = tString;
    }

    public String geteString() {
        return eString;
    }

    public void seteString(String eString) {
        this.eString = eString;
    }

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
