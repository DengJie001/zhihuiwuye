package club.codemata.vo;

import club.codemata.bo.RepairInfoBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName RepairInfoVO
 * @Description 物业维修记录表现层对象
 * @createTime 2021/04/22 13:32:00
 */
public class RepairInfoVO {
    private List<RepairInfoBO> repairInfos;
    private int code;
    private int count;
    private String msg;

    public RepairInfoVO() {
    }

    public List<RepairInfoBO> getRepairInfos() {
        return repairInfos;
    }

    public void setRepairInfos(List<RepairInfoBO> repairInfos) {
        this.repairInfos = repairInfos;
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
        return "RepairInfoVO{" +
                "repairInfos=" + repairInfos +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
