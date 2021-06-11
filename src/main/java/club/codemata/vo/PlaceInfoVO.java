package club.codemata.vo;

import club.codemata.bo.PlaceInfoBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceInfoVO
 * @Description 场地信息表现层对象
 * @createTime 2021/04/06 15:44:00
 */
public class PlaceInfoVO {
    private List<PlaceInfoBO> placeInfos;
    private int count;
    private int code;
    private String msg;

    public PlaceInfoVO() {
    }

    public List<PlaceInfoBO> getPlaceInfos() {
        return placeInfos;
    }

    public void setPlaceInfos(List<PlaceInfoBO> placeInfos) {
        this.placeInfos = placeInfos;
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
        return "PlaceInfoVO{" +
                "placeInfos=" + placeInfos +
                ", count=" + count +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
