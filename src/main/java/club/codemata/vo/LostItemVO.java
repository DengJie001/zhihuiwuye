package club.codemata.vo;

import club.codemata.bo.LostItemBO;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName LostItemVO
 * @Description TODO
 * @createTime 2021/04/30 15:56:00
 */
public class LostItemVO {
    private List<LostItemBO> items;
    private int code;
    private int count;
    private String msg;

    public LostItemVO() {
    }

    public List<LostItemBO> getItems() {
        return items;
    }

    public void setItems(List<LostItemBO> items) {
        this.items = items;
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
        return "LostItemVO{" +
                "items=" + items +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
