package club.codemata.vo;

import club.codemata.entity.Manager;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ManagerVO
 * @Description 管理员信息页面对象
 * @createTime 2021/03/06 15:10:00
 */
public class ManagerVO {
    private int code;
    private String msg;
    private int count;
    private List<Manager> managers;

    public ManagerVO() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    @Override
    public String toString() {
        return "ManagerVO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", managers=" + managers +
                '}';
    }
}
