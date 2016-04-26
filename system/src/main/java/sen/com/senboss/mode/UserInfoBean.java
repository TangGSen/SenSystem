package sen.com.senboss.mode;

/**
 * Created by Administrator on 2016/4/26.
 */
public class UserInfoBean {
    private String userName;
    private String userPhone;
    private String proviceName ;
    private String areaName ;
    private String cityName ;
    private String date;

    public UserInfoBean(String userName, String userPhone, String proviceName, String areaName, String cityName, String date) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.proviceName = proviceName;
        this.areaName = areaName;
        this.cityName = cityName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", proviceName='" + proviceName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
