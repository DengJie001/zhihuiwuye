package club.codemata.vo;

import club.codemata.bo.UtilityBillBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName UtilityBillVO
 * @Description 物业账单表现层对象
 * @createTime 2021/04/26 03:47:00
 */
public class UtilityBillVO {
    private List<UtilityBillBO> bills;
    private int code;
    private int count;
    private String msg;

    public UtilityBillVO() {
    }

    public List<UtilityBillBO> getBills() {
        return bills;
    }

    public void setBills(List<UtilityBillBO> bills) {
        this.bills = bills;
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
        return "UtilityBillVO{" +
                "bills=" + bills +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
