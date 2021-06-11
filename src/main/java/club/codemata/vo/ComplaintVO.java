package club.codemata.vo;

import club.codemata.bo.ComplaintBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ComplaintVO
 * @Description 用户投诉建议表现层对象
 * @createTime 2021/04/17 17:54:00
 */
public class ComplaintVO {
    private List<ComplaintBO> complaints;
    private String msg;
    private int code;
    private int count;

    public ComplaintVO() {
    }

    public List<ComplaintBO> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<ComplaintBO> complaints) {
        this.complaints = complaints;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    @Override
    public String toString() {
        return "ComplaintVO{" +
                "complaints=" + complaints +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", count=" + count +
                '}';
    }
}
