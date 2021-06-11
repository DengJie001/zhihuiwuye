package club.codemata.vo;

import club.codemata.entity.Visitor;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName VisitorVO
 * @Description TODO
 * @createTime 2021/05/08 17:55:00
 */
public class VisitorVO {
    private List<Visitor> visitors;
    private int code;
    private int count;
    private String msg;

    public VisitorVO() {
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "VisitorVO{" +
                "visitors=" + visitors +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
