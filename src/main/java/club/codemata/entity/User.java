package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName User
 * @Description 用户基本信息实体类
 * @createTime 2021/03/03 20:32:00
 */
public class User {
    private String userId;  // 用户微信小程序端唯一id---对应微信小程序中的openid
    private String userTel; // 用户电话号码
    private String userName;    // 用户姓名
    private String gender;  // 用户性别
    private String nationality; // 用户国籍
    private String province;    // 用户所在省份
    private String city;    // 用户所在城市

    public User() {
    }

    public User(String userId, String userTel, String userName, String gender, String nationality, String province, String city) {
        this.userId = userId;
        this.userTel = userTel;
        this.userName = userName;
        this.gender = gender;
        this.nationality = nationality;
        this.province = province;
        this.city = city;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
