package club.codemata.vo;

import club.codemata.bo.LostItemComplaintBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemComplaintVO
 * @Description 失物招领举报表现层实体类
 * @createTime 2021/05/07 17:50:00
 */
public class LostItemComplaintVO {
    private List<LostItemComplaintBO> complaints;
    private int count;
    private int code;
    private String msg;

    public LostItemComplaintVO() {
    }

    public List<LostItemComplaintBO> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<LostItemComplaintBO> complaints) {
        this.complaints = complaints;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    @Override
    public String toString() {
        return "LostItemComplaintVO{" +
                "complaints=" + complaints +
                ", count=" + count +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
