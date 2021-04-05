package club.codemata.entity;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName Manager
 * @Description 管理员基本信息实体类
 * @createTime 2021/03/03 20:36:00
 */
public class Manager {
    private String managerId;   // 管理员id，与注册时的电话号码相同，且不可以更改
    private String managerTel;  // 管理员电话号码，可以修改
    private String managerName; // 管理员姓名
    private String managerAvatar;   // 管理员本人头像(必须是真人)
    private String password;    // 登录密码

    public Manager() {
    }

    public Manager(String managerId, String managerTel, String managerName, String managerAvatar, String password) {
        this.managerId = managerId;
        this.managerTel = managerTel;
        this.managerName = managerName;
        this.managerAvatar = managerAvatar;
        this.password = password;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerAvatar() {
        return managerAvatar;
    }

    public void setManagerAvatar(String managerAvatar) {
        this.managerAvatar = managerAvatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId='" + managerId + '\'' +
                ", managerTel='" + managerTel + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerAvatar='" + managerAvatar + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
