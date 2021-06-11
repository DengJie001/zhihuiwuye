package club.codemata.vo;

import club.codemata.bo.PlaceApplicationBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName PlaceApplicationVO
 * @Description 场地申请记录表现层对象
 * @createTime 2021/04/14 10:28:00
 */
public class PlaceApplicationVO {
    private List<PlaceApplicationBO> placeApplications;
    private String msg;
    private int count;
    private int code;

    public PlaceApplicationVO() {
    }

    public List<PlaceApplicationBO> getPlaceApplications() {
        return placeApplications;
    }

    public void setPlaceApplications(List<PlaceApplicationBO> placeApplications) {
        this.placeApplications = placeApplications;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PlaceApplicationVO{" +
                "placeApplications=" + placeApplications +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", code=" + code +
                '}';
    }
}
