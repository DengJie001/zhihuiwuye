package club.codemata.vo;

import club.codemata.entity.Worker;

import java.util.List;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName WorkerVO
 * @Description 维修人员的表现层实体对象
 * @createTime 2021/04/15 21:57:00
 */
public class WorkerVO {
    private List<Worker> workers;
    private int code;
    private int count;
    private String msg;

    public WorkerVO() {
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
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
        return "WorkerVO{" +
                "workers=" + workers +
                ", code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
