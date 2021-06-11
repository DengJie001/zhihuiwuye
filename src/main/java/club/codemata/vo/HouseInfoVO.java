package club.codemata.vo;

import club.codemata.bo.HouseInfoBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName HouseInfoVO
 * @Description 房产信息表现层实体类
 * @createTime 2021/05/05 21:05:00
 */
public class HouseInfoVO {
    private List<HouseInfoBO> houseInfos;
    private int code;
    private int count;
    private String msg;

    public HouseInfoVO() {
    }

    public List<HouseInfoBO> getHouseInfos() {
        return houseInfos;
    }

    public void setHouseInfos(List<HouseInfoBO> houseInfos) {
        this.houseInfos = houseInfos;
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
        return "HouseInfoVO{" +
                "houseInfos=" + houseInfos +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
