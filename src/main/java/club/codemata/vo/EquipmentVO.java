package club.codemata.vo;

import club.codemata.bo.EquipmentBO;
import club.codemata.entity.Equipment;
import club.codemata.entity.Manager;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName EquipmentVO
 * @Description 设备信息的页面对象 用于前端数据展示
 * @createTime 2021/03/11 09:42:00
 */
public class EquipmentVO {
    private List<EquipmentBO> equipments;
    private int code;
    private int count;
    private String msg;

    public EquipmentVO() {
    }

    public List<EquipmentBO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentBO> equipments) {
        this.equipments = equipments;
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
        return "EquipmentVO{" +
                "equipments=" + equipments +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
